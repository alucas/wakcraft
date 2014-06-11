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

public class PacketFightStart extends PacketFight {
	protected List<List<Integer>> fightersId;
	protected List<FightBlockCoordinates> startPositions;

	public PacketFightStart() {
		this.fightersId = new ArrayList<List<Integer>>();
	}

	public PacketFightStart(int fightId, List<List<EntityLivingBase>> fighters, List<FightBlockCoordinates> startPositions) {
		super(fightId);

		this.fightersId = getIds(fighters);
		this.startPositions = startPositions;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		super.encodeInto(ctx, buffer);

		encodeFighters(buffer, fightersId);
		encoreStartPositions(buffer, startPositions);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer)
			throws IOException {
		super.decodeInto(ctx, buffer);

		this.fightersId = decodeFighters(buffer);
		this.startPositions = decodeStartPositions(buffer);
	}

	@Override
	public void handleClientSide(EntityPlayer player) throws Exception {
		FightManager.INSTANCE.startClientFight(player.worldObj, fightId, getEntities(player.worldObj, fightersId), startPositions);
	}
}
