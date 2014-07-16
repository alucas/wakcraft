package heero.mc.mod.wakcraft.spell;

/**
 * Main spell interface.
 */
public interface ISpell {
	/**
	 * Returns the description of the spell.
	 * @return	The description of the spell.
	 */
	public String getDescription();

	/**
	 * Returns the level of the spell.
	 * @param metadata	Value used to retrieve the spell's level.
	 * @return	The level of the spell.
	 */
	public int getLevel(final int metadata);
}
