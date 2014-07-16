package heero.mc.mod.wakcraft.spell.effect;

import heero.mc.mod.wakcraft.characteristic.ICharacteristic;

/**
 * Characteristic modification effect.
 */
public interface IEffectCharacteristic extends IEffect {
	/**
	 * Returns the affected characteristic.
	 * @return	The affected characteristic.
	 */
	public ICharacteristic getCharacteristic();

	/**
	 * Returns amount to add (if positive) to the characteristic.
	 * @param spellLevel	The spell's level, used to retrieve the amount to add to the characteristic.
	 * @return	The amount to add to the characteristic.
	 */
	public int getValue(final int spellLevel);
}
