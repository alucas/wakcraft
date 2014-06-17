package heero.mc.mod.wakcraft.helper;

import heero.mc.mod.wakcraft.characteristic.CharacteristicsManager.CHARACTERISTIC;
import heero.mc.mod.wakcraft.entity.creature.IEntityWithCharacteristics;
import heero.mc.mod.wakcraft.entity.property.CharacteristicsProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class CharacteristicsHelper {
	public static boolean haveCharacteristics(Entity entity) {
		return (entity instanceof EntityPlayer || entity instanceof IEntityWithCharacteristics);
	}

	public static int getCharacteristics(Entity entity, CHARACTERISTIC characteristic) {
		return ((CharacteristicsProperty) entity.getExtendedProperties(CharacteristicsProperty.IDENTIFIER)).get(characteristic);
	}

	public static void init(Entity entity) {
		CharacteristicsProperty property = (CharacteristicsProperty) entity.getExtendedProperties(CharacteristicsProperty.IDENTIFIER);

		if (entity instanceof IEntityWithCharacteristics) {
			((IEntityWithCharacteristics) entity).initCharacteristics(property);
		} else if (entity instanceof EntityPlayer) {
			property.enablePersistence(true);

			property.set(CHARACTERISTIC.ACTION, 6);
			property.set(CHARACTERISTIC.MOVEMENT, 3);
			property.set(CHARACTERISTIC.HEALTH, 49);
			property.set(CHARACTERISTIC.CRITICAL, 3);

			property.set(CHARACTERISTIC.WAKFU, 6);
			property.set(CHARACTERISTIC.CONTROL, 1);
		}
	}
}
