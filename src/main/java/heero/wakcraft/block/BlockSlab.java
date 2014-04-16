package heero.wakcraft.block;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockSlab extends Block {
	protected String[] names;

	protected IIcon[] blockIconTop = new IIcon[4];
	protected IIcon[] blockIconSide = new IIcon[4];
	protected IIcon[] blockIconDemiSide = new IIcon[4];

	public BlockSlab(Material material) {
		super(material);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockName("Slab");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < names.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer) {
		for (int i = 0; i < names.length; i++) {
			blockIconTop[i] = registerer.registerIcon(WakcraftInfo.MODID.toLowerCase() + ":" + names[i] + "Top");
			blockIconSide[i] = registerer.registerIcon(WakcraftInfo.MODID.toLowerCase() + ":" + names[i] + "Side");
			blockIconDemiSide[i] = registerer.registerIcon(WakcraftInfo.MODID.toLowerCase() + ":" + names[i] + "DemiSide");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		int type = metadata % names.length;

		if (side == 1) {
			return blockIconTop[type];
		}

		if (metadata == 0) {
			return blockIconDemiSide[type];
		}

		return blockIconSide[type];
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y,
			int z) {
		int state = (world.getBlockMetadata(x, y, z) >> 2) % 3;
		if (state == 1) {
			setBlockBounds(0, 0.5f, 0, 1, 1, 1);
		} else if (state == 2) {
			setBlockBounds(0, 0, 0, 1, 1, 1);
		} else {
			setBlockBounds(0, 0, 0, 1, 0.5f, 1);
		}
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0, 0, 0, 1, 0.5f, 1);
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
