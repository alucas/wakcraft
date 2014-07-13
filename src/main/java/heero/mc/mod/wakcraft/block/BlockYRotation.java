package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.client.renderer.block.RendererBlockRotation;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockYRotation extends BlockGeneric implements IRotation {
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
