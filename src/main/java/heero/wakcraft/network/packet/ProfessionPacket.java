package heero.wakcraft.network.packet;

import heero.wakcraft.profession.ProfessionManager;
import heero.wakcraft.profession.ProfessionManager.PROFESSION;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ProfessionPacket implements IPacket {
	private int xp;
	private PROFESSION profession;

	public ProfessionPacket() {
	}

	public ProfessionPacket(PROFESSION profession, int xp) {
		this.profession = profession;
		this.xp = xp;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(profession.getValue());
		buffer.writeInt(xp);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		profession = PROFESSION.getProfession(buffer.readInt());
		xp = buffer.readInt();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleClientSide(EntityPlayer player) {
		if (profession != null) {
			ProfessionManager.setXp(player, profession, xp);
		}
	}

	@SideOnly(Side.SERVER)
	@Override
	public void handleServerSide(EntityPlayer player) {
	}
}