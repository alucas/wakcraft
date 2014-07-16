package heero.mc.mod.wakcraft.spell.effect;

/**
 * Damage effect interface.
 */
public interface IEffectDamage extends IEffect {
	/**
	 * Returns the damage value.
	 * @param spellLevel	The spell's level.
	 * @return	The damage base value.
	 */
	public int getDamage(final int spellLevel);
}
