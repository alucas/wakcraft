package heero.mc.mod.wakcraft.characteristic;

import heero.mc.mod.wakcraft.item.ItemWArmor;
import heero.mc.mod.wakcraft.util.CharacteristicUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.EnumSet;
import java.util.Set;

public class CharacteristicsManager {
    protected static Set<Characteristic> customizableCharacteristics = EnumSet
            .of(Characteristic.STRENGTH, Characteristic.INTELLIGENCE, Characteristic.AGILITY,
                    Characteristic.CHANCE, Characteristic.BLOCK, Characteristic.RANGE,
                    Characteristic.ACTION, Characteristic.MOVEMENT, Characteristic.CRITICAL,
                    Characteristic.KIT, Characteristic.LOCK, Characteristic.DODGE,
                    Characteristic.INITIATIVE, Characteristic.HEALTH);

    public static boolean isCustomizable(Characteristic characteristics) {
        return customizableCharacteristics.contains(characteristics);
    }

    public static void equipItem(final Entity entity, final ItemWArmor item) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }

        for (final Characteristic characteristic : item.getCharacteristics()) {
            final Integer characteristicValue = item.getCharacteristic(characteristic);
            final Integer entityCharacteristicValue = CharacteristicUtil.getCharacteristic(entity, characteristic);
            if (characteristicValue == null || entityCharacteristicValue == null) {
                continue;
            }

            CharacteristicUtil.setCharacteristic(entity, characteristic, entityCharacteristicValue + characteristicValue);
        }
    }

    public static void unequipItem(final Entity entity, final ItemWArmor item) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }

        for (final Characteristic characteristic : item.getCharacteristics()) {
            final Integer characteristicValue = item.getCharacteristic(characteristic);
            final Integer entityCharacteristicValue = CharacteristicUtil.getCharacteristic(entity, characteristic);
            if (characteristicValue == null || entityCharacteristicValue == null) {
                continue;
            }

            CharacteristicUtil.setCharacteristic(entity, characteristic, entityCharacteristicValue - characteristicValue);
        }
    }
}
