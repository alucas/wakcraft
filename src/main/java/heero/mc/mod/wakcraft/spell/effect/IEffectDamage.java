package heero.mc.mod.wakcraft.spell.effect;

/**
 * Damage effect interface. In Wakfu damage usually obey to the
 * formula : Base + Level * Factor.
 */
public interface IEffectDamage extends IEffect {
	/**
	 * Returns the damage base value.
	 * @return	The damage base value.
	 */
	public int getDamageBase();

	/**
	 * Returns the damage factor value.
	 * @return	The damage factor value.
	 */
	public int getDamageFactor(); 
}
