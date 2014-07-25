package heero.mc.mod.wakcraft.spell.effect;

import heero.mc.mod.wakcraft.characteristic.Characteristic;

/**
 * Elemental spells' IEffectDamage implementation.
 */
public class EffectDamage extends EffectCharacteristic implements IEffectDamage {
	private final IEffectElement element;

	/**
	 * Main constructor.
	 * @param damageMin	The minimal damage value.
	 * @param damageMax	The maximal damage value.
	 * @param effectArea	The area of the effect.
	 */
	public EffectDamage(final int damageMin, final int damageMax, IEffectElement element, IEffectArea effectArea) {
		super(Characteristic.HEALTH, -damageMin, - ((damageMax - damageMin) / 200.0F), 1, 0, effectArea);

		this.element = element;
	}

	/**
	 * Constructor with default value of effect's area : POINT
	 * @param damageMin	The minimal damage value.
	 * @param damageMax	The maximal damage value.
	 */
	public EffectDamage(final int damageMin, final int damageMax, IEffectElement element) {
		this(damageMin, damageMax, element, EffectArea.POINT);
	}

	@Override
	public IEffectElement getElement() {
		return element;
	}
}
