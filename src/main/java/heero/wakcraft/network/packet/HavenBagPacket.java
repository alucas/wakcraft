package heero.wakcraft.network.packet;

import heero.wakcraft.WakcraftBlocks;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.havenbag.HavenBagManager;
import heero.wakcraft.tileentity.TileEntityHavenBag;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

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
		HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
		if (properties == null) {
			System.err.println("Error while loading player properties");
			return;
		}

		if (properties.isInHavenBag()) {
			player.setPositionAndUpdate(properties.posX, properties.posY, properties.posZ);

			properties.setLeaveHavenBag();

			return;
		}

		if (! player.onGround) {
			return;
		}

		int posX = (int)Math.floor(player.posX);
		int posY = (int)Math.floor(player.posY - 0.1) + 1;
		int posZ = (int)Math.floor(player.posZ);

		Block block = player.worldObj.getBlock(posX, posY - 1, posZ);
		if (block == null || (block != null && !block.isOpaqueCube())) {
			return;
		}

		// Initialisation
		if (properties.uid == 0) {
			properties.uid = HavenBagManager.getNextAvailableUID(player.worldObj);

			HavenBagManager.generateHavenBag(player.worldObj, properties.uid);
		}

		player.worldObj.setBlock(posX, posY, posZ, WakcraftBlocks.havenbag);
		TileEntity tileEntity = player.worldObj.getTileEntity(posX, posY, posZ);
		if (tileEntity == null) {
			System.err.println("Error while loading the tile entity of the haven block");
		}

		TileEntityHavenBag tileHavenBag = (TileEntityHavenBag) tileEntity;
		tileHavenBag.uid = properties.uid;
		tileHavenBag.markDirty();

		if (player instanceof EntityPlayerMP) {
			((EntityPlayerMP) player).playerNetServerHandler.sendPacket(tileHavenBag.getDescriptionPacket());
		}

		HavenBagManager.teleportPlayerToHavenBag(player, properties.uid);
	}
}