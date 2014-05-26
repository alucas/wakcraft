package heero.mc.mod.wakcraft.manager;

import heero.mc.mod.wakcraft.entity.property.AbilitiesProperty;
import heero.mc.mod.wakcraft.item.ItemWArmor;

import java.util.EnumSet;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLLog;

public class AbilityManager {
	public static enum ABILITY {
		// Principal
		HEALTH("Health"),
		ACTION("Action"),
		MOVEMENT("Movement"),
		WAKFU("Wakfu"),
		STRENGTH("Strength"),
		INTELLIGENCE("Intelligence"),
		AGILITY("Agility"),
		CHANCE("Chance"),

		// Battle
		INITIATIVE("Initiative"),
		HEAL("Heal"),
		CRITICAL("Critical"),
		CRITICAL_DAMAGE("Critical_Damage"),
		CRITICAL_FAILURE("Critical_Failure"),
		BACKSTAB("Backstab"),
		BACKSTAB_RESISTANCE("Backstab_Resistance"),
		DODGE("Dodge"),
		LOCK("Lock"),
		BLOCK("Block"),
		WILLPOWER("Willpower"),
		RANGE("Range"),

		// Secondary
		CONTROL("Control"),
		/** Increases the damages of the creatures and mechanisms you control */
		CMC_DAMAGE("CMC_Damage"),
		WISDOM("Wisdom"),
		PROSPECTION("Prospection"),
		PERCEPTION("Perception"),
		KIT("Kit"),

		// Mastery
		WATER_RES("Water_Res"),
		WATER_ATT("Water_Att"),
		EARTH_RES("Earth_Res"),
		EARTH_ATT("Earth_Att"),
		FIRE_RES("Fire_Res"),
		FIRE_ATT("Fire_Att"),
		AIR_RES("Air_Res"),
		AIR_ATT("Air_Att"),
		ACTION_RES("Action_Res"),
		ACTION_ATT("Action_Att"),
		MOVEMENT_RES("Movement_Res"),
		MOVEMENT_ATT("Movement_Att");

		protected String name;

		private ABILITY(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	protected static Set<AbilityManager.ABILITY> customizableAbilities = EnumSet
			.of(ABILITY.STRENGTH, ABILITY.INTELLIGENCE, ABILITY.AGILITY,
					ABILITY.CHANCE, ABILITY.BLOCK, ABILITY.RANGE,
					ABILITY.ACTION, ABILITY.MOVEMENT, ABILITY.CRITICAL,
					ABILITY.KIT, ABILITY.LOCK, ABILITY.DODGE,
					ABILITY.INITIATIVE, ABILITY.HEALTH);

	public static boolean isCustomizable(ABILITY ability) {
		return customizableAbilities.contains(ability);
	}

	public static void equipItem(Entity entity, ItemWArmor item) {
		if (!(entity instanceof EntityPlayer)) {
			return;
		}

		AbilitiesProperty properties = (AbilitiesProperty) ((EntityPlayer) entity).getExtendedProperties(AbilitiesProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading the ability properties of player : " + ((EntityPlayer) entity).getDisplayName());
			return;
		}

		for (ABILITY characteristic : item.getCharacteristics()) {
			properties.set(characteristic, properties.get(characteristic) + item.getCharacteristic(characteristic));
		}
	}

	public static void unequipItem(Entity entity, ItemWArmor item) {
		if (!(entity instanceof EntityPlayer)) {
			return;
		}

		AbilitiesProperty properties = (AbilitiesProperty) ((EntityPlayer) entity).getExtendedProperties(AbilitiesProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading the ability properties of player : " + ((EntityPlayer) entity).getDisplayName());
			return;
		}

		for (ABILITY characteristic : item.getCharacteristics()) {
			properties.set(characteristic, properties.get(characteristic) - item.getCharacteristic(characteristic));
		}
	}
}
