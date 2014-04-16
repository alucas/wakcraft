package heero.wakcraft.util;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RotationUtil {
	public static void setOrientationFromYaw(World world, int x, int y, int z,
			float yaw) {
		int l = MathHelper.floor_double((double) (yaw * 4.0F / 360.0F) + 0.5D) & 3;

		switch (l) {
		case 0:
			setOrientation(world, x, y, z, ForgeDirection.SOUTH);
			break;
		case 1:
			setOrientation(world, x, y, z, ForgeDirection.WEST);
			break;
		case 2:
			setOrientation(world, x, y, z, ForgeDirection.NORTH);
			break;
		default:
			setOrientation(world, x, y, z, ForgeDirection.EAST);
		}
	}

	public static void setOrientation(World world, int x, int y, int z,
			ForgeDirection direction) {

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
