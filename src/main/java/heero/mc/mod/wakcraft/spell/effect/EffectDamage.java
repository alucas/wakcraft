package heero.mc.mod.wakcraft.spell.effect;

/**
 * Main IEffectDamage implementation.
 */
public class EffectDamage implements IEffectDamage {
	private final int damageBase;
	private final float damageFactor;
	private final IEffectArea effectArea;

	/**
	 * Main constructor.
	 * @param damageBase	The damage base value.
	 * @param damageFactor	The damage factor value.
	 * @param effectArea	The area of the effect.
	 */
	public EffectDamage(final int damageBase, final float damageFactor, IEffectArea effectArea) {
		this.damageBase = damageBase;
		this.damageFactor = damageFactor;
		this.effectArea = effectArea;
	}

	/**
	 * Constructor with default value of effect's area : POINT
	 * @param damageBase	The damage vase value.
	 * @param damageFactor	The damage factor value.
	 */
	public EffectDamage(final int damageBase, final float damageFactor) {
		this(damageBase, damageFactor, EffectArea.POINT);
	}

	@Override
	public int getDamage(final int spellLevel) {
		return (int) (damageBase + damageFactor * spellLevel);
	}

	@Override
	public IEffectArea getZone() {
		return effectArea;
	}
}
