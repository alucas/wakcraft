package heero.wakcraft.network.packet;

import heero.wakcraft.havenbag.HavenBagsManager;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class PacketHavenBagProperties implements IPacket {
	private int uid;
	private NBTTagCompound tagHavenBag;

	public PacketHavenBagProperties() {
	}

	public PacketHavenBagProperties(int uid) {
		this.uid = uid;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) throws IOException {
		buffer.writeNBTTagCompoundToBuffer(HavenBagsManager.getHavenBagNBT(uid));
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) throws IOException {
		tagHavenBag = buffer.readNBTTagCompoundFromBuffer();
	}

	@Override
	public void handleClientSide(EntityPlayer player) throws Exception {
		HavenBagsManager.setHavenBagNBT(tagHavenBag);
	}

	@Override
	public void handleServerSide(EntityPlayer player) throws Exception {
		throw new Exception("This is a clientbound packet only");
	}
}