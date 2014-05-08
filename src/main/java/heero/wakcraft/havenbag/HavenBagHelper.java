package heero.wakcraft.havenbag;

import heero.wakcraft.WakcraftBlocks;
import heero.wakcraft.WakcraftItems;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.item.ItemIkiakit;
import heero.wakcraft.tileentity.TileEntityHavenBag;
import heero.wakcraft.tileentity.TileEntityHavenBagProperties;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;

public class HavenBagHelper {
	public static int getNextAvailableUID(World world) {
		int uid = 0;

		while (true) {
			int[] coords = getCoordFromUID(++uid);
			if (world.getBlock(coords[0], coords[1] - 1, coords[2] + 1).equals(Blocks.air)) {
				break;
			}
		}

		return uid;
	}

	public static int[] getCoordFromUID(int uid) {
		return new int[] { 100000 + (uid % 10) * 30, 20, (uid / 10) * 30 };
	}

	public static int getUIDFromCoord(int x, int y, int z) {
		return ((z + 7) / 30) * 10 + ((x - 99999) / 30);
	}

	public static TileEntityHavenBagProperties getHavenBagProperties(World world, int uid) {
		int[] coords = getCoordFromUID(uid);

		TileEntity tileEntity = world.getTileEntity(coords[0], coords[1], coords[2] - 1);
		if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBagProperties)) {
			FMLLog.warning("Error while loading tile entity haven bag properties (%d, %d, %d)", coords[0], coords[1], coords[2]);
			return null;
		}

		return (TileEntityHavenBagProperties) tileEntity;
	}

	public static void teleportPlayerToHavenBag(EntityPlayer player, int havenBagUID) {
		HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading player (%s) properties", player.getDisplayName());
			return;
		}

		properties.setEnterHavenBag(player.posX, player.posY, player.posZ, havenBagUID);

		int[] coords = HavenBagHelper.getCoordFromUID(havenBagUID);
		player.rotationYaw = -90;
		player.rotationPitch = 0;
		player.setPositionAndUpdate(coords[0] + 0.5, coords[1], coords[2] + 1.5);
	}

	public static void tryTeleportPlayerToHavenBag(EntityPlayer player) {
		HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading player (%s) havenbag properties", player.getDisplayName());
			return;
		}

		if (properties.isInHavenBag()) {
			player.setPositionAndUpdate(properties.posX, properties.posY, properties.posZ);

			properties.setLeaveHavenBag();

			return;
		}

		if (! player.onGround) {
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cantOpenHavenBagInAir")));
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
			properties.uid = HavenBagHelper.getNextAvailableUID(player.worldObj);

			FMLLog.info("New HavenBag atribution : %s, uid = %d", player.getDisplayName(), properties.uid);

			HavenBagGenerationHelper.generateHavenBag(player.worldObj, properties.uid);
		}

		player.worldObj.setBlock(posX, posY, posZ, WakcraftBlocks.havenbag);

		TileEntity tileEntity = player.worldObj.getTileEntity(posX, posY, posZ);
		if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBag)) {
			FMLLog.warning("Error while loading the tile entity (%d, %d, %d)", posX, posY, posZ);
			return;
		}

		TileEntityHavenBag tileHavenBag = (TileEntityHavenBag) tileEntity;
		tileHavenBag.uid = properties.uid;
		tileHavenBag.markDirty();

		if (player instanceof EntityPlayerMP) {
			((EntityPlayerMP) player).playerNetServerHandler.sendPacket(tileHavenBag.getDescriptionPacket());
		}

		HavenBagHelper.teleportPlayerToHavenBag(player, properties.uid);
	}
}
