package heero.wakcraft.block;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.tileentity.TileEntityDragoexpress;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockDragoexpress extends BlockContainer {

	public BlockDragoexpress() {
		super(Material.wood);
		
		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":dragoexpress");
		setCreativeTab(WakcraftCreativeTabs.tabSpecialBlock);
		setBlockName("dragoexpress");
		setLightOpacity(0);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityDragoexpress();
	}
	
	/**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
	@Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);

        if (!world.isRemote)
        {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
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
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
			break;
		case 1:
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
			break;
		case 3:
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
			break;
		default:
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (itemBlock.hasDisplayName()) {
			((TileEntityFurnace) world.getTileEntity(x, y, z))
					.func_145951_a(itemBlock.getDisplayName());
		}
	}
}
