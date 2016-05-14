package heero.mc.mod.wakcraft.util;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.entity.creature.IEntityWithCharacteristics;
import heero.mc.mod.wakcraft.entity.property.CharacteristicsProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.EnumSet;
import java.util.Set;

public class CharacteristicUtil {
    public static boolean hasCharacteristics(final Entity entity) {
        return (entity instanceof EntityPlayer || entity instanceof IEntityWithCharacteristics);
    }

    public static void registerCharacteristics(final Entity entity) {
        entity.registerExtendedProperties(CharacteristicsProperty.IDENTIFIER, new CharacteristicsProperty());
    }

    protected static CharacteristicsProperty getCharacteristicsProperty(final Entity entity) {
        final CharacteristicsProperty properties = (CharacteristicsProperty) entity.getExtendedProperties(CharacteristicsProperty.IDENTIFIER);
        if (properties == null) {
            WLog.warning("Error while loading the player's abilities (%s)", entity.getDisplayName());
            return null;
        }

        return properties;
    }

    public static Integer getBaseCharacteristic(final Entity entity, final Characteristic characteristic) {
        final CharacteristicsProperty properties = getCharacteristicsProperty(entity);
        if (properties == null) {
            return null;
        }

        return properties.get(characteristic);
    }

    public static Integer getCharacteristic(final Entity entity, final Characteristic characteristic) {
        final Integer baseCharacteristic = getBaseCharacteristic(entity, characteristic);
        final Integer armorsCharacteristic = ArmorUtil.hasArmor(entity) ? ArmorUtil.getArmorsCharacteristic(entity, characteristic) : Integer.valueOf(0);
        if (baseCharacteristic == null || armorsCharacteristic == null) {
            return null;
        }

        return baseCharacteristic + armorsCharacteristic;
    }

    public static void setBaseCharacteristic(final Entity entity, final Characteristic characteristic, final Integer value) {
        final CharacteristicsProperty properties = getCharacteristicsProperty(entity);
        if (properties == null) {
            return;
        }

        properties.set(characteristic, value);
    }


    public static void initCharacteristics(final Entity entity) {
        final CharacteristicsProperty properties = getCharacteristicsProperty(entity);
        if (properties == null) {
            return;
        }

        if (entity instanceof IEntityWithCharacteristics) {
            ((IEntityWithCharacteristics) entity).initCharacteristics(properties);
        } else if (entity instanceof EntityPlayer) {
            properties.enablePersistence(true);

            properties.set(Characteristic.ACTION, 6);
            properties.set(Characteristic.MOVEMENT, 3);
            properties.set(Characteristic.HEALTH, 49);
            properties.set(Characteristic.CRITICAL, 3);

            properties.set(Characteristic.WAKFU, 6);
            properties.set(Characteristic.CONTROL, 1);
        }
    }

    protected static Set<Characteristic> customizableCharacteristics = EnumSet
            .of(Characteristic.STRENGTH, Characteristic.INTELLIGENCE, Characteristic.AGILITY,
                    Characteristic.CHANCE, Characteristic.BLOCK, Characteristic.RANGE,
                    Characteristic.ACTION, Characteristic.MOVEMENT, Characteristic.CRITICAL,
                    Characteristic.KIT, Characteristic.LOCK, Characteristic.DODGE,
                    Characteristic.INITIATIVE, Characteristic.HEALTH);

    public static boolean isCustomizable(Characteristic characteristics) {
        return customizableCharacteristics.contains(characteristics);
    }
}
