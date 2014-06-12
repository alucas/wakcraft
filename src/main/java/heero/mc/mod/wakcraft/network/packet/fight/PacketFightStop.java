package heero.mc.mod.wakcraft.network.packet.fight;

import heero.mc.mod.wakcraft.fight.FightManager;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class PacketFightStop implements IPacketFight {
	protected PacketFight packetFight;

	public PacketFightStop() {
		packetFight = new PacketFight();
	}

	public PacketFightStop(int fightId) {
		packetFight = new PacketFight(fightId);
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		packetFight.encodeInto(ctx, buffer);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		packetFight.decodeInto(ctx, buffer);
	}

	@Override
	public void handleClientSide(EntityPlayer player) throws Exception {
		FightManager.INSTANCE.stopFight(player.worldObj, getFightId());
	}

	@Override
	public void handleServerSide(EntityPlayer player) throws Exception {
		throw new RuntimeException("This is a client side packet");
	}

	@Override
	public int getFightId() {
		return packetFight.getFightId();
	}
}
