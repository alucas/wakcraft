package heero.mc.mod.wakcraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityPhoenix extends TileEntity {
	/**
	 * Return an {@link AxisAlignedBB} that controls the visible scope of a
	 * {@link TileEntitySpecialRenderer} associated with this {@link TileEntity}
	 * Defaults to the collision bounding box
	 * {@link Block#getCollisionBoundingBoxFromPool(World, int, int, int)}
	 * associated with the block at this location.
	 * 
	 * @return an appropriately size {@link AxisAlignedBB} for the
	 *         {@link TileEntity}
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 2, zCoord + 1);
	}

	/**
	 * Determines if this TileEntity requires update calls.
	 * 
	 * @return True if you want updateEntity() to be called, false if not
	 */
	@Override
	public boolean canUpdate() {
		return false;
	}
}
