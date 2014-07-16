package heero.mc.mod.wakcraft.spell.effect;

/**
 * Main IEffectDamage implementation.
 */
public class EffectDamage implements IEffectDamage {
	private final int damageBase;
	private final int damageFactor;
	private final IEffectArea effectArea;

	/**
	 * Main constructor.
	 * @param damageBase	The damage base value.
	 * @param damageFactor	The damage factor value.
	 * @param effectArea	The area of the effect.
	 */
	public EffectDamage(final int damageBase, final int damageFactor, IEffectArea effectArea) {
		this.damageBase = damageBase;
		this.damageFactor = damageFactor;
		this.effectArea = effectArea;
	}

	/**
	 * Constructor with default value of effect's area : POINT
	 * @param damageBase	The damage vase value.
	 * @param damageFactor	The damage factor value.
	 */
	public EffectDamage(final int damageBase, final int damageFactor) {
		this(damageBase, damageFactor, EffectArea.POINT);
	}

	@Override
	public int getDamageBase() {
		return damageBase;
	}

	@Override
	public int getDamageFactor() {
		return damageFactor;
	}

	@Override
	public IEffectArea getZone() {
		return effectArea;
	}
}
