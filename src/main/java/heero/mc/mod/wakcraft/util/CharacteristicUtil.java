package heero.mc.mod.wakcraft.util;

import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.entity.creature.IEntityWithCharacteristics;
import heero.mc.mod.wakcraft.entity.property.CharacteristicsProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class CharacteristicUtil {
	public static boolean haveCharacteristics(Entity entity) {
		return (entity instanceof EntityPlayer || entity instanceof IEntityWithCharacteristics);
	}

	public static int getCharacteristic(Entity entity, Characteristic characteristic) {
		return ((CharacteristicsProperty) entity.getExtendedProperties(CharacteristicsProperty.IDENTIFIER)).get(characteristic);
	}

	public static void initCharacteristics(Entity entity) {
		CharacteristicsProperty property = (CharacteristicsProperty) entity.getExtendedProperties(CharacteristicsProperty.IDENTIFIER);

		if (entity instanceof IEntityWithCharacteristics) {
			((IEntityWithCharacteristics) entity).initCharacteristics(property);
		} else if (entity instanceof EntityPlayer) {
			property.enablePersistence(true);

			property.set(Characteristic.ACTION, 6);
			property.set(Characteristic.MOVEMENT, 3);
			property.set(Characteristic.HEALTH, 49);
			property.set(Characteristic.CRITICAL, 3);

			property.set(Characteristic.WAKFU, 6);
			property.set(Characteristic.CONTROL, 1);
		}
	}
}
