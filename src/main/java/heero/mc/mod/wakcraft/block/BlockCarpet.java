package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCarpet extends BlockYRotation {
	private IIcon iconCorner, iconCenter;

	public BlockCarpet() {
		super(Material.cloth);

		setBlockTextureName("carpet1");

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockName("Carpet1");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer) {
		super.registerBlockIcons(registerer);

		iconCorner = registerer.registerIcon(WInfo.MODID.toLowerCase() + ":carpet1Corner");
		iconCenter = registerer.registerIcon(WInfo.MODID.toLowerCase() + ":carpet1Center");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getCornerIcon(int side, int metadata) {
		return iconCorner;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getCenterIcon(int side, int metadata) {
		return iconCenter;
	}
}
