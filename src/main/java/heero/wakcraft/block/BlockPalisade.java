package heero.wakcraft.block;

import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.reference.References;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPalisade extends BlockPillar {
	public BlockPalisade() {
		super(Material.wood);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockName("Palisade");

		setBlockTextureName(References.MODID.toLowerCase() + ":" + "palisade");
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y,
			int z) {
		super.setBlockBoundsBasedOnState(world, x, y, z);

		int orientation = world.getBlockMetadata(x, y, z) & 3;
		switch (orientation) {
		case 0:
			setBlockBounds(0, 0, 0, 1, 1, 0.1875f);
			break;
		case 1:
			setBlockBounds(0.8125f, 0, 0, 1, 1, 1);
			break;
		case 2:
			setBlockBounds(0, 0, 0, 0.1875f, 1, 1);
			break;
		default:
			setBlockBounds(0, 0, 0.8125f, 1, 1, 1);
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getTopIcon(int side, int metadata) {
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getSideIcon(int side, int metadata) {
		return blockIcon;
	}
}
