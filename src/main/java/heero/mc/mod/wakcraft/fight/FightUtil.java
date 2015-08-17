package heero.mc.mod.wakcraft.fight;

import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.entity.creature.IFighter;
import heero.mc.mod.wakcraft.spell.IActiveSpell;
import heero.mc.mod.wakcraft.spell.IRangeMode;
import heero.mc.mod.wakcraft.spell.RangeMode;
import heero.mc.mod.wakcraft.spell.effect.IEffect;
import heero.mc.mod.wakcraft.spell.effect.IEffectCharacteristic;
import heero.mc.mod.wakcraft.spell.effect.IEffectDamage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

import java.util.List;

public class FightUtil {
	public static boolean canCastSpell(final EntityPlayer fighter, final BlockPos targetPosition) {
        BlockPos fighterPosition = heero.mc.mod.wakcraft.util.FightUtil.getCurrentPosition(fighter);
		ItemStack spellStack = heero.mc.mod.wakcraft.util.FightUtil.getCurrentSpell(fighter);
		if (!heero.mc.mod.wakcraft.fight.FightUtil.isAimingPositionValid(fighterPosition, targetPosition, spellStack)) {
			return false;
		}

		if (!heero.mc.mod.wakcraft.fight.FightUtil.isSpellCostAvailable(fighter, spellStack)) {
			return false;
		}

		if (!heero.mc.mod.wakcraft.fight.FightUtil.isSpellConditionValid(fighter, spellStack)) {
			return false;
		}

		return true;
	}

	public static boolean isAimingPositionValid(final BlockPos fighterPosition, final MovingObjectPosition target, final ItemStack spellStack) {
		return isAimingPositionValid(fighterPosition, target.func_178782_a(), spellStack);
	}

	public static boolean isAimingPositionValid(final BlockPos fighterPosition, final BlockPos target, final ItemStack spellStack) {
		return isAimingPositionValid(fighterPosition, target.getX(), target.getY(), target.getZ(), spellStack);
	}

	public static boolean isAimingPositionValid(final BlockPos fighterPosition, final int posX, final int posY, final int posZ, final ItemStack spellStack) {
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
			if (posX != fighterPosition.getX() && posZ != fighterPosition.getZ()) {
				return false;
			}

			int distanceX = MathHelper.abs_int(fighterPosition.getX() - posX);
			if (fighterPosition.getZ() == posZ && (distanceX < rangeMin || distanceX > rangeMax)) {
				return false;
			}

			int distanceZ = MathHelper.abs_int(fighterPosition.getZ() - posZ);
			if (fighterPosition.getX() == posX && (distanceZ < rangeMin || distanceZ > rangeMax)) {
				return false;
			}
		} else {
			int distanceX = MathHelper.abs_int(fighterPosition.getX() - posX);
			int distanceZ = MathHelper.abs_int(fighterPosition.getZ() - posZ);

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
		int wakfuPoint = heero.mc.mod.wakcraft.util.FightUtil.getFightCharacteristic(fighter, Characteristic.WAKFU);
		int movementPoint = heero.mc.mod.wakcraft.util.FightUtil.getFightCharacteristic(fighter, Characteristic.MOVEMENT);
		int actionPoint = heero.mc.mod.wakcraft.util.FightUtil.getFightCharacteristic(fighter, Characteristic.ACTION);

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

	public static void tryCastSpell(final EntityPlayer fighter, final BlockPos targetPosition) {
		if (!canCastSpell(fighter, targetPosition)) {
			return;
		}

        BlockPos fighterPosition = heero.mc.mod.wakcraft.util.FightUtil.getCurrentPosition(fighter);
		ItemStack spellStack = heero.mc.mod.wakcraft.util.FightUtil.getCurrentSpell(fighter);
		List<List<EntityLivingBase>> fighters = heero.mc.mod.wakcraft.util.FightUtil.getFighers(fighter.worldObj, heero.mc.mod.wakcraft.util.FightUtil.getFightId(fighter));

		heero.mc.mod.wakcraft.util.FightUtil.setFightCharacteristic(fighter, Characteristic.WAKFU, heero.mc.mod.wakcraft.util.FightUtil.getFightCharacteristic(fighter, Characteristic.WAKFU) - getSpellWakfuCost(spellStack));
		heero.mc.mod.wakcraft.util.FightUtil.setFightCharacteristic(fighter, Characteristic.MOVEMENT, heero.mc.mod.wakcraft.util.FightUtil.getFightCharacteristic(fighter, Characteristic.MOVEMENT) - getSpellMovementCost(spellStack));
		heero.mc.mod.wakcraft.util.FightUtil.setFightCharacteristic(fighter, Characteristic.ACTION, heero.mc.mod.wakcraft.util.FightUtil.getFightCharacteristic(fighter, Characteristic.ACTION) - getSpellActionCost(spellStack));

		if (spellStack == null || spellStack.getItem() == null) {
			
		} else if (spellStack.getItem() instanceof IActiveSpell) {
			IActiveSpell spell = (IActiveSpell) spellStack.getItem();
			for (IEffect effect : spell.getEffects()) {
				if (!(effect instanceof IEffectCharacteristic)) {
					continue;
				}

				int characteristicValue = ((IEffectCharacteristic) effect).getValue(spell.getLevel(spellStack.getItemDamage()));
				Characteristic characteristicType = ((IEffectCharacteristic) effect).getCharacteristic();

				List<BlockPos> targetBlocks = effect.getZone().getEffectCoors(fighterPosition, targetPosition);
				for (List<EntityLivingBase> team : fighters) {
					for (EntityLivingBase targetFighter : team) {
                        BlockPos position = heero.mc.mod.wakcraft.util.FightUtil.getCurrentPosition(targetFighter);
						for (BlockPos block : targetBlocks) {
							if (block.getX() != position.getX() || block.getZ() != position.getZ()) {
								continue;
							}

							if (effect instanceof IEffectDamage) {
								characteristicValue = DamageUtil.computeDamage(fighter, targetFighter, spellStack, (IEffectDamage) effect);
							}

							int oldValue = heero.mc.mod.wakcraft.util.FightUtil.getFightCharacteristic(targetFighter, characteristicType);
							heero.mc.mod.wakcraft.util.FightUtil.setFightCharacteristic(targetFighter, characteristicType, oldValue + characteristicValue);

							if (targetFighter instanceof IFighter) {
								((IFighter) targetFighter).onAttacked(fighter, spellStack);
							}
						}
					}
				}

			}
		}
	}
}
