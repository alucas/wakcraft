package heero.wakcraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockGrass extends Block {
	private IIcon blockIconTop;
	private IIcon blockIconSide;
	private IIcon blockIconDemiSide;

	public BlockGrass() {
		super(Material.grass);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":grassTop");
		setBlockName("Grass");

		setBlockBounds(0, 0, 0, 1, 0.5f, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer) {
		blockIconTop = registerer.registerIcon(WakcraftInfo.MODID.toLowerCase() + ":grassTop");
		blockIconSide = registerer.registerIcon(WakcraftInfo.MODID.toLowerCase() + ":grassSide");
		blockIconDemiSide = registerer.registerIcon(WakcraftInfo.MODID.toLowerCase() + ":grassDemiSide");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if (side == 1) {
			return blockIconTop;
		}

		if (metadata == 1) {
			return blockIconSide;
		}

		return blockIconDemiSide;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y,
			int z) {
		if (world.getBlockMetadata(x, y, z) == 1) {
			setBlockBounds(0, 0, 0, 1, 1, 1);
		} else {
			setBlockBounds(0, 0, 0, 1, 0.5f, 1);
		}
	}
	
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
