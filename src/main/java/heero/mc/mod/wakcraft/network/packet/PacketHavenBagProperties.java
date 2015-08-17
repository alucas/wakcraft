package heero.mc.mod.wakcraft.network.packet;

import heero.mc.mod.wakcraft.havenbag.HavenBagsManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketHavenBagProperties implements IMessage {
	public NBTTagCompound tagHavenBag;

	public PacketHavenBagProperties() {
	}

	public PacketHavenBagProperties(int uid) {
		this.tagHavenBag = HavenBagsManager.getHavenBagNBT(uid);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		tagHavenBag = ByteBufUtils.readTag(buffer);
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		ByteBufUtils.writeTag(buffer, tagHavenBag);
	}
}