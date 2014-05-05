package heero.wakcraft.network.packet;

import cpw.mods.fml.common.FMLLog;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

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
			FMLLog.warning("Error while loading player (%s) properties", player.getDisplayName());
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
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cantOpenHavenBagHere")));
			return;
		}

		// Initialisation
		if (properties.uid == 0) {
			properties.uid = HavenBagManager.getNextAvailableUID(player.worldObj);

			FMLLog.info("New HavenBag atribution : %s, uid = %d", player.getDisplayName(), properties.uid);

			HavenBagManager.generateHavenBag(player.worldObj, properties.uid);
		}

		player.worldObj.setBlock(posX, posY, posZ, WakcraftBlocks.havenbag);

		TileEntity tileEntity = player.worldObj.getTileEntity(posX, posY, posZ);
		if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBag)) {
			FMLLog.warning("Error while loading the tile entity (%d, %d, %d)", posX, posY, posZ);
			return;
		}

		TileEntityHavenBag tileHavenBag = (TileEntityHavenBag) tileEntity;
		tileHavenBag.uid = properties.uid;
		tileHavenBag.isLocked = properties.locked;
		tileHavenBag.markDirty();

		if (player instanceof EntityPlayerMP) {
			((EntityPlayerMP) player).playerNetServerHandler.sendPacket(tileHavenBag.getDescriptionPacket());
		}

		HavenBagManager.teleportPlayerToHavenBag(player, properties.uid);
	}
}