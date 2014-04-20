package heero.wakcraft.eventhandler;

import heero.wakcraft.entity.property.XpProfessionProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerEventHandler {
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			player.registerExtendedProperties(XpProfessionProperty.IDENTIFIER, new XpProfessionProperty());
		}
	}
}