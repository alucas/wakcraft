package heero.mc.mod.wakcraft.spell;

import net.minecraft.util.MathHelper;

public class SpellUtil {
	public static int getLevelFromXp(final int xp) {
		if (xp <= 100) {
			return 1;
		}

		return (int) ((MathHelper.sqrt_double((((int) ((xp - 100.0) / 1000.0) * 2.0) + (1.0 / 4.0)) * 4.0) - 1.0) / 2.0);
	}
}
