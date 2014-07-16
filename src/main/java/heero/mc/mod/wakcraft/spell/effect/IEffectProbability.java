package heero.mc.mod.wakcraft.spell.effect;

/**
 * Add application probability to the effect.
 */
public interface IEffectProbability {
	/**
	 * Returns the probability to apply the state.
	 * @param spellLevel	Value used to retrieve the probability to apply a state.
	 * @return	The probability to apply the state.
	 */
	public float getProbability(final int metadata);
}
