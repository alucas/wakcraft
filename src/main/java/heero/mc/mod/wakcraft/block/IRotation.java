package heero.mc.mod.wakcraft.block;

import net.minecraft.util.EnumFacing;

public interface IRotation {
    public EnumFacing getTopRotation(final int metadata);
    public EnumFacing getBottomRotation(final int metadata);
    public EnumFacing getEastRotation(final int metadata);
    public EnumFacing getWestRotation(final int metadata);
    public EnumFacing getNorthRotation(final int metadata);
    public EnumFacing getSouthRotation(final int metadata);
}
