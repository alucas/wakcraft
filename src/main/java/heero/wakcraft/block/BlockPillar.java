package heero.wakcraft.block;

import heero.wakcraft.renderer.RenderPillarBlock;
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

public abstract class BlockPillar extends Block {
	protected boolean isCorner = false;
	protected boolean isCenter = false;
	
	public BlockPillar(Material material) {
		super(material);
	}
	
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType()
    {
        return RenderPillarBlock.renderId;
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int metadata)
    {
        return metadata & 12;
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
			if (isCenter) {
				return getCenterIcon(side, metadata);
			} else if (isCorner) {
				return getCornerIcon(side, metadata);
			}

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
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y,
			int z) {
		super.setBlockBoundsBasedOnState(world, x, y, z);

		BlockPillar pillarBlock = (BlockPillar) world.getBlock(x, y, z);
		int blockId = Block.getIdFromBlock(pillarBlock);
		boolean t1 = Block.getIdFromBlock(world.getBlock(x + 1, y, z)) == blockId;
		boolean t2 = Block.getIdFromBlock(world.getBlock(x, y, z + 1)) == blockId;
		boolean t3 = Block.getIdFromBlock(world.getBlock(x - 1, y, z)) == blockId;
		boolean t4 = Block.getIdFromBlock(world.getBlock(x, y, z - 1)) == blockId;

		int neighbor = (t1 ? 0b1 : 0) + (t2 ? 0b10 : 0) + (t3 ? 0b100 : 0)
				+ (t4 ? 0b1000 : 0);

		if (neighbor == 0b1111) {
			isCenter = true;
			isCorner = false;
		} else if (neighbor == 0b1100 || neighbor == 0b1001
				|| neighbor == 0b0011 || neighbor == 0b0110) {
			isCenter = false;
			isCorner = true;
		} else {
			isCorner = false;
			isCenter = false;
		}
	}
    
	/**
     * Called when the block is placed in the world.
     */
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemBlock)
    {
		super.onBlockPlacedBy(world, x, y, z, player, itemBlock);
		
		setOrientationFromYaw(world, x, y, z, player.rotationYaw);
	}
    
    public static void setOrientationFromYaw(World world, int x, int y, int z, float yaw){
        int l = MathHelper.floor_double((double)(yaw * 4.0F / 360.0F) + 0.5D) & 3;
        
        switch (l) {
		case 0:
			setOrientation(world, x, y, z, ForgeDirection.SOUTH);
			break;
		case 1:
			setOrientation(world, x, y, z, ForgeDirection.WEST);
			break;
		case 2:
			setOrientation(world, x, y, z, ForgeDirection.NORTH);
			break;
		default:
			setOrientation(world, x, y, z, ForgeDirection.EAST);
		}
    }
    
    public static void setOrientation(World world, int x, int y, int z,
			ForgeDirection direction) {
		
		int metadata = world.getBlockMetadata(x, y, z) & 0xc;
		
		switch (direction) {
		case NORTH:
			world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
			break;
		case EAST:
			world.setBlockMetadataWithNotify(x, y, z, metadata | 0x1, 2);
			break;
		case WEST:
			world.setBlockMetadataWithNotify(x, y, z, metadata | 0x2, 2);
			break;
		default:
			world.setBlockMetadataWithNotify(x, y, z, metadata | 0x3, 2);
			break;
		}
	}

	public static ForgeDirection getOrientationFromMetadata(int metadata) {
		metadata &= 0x3;

		return metadata == 0x0 ? ForgeDirection.NORTH
				: metadata == 0x1 ? ForgeDirection.SOUTH
						: metadata == 0x2 ? ForgeDirection.EAST
								: ForgeDirection.WEST;
	}
}
