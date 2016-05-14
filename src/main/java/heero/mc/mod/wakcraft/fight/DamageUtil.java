package heero.mc.mod.wakcraft.fight;

import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.spell.IActiveSpell;
import heero.mc.mod.wakcraft.spell.effect.EffectElement;
import heero.mc.mod.wakcraft.spell.effect.IEffectDamage;
import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.entity.EntityLivingBase;

public class DamageUtil {
    public static int computeDamage(final EntityLivingBase attacker, final EntityLivingBase target, final IActiveSpell stackSpell, final Integer spellXp, final IEffectDamage effect) {
        final int spellLevel = stackSpell.getLevel(spellXp);
        final int damageValue = effect.getValue(spellLevel);

        switch ((EffectElement) effect.getElement()) {
            case AIR:
                final Integer attackerAirAtt = FightUtil.getFightCharacteristic(attacker, Characteristic.AIR_ATT);
                final Integer targetAirRes = FightUtil.getFightCharacteristic(target, Characteristic.AIR_RES);
                if (attackerAirAtt == null || targetAirRes == null) {
                    return damageValue;
                }

                return (int) (damageValue * ((attackerAirAtt - targetAirRes) / 100.0));
            case EARTH:
                final Integer attackerEarthAtt = FightUtil.getFightCharacteristic(attacker, Characteristic.EARTH_ATT);
                final Integer targetEarthRes = FightUtil.getFightCharacteristic(target, Characteristic.EARTH_RES);
                if (attackerEarthAtt == null || targetEarthRes == null) {
                    return damageValue;
                }

                return (int) (damageValue * ((attackerEarthAtt - targetEarthRes) / 100.0));
            case FIRE:
                final Integer attackerFireAtt = FightUtil.getFightCharacteristic(attacker, Characteristic.FIRE_ATT);
                final Integer targetFireRes = FightUtil.getFightCharacteristic(target, Characteristic.FIRE_RES);
                if (attackerFireAtt == null || targetFireRes == null) {
                    return damageValue;
                }

                return (int) (damageValue * ((attackerFireAtt - targetFireRes) / 100.0));
            case WATER:
                final Integer attackerWaterAtt = FightUtil.getFightCharacteristic(attacker, Characteristic.WATER_ATT);
                final Integer targetWaterRes = FightUtil.getFightCharacteristic(target, Characteristic.WATER_RES);
                if (attackerWaterAtt == null || targetWaterRes == null) {
                    return damageValue;
                }

                return (int) (damageValue * ((attackerWaterAtt - targetWaterRes) / 100.0));
            default:
        }

        return damageValue;
    }
}
