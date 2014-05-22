package heero.wakcraft.ability;

import java.util.EnumSet;
import java.util.Set;

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
		KIT("Kit");

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
}
