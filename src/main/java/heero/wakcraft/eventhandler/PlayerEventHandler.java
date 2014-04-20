package heero.wakcraft.eventhandler;

import heero.wakcraft.entity.property.XpCraftingProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerEventHandler {
	@SubscribeEvent
	public void playerLoggedIn(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			player.registerExtendedProperties(XpCraftingProperty.IDENTIFIER, new XpCraftingProperty());
		}
	}
}