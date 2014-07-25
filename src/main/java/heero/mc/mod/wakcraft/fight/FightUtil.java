package heero.mc.mod.wakcraft.fight;

import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.helper.FightHelper;
import heero.mc.mod.wakcraft.spell.IActiveSpell;
import heero.mc.mod.wakcraft.spell.IRangeMode;
import heero.mc.mod.wakcraft.spell.RangeMode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

public class FightUtil {
	public static boolean canCastSpell(final EntityPlayer fighter, final ChunkCoordinates targetPosition) {
		ChunkCoordinates fighterPosition = FightHelper.getCurrentPosition(fighter);
		ItemStack spellStack = FightHelper.getCurrentSpell(fighter);
		if (!FightUtil.isAimingPositionValid(fighterPosition, targetPosition, spellStack)) {
			return false;
		}

		if (!FightUtil.isSpellCostAvailable(fighter, spellStack)) {
			return false;
		}

		if (!FightUtil.isSpellConditionValid(fighter, spellStack)) {
			return false;
		}

		return true;
	}

	public static boolean isAimingPositionValid(final ChunkCoordinates fighterPosition, final MovingObjectPosition target, final ItemStack spellStack) {
		return isAimingPositionValid(fighterPosition, target.blockX, target.blockY, target.blockZ, spellStack);
	}

	public static boolean isAimingPositionValid(final ChunkCoordinates fighterPosition, final ChunkCoordinates target, final ItemStack spellStack) {
		return isAimingPositionValid(fighterPosition, target.posX, target.posY, target.posZ, spellStack);
	}

	public static boolean isAimingPositionValid(final ChunkCoordinates fighterPosition, final int posX, final int posY, final int posZ, final ItemStack spellStack) {
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
			if (posX != fighterPosition.posX && posZ != fighterPosition.posZ) {
				return false;
			}

			int distanceX = MathHelper.abs_int(fighterPosition.posX - posX);
			if (fighterPosition.posZ == posZ && (distanceX < rangeMin || distanceX > rangeMax)) {
				return false;
			}

			int distanceZ = MathHelper.abs_int(fighterPosition.posZ - posZ);
			if (fighterPosition.posX == posX && (distanceZ < rangeMin || distanceZ > rangeMax)) {
				return false;
			}
		} else {
			int distanceX = MathHelper.abs_int(fighterPosition.posX - posX);
			int distanceZ = MathHelper.abs_int(fighterPosition.posZ - posZ);

			if (distanceX + distanceZ > rangeMax) {
				return false;
			}

			if (distanceX + distanceZ < rangeMin) {
				return false;
			}
		}

		return true;
	}

	public static boolean isSpellCostAvailable(final EntityPlayer fighter, final ItemStack spellStack) {
		int wakfuPoint = FightHelper.getFightCharacteristic(fighter, Characteristic.WAKFU);
		int movementPoint = FightHelper.getFightCharacteristic(fighter, Characteristic.MOVEMENT);
		int actionPoint = FightHelper.getFightCharacteristic(fighter, Characteristic.ACTION);

		int wakfuCost = getSpellWakfuCost(spellStack);
		int movementCost = getSpellMovementCost(spellStack);
		int actionCost = getSpellActionCost(spellStack);

		if (wakfuCost > wakfuPoint || movementCost > movementPoint || actionCost > actionPoint) {
			return false;
		}

		return true;
	}

	public static int getSpellWakfuCost(final ItemStack spellStack) {
		if (spellStack == null || spellStack.getItem() == null) {
			return 0;
		}

		if (spellStack.getItem() instanceof IActiveSpell) {
			return ((IActiveSpell) spellStack.getItem()).getWakfuCost();
		}

		return 0;
	}

	public static int getSpellMovementCost(final ItemStack spellStack) {
		if (spellStack == null || spellStack.getItem() == null) {
			return 0;
		}

		if (spellStack.getItem() instanceof IActiveSpell) {
			return ((IActiveSpell) spellStack.getItem()).getMovementCost();
		}

		return 0;
	}

	public static int getSpellActionCost(final ItemStack spellStack) {
		if (spellStack == null || spellStack.getItem() == null) {
			return 5;
		}

		if (spellStack.getItem() instanceof IActiveSpell) {
			return ((IActiveSpell) spellStack.getItem()).getActionCost();
		}

		return 5;
	}

	public static boolean isSpellConditionValid(final EntityPlayer fighter, final ItemStack spellStack) {
		// TODO : Test spell conditions
		return true;
	}

	public static void tryCastSpell(final EntityPlayer fighter, final ChunkCoordinates targetPosition) {
		if (!canCastSpell(fighter, targetPosition)) {
			return;
		}

		// TODO : Cast spell
	}
}
