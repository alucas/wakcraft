package heero.wakcraft.block;

import heero.wakcraft.renderer.RenderBlockYRotation;
import heero.wakcraft.util.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockYRotation extends Block {
	public BlockYRotation(Material material) {
		super(material);
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return RenderBlockYRotation.renderId;
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
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		int metadata = world.getBlockMetadata(x, y, z);
		switch (side) {
		case 0:
		case 1:
			int blockId = Block.getIdFromBlock(world.getBlock(x, y, z));
			boolean t1 = Block.getIdFromBlock(world.getBlock(x + 1, y, z)) == blockId;
			boolean t2 = Block.getIdFromBlock(world.getBlock(x, y, z + 1)) == blockId;
			boolean t3 = Block.getIdFromBlock(world.getBlock(x - 1, y, z)) == blockId;
			boolean t4 = Block.getIdFromBlock(world.getBlock(x, y, z - 1)) == blockId;

			int neighbor = (t1 ? 1 : 0) + (t2 ? 2 : 0) + (t3 ? 4 : 0)
					+ (t4 ? 8 : 0);

			if (neighbor == 15) {
				return getCenterIcon(side, metadata);
			} else if (neighbor == 12 || neighbor == 9 || neighbor == 03
					|| neighbor == 6) {
				return getCornerIcon(side, metadata);
			}

			return getTopIcon(side, metadata);
		default:
			return getSideIcon(side, metadata);
		}
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata) {
		switch (side) {
		case 0:
		case 1:
			return getTopIcon(side, metadata);
		default:
			return getSideIcon(side, metadata);
		}
	}

	@SideOnly(Side.CLIENT)
	public abstract IIcon getTopIcon(int side, int metadata);

	@SideOnly(Side.CLIENT)
	public abstract IIcon getSideIcon(int side, int metadata);

	@SideOnly(Side.CLIENT)
	public IIcon getCornerIcon(int side, int metadata) {
		return getTopIcon(side, metadata);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getCenterIcon(int side, int metadata) {
		return getTopIcon(side, metadata);
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase player, ItemStack itemBlock) {
		super.onBlockPlacedBy(world, x, y, z, player, itemBlock);

		RotationUtil.setOrientationFromYaw(world, x, y, z, player.rotationYaw);
	}
}
