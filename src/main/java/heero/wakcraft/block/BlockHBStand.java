package heero.wakcraft.block;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHBStand extends BlockYRotation {

	public BlockHBStand() {
		super(Material.wood);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":hbstand");
		setBlockName("HBStand");
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