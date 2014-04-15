package heero.wakcraft.block;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPillar extends Block {
	protected IIcon blockIconTop;
	protected IIcon blockIconSide;

	public BlockPillar() {
		super(Material.wood);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":pillarSide");
		setBlockName("Pillar");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if (side == 0 || side == 1) {
			return blockIconTop;
		}

		return blockIconSide;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer) {
		super.registerBlockIcons(registerer);

		blockIconTop = registerer.registerIcon(WakcraftInfo.MODID.toLowerCase() + ":pillarTop");
		blockIconSide = registerer.registerIcon(WakcraftInfo.MODID.toLowerCase() + ":pillarSide");
	}
}
