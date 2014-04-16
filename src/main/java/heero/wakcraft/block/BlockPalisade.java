package heero.wakcraft.block;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.renderer.RenderBlockPalisade;
import heero.wakcraft.util.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPalisade extends Block {
	public BlockPalisade() {
		super(Material.wood);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockName("Palisade");

		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":" + "palisade");
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return RenderBlockPalisade.renderId;
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and
	 * wood.
	 */
	@Override
	public int damageDropped(int metadata) {
		return metadata & 12;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y,
			int z) {
		super.setBlockBoundsBasedOnState(world, x, y, z);

		int m = world.getBlockMetadata(x, y, z) & 0x3;
		int m1 = world.getBlockMetadata(x + 1, y, z) & 0x3;
		int m2 = world.getBlockMetadata(x, y, z + 1) & 0x3;
		int m3 = world.getBlockMetadata(x - 1, y, z) & 0x3;
		int m4 = world.getBlockMetadata(x, y, z - 1) & 0x3;

		Block block = world.getBlock(x, y, z);
		int blockId = Block.getIdFromBlock(block);
		boolean t1 = Block.getIdFromBlock(world.getBlock(x + 1, y, z)) == blockId;
		boolean t2 = Block.getIdFromBlock(world.getBlock(x, y, z + 1)) == blockId;
		boolean t3 = Block.getIdFromBlock(world.getBlock(x - 1, y, z)) == blockId;
		boolean t4 = Block.getIdFromBlock(world.getBlock(x, y, z - 1)) == blockId;

		if ((t3 && m == 1 && m3 == 0) || (t1 && m == 2 && m1 == 0)
				|| (t4 && m == 3 && m4 == 1) || (t2 && m == 0 && m2 == 1)
				|| (t3 && m == 1 && m3 == 3) || (t1 && m == 2 && m1 == 3)
				|| (t4 && m == 3 && m4 == 2) || (t2 && m == 0 && m2 == 2)) {
			block.setBlockBounds(0, 0, 0, 1, 1, 1);
		} else {
			switch (m) {
			case 0:
				block.setBlockBounds(0, 0, 0, 1, 1, 0.1875f);
				break;
			case 1:
				block.setBlockBounds(0.8125f, 0, 0, 1, 1, 1);
				break;
			case 2:
				block.setBlockBounds(0, 0, 0, 0.1875f, 1, 1);
				break;
			default:
				block.setBlockBounds(0, 0, 0.8125f, 1, 1, 1);
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase player, ItemStack itemBlock) {
		super.onBlockPlacedBy(world, x, y, z, player, itemBlock);

		RotationUtil.setOrientationFromYaw(world, x, y, z, player.rotationYaw);
	}
}
