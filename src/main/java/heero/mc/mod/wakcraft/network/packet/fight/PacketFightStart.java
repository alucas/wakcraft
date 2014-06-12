package heero.mc.mod.wakcraft.network.packet.fight;

import heero.mc.mod.wakcraft.fight.FightBlockCoordinates;
import heero.mc.mod.wakcraft.fight.FightManager;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class PacketFightStart implements IPacketFight {
	protected IPacketFight packetFight;
	protected List<List<Integer>> fightersId;
	protected List<FightBlockCoordinates> startPositions;

	public PacketFightStart() {
		this.packetFight = new PacketFight();
		this.fightersId = new ArrayList<List<Integer>>();
	}

	public PacketFightStart(int fightId, List<List<EntityLivingBase>> fighters, List<FightBlockCoordinates> startPositions) {
		this.packetFight = new PacketFight(fightId);
		this.fightersId = PacketFight.getIds(fighters);
		this.startPositions = startPositions;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		packetFight.encodeInto(ctx, buffer);

		PacketFight.encodeFighters(buffer, fightersId);
		PacketFight.encoreStartPositions(buffer, startPositions);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		packetFight.decodeInto(ctx, buffer);

		this.fightersId = PacketFight.decodeFighters(buffer);
		this.startPositions = PacketFight.decodeStartPositions(buffer);
	}

	@Override
	public void handleClientSide(EntityPlayer player) throws Exception {
		packetFight.handleClientSide(player);

		FightManager.INSTANCE.startClientFight(player.worldObj, getFightId(), PacketFight.getEntities(player.worldObj, fightersId), startPositions);
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
