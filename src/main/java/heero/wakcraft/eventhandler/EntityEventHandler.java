package heero.wakcraft.eventhandler;

import heero.wakcraft.entity.property.AbilitiesProperty;
import heero.wakcraft.entity.property.FightProperty;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.entity.property.InventoryProperty;
import heero.wakcraft.entity.property.ProfessionProperty;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityEventHandler {
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityLivingBase) {
			event.entity.registerExtendedProperties(FightProperty.IDENTIFIER, new FightProperty());
		}

		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			player.registerExtendedProperties(ProfessionProperty.IDENTIFIER, new ProfessionProperty());
			player.registerExtendedProperties(HavenBagProperty.IDENTIFIER, new HavenBagProperty());
			player.registerExtendedProperties(AbilitiesProperty.IDENTIFIER, new AbilitiesProperty());
			player.registerExtendedProperties(InventoryProperty.IDENTIFIER, new InventoryProperty());
		}
	}
}