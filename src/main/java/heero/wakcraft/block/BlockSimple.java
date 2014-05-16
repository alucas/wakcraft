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
	protected IIcon icons[] = new IIcon[6];
	protected String textures[] = new String[6];

	public BlockSimple(Material material) {
		super(material);
	}

	@Override
	public Block setBlockTextureName(String textureName) {
		return super.setBlockTextureName(WInfo.MODID.toLowerCase() + ":" + textureName);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		IIcon icon = icons[side];
		if (icon != null) {
			return icon;
		}

		return blockIcon;
	}

	public BlockSimple setBlockTextureName(ForgeDirection side, String name) {
		textures[Arrays.asList(ForgeDirection.VALID_DIRECTIONS).indexOf(side)] = name;

		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		super.registerBlockIcons(register);

		String modBaseName = WInfo.MODID.toLowerCase();
		for (int i = 0; i < 6; i++) {
			if (textures[i] == null) {
				continue;
			}

			icons[i] = register.registerIcon(modBaseName + ":" + textures[i]);
		}
	}
}
