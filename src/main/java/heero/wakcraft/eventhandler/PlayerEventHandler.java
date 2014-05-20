package heero.wakcraft.eventhandler;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.network.packet.PacketExtendedEntityProperty;
import heero.wakcraft.network.packet.PacketHavenBagProperties;
import heero.wakcraft.network.packet.PacketProfession;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class PlayerEventHandler {
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		if (!event.player.worldObj.isRemote && event.player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP) event.player;

			Wakcraft.packetPipeline.sendTo(new PacketProfession(event.player), playerMP);
			Wakcraft.packetPipeline.sendTo(new PacketExtendedEntityProperty(event.player, HavenBagProperty.IDENTIFIER), playerMP);

			HavenBagProperty properties = (HavenBagProperty) playerMP.getExtendedProperties(HavenBagProperty.IDENTIFIER);
			if (properties == null) {
				FMLLog.warning("Error while loading player (%s) havenbag properties", playerMP.getDisplayName());
				return;
			}

			if (properties.isInHavenBag()) {
				Wakcraft.packetPipeline.sendTo(new PacketHavenBagProperties(properties.getHavenBag()), playerMP);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
		if (!event.player.worldObj.isRemote && event.player instanceof EntityPlayerMP) {
			Wakcraft.packetPipeline.sendTo(new PacketProfession(event.player), (EntityPlayerMP) event.player);
			Wakcraft.packetPipeline.sendTo(new PacketExtendedEntityProperty(event.player, HavenBagProperty.IDENTIFIER), (EntityPlayerMP) event.player);
		}
	}
}