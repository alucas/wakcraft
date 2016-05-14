package heero.mc.mod.wakcraft.util;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.entity.creature.IEntityWithCharacteristics;
import heero.mc.mod.wakcraft.entity.property.CharacteristicsProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class CharacteristicUtil {
    public static boolean hasCharacteristics(Entity entity) {
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

    public static Integer getCharacteristic(final Entity entity, final Characteristic characteristic) {
        final CharacteristicsProperty properties = getCharacteristicsProperty(entity);
        if (properties == null) {
            return null;
        }

        return properties.get(characteristic);
    }

    public static void setCharacteristic(final Entity entity, final Characteristic characteristic, final Integer value) {
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
}
