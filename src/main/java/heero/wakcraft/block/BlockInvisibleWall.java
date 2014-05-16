package heero.wakcraft.block;

import heero.wakcraft.WConfig;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockInvisibleWall extends Block {

	public BlockInvisibleWall() {
		super(Material.air);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockName("InvisibleWall");
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
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		if (world instanceof World && ((World) world).provider.dimensionId == WConfig.havenBagDimensionId) {
			return 15;
		}

		return super.getLightValue(world, x, y, z);
	}
}
