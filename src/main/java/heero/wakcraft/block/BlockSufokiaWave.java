package heero.wakcraft.block;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSufokiaWave extends BlockYRotation {
	protected static final int nbBlock = 3;
	protected IIcon[] blockIconWave = new IIcon[nbBlock];

	public BlockSufokiaWave() {
		super(Material.sand);

		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":sufokiaWave1");

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockName("SufokiaWave");
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		for (int i = 0; i < nbBlock; i++) {
			subItems.add(new ItemStack(item, 1, i << 2));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister registerer) {
		for (int i = 0; i < nbBlock; i++) {
			blockIconWave[i] = registerer.registerIcon(WakcraftInfo.MODID
					.toLowerCase() + ":sufokiaWave" + (i + 1));
		}
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getTopIcon(int side, int metadata) {
		return blockIconWave[(metadata >> 2) % nbBlock];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getSideIcon(int side, int metadata) {
		return getTopIcon(side, metadata);
	}
}
