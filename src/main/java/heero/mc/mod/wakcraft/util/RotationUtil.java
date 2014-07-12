package heero.mc.mod.wakcraft.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RotationUtil {
	public static List<ForgeDirection> yRotation = new ArrayList<>();
	static {
		yRotation.add(ForgeDirection.SOUTH);
		yRotation.add(ForgeDirection.WEST);
		yRotation.add(ForgeDirection.NORTH);
		yRotation.add(ForgeDirection.EAST);
	}

	public static ForgeDirection getYRotationFromYaw(float yaw) {
		return yRotation.get(MathHelper.floor_double((double) (yaw * 4.0F / 360.0F) + 0.5D) & 0b11);
	}

	public static ForgeDirection getYRotationFromMetadata(int metadata) {
		return yRotation.get(metadata & 0b11);
	}

	public static void setYRotationFromYaw(World world, int x, int y, int z,
			float yaw) {
		ForgeDirection rotation = getYRotationFromYaw(yaw);

		int metadata = world.getBlockMetadata(x, y, z);

		world.setBlockMetadataWithNotify(x, y, z, (metadata & 0b1100) + yRotation.indexOf(rotation), 2);
	}

	public static int getNorthRotation(ForgeDirection direction) {
		return direction == ForgeDirection.DOWN ? 3 : direction == ForgeDirection.EAST ? 2 : direction == ForgeDirection.WEST ? 1 : 0 ;
	}

	public static int getSouthRotation(ForgeDirection direction) {
		return direction == ForgeDirection.DOWN ? 3 : direction == ForgeDirection.WEST ? 2 : direction == ForgeDirection.EAST ? 1 : 0 ;
	}

	public static int getTopRotation(ForgeDirection direction) {
		return direction == ForgeDirection.SOUTH ? 3 : direction == ForgeDirection.WEST ? 2 : direction == ForgeDirection.EAST ? 1 : 0 ;
	}

	public static int getYNegRotation(ForgeDirection direction) {
		return direction == ForgeDirection.SOUTH ? 3 : direction == ForgeDirection.EAST ? 2 : direction == ForgeDirection.WEST ? 1 : 0 ;
	}

	public static int getEastRotation(ForgeDirection direction) {
		return direction == ForgeDirection.DOWN ? 3 : direction == ForgeDirection.SOUTH ? 2 : direction == ForgeDirection.NORTH ? 1 : 0 ;
	}

	public static int getWestRotation(ForgeDirection direction) {
		return direction == ForgeDirection.DOWN ? 3 : direction == ForgeDirection.NORTH ? 2 : direction == ForgeDirection.SOUTH ? 1 : 0 ;
	}
}
