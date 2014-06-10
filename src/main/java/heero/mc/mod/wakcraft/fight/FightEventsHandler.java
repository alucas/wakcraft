package heero.mc.mod.wakcraft.fight;

import heero.mc.mod.wakcraft.entity.property.FightProperty;
import heero.mc.mod.wakcraft.helper.FightHelper;
import heero.mc.mod.wakcraft.manager.FightManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

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

		if (world.isRemote) {
			return;
		}

		if (world.provider.dimensionId != 0) {
			return;
		}

		if (!FightHelper.isFighter(event.entityPlayer)) {
			return;
		}

		if (!FightHelper.isFighter(event.target) || !(event.target instanceof EntityLivingBase)) {
			return;
		}

		EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;
		EntityLivingBase target = (EntityLivingBase) event.target;
		if (!target.isEntityAlive()) {
			return;
		}

		if (!FightHelper.isFighting(player) && !FightHelper.isFighting(target)) {
			FightManager.startFight(world, player, target);

			event.setCanceled(true);
			return;
		}

		if (FightHelper.getFightId(player) != FightHelper.getFightId(target)) {
			event.setCanceled(true);
			return;
		}
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

		if (event.entityLiving.worldObj.provider.dimensionId != 0) {
			return;
		}

		if (!FightHelper.isFighter(event.entityLiving)) {
			return;
		}

		if (!FightHelper.isFighting(event.entityLiving)) {
			return;
		}

		int fightId = FightHelper.getFightId(event.entityLiving);

		int defeatedTeam = FightManager.getDefeatedTeam(event.entityLiving.worldObj, fightId);
		if (defeatedTeam <= 0) {
			return;
		}

		FightManager.stopFight(event.entityLiving.worldObj, fightId);
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

		if (event.player.worldObj.provider.dimensionId != 0) {
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

		int defeatedTeam = FightManager.getDefeatedTeam(event.player.worldObj, fightId);
		if (defeatedTeam <= 0) {
			return;
		}

		FightManager.stopFight(event.player.worldObj, fightId);
	}
}
