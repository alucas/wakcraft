package heero.wakcraft.block;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.WakcraftBlocks;
import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.network.GuiHandler;
import heero.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHavenGemWorkbench extends BlockContainer {
	public BlockHavenGemWorkbench() {
		super(Material.wood);
		setCreativeTab(WakcraftCreativeTabs.tabSpecialBlock);
		setBlockName("HavenGemWorkbench");
		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":havengemworkbench");
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if (world.isRemote) {
			return true;
		}

		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null && !(tile instanceof TileEntityHavenGemWorkbench)) {
			return true;
		}

		player.openGui(Wakcraft.instance, GuiHandler.GUI_HAVEN_GEM_WORKBENCH, world, x, y, z);

		return true;
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntityHavenGemWorkbench();
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z);
	}

	/**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
	@Override
    public void onBlockAdded(World world, int x, int y, int z) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				world.setBlock(x - 2 + i, y - 1, z + j, Blocks.stone);
			}
		}

		for (int i = 0; i < 2; i++) {
			world.setBlock(x - 1 + i, y - 1, z + 4, Blocks.stone_slab, 8, 2);
		}

		for (int i = 0; i < 2; i++) {
			world.setBlock(x - 4 + i, y - 1, z + 7, Blocks.stone_slab, 8, 2);
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				world.setBlock(x - 2 + i, y - 1, z + 5 + j, Blocks.planks);
			}
		}

		fillWalls(world, x - 5, y - 1, z - 1, 4, WakcraftBlocks.invisiblewall, 0);

		super.onBlockAdded(world, x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
		fillAir(world, x - 5, y - 1, z - 1);

		super.breakBlock(world, x, y, z, block, metadata);
	}

	private void fillWalls(World world, int x, int y, int z, int nbLayer, Block block, int metadata) {
		for (int i = 0; i < 21; i++) {
			for (int j = 0; j < 24; j++) {
				if (world.getBlock(x + i, y, z + j) == Blocks.air) {
					for (int k = 0; k < 4; k++) {
						world.setBlock(x + i, y + k, z + j, block, metadata, 2);
					}
				}
			}
		}
	}

	private void fillAir(World world, int x, int y, int z) {
		for (int i = 0; i < 21; i++) {
			for (int j = 0; j < 24; j++) {
				for (int k = 0; k < 4; k++) {
					world.setBlockToAir(x + i, y + k, z + j);
				}
			}
		}
	}
}
