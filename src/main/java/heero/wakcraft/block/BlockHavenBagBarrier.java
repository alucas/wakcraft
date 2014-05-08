package heero.wakcraft.block;

import heero.wakcraft.WakcraftBlocks;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.havenbag.HavenBagHelper;
import heero.wakcraft.tileentity.TileEntityHavenBagProperties;

import java.util.List;

import cpw.mods.fml.common.FMLLog;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class BlockHavenBagBarrier extends Block {

	public BlockHavenBagBarrier() {
		super(Material.air);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockName("HavenBagBarrier");
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if (world.isRemote) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.canPlaceBlockManualy")));
		}

		return true;
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add
	 * boxes to the list if they intersect the mask.) Parameters: World, X, Y,
	 * Z, mask, list, colliding entity
	 */
	public void addCollisionBoxesToList(World world, int x, int y, int z,
			AxisAlignedBB mask, List list, Entity entity) {
		do {
			if (!(entity instanceof EntityPlayer)) {
				return;
			}

			EntityPlayer player = (EntityPlayer) entity;

			int havenBagUID = HavenBagHelper.getUIDFromCoord((int)entity.posX, (int)entity.posY, (int)entity.posZ);
			IExtendedEntityProperties properties = entity.getExtendedProperties(HavenBagProperty.IDENTIFIER);
			if (properties == null || !(properties instanceof HavenBagProperty)) {
				FMLLog.warning("Error while loading the player (%s) properties", player.getDisplayName());
				break;
			}

			HavenBagProperty havenBagProperties = (HavenBagProperty) properties;
			if (havenBagUID == havenBagProperties.uid) {
				return;
			}

			TileEntityHavenBagProperties tile = HavenBagHelper.getHavenBagProperties(world, havenBagUID);
			if (tile == null) {
				break;
			}

			int rightAll = tile.acl.get(TileEntityHavenBagProperties.ACL_KEY_ALL);
//			int rightGuild = tile.acl.get(TileEntityHavenBagProperties.ACL_KEY_GUILD);
//			int right = tile.acl.get(player.getDisplayName());

			Block block1 = world.getBlock(x, 19, z + 1);
			Block block2 = world.getBlock(x, 19, z - 1);
			Block block3 = world.getBlock(x + 1, 19, z);
			Block block4 = world.getBlock(x - 1, 19, z);

			if ((block1.equals(WakcraftBlocks.hbGarden) && (rightAll & HavenBagHelper.R_GARDEN) == 0)
					|| (block2.equals(WakcraftBlocks.hbGarden) && (rightAll & HavenBagHelper.R_GARDEN) == 0)
					|| (block3.equals(WakcraftBlocks.hbGarden) && (rightAll & HavenBagHelper.R_GARDEN) == 0)
					|| (block4.equals(WakcraftBlocks.hbGarden) && (rightAll & HavenBagHelper.R_GARDEN) == 0)
					|| (block1.equals(WakcraftBlocks.hbMerchant) && (rightAll & HavenBagHelper.R_MERCHANT) == 0)
					|| (block2.equals(WakcraftBlocks.hbMerchant) && (rightAll & HavenBagHelper.R_MERCHANT) == 0)
					|| (block3.equals(WakcraftBlocks.hbMerchant) && (rightAll & HavenBagHelper.R_MERCHANT) == 0)
					|| (block4.equals(WakcraftBlocks.hbMerchant) && (rightAll & HavenBagHelper.R_MERCHANT) == 0)
					|| (block1.equals(WakcraftBlocks.hbDeco) && (rightAll & HavenBagHelper.R_DECO) == 0)
					|| (block2.equals(WakcraftBlocks.hbDeco) && (rightAll & HavenBagHelper.R_DECO) == 0)
					|| (block3.equals(WakcraftBlocks.hbDeco) && (rightAll & HavenBagHelper.R_DECO) == 0)
					|| (block4.equals(WakcraftBlocks.hbDeco) && (rightAll & HavenBagHelper.R_DECO) == 0)
					|| (block1.equals(WakcraftBlocks.hbCraft) && (rightAll & HavenBagHelper.R_CRAFT) == 0)
					|| (block2.equals(WakcraftBlocks.hbCraft) && (rightAll & HavenBagHelper.R_CRAFT) == 0)
					|| (block3.equals(WakcraftBlocks.hbCraft) && (rightAll & HavenBagHelper.R_CRAFT) == 0)
					|| (block4.equals(WakcraftBlocks.hbCraft) && (rightAll & HavenBagHelper.R_CRAFT) == 0)
					) {
				break;
			}

			return;
		} while(false);

		AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(world, x, y, z);

		if (axisalignedbb1 != null && mask.intersectsWith(axisalignedbb1)) {
			list.add(axisalignedbb1);
		}
	}
}
