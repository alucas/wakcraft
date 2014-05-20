package heero.wakcraft.fight;

import heero.wakcraft.entity.property.FightProperty;
import heero.wakcraft.event.FightEvent;
import heero.wakcraft.event.FightEvent.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FightManager {
	protected Map<Integer, List<List<EntityLivingBase>>> fights = new HashMap<Integer, List<List<EntityLivingBase>>>();

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
			if (event.target instanceof EntityLivingBase) {
				InitFight(event.entityPlayer, (EntityLivingBase) event.target);
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

		updateFight(fightId);
	}

	protected void InitFight(EntityPlayer assailant, EntityLivingBase target) {
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

		assailantProperties.setFightId(fightId);
		targetProperties.setFightId(fightId);

		ArrayList<EntityLivingBase> fighters1 = new ArrayList<EntityLivingBase>();
		ArrayList<EntityLivingBase> fighters2 = new ArrayList<EntityLivingBase>();
		fighters1.add(assailant);
		fighters2.add(target);

		ArrayList<List<EntityLivingBase>> fighters = new ArrayList<List<EntityLivingBase>>();
		fighters.add(fighters1);
		fighters.add(fighters2);

		fights.put(fightId, fighters);

		MinecraftForge.EVENT_BUS.post(new FightEvent(Type.START, fightId, fighters));
	}

	private void updateFight(int fightId) {
		List<List<EntityLivingBase>> fighters = fights.get(fightId);
		if (fighters == null) {
			FMLLog.warning("Trying to update a fight who does not exist : %d", fightId);
			return;
		}

		for (int teamId = 0; teamId < 2; teamId++) {
			List<EntityLivingBase> teamFighters = fighters.get(teamId);

			boolean living = false;
			for (EntityLivingBase entity : teamFighters) {
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
				stopFight(fightId);
				return;
			}
		}
	}

	private void stopFight(int fightId) {
		List<List<EntityLivingBase>> fighters = fights.get(fightId);
		if (fighters == null) {
			FMLLog.warning("Trying to stop a fight who does not exist : %d", fightId);
			return;
		}

		MinecraftForge.EVENT_BUS.post(new FightEvent(Type.STOP, fightId, fighters));

		for (int teamId = 0; teamId < 2; teamId++) {
			List<EntityLivingBase> teamFighters = fighters.get(teamId);

			for (EntityLivingBase entity : teamFighters) {
					FightProperty entityProperties = (FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER);
					if (entityProperties == null) {
						FMLLog.warning("Error while loading the Fight properties of player : %s", entity.getClass().getName());
						return;
					}

					entityProperties.resetFightId();
			}
		}

		fights.remove(fightId);
	}
}
