package heero.mc.mod.wakcraft.network.packet.fight;

import heero.mc.mod.wakcraft.fight.FightInfo.Stage;
import heero.mc.mod.wakcraft.fight.FightManager;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class PacketFightChangeStage implements IPacketFight {
	protected PacketFight packetFight;
	protected Stage stage;

	public PacketFightChangeStage() {
		packetFight = new PacketFight();
	}

	public PacketFightChangeStage(int fightId, Stage stage) {
		packetFight = new PacketFight(fightId);

		this.stage = stage;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		packetFight.encodeInto(ctx, buffer);

		buffer.writeByte(stage.ordinal());
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		packetFight.decodeInto(ctx, buffer);

		int stageId = buffer.readByte();
		if (stageId < 0 || stageId >= Stage.values().length) {
			throw new RuntimeException("Invialide value of 'stage'");
		}

		stage = Stage.values()[stageId];
	}

	@Override
	public void handleClientSide(EntityPlayer player) throws Exception {
		FightManager.INSTANCE.changeFightStage(player.worldObj, getFightId(), stage);
	}

	@Override
	public void handleServerSide(EntityPlayer player) throws Exception {
		packetFight.handleServerSide(player);
	}

	@Override
	public int getFightId() {
		return packetFight.getFightId();
	}
}
