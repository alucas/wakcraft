package heero.mc.mod.wakcraft.fight;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.entity.creature.IFighter;
import heero.mc.mod.wakcraft.spell.IActiveSpell;
import heero.mc.mod.wakcraft.spell.IRangeMode;
import heero.mc.mod.wakcraft.spell.RangeMode;
import heero.mc.mod.wakcraft.spell.effect.IEffect;
import heero.mc.mod.wakcraft.spell.effect.IEffectCharacteristic;
import heero.mc.mod.wakcraft.spell.effect.IEffectDamage;
import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

import java.util.List;

public class FightHelper {
    public static boolean canCastSpell(final EntityPlayer fighter, final BlockPos targetPosition) {
        BlockPos fighterPosition = FightUtil.getCurrentPosition(fighter);
        ItemStack spellStack = FightUtil.getCurrentSpell(fighter);

        if (!FightHelper.isAimingPositionValid(fighterPosition, targetPosition, spellStack)) {
            return false;
        }

        if (!FightHelper.isSpellCostAvailable(fighter, spellStack)) {
            return false;
        }

        return true;
    }

    public static boolean isAimingPositionValid(final BlockPos casterPosition, final MovingObjectPosition targetPosition, final ItemStack spellStack) {
        return isAimingPositionValid(casterPosition, targetPosition.getBlockPos(), spellStack);
    }

    public static boolean isAimingPositionValid(final BlockPos casterPosition, final BlockPos targetPosition, final ItemStack spellStack) {
        return isAimingPositionValid(casterPosition, targetPosition.getX(), targetPosition.getY(), targetPosition.getZ(), spellStack);
    }

    public static boolean isAimingPositionValid(final BlockPos casterPosition, final int posX, final int posY, final int posZ, final ItemStack spellStack) {
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
            if (posX != casterPosition.getX() && posZ != casterPosition.getZ()) {
                return false;
            }

            int distanceX = MathHelper.abs_int(casterPosition.getX() - posX);
            if (casterPosition.getZ() == posZ && (distanceX < rangeMin || distanceX > rangeMax)) {
                return false;
            }

            int distanceZ = MathHelper.abs_int(casterPosition.getZ() - posZ);
            if (casterPosition.getX() == posX && (distanceZ < rangeMin || distanceZ > rangeMax)) {
                return false;
            }
        } else {
            int distanceX = MathHelper.abs_int(casterPosition.getX() - posX);
            int distanceZ = MathHelper.abs_int(casterPosition.getZ() - posZ);

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
        final Integer wakfuPoint = FightUtil.getFightCharacteristic(fighter, Characteristic.WAKFU);
        final Integer movementPoint = FightUtil.getFightCharacteristic(fighter, Characteristic.MOVEMENT);
        final Integer actionPoint = FightUtil.getFightCharacteristic(fighter, Characteristic.ACTION);
        if (wakfuPoint == null || movementPoint == null || actionPoint == null) {
            return false;
        }

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

        BlockPos fighterPosition = FightUtil.getCurrentPosition(fighter);
        ItemStack spellStack = FightUtil.getCurrentSpell(fighter);
        List<List<EntityLivingBase>> fighters = FightUtil.getFighters(fighter.worldObj, FightUtil.getFightId(fighter));

        FightUtil.setFightCharacteristic(fighter, Characteristic.WAKFU, FightUtil.getFightCharacteristic(fighter, Characteristic.WAKFU) - getSpellWakfuCost(spellStack));
        FightUtil.setFightCharacteristic(fighter, Characteristic.MOVEMENT, FightUtil.getFightCharacteristic(fighter, Characteristic.MOVEMENT) - getSpellMovementCost(spellStack));
        FightUtil.setFightCharacteristic(fighter, Characteristic.ACTION, FightUtil.getFightCharacteristic(fighter, Characteristic.ACTION) - getSpellActionCost(spellStack));

        if (spellStack == null || spellStack.getItem() == null) {
            WLog.warning("The fighter (" + fighter.getName() + ") is not holding a Spell");
            return;
        }

        if (!(spellStack.getItem() instanceof IActiveSpell)) {
            WLog.warning("The fighter (" + fighter.getName() + ") is not holding an Active Spell");
            return;
        }

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
                    BlockPos position = FightUtil.getCurrentPosition(targetFighter);
                    for (BlockPos block : targetBlocks) {
                        if (block.getX() != position.getX() || block.getZ() != position.getZ()) {
                            continue;
                        }

                        if (effect instanceof IEffectDamage) {
                            characteristicValue = DamageUtil.computeDamage(fighter, targetFighter, spell, spellStack.getItemDamage(), (IEffectDamage) effect);
                        }

                        final Integer oldValue = FightUtil.getFightCharacteristic(targetFighter, characteristicType);
                        if (oldValue == null) {
                            return;
                        }

                        FightUtil.setFightCharacteristic(targetFighter, characteristicType, oldValue + characteristicValue);

                        if (targetFighter instanceof IFighter) {
                            ((IFighter) targetFighter).onAttacked(fighter, spellStack);
                        }
                    }
                }
            }
        }

        FightUtil.sendFightCharacteristicToClient((EntityPlayerMP) fighter, fighter);
    }
}
