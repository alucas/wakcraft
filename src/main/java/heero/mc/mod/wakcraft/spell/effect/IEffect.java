package heero.mc.mod.wakcraft.spell.effect;

/**
 * Main effect interface
 */
public interface IEffect {
	/**
	 * Returns the area of effect of the effect.
	 * @return	The area of effect.
	 */
	IEffectArea getZone();
}
