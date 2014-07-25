package heero.mc.mod.wakcraft.fight;

import heero.mc.mod.wakcraft.spell.IActiveSpell;
import heero.mc.mod.wakcraft.spell.IRangeMode;
import heero.mc.mod.wakcraft.spell.RangeMode;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

public class FightUtil {
	public static boolean isAimingPositionValid(final ChunkCoordinates fighterPosition, final MovingObjectPosition target, final ItemStack spellStack) {
		int rangeMin = 1;
		int rangeMax = 1;
		IRangeMode rangeMode = RangeMode.DEFAULT;

		if (spellStack != null && spellStack.getItem() instanceof IActiveSpell) {
			IActiveSpell spell = (IActiveSpell) spellStack.getItem();
			int spellLevel = spell.getLevel(spellStack.getItemDamage());
			rangeMin = spell.getRangeMin(spellLevel);
			rangeMax = spell.getRangeMax(spellLevel);
			rangeMode = spell.getRangeMode();
		}

		if (rangeMode == RangeMode.LINE) {
			if (target.blockX != fighterPosition.posX && target.blockZ != fighterPosition.posZ) {
				return false;
			}

			int distanceX = MathHelper.abs_int(fighterPosition.posX - target.blockX);
			if (fighterPosition.posZ == target.blockZ && (distanceX < rangeMin || distanceX > rangeMax)) {
				return false;
			}

			int distanceZ = MathHelper.abs_int(fighterPosition.posZ - target.blockZ);
			if (fighterPosition.posX == target.blockX && (distanceZ < rangeMin || distanceZ > rangeMax)) {
				return false;
			}
		} else {
			int distanceX = MathHelper.abs_int(fighterPosition.posX - target.blockX);
			int distanceZ = MathHelper.abs_int(fighterPosition.posZ - target.blockZ);

			if (distanceX + distanceZ > rangeMax) {
				return false;
			}

			if (distanceX + distanceZ < rangeMin) {
				return false;
			}
		}

		return true;
	}
}
