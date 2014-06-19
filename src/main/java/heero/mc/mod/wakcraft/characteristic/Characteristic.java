package heero.mc.mod.wakcraft.characteristic;

public enum Characteristic {
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

	private Characteristic(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
