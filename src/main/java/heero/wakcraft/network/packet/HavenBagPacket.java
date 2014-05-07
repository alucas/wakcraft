package heero.wakcraft.network.packet;

import heero.wakcraft.havenbag.HavenBagManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class HavenBagPacket implements IPacket {
	public HavenBagPacket() {
	}

	public HavenBagPacket(EntityPlayer player) {
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		HavenBagManager.tryTeleportPlayerToHavenBag(player);
	}
}