package heero.wakcraft.block;

import heero.wakcraft.WBlocks;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.havenbag.HavenBagHelper;
import heero.wakcraft.havenbag.HavenBagProperties;
import heero.wakcraft.havenbag.HavenBagsManager;

import java.util.List;

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
import cpw.mods.fml.common.FMLLog;

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
	public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_) {
		return false;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
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
			if (havenBagUID == havenBagProperties.getUID()) {
				return;
			}

			HavenBagProperties hbProperties = HavenBagsManager.getProperties(havenBagUID);
			if (hbProperties == null) {
				break;
			}

			Integer rightAll = hbProperties.getRight(HavenBagProperties.ACL_KEY_ALL);
			Integer rightGuild = hbProperties.getRight(HavenBagProperties.ACL_KEY_GUILD);
			Integer right = hbProperties.getRight(player.getDisplayName());

			if (rightAll == 0 && rightGuild == 0 && (right == null || right == 0)) {
				break;
			}

			Block[] blocks = new Block[4];
			blocks[1] = world.getBlock(x, 19, z + 1);
			blocks[2] = world.getBlock(x, 19, z - 1);
			blocks[3] = world.getBlock(x + 1, 19, z);
			blocks[4] = world.getBlock(x - 1, 19, z);

			for (int i = 0; i < blocks.length; i++) {
				if ((blocks[i].equals(WBlocks.hbGarden) && (rightAll & HavenBagHelper.R_GARDEN) == 0)
						|| (blocks[i].equals(WBlocks.hbMerchant) && (rightAll & HavenBagHelper.R_MERCHANT) == 0)
						|| (blocks[i].equals(WBlocks.hbDeco) && (rightAll & HavenBagHelper.R_DECO) == 0)
						|| (blocks[i].equals(WBlocks.hbCraft) && (rightAll & HavenBagHelper.R_CRAFT) == 0)
						|| (blocks[i].equals(WBlocks.hbDeco2) && (rightAll & HavenBagHelper.R_DECO) == 0)
						|| (blocks[i].equals(WBlocks.hbCraft2) && (rightAll & HavenBagHelper.R_CRAFT) == 0)
						) {
					break;
				}
			}

			return;
		} while(false);

		AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(world, x, y, z);

		if (axisalignedbb1 != null && mask.intersectsWith(axisalignedbb1)) {
			list.add(axisalignedbb1);
		}
	}
}
