package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.client.renderer.block.RendererBlockPlant;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPlant extends BlockGeneric {
	public BlockPlant() {
		super(Material.ground);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return RendererBlockPlant.renderId;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube() {
		return false;
	}
}
