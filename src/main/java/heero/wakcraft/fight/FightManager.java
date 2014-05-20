package heero.wakcraft.fight;

import heero.wakcraft.entity.property.FightProperty;
import heero.wakcraft.event.FightEvent;
import heero.wakcraft.event.FightEvent.Type;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FightManager {
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
			InitFight(event.entityPlayer, event.target);

			event.setCanceled(true);
			return;
		}

		if (properties.getFightId() != targetProperties.getFightId()) {
			event.setCanceled(true);
			return;
		}
	}

	@SubscribeEvent
	@SuppressWarnings("unchecked")
	public void onLivingDeathEvent(LivingDeathEvent event) {
		if (event.entityLiving.worldObj.isRemote) {
			return;
		}

		FightProperty properties = (FightProperty) event.entityLiving.getExtendedProperties(FightProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading the Fight properties of player : %s", event.entityLiving.getClass().getName());
			return;
		}

		List<Entity> entities = (List<Entity>) event.entityLiving.worldObj.getLoadedEntityList();
		for (Entity entity : entities) {
			if (entity instanceof EntityLivingBase) {
				FightProperty entityProperties = (FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER);
				if (entityProperties == null) {
					FMLLog.warning("Error while loading the Fight properties of player : %s", entity.getClass().getName());
					return;
				}

				if (entityProperties.getFightId() != properties.getFightId()) {
					return;
				}

				entityProperties.resetFightId();
			}
		}
	}

	protected void InitFight(EntityPlayer assailant, Entity target) {
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

		MinecraftForge.EVENT_BUS.post(new FightEvent(Type.START, fightId));
	}
}
