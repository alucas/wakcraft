package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WInfo;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSufokiaWave extends BlockCenterCorner {
	protected static final int nbBlock = 3;

	protected IIcon wave3Corner;

	public BlockSufokiaWave() {
		super(Material.sand);

		setBlockTextureName("sufokiaWave1");

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
		super.registerBlockIcons(registerer);

		for (int i = 0; i < nbBlock; i++) {
			IIcon icon = registerer.registerIcon(WInfo.MODID
					.toLowerCase() + ":sufokiaWave" + (i + 1));

			for (int j = 0; j < 6; j++) {
				icons[j][(i<<2)] = icon;
				icons[j][(i<<2) + 1] = icon;
				icons[j][(i<<2) + 2] = icon;
				icons[j][(i<<2) + 3] = icon;
			}
		}

		wave3Corner = registerer.registerIcon(WInfo.MODID
				.toLowerCase() + ":sufokiaWave3Corner");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getCornerIcon(int side, int metadata) {
		if ((metadata >> 2) == 2) {
			return wave3Corner;
		}

		return super.getCornerIcon(side, metadata);
	}
}
