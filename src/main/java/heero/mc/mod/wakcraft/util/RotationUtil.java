package heero.mc.mod.wakcraft.util;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RotationUtil {
	public static ForgeDirection directions[] = new ForgeDirection[]{ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.EAST};
	public static ForgeDirection getOrientationFromYaw(float yaw) {
		return directions[MathHelper.floor_double((double) (yaw * 4.0F / 360.0F) + 0.5D) & 3];
	}

	public static void setOrientationFromYaw(World world, int x, int y, int z,
			float yaw) {
		ForgeDirection direction = getOrientationFromYaw(yaw);

		int metadata = world.getBlockMetadata(x, y, z) & 0xc;

		switch (direction) {
		case NORTH:
			world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
			break;
		case EAST:
			world.setBlockMetadataWithNotify(x, y, z, metadata | 0x1, 2);
			break;
		case WEST:
			world.setBlockMetadataWithNotify(x, y, z, metadata | 0x2, 2);
			break;
		default:
			world.setBlockMetadataWithNotify(x, y, z, metadata | 0x3, 2);
			break;
		}
	}

	public static ForgeDirection getOrientationFromMetadata(int metadata) {
		metadata &= 0x3;

		return metadata == 0x0 ? ForgeDirection.NORTH
				: metadata == 0x1 ? ForgeDirection.SOUTH
						: metadata == 0x2 ? ForgeDirection.EAST
								: ForgeDirection.WEST;
	}
}
