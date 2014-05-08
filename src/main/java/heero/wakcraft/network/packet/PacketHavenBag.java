package heero.wakcraft.network.packet;

import heero.wakcraft.havenbag.HavenBagHelper;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class PacketHavenBag implements IPacket {
	public PacketHavenBag() {
	}

	public PacketHavenBag(EntityPlayer player) {
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		HavenBagHelper.tryTeleportPlayerToHavenBag(player);
	}
}