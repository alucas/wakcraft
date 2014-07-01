package heero.mc.mod.wakcraft.fight;

import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.entity.creature.IFighter;
import heero.mc.mod.wakcraft.entity.property.FightCharacteristicsProperty;
import heero.mc.mod.wakcraft.entity.property.FightProperty;
import heero.mc.mod.wakcraft.entity.property.SpellsProperty;
import heero.mc.mod.wakcraft.fight.FightInfo.FightStage;
import heero.mc.mod.wakcraft.helper.FightHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class FightEventsHandler {
	/**
	 * Handler called when an entity is created.
	 * 
	 * @param event	Event object.
	 */
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (!(event.entity instanceof EntityLivingBase)) {
			return;
		}

		if (FightHelper.isFighter((EntityLivingBase) event.entity)) {
			event.entity.registerExtendedProperties(FightProperty.IDENTIFIER, new FightProperty());
			event.entity.registerExtendedProperties(FightCharacteristicsProperty.IDENTIFIER, new FightCharacteristicsProperty());
			event.entity.registerExtendedProperties(SpellsProperty.IDENTIFIER, new SpellsProperty());
		}
	}

	/**
	 * Handler called when a player attack an entity. Test if the player and the
	 * entity are not already in a fight, and initialize a new fight.
	 * 
	 * @param event	Event object.
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

		if (!FightHelper.isFighter(event.entityPlayer)) {
			return;
		}

		if (!FightHelper.isFighter(event.target) || !(event.target instanceof EntityLivingBase) || !(event.target instanceof IFighter)) {
			return;
		}

		EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;
		EntityLivingBase target = (EntityLivingBase) event.target;
		IFighter targetFighter = (IFighter) event.target;
		if (!target.isEntityAlive()) {
			event.setCanceled(true);
			return;
		}

		if (!FightHelper.isFighting(player) && !FightHelper.isFighting(target)) {
			FightManager.INSTANCE.startServerFight(world, player, target);

			event.setCanceled(true);
			return;
		}

		int fightId = FightHelper.getFightId(player);
		if (fightId != FightHelper.getFightId(target)) {
			event.setCanceled(true);
			return;
		}

		if (FightManager.INSTANCE.getFightStage(world, fightId) != FightStage.FIGHT) {
			event.setCanceled(true);
			return;
		}

		if (FightManager.INSTANCE.getCurrentFighter(world, fightId) != player) {
			event.setCanceled(true);
			return;
		}

		ChunkCoordinates playerPosition = FightHelper.getCurrentPosition(player);
		ChunkCoordinates targetPosition = FightHelper.getCurrentPosition(target);
		if (MathHelper.abs(playerPosition.posX - targetPosition.posX) + MathHelper.abs(playerPosition.posZ - targetPosition.posZ) > 1) {
			event.setCanceled(true);
			return;
		}

		targetFighter.onAttacked(player, FightHelper.getCurrentSpell(player));
		event.setCanceled(true);
	}

	/**
	 * Handler called when an Entity die.
	 * 
	 * @param event	The Event object.
	 */
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event) {
		if (event.entityLiving.worldObj.isRemote) {
			return;
		}

		if (!FightHelper.isFighter(event.entityLiving)) {
			return;
		}

		if (!FightHelper.isFighting(event.entityLiving)) {
			return;
		}

		int fightId = FightHelper.getFightId(event.entityLiving);

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
	 * @param event	The Event object.
	 */
	@SubscribeEvent
	public void onPlayerLoggedOutEvent(PlayerLoggedOutEvent event) {
		if (event.player.worldObj.isRemote) {
			return;
		}

		if (!FightHelper.isFighter(event.player)) {
			return;
		}

		if (!FightHelper.isFighting(event.player)) {
			return;
		}

		event.player.setDead();

		int fightId = FightHelper.getFightId(event.player);

		int defeatedTeam = FightManager.INSTANCE.getDefeatedTeam(event.player.worldObj, fightId);
		if (defeatedTeam <= 0) {
			return;
		}

		FightManager.INSTANCE.stopFight(event.player.worldObj, fightId);
	}

	@SubscribeEvent
	public void onServerTickEvent(ServerTickEvent event) {
		if (event.phase == Phase.END) {
			FightManager.INSTANCE.updateFights(MinecraftServer.getServer().getTickCounter());
		}
	}

	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;

		if (!FightHelper.isFighter(entity) || !FightHelper.isFighting(entity)) {
			return;
		}

		int fightId = FightHelper.getFightId(entity);
		if (FightManager.INSTANCE.getFightStage(entity.worldObj, fightId) != FightStage.FIGHT) {
			return;
		}

		EntityLivingBase currentFighter = FightManager.INSTANCE.getCurrentFighter(entity.worldObj, fightId);
		if (currentFighter != entity) {
			return;
		}

		FightManager.INSTANCE.updateFights(entity);
	}
}
