package heero.wakcraft.network.packet;

import heero.wakcraft.entity.property.ISynchProperties;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.IExtendedEntityProperties;
import cpw.mods.fml.common.FMLLog;

public class PacketExtendedEntityProperty implements IPacket {
	private Entity entity;
	private int entityId;
	private String identifier;
	private NBTTagCompound properties;

	public PacketExtendedEntityProperty() {
	}

	public PacketExtendedEntityProperty(Entity entity, String identifier) {
		System.out.println(entity.getEntityId());
		this.entity = entity;
		this.entityId = entity.getEntityId();
		this.identifier = identifier;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) throws IOException {
		buffer.writeInt(entityId);
		buffer.writeStringToBuffer(identifier);

		IExtendedEntityProperties properties = entity.getExtendedProperties(identifier);
		if (properties == null) {
			FMLLog.warning("Error while loading %s properties of entity %d, properties not found", identifier, entityId);

			throw new IOException("Properties not found");
		}

		if (!(properties instanceof ISynchProperties)) {
			FMLLog.warning("Properties %s are not synchronisable", identifier);

			throw new IOException("Properties not synchronisable");
		}

		buffer.writeNBTTagCompoundToBuffer(((ISynchProperties) properties).getClientPacket());
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) throws IOException {
		this.entityId = buffer.readInt();
		this.identifier = buffer.readStringFromBuffer(20);
		this.properties = buffer.readNBTTagCompoundFromBuffer();
	}

	@Override
	public void handleClientSide(EntityPlayer player) throws Exception {
		entity = Minecraft.getMinecraft().theWorld.getEntityByID(entityId);
		if (entity == null) {
			FMLLog.warning("Error while loading entity %d, entity not found", entityId);

			throw new IOException("Entity not found");
		}

		IExtendedEntityProperties properties = entity.getExtendedProperties(identifier);
		if (properties == null) {
			FMLLog.warning("Error while loading %s properties of entity %d, properties not found", identifier, entityId);

			throw new IOException("Properties not found");
		}

		if (!(properties instanceof ISynchProperties)) {
			FMLLog.warning("Properties %s are not synchronisable", identifier);

			throw new IOException("Properties not synchronisable");
		}

		((ISynchProperties) properties).onClientPacket(this.properties);
	}

	@Override
	public void handleServerSide(EntityPlayer player) throws Exception {
		throw new Exception("This is a clientbound pack only");
	}
}