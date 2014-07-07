package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSlab extends BlockGeneric implements IBlockProvider {
	// Opaque version of the slab block
	private final Block blockOpaque;
	private final int blockOpaqueMetadata;

	public static int getSize(int metadata) {
		return (metadata >> 2) & 0b11;
	}

	public static int getBottomPosition(int metadata) {
		return metadata & 0b11;
	}

	public static int getTopPosition(int metadata) {
		return getBottomPosition(metadata) + getSize(metadata);
	}

	public BlockSlab(final Material material, final Block blockOpaque, final int blockOpaqueMetadata) {
		super(material);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);

		if (!blockOpaque.isOpaqueCube()) {
			WLog.warning("The slab block " + this.getClass().getName() + " use a non opaque block : " + blockOpaque.getLocalizedName());
		}

		this.blockOpaque = blockOpaque;
		this.blockOpaqueMetadata = blockOpaqueMetadata;
		this.useNeighborBrightness = true;
		this.setLightOpacity(255);
	}

	public BlockSlab(final Material material, final Block blockOpaque) {
		this(material, blockOpaque, 0);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y,
	 * z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		int type = metadata >> 2;
		int pos = metadata & 0b11;

		switch (type) {
		case 0:
			setBlockBounds(0, pos * 0.25F, 0, 1, pos * 0.25F + 0.25F, 1);
			break;

		case 1:
			setBlockBounds(0, pos * 0.25F, 0, 1, pos * 0.25F + 0.5F, 1);
			break;

		case 2:
			setBlockBounds(0, pos * 0.25F, 0, 1, pos * 0.25F + 0.75F, 1);
			break;

		default:
			// Avoid to create a full block with isOpaqueCube() == false
			setBlockBounds(0, 0, 0, 1, 1, 1);
		}
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0, 0, 0, 1, 0.25f, 1);
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add
	 * boxes to the list if they intersect the mask.) Parameters: World, X, Y,
	 * Z, mask, list, colliding entity
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z,
			AxisAlignedBB mask, List list, Entity entity) {
		setBlockBoundsBasedOnState(world, x, y, z);

		super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
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
	 * How bright to render this block based on the light its receiving. Args:
	 * iBlockAccess, x, y, z
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public int getMixedBrightnessForBlock(IBlockAccess world, int x, int y, int z) {
		int l = super.getMixedBrightnessForBlock(world, x, y, z);

		if (l == 0) {
			--y;
			Block block = world.getBlock(x, y, z);
			l = world.getLightBrightnessForSkyBlocks(x, y, z, block.getLightValue(world, x, y, z));
		}

		return l;
	}

	/**
	 * Returns true if the given side of this block type should be rendered, if
	 * the adjacent block is at the given coordinates. Args: blockAccess, x, y,
	 * z, side
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		if (side != 1 && side != 0 && !super.shouldSideBeRendered(world, x, y, z, side)) {
			return false;
		} else {
			int x2 = x + Facing.offsetsXForSide[Facing.oppositeSide[side]];
			int y2 = y + Facing.offsetsYForSide[Facing.oppositeSide[side]];
			int z2 = z + Facing.offsetsZForSide[Facing.oppositeSide[side]];
			return !world.getBlock(x2, y2, z2).isOpaqueCube();
		}
	}

	@Override
	public Block getBlock() {
		return blockOpaque;
	}

	@Override
	public int getBlockMetadata() {
		return blockOpaqueMetadata;
	}
}
