package heero.wakcraft.block;

import heero.wakcraft.WInfo;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSimple extends Block {
	protected IIcon icons[][] = new IIcon[6][16];
	protected String textures[][] = new String[6][16];

	public BlockSimple(Material material) {
		super(material);
	}

	@Override
	public Block setBlockTextureName(String textureName) {
		return super.setBlockTextureName(WInfo.MODID.toLowerCase() + ":" + textureName);
	}

	public BlockSimple setBlockTextureName(ForgeDirection side, String name) {
		return setBlockTextureName(side, 0, name);
	}

	public BlockSimple setBlockTextureName(ForgeDirection side, int metadata, String name) {
		textures[Arrays.asList(ForgeDirection.VALID_DIRECTIONS).indexOf(side)][metadata] = name;

		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		super.registerBlockIcons(register);

		String modBaseName = WInfo.MODID.toLowerCase();
		for (int i = 0; i < textures.length; i++) {
			for (int j = 0; j < textures[0].length; j++) {
				if (textures[i][j] == null) {
					continue;
				}

				icons[i][j] = register.registerIcon(modBaseName + ":" + textures[i][j]);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		IIcon icon = icons[side][metadata];
		if (icon != null) {
			return icon;
		}

		icon = icons[side][0];
		if (icon != null) {
			return icon;
		}

		return blockIcon;
	}
}
