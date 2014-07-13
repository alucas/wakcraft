package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.client.renderer.block.RendererBlockRotation;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockYRotation extends BlockGeneric implements IRotation, ICenterCorner {
	private String nameCorner, nameCenter;
	private IIcon iconCorner, iconCenter;

	public BlockYRotation(Material material) {
		super(material);
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return RendererBlockRotation.renderId;
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and
	 * wood.
	 */
	@Override
	public int damageDropped(int metadata) {
		return metadata & 12;
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

	@SideOnly(Side.CLIENT)
	public BlockYRotation setBlockCornerTextureName(String nameCorner) {
		this.nameCorner = nameCorner;

		return this;
	}

	@SideOnly(Side.CLIENT)
	public BlockYRotation setBlockCenterTextureName(String nameCenter) {
		this.nameCenter = nameCenter;

		return this;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getCornerIcon(int side, int metadata) {
		return iconCorner;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getCenterIcon(int side, int metadata) {
		return iconCenter;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer) {
		super.registerBlockIcons(registerer);

		iconCorner = registerer.registerIcon(WInfo.MODID.toLowerCase() + ":" + nameCorner);
		iconCenter = registerer.registerIcon(WInfo.MODID.toLowerCase() + ":" + nameCenter);
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase player, ItemStack itemBlock) {
		super.onBlockPlacedBy(world, x, y, z, player, itemBlock);

		RotationUtil.setYRotationFromYaw(world, x, y, z, player.rotationYaw);
	}

	@Override
	public ForgeDirection getTopRotation(int metadata) {
		return RotationUtil.getYRotationFromMetadata(metadata);
	}

	@Override
	public ForgeDirection getBottomRotation(int metadata) {
		return RotationUtil.getYRotationFromMetadata(metadata);
	}

	@Override
	public ForgeDirection getEastRotation(int metadata) {
		return ForgeDirection.UP;
	}

	@Override
	public ForgeDirection getWestRotation(int metadata) {
		return ForgeDirection.UP;
	}

	@Override
	public ForgeDirection getNorthRotation(int metadata) {
		return ForgeDirection.UP;
	}

	@Override
	public ForgeDirection getSouthRotation(int metadata) {
		return ForgeDirection.UP;
	}
}
