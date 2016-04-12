package heero.mc.mod.wakcraft.fight;

import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.entity.creature.IFighter;
import heero.mc.mod.wakcraft.entity.property.FightCharacteristicsProperty;
import heero.mc.mod.wakcraft.entity.property.FightProperty;
import heero.mc.mod.wakcraft.entity.property.SpellsProperty;
import heero.mc.mod.wakcraft.fight.FightInfo.FightStage;
import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FightEventsHandler {
    /**
     * Handler called when an entity is created.
     *
     * @param event Event object.
     */
    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        if (!(event.entity instanceof EntityLivingBase)) {
            return;
        }

        if (FightUtil.isFighter(event.entity)) {
            event.entity.registerExtendedProperties(FightProperty.IDENTIFIER, new FightProperty());
            event.entity.registerExtendedProperties(FightCharacteristicsProperty.IDENTIFIER, new FightCharacteristicsProperty());
            event.entity.registerExtendedProperties(SpellsProperty.IDENTIFIER, new SpellsProperty());
        }
    }

    /**
     * Handler called when a player attack an entity. Test if the player and the
     * entity are not already in a fight, and initialize a new fight.
     *
     * @param event Event object.
     */
    @SubscribeEvent
    public void onAttackEntityEvent(AttackEntityEvent event) {
        World world = event.entityPlayer.worldObj;

        if (!WConfig.isWakfuFightEnable()) {
            return;
        }

        if (world.isRemote) {
            return;
        }

        if (!FightUtil.isFighter(event.entityPlayer)) {
            return;
        }

        if (!FightUtil.isFighter(event.target) || !(event.target instanceof EntityLivingBase) || !(event.target instanceof IFighter)) {
            return;
        }

        EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;
        EntityLivingBase target = (EntityLivingBase) event.target;
        IFighter targetFighter = (IFighter) event.target;
        if (!target.isEntityAlive()) {
            event.setCanceled(true);
            return;
        }

        if (!FightUtil.isFighting(player) && !FightUtil.isFighting(target)) {
            FightManager.INSTANCE.startServerFight(world, player, target);

            event.setCanceled(true);
            return;
        }

        int fightId = FightUtil.getFightId(player);
        if (fightId != FightUtil.getFightId(target)) {
            event.setCanceled(true);
            return;
        }

        if (FightUtil.getFightStage(world, fightId) != FightStage.FIGHT) {
            event.setCanceled(true);
            return;
        }

        if (FightUtil.getCurrentFighter(world, fightId) != player) {
            event.setCanceled(true);
            return;
        }

        BlockPos playerPosition = FightUtil.getCurrentPosition(player);
        BlockPos targetPosition = FightUtil.getCurrentPosition(target);
        if (MathHelper.abs(playerPosition.getX() - targetPosition.getX()) + MathHelper.abs(playerPosition.getZ() - targetPosition.getZ()) > 1) {
            event.setCanceled(true);
            return;
        }

        targetFighter.onAttacked(player, FightUtil.getCurrentSpell(player));
        event.setCanceled(true);
    }

    /**
     * Handler called when an Entity die.
     *
     * @param event The Event object.
     */
    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
        if (event.entityLiving.worldObj.isRemote) {
            return;
        }

        if (!FightUtil.isFighter(event.entityLiving)) {
            return;
        }

        if (!FightUtil.isFighting(event.entityLiving)) {
            return;
        }

        int fightId = FightUtil.getFightId(event.entityLiving);

        int defeatedTeam = FightManager.INSTANCE.getDefeatedTeam(event.entityLiving.worldObj, fightId);
        if (defeatedTeam <= 0) {
            return;
        }

        FightManager.INSTANCE.stopFight(event.entityLiving.worldObj, fightId);
    }

    /**
     * Handler called when a Player log out of the world (only in multiplayer
     * mode)
     *
     * @param event The Event object.
     */
    @SubscribeEvent
    public void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.player.worldObj.isRemote) {
            return;
        }

        if (!FightUtil.isFighter(event.player)) {
            return;
        }

        if (!FightUtil.isFighting(event.player)) {
            return;
        }

        event.player.setDead();

        int fightId = FightUtil.getFightId(event.player);

        int defeatedTeam = FightManager.INSTANCE.getDefeatedTeam(event.player.worldObj, fightId);
        if (defeatedTeam <= 0) {
            return;
        }

        FightManager.INSTANCE.stopFight(event.player.worldObj, fightId);
    }

    @SubscribeEvent
    public void onServerTickEvent(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            FightManager.INSTANCE.updateFights(MinecraftServer.getServer().getTickCounter());
        }
    }

    @SubscribeEvent
    public void onLivingUpdateEvent(LivingUpdateEvent event) {
        EntityLivingBase entity = event.entityLiving;

        if (!FightUtil.isFighter(entity) || !FightUtil.isFighting(entity)) {
            return;
        }

        int fightId = FightUtil.getFightId(entity);
        if (FightUtil.getFightStage(entity.worldObj, fightId) != FightStage.FIGHT) {
            return;
        }

        EntityLivingBase currentFighter = FightUtil.getCurrentFighter(entity.worldObj, fightId);
        if (currentFighter != entity) {
            return;
        }

        FightManager.INSTANCE.updateFights(entity);
    }
}
