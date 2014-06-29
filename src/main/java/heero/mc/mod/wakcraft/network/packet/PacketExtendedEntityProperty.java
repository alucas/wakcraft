package heero.mc.mod.wakcraft.network.packet;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.entity.property.ISynchProperties;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IExtendedEntityProperties;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketExtendedEntityProperty implements IMessage {
	protected Entity entity;
	public int entityId;
	public String identifier;
	public NBTTagCompound tagProperties;

	public PacketExtendedEntityProperty() {
	}

	public PacketExtendedEntityProperty(Entity entity, String identifier) {
		this.entity = entity;
		this.entityId = entity.getEntityId();
		this.identifier = identifier;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(entityId);
		ByteBufUtils.writeUTF8String(buffer, identifier);

		IExtendedEntityProperties properties = entity.getExtendedProperties(identifier);
		if (properties == null) {
			WLog.warning("Error while loading %s properties of entity %d, properties not found", identifier, entityId);

			throw new RuntimeException("Properties not found");
		}

		if (!(properties instanceof ISynchProperties)) {
			WLog.warning("Properties %s are not synchronisable", identifier);

			throw new RuntimeException("Properties not synchronisable");
		}

		ByteBufUtils.writeTag(buffer, ((ISynchProperties) properties).getClientPacket());
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.entityId = buffer.readInt();
		this.identifier = ByteBufUtils.readUTF8String(buffer);
		this.tagProperties = ByteBufUtils.readTag(buffer);
	}
}