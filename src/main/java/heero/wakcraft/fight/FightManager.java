package heero.wakcraft.fight;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.entity.property.FightProperty;
import heero.wakcraft.event.FightEvent;
import heero.wakcraft.event.FightEvent.Type;
import heero.wakcraft.network.packet.PacketFight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FightManager {
	protected Map<Integer, List<List<Integer>>> fights = new HashMap<Integer, List<List<Integer>>>();

	@SubscribeEvent
	public void onAttackEntityEvent(AttackEntityEvent event) {
		if (event.entityPlayer.worldObj.isRemote) {
			event.setCanceled(true);
			return;
		}

		FightProperty properties = (FightProperty) event.entityPlayer.getExtendedProperties(FightProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading the Fight properties of player : %s", event.entityPlayer.getDisplayName());
			event.setCanceled(true);
			return;
		}

		FightProperty targetProperties = (FightProperty) event.target.getExtendedProperties(FightProperty.IDENTIFIER);
		if (targetProperties == null) {
			FMLLog.warning("Error while loading the Fight properties of : %s", event.target.getClass().getName());
			event.setCanceled(true);
			return;
		}

		if (!properties.isFighting() && !targetProperties.isFighting()) {
			if (event.entityPlayer instanceof EntityPlayerMP && event.target instanceof EntityLivingBase) {
				InitFight((EntityPlayerMP) event.entityPlayer, (EntityLivingBase) event.target);
			} else {
				FMLLog.warning("Trying to create a fight with an inanimate entity : %s", event.target.getClass().getName());
			}

			event.setCanceled(true);
			return;
		}

		if (properties.getFightId() != targetProperties.getFightId()) {
			event.setCanceled(true);
			return;
		}
	}

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event) {
		if (event.entityLiving.worldObj.isRemote) {
			return;
		}

		FightProperty properties = (FightProperty) event.entityLiving.getExtendedProperties(FightProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading the Fight properties of player : %s", event.entityLiving.getClass().getName());
			return;
		}

		if (!properties.isFighting()) {
			return;
		}

		int fightId = properties.getFightId();
		properties.resetFightId();

		updateFight(event.entity.worldObj, fightId);
	}

	protected void InitFight(EntityPlayerMP assailant, EntityLivingBase target) {
		FightProperty assailantProperties = (FightProperty) assailant.getExtendedProperties(FightProperty.IDENTIFIER);
		if (assailantProperties == null) {
			FMLLog.warning("Error while loading the Fight properties of player : %s", assailant.getDisplayName());
			return;
		}

		FightProperty targetProperties = (FightProperty) target.getExtendedProperties(FightProperty.IDENTIFIER);
		if (targetProperties == null) {
			FMLLog.warning("Error while loading the Fight properties of : %s", target.getClass().getName());
			return;
		}

		int fightId = assailant.worldObj.getUniqueDataId("fightId");

		ArrayList<Integer> fighters1 = new ArrayList<Integer>();
		ArrayList<Integer> fighters2 = new ArrayList<Integer>();
		fighters1.add(assailant.getEntityId());
		fighters2.add(target.getEntityId());

		ArrayList<List<Integer>> fighters = new ArrayList<List<Integer>>();
		fighters.add(fighters1);
		fighters.add(fighters2);

		MinecraftForge.EVENT_BUS.post(new FightEvent(assailant.worldObj, Type.START, fightId, fighters));
		Wakcraft.packetPipeline.sendTo(new PacketFight(Type.START, fightId, fighters), assailant);

		if (target instanceof EntityPlayerMP) {
			Wakcraft.packetPipeline.sendTo(new PacketFight(Type.START, fightId, fighters), (EntityPlayerMP) target);
		}
	}

	protected void updateFight(World world, int fightId) {
		List<List<Integer>> fight = fights.get(fightId);
		if (fight == null) {
			FMLLog.warning("Trying to update a fight who does not exist : %d", fightId);
			return;
		}

		for (int teamId = 0; teamId < 2; teamId++) {
			List<Integer> team = fight.get(teamId);

			boolean living = false;
			for (Integer entityId : team) {
				Entity entity = world.getEntityByID(entityId);
				if (entity == null || !(entity instanceof EntityLivingBase)) {
					FMLLog.warning("Wrond fighting entity id");
					return;
				}

				FightProperty entityProperties = (FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER);
				if (entityProperties == null) {
					FMLLog.warning("Error while loading the Fight properties of player : %s", entity.getClass().getName());
					return;
				}

				if (entityProperties.getFightId() != fightId) {
					continue;
				}

				living = true;
			}

			if (!living) {
				stopFight(world, fightId);
				return;
			}
		}
	}

	protected void stopFight(World world, int fightId) {
		List<List<Integer>> fight = fights.get(fightId);
		if (fight == null) {
			FMLLog.warning("Trying to stop a fight who does not exist : %d", fightId);
			return;
		}

		MinecraftForge.EVENT_BUS.post(new FightEvent(world, Type.STOP, fightId, fight));

		for (int teamId = 0; teamId < 2; teamId++) {
			List<Integer> team = fight.get(teamId);

			for (Integer entityId : team) {
				Entity entity = world.getEntityByID(entityId);
				if (entity == null || !(entity instanceof EntityLivingBase)) {
					FMLLog.warning("Wrond fighting entity id");
					return;
				}

				if (entity instanceof EntityPlayerMP) {
					Wakcraft.packetPipeline.sendTo(new PacketFight(Type.STOP, fightId, fight), (EntityPlayerMP) entity);
				}
			}
		}
	}

	@SubscribeEvent
	public void onFightEvent(FightEvent event) {
		switch (event.type) {
		case START:
			Iterator<List<Integer>> teams = event.fighters.iterator();
			while (teams.hasNext()) {
				Iterator<Integer> entities = teams.next().iterator();
				while (entities.hasNext()) {
					Entity entity = event.world.getEntityByID(entities.next());
					if (entity == null || !(entity instanceof EntityLivingBase)) {
						FMLLog.warning("Wrond fighting entity id");
						return;
					}

					FightProperty entityProperties = (FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER);
					if (entityProperties == null) {
						FMLLog.warning("Error while loading the Fight properties of player : %s", entity.getClass().getName());
						return;
					}

					entityProperties.setFightId(event.fightId);
				}
			}

			fights.put(event.fightId, event.fighters);
			break;

		case STOP:
			teams = event.fighters.iterator();
			while (teams.hasNext()) {
				Iterator<Integer> entities = teams.next().iterator();
				while (entities.hasNext()) {
					Entity entity = event.world.getEntityByID(entities.next());
					if (entity == null || !(entity instanceof EntityLivingBase)) {
						FMLLog.warning("Wrond fighting entity id");
						return;
					}

					FightProperty entityProperties = (FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER);
					if (entityProperties == null) {
						FMLLog.warning("Error while loading the Fight properties of player : %s", entity.getClass().getName());
						return;
					}

					entityProperties.resetFightId();
				}
			}

			fights.remove(event.fightId);
			break;

		default:
			break;
		}
	}
}
