package heero.mc.mod.wakcraft.eventhandler;

import heero.mc.mod.wakcraft.entity.property.CharacterProperty;
import heero.mc.mod.wakcraft.entity.property.HavenBagProperty;
import heero.mc.mod.wakcraft.entity.property.InventoryProperty;
import heero.mc.mod.wakcraft.entity.property.ItemInUseProperty;
import heero.mc.mod.wakcraft.entity.property.ProfessionProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityEventHandler {
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			player.registerExtendedProperties(ProfessionProperty.IDENTIFIER, new ProfessionProperty());
			player.registerExtendedProperties(HavenBagProperty.IDENTIFIER, new HavenBagProperty());
			player.registerExtendedProperties(InventoryProperty.IDENTIFIER, new InventoryProperty());
			player.registerExtendedProperties(CharacterProperty.IDENTIFIER, new CharacterProperty());
			player.registerExtendedProperties(ItemInUseProperty.IDENTIFIER, new ItemInUseProperty());
		}
	}
}