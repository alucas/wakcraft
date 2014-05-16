package heero.wakcraft.block;

import heero.wakcraft.WInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWood extends BlockYRotation {

	public BlockWood() {
		super(Material.wood);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockTextureName(WInfo.MODID.toLowerCase() + ":wood");
		setBlockName("Wood");
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
