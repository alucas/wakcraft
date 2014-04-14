package heero.wakcraft.block;

import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.reference.References;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCarpet extends BlockYOrientation {
	private IIcon iconCarpet1, iconCarpet1Corner, iconCarpet1Center;

	public BlockCarpet() {
		super(Material.cloth);

		setBlockTextureName(References.MODID.toLowerCase() + ":carpet1");

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockName("Carpet1");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer) {
		super.registerBlockIcons(registerer);

		iconCarpet1 = registerer.registerIcon(References.MODID.toLowerCase() + ":carpet1");
		iconCarpet1Corner = registerer.registerIcon(References.MODID.toLowerCase() + ":carpet1Corner");
		iconCarpet1Center = registerer.registerIcon(References.MODID.toLowerCase() + ":carpet1Center");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getTopIcon(int side, int metadata) {
		return iconCarpet1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getSideIcon(int side, int metadata) {
		return iconCarpet1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getCornerIcon(int side, int metadata) {
		return iconCarpet1Corner;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getCenterIcon(int side, int metadata) {
		return iconCarpet1Center;
	}
}
