package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCenterCornerSlab extends BlockYRotationSlab implements ICenterCorner {
	private String nameCorner, nameCenter;
	private IIcon iconCorner, iconCenter;

	public BlockCenterCornerSlab(final Material material, final Block blockOpaque, final int blockOpaqueMetadata, ForgeDirection yRotation) {
		super(material, blockOpaque, blockOpaqueMetadata, yRotation);
	}

	public BlockCenterCornerSlab(final Material material, final Block blockOpaque, ForgeDirection yRotation) {
		this(material, blockOpaque, 0, yRotation);
	}

	public BlockCenterCornerSlab(final Material material, final Block blockOpaque) {
		this(material, blockOpaque, 0, ForgeDirection.NORTH);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		int metadata = world.getBlockMetadata(x, y, z);
		switch (side) {
		case 0:
		case 1:
			boolean t1 = (world.getBlock(x + 1, y, z) instanceof ICenterCorner);
			boolean t2 = (world.getBlock(x, y, z + 1) instanceof ICenterCorner);
			boolean t3 = (world.getBlock(x - 1, y, z) instanceof ICenterCorner);
			boolean t4 = (world.getBlock(x, y, z - 1) instanceof ICenterCorner);

			int neighbor = (t1 ? 1 : 0) + (t2 ? 2 : 0) + (t3 ? 4 : 0)
					+ (t4 ? 8 : 0);

			if (neighbor == 15) {
				return getCenterIcon(side, metadata);
			} else if (neighbor == 12 || neighbor == 9 || neighbor == 03
					|| neighbor == 6) {
				return getCornerIcon(side, metadata);
			}
		}

		return getIcon(side, metadata);
	}

	public BlockCenterCornerSlab setBlockCornerTextureName(String nameCorner) {
		this.nameCorner = nameCorner;

		return this;
	}

	public BlockCenterCornerSlab setBlockCenterTextureName(String nameCenter) {
		this.nameCenter = nameCenter;

		return this;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getCornerIcon(int side, int metadata) {
		if (iconCorner == null) {
			getIcon(side, metadata);
		}

		return iconCorner;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getCenterIcon(int side, int metadata) {
		if (iconCenter == null) {
			getIcon(side, metadata);
		}

		return iconCenter;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer) {
		super.registerBlockIcons(registerer);

		if (nameCorner != null) {
			iconCorner = registerer.registerIcon(WInfo.MODID.toLowerCase() + ":" + nameCorner);
		}

		if (nameCenter != null) {
			iconCenter = registerer.registerIcon(WInfo.MODID.toLowerCase() + ":" + nameCenter);
		}
	}
}
