package heero.wakcraft.block;

import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.havenbag.HavenBagManager;
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
		super(Material.clay);

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

			int havenBagUID = HavenBagManager.getUIDFromCoord((int)entity.posX, (int)entity.posY, (int)entity.posZ);
			IExtendedEntityProperties properties = entity.getExtendedProperties(HavenBagProperty.IDENTIFIER);
			if (properties == null || !(properties instanceof HavenBagProperty)) {
				FMLLog.warning("Error while loading the player (%s) properties", ((EntityPlayer)entity).getDisplayName());
				break;
			}

			HavenBagProperty havenBagProperties = (HavenBagProperty) properties;
			if (havenBagUID == havenBagProperties.uid) {
				return;
			}
		} while(false);

		AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(world, x, y, z);

		if (axisalignedbb1 != null && mask.intersectsWith(axisalignedbb1)) {
			list.add(axisalignedbb1);
		}
	}
}
