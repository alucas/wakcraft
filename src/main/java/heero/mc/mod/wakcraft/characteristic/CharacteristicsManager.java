package heero.mc.mod.wakcraft.characteristic;

import heero.mc.mod.wakcraft.entity.property.CharacteristicsProperty;
import heero.mc.mod.wakcraft.item.ItemWArmor;

import java.util.EnumSet;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLLog;

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

	public static void equipItem(Entity entity, ItemWArmor item) {
		if (!(entity instanceof EntityPlayer)) {
			return;
		}

		CharacteristicsProperty properties = (CharacteristicsProperty) ((EntityPlayer) entity).getExtendedProperties(CharacteristicsProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading the characteristics properties of player : " + ((EntityPlayer) entity).getDisplayName());
			return;
		}

		for (Characteristic characteristic : item.getCharacteristics()) {
			properties.set(characteristic, properties.get(characteristic) + item.getCharacteristic(characteristic));
		}
	}

	public static void unequipItem(Entity entity, ItemWArmor item) {
		if (!(entity instanceof EntityPlayer)) {
			return;
		}

		CharacteristicsProperty properties = (CharacteristicsProperty) ((EntityPlayer) entity).getExtendedProperties(CharacteristicsProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading the characteristics properties of player : " + ((EntityPlayer) entity).getDisplayName());
			return;
		}

		for (Characteristic characteristic : item.getCharacteristics()) {
			properties.set(characteristic, properties.get(characteristic) - item.getCharacteristic(characteristic));
		}
	}
}
