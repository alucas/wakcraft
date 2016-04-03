package heero.mc.mod.wakcraft.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityDragoExpress extends TileEntity {
    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.fromBounds(pos.getX() - 6, pos.getY(), pos.getZ() - 6, pos.getX() + 6, pos.getY() + 2, pos.getZ() + 6);
    }
}
