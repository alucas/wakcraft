package heero.mc.mod.wakcraft.spell.effect;

import heero.mc.mod.wakcraft.characteristic.Characteristic;

/**
 * Main IEffectDamage implementation.
 */
public class EffectDamage extends EffectCharacteristic implements IEffectDamage {
	private final IEffectElement element;

	/**
	 * Main constructor.
	 * @param damageBase	The damage base value.
	 * @param damageFactor	The damage factor value.
	 * @param effectArea	The area of the effect.
	 */
	public EffectDamage(final int damageBase, final float damageFactor, IEffectElement element, IEffectArea effectArea) {
		super(Characteristic.HEALTH, -damageBase, -damageFactor, 1, 0);

		this.element = element;
	}

	/**
	 * Constructor with default value of effect's area : POINT
	 * @param damageBase	The damage vase value.
	 * @param damageFactor	The damage factor value.
	 */
	public EffectDamage(final int damageBase, final float damageFactor, IEffectElement element) {
		this(damageBase, damageFactor, element, EffectArea.POINT);
	}

	@Override
	public IEffectElement getElement() {
		return element;
	}
}
