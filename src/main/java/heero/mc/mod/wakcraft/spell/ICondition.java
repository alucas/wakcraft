package heero.mc.mod.wakcraft.spell;

/**
 * Spell usage condition main interface.
 */
public interface ICondition {
	/**
	 * Returns the description of the condition.
	 * @param spellLevel	The spell's level.
	 * @return	The description of the condition.
	 */
	public String getDescription(final int spellLevel);
}
