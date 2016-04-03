package heero.mc.mod.wakcraft.util;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class RotationUtil {
    public static final PropertyDirection PROP_Y_ROTATION = PropertyDirection.create("propertyYRotation", EnumFacing.Plane.HORIZONTAL);

    public static EnumFacing getYRotationFromYaw(final float yaw) {
        return EnumFacing.fromAngle(yaw);
    }

    public static EnumFacing getYRotationFromMetadata(final int metadata) {
        return EnumFacing.getHorizontal(metadata & 0b0011);
    }

    public static int getMetadataFromYRotation(final EnumFacing yRotation) {
        return yRotation.getHorizontalIndex() & 0b0011;
    }

    public static void setYRotationFromYaw(final World world, final BlockPos pos, final IBlockState state, final float yaw) {
        if (!state.getProperties().containsKey(PROP_Y_ROTATION)) {
            return;
        }

        world.setBlockState(pos, state.withProperty(PROP_Y_ROTATION, getYRotationFromYaw(yaw)));
    }

    public static EnumFacing getYRotationFromState(final IBlockState state) {
        if (!state.getProperties().containsKey(PROP_Y_ROTATION)) {
            return null;
        }

        return state.getValue(PROP_Y_ROTATION);
    }

    public static int getNorthRotation(final EnumFacing direction) {
        return direction == EnumFacing.DOWN ? 3 : direction == EnumFacing.EAST ? 2 : direction == EnumFacing.WEST ? 1 : 0;
    }

    public static int getSouthRotation(final EnumFacing direction) {
        return direction == EnumFacing.DOWN ? 3 : direction == EnumFacing.WEST ? 2 : direction == EnumFacing.EAST ? 1 : 0;
    }

    public static int getTopRotation(final EnumFacing direction) {
        return direction == EnumFacing.SOUTH ? 3 : direction == EnumFacing.WEST ? 2 : direction == EnumFacing.EAST ? 1 : 0;
    }

    public static int getYNegRotation(final EnumFacing direction) {
        return direction == EnumFacing.SOUTH ? 3 : direction == EnumFacing.EAST ? 2 : direction == EnumFacing.WEST ? 1 : 0;
    }

    public static int getEastRotation(final EnumFacing direction) {
        return direction == EnumFacing.DOWN ? 3 : direction == EnumFacing.SOUTH ? 2 : direction == EnumFacing.NORTH ? 1 : 0;
    }

    public static int getWestRotation(final EnumFacing direction) {
        return direction == EnumFacing.DOWN ? 3 : direction == EnumFacing.NORTH ? 2 : direction == EnumFacing.SOUTH ? 1 : 0;
    }
}
