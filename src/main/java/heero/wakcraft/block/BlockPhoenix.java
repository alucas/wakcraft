package heero.wakcraft.block;

import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.reference.References;
import heero.wakcraft.tileentity.TileEntityPhoenix;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPhoenix extends BlockContainer {

	public BlockPhoenix() {
		super(Material.wood);
		
		setBlockTextureName(References.MODID.toLowerCase() + ":phoenix");
		
		setCreativeTab(WakcraftCreativeTabs.tabSpecialBlock);
		setBlockName("phoenix");
		setLightOpacity(0);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPhoenix();
	}
	
	/**
     * Called when the block is placed in the world.
     */
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemBlock)
    {
        int l = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        
		switch (l) {
		case 0:
			setOrientation(world, x, y, z, ForgeDirection.NORTH);
			break;
		case 1:
			setOrientation(world, x, y, z, ForgeDirection.EAST);
			break;
		case 2:
			setOrientation(world, x, y, z, ForgeDirection.SOUTH);
			break;
		default:
			setOrientation(world, x, y, z, ForgeDirection.WEST);
		}
	}
	
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
	@Override
    public boolean isOpaqueCube()
    {
        return false;
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
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     */
    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity)
    {
    	setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.5F, 1.0F);
    	super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
    	setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    @Override
    public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_)
    {
    	setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    	MovingObjectPosition mop1 = super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
    	setBlockBounds(0.188F, 0.5F, 0.188F, 0.813F, 1.0F, 0.813F);
    	MovingObjectPosition mop2 = super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
    	
    	double d1 = 0, d2 = 0;
    	if (mop1 != null)
    	{
    		d1 = mop1.hitVec.squareDistanceTo(p_149731_6_);
    	}
    	
    	if (mop2 != null)
    	{
    		d2 = mop2.hitVec.squareDistanceTo(p_149731_6_);
    	}
        
    	setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

        return (d1 > d2) ? mop1 : mop2;
    }
    
    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        return false;
    }
    
    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    @Override
    public boolean canProvidePower()
    {
        return true;
    }
    
    @Override
    public int isProvidingWeakPower(IBlockAccess blockAccess, int x, int y, int z, int p_149709_5_)
    {
        return getPoweredFromMetadata(blockAccess.getBlockMetadata(x, y, z)) ? 1 : 0;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess blockAccess, int x, int y, int z, int p_149748_5_)
    {
        return isProvidingWeakPower(blockAccess, x, y, z, p_149748_5_);
    }
    
    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int p_149727_6_, float p_149727_7_,
			float p_149727_8_, float p_149727_9_) {
		boolean powered = getPoweredFromMetadata(world.getBlockMetadata(x, y, z));

		if (!powered) {
			setPowered(world, x, y, z, true);
//			world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
			world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "random.click", 0.3F, 1F);
			world.notifyBlocksOfNeighborChange(x, y, z, this);
			world.scheduleBlockUpdate(x, y, z, this, 5);
		}

		return true;
	}
    
    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World world, int x, int y, int z, Random p_149674_5_)
    {
        if (!world.isRemote)
        {
            boolean isPowered = getPoweredFromMetadata(world.getBlockMetadata(x, y, z));
            
            if (isPowered)
            {
            	setPowered(world, x, y, z, false);
                world.notifyBlocksOfNeighborChange(x, y, z, this);
            }
        }
    }

	public static void setOrientation(World world, int x, int y, int z,
			ForgeDirection direction) {
		
		int metadata = world.getBlockMetadata(x, y, z) & 0xc;
		
		switch (direction) {
		case NORTH:
			world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
			break;
		case SOUTH:
			world.setBlockMetadataWithNotify(x, y, z, metadata | 0x1, 2);
			break;
		case EAST:
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

	public static void setPowered(World world, int x, int y, int z,
			boolean powered) {
		int metadata = world.getBlockMetadata(x, y, z) & 0xb;
		
		world.setBlockMetadataWithNotify(x, y, z, metadata
				| (powered ? 0x4 : 0x0), 2);
	}

	public static boolean getPoweredFromMetadata(int metadata) {
		metadata &= 0x4;

		return metadata == 0x4;
	}
}
