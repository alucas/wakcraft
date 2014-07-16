package heero.mc.mod.wakcraft.spell;

import heero.mc.mod.wakcraft.spell.effect.IEffect;

import java.util.List;

/**
 * Active spell interface.
 */
public interface IActiveSpell extends ISpell {
	/**
	 * Returns the action point cost of the spell.
	 * @return	The action point cost of the spell.
	 */
	public int getActionCost();

	/**
	 * Returns the movement point cost of the spell.
	 * @return	The movement point cost of the spell.
	 */
	public int getMovementCost();

	/**
	 * Returns the wakfu point cost of the spell.
	 * @return	The wakfu point cost of the spell.
	 */
	public int getWakfuCost();

	/**
	 * Add an effect to the spell.
	 * @param effect	The effect to add to the spell.
	 * @return	This instance, for usage commodity.
	 */
	public ISpell setEffect(final IEffect effect);

	/**
	 * Returns the effects of the spell.
	 * @return	The effects of the spell.
	 */
	public List<IEffect> getEffects();

	/**
	 * Add a critical effect to the spell.
	 * @param effect	The effect to add to the spell.
	 * @return	This instance, for usage commodity.
	 */
	public ISpell setEffectCritical(final IEffect effect);

	/**
	 * Returns the critical effects of the spells.
	 * @return
	 */
	public List<IEffect> getEffectsCritical();

	/**
	 * Add an usage condition to the spell.
	 * @param condition	The condition to add to the spell.
	 * @return	This instance, for usage commodity.
	 */
	public ISpell setCondition(final ICondition condition);

	/**
	 * Returns the usage conditions of the spell.
	 * @return	The usage conditions of the spell.
	 */
	public List<ICondition> getCondition();
}
