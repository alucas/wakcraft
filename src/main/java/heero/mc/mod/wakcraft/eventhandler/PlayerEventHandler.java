package heero.mc.mod.wakcraft.eventhandler;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.entity.property.HavenBagProperty;
import heero.mc.mod.wakcraft.entity.property.SpellsProperty;
import heero.mc.mod.wakcraft.network.packet.PacketExtendedEntityProperty;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagProperties;
import heero.mc.mod.wakcraft.network.packet.PacketProfession;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerEventHandler {
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.player.worldObj.isRemote && event.player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP) event.player;

			Wakcraft.packetPipeline.sendTo(new PacketProfession(event.player), playerMP);
			Wakcraft.packetPipeline.sendTo(new PacketExtendedEntityProperty(event.player, HavenBagProperty.IDENTIFIER), playerMP);
			Wakcraft.packetPipeline.sendTo(new PacketExtendedEntityProperty(event.player, SpellsProperty.IDENTIFIER), playerMP);

			HavenBagProperty properties = (HavenBagProperty) playerMP.getExtendedProperties(HavenBagProperty.IDENTIFIER);
			if (properties == null) {
				WLog.warning("Error while loading player (%s) havenbag properties", playerMP.getDisplayName());
				return;
			}

			if (properties.isInHavenBag()) {
				Wakcraft.packetPipeline.sendTo(new PacketHavenBagProperties(properties.getHavenBag()), playerMP);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (!event.player.worldObj.isRemote && event.player instanceof EntityPlayerMP) {
			Wakcraft.packetPipeline.sendTo(new PacketProfession(event.player), (EntityPlayerMP) event.player);
			Wakcraft.packetPipeline.sendTo(new PacketExtendedEntityProperty(event.player, HavenBagProperty.IDENTIFIER), (EntityPlayerMP) event.player);
		}
	}
}