package heero.mc.mod.wakcraft.util;

import heero.mc.mod.wakcraft.WConfig;
import net.minecraft.world.World;

public class WorldUtil {
    public static boolean isMainWorld(final World world) {
        return world.provider.getDimensionId() == 0;
    }

    public static boolean isHavenBagWorld(final World world) {
        return world.provider.getDimensionId() == WConfig.getHavenBagDimensionId();
    }
}
