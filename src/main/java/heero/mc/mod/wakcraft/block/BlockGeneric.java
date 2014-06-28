package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WInfo;

import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGeneric extends Block {
	protected IIcon icons[][] = new IIcon[6][16];
	protected String textures[][] = new String[6][16];

	public BlockGeneric(Material material) {
		super(material);

		setBlockUnbreakable();
	}

	/**
	 * Set the default texture name of your block. With the Wakcraft mod id
	 * prepended.
	 */
	@Override
	public Block setBlockTextureName(String textureName) {
		return setBlockTextureName(WInfo.MODID, textureName);
	}

	/**
	 * Set the default texture name of your block.
	 */
	public Block setBlockTextureName(String modId, String textureName) {
		return super.setBlockTextureName(modId.toLowerCase() + ":" + textureName);
	}

	public BlockGeneric setBlockTextureName(ForgeDirection side, String name) {
		return setBlockTextureName(side, 0, name);
	}

	public BlockGeneric setBlockTextureName(int metadata, String name) {
		for (int i = 0; i < 6; i++) {
			textures[Arrays.asList(ForgeDirection.VALID_DIRECTIONS).indexOf(i)][metadata] = name;
		}

		return this;
	}

	public BlockGeneric setBlockTextureName(ForgeDirection side, int metadata, String name) {
		textures[Arrays.asList(ForgeDirection.VALID_DIRECTIONS).indexOf(side)][metadata] = name;

		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		super.registerBlockIcons(register);

		String modBaseName = WInfo.MODID.toLowerCase();
		HashMap<String, IIcon> iconsCache = new HashMap<String, IIcon>();
		for (int i = 0; i < textures.length; i++) {
			for (int j = 0; j < textures[0].length; j++) {
				if (textures[i][j] == null) {
					continue;
				}

				IIcon icon = iconsCache.get(textures[i][j]);
				if (icon == null) {
					icon = register.registerIcon(modBaseName + ":" + textures[i][j]);
					iconsCache.put(textures[i][j], icon);
				}

				icons[i][j] = icon;
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
