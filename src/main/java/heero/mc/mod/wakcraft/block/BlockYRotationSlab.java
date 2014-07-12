package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.client.renderer.block.RendererBlockRotation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockYRotationSlab extends BlockSlab implements IRotation {
	protected final ForgeDirection yRotation;

	public BlockYRotationSlab(final Material material, final Block blockOpaque, final int blockOpaqueMetadata, ForgeDirection yRotation) {
		super(material, blockOpaque, blockOpaqueMetadata);

		setCreativeTab(null);

		this.yRotation = yRotation;
	}

	public BlockYRotationSlab(final Material material, final Block blockOpaque, ForgeDirection yRotation) {
		this(material, blockOpaque, 0, yRotation);
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

	@Override
	public ForgeDirection getTopRotation(int metadata) {
		return yRotation;
	}

	@Override
	public ForgeDirection getBottomRotation(int metadata) {
		return yRotation;
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
