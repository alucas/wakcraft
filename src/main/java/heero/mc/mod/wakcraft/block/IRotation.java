package heero.mc.mod.wakcraft.block;

import net.minecraftforge.common.util.ForgeDirection;

public interface IRotation {
	ForgeDirection getTopRotation(final int metadata);
	ForgeDirection getBottomRotation(final int metadata);
	ForgeDirection getEastRotation(final int metadata);
	ForgeDirection getWestRotation(final int metadata);
	ForgeDirection getNorthRotation(final int metadata);
	ForgeDirection getSouthRotation(final int metadata);
}
