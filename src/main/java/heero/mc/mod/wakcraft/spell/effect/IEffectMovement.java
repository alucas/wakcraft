package heero.mc.mod.wakcraft.spell.effect;


/**
 * Movement effect.
 */
public interface IEffectMovement extends IEffect {
	/**
	 * Returns the movement value to apply.
	 * @param spellLevel	The spell's level, used to retrieve the movement value to apply.
	 * @return	The movement value to apply.
	 */
	public int getValue(final int spellLevel);
}
