package heero.mc.mod.wakcraft.network.packet.fight;

import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.helper.FightHelper;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ChunkCoordinates;

public class PacketFightSelectPosition implements IPacketFight {
	protected IPacketFight packetFight;
	protected Integer fighterId;
	protected ChunkCoordinates selectedPosition;

	public PacketFightSelectPosition() {
		this.packetFight = new PacketFight();
	}

	public PacketFightSelectPosition(int fightId, EntityLivingBase fighter, ChunkCoordinates selectedPosition) {
		this.packetFight = new PacketFight(fightId);
		this.fighterId = fighter.getEntityId();
		this.selectedPosition = selectedPosition;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		packetFight.encodeInto(ctx, buffer);

		buffer.writeInt(fighterId);
		buffer.writeInt(selectedPosition.posX);
		buffer.writeInt(selectedPosition.posY);
		buffer.writeInt(selectedPosition.posZ);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		packetFight.decodeInto(ctx, buffer);

		fighterId = buffer.readInt();
		selectedPosition = new ChunkCoordinates(buffer.readInt(), buffer.readInt(), buffer.readInt());
	}

	@Override
	public void handleClientSide(EntityPlayer player) throws Exception {
		Entity entity = player.worldObj.getEntityByID(fighterId);
		if (entity == null) {
			throw new RuntimeException("No entity found for id " + fighterId);
		}

		if (!FightHelper.isFighter(entity)) {
			throw new RuntimeException("The entity " + entity + " is not a valid fighter");
		}

		FightHelper.setStartPosition(entity, selectedPosition);
	}

	@Override
	public void handleServerSide(EntityPlayer player) throws Exception {
		if (!FightHelper.isFighter(player)) {
			throw new RuntimeException("The entity " + player + " is not a valid fighter");
		}

		FightManager.INSTANCE.selectPosition((EntityLivingBase) player, selectedPosition);
	}

	@Override
	public int getFightId() {
		return packetFight.getFightId();
	}
}
