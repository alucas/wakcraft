package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.entity.property.ISynchProperties;
import heero.mc.mod.wakcraft.network.packet.PacketExtendedEntityProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.IExtendedEntityProperties;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientExtendedEntityProperty implements IMessageHandler<PacketExtendedEntityProperty, IMessage> {
	@Override
	public IMessage onMessage(PacketExtendedEntityProperty message, MessageContext ctx) {
		Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(message.entityId);
		if (entity == null) {
			FMLLog.warning("Error while loading entity %d, entity not found", message.entityId);
			return null;
		}

		IExtendedEntityProperties properties = entity.getExtendedProperties(message.identifier);
		if (properties == null) {
			FMLLog.warning("Error while loading %s properties of entity %d, properties not found", message.identifier, message.entityId);
			return null;
		}

		if (!(properties instanceof ISynchProperties)) {
			FMLLog.warning("Properties %s are not synchronisable", message.identifier);
			return null;
		}

		((ISynchProperties) properties).onClientPacket(message.tagProperties);

		return null;
	}
}