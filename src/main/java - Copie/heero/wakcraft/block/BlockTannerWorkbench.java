package heero.wakcraft.block;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.tileentity.TileEntityTannerWorkbench;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTannerWorkbench extends BlockContainer {

	public BlockTannerWorkbench() {
		super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	public boolean onBlockActivated(World worldObj, int blockXPos,
			int blockYPos, int blockZPos, EntityPlayer player, int p_149727_6_,
			float p_149727_7_, float p_149727_8_, float p_149727_9_) {

		if (worldObj.isRemote) { // Server
			return true;
		}

		// Client
		TileEntity tile_entity = worldObj.getTileEntity(blockXPos, blockYPos, blockZPos);
		if (tile_entity == null) {
			return false;
		}
		
		player.openGui(Wakcraft.instance, 0, worldObj, blockXPos, blockYPos,
				blockZPos);

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityTannerWorkbench();
	}
}
