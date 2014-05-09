package heero.wakcraft.block;

import heero.wakcraft.tileentity.TileEntityHavenBagProperties;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHavenBagProperties extends BlockContainer {

	public BlockHavenBagProperties() {
		super(Material.air);

		setBlockName("HavenBagProperties");
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
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityHavenBagProperties();
	}
}
