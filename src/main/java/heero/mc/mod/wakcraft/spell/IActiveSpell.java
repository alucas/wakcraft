package heero.mc.mod.wakcraft.spell;

import heero.mc.mod.wakcraft.spell.effect.IEffect;
import heero.mc.mod.wakcraft.spell.effect.IEffectArea;

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
	 * Returns the maximal range for the spell.
	 * @param spellLevel	The level of the spell.
	 * @return	The maximal range for the spell.
	 */
	public int getRangeMax(final int spellLevel);

	/**
	 * Returns the minimal range for the spell.
	 * @param spellLevel	The level of the spell.
	 * @return	The minimal range for the spell.
	 */
	public int getRangeMin(final int spellLevel);

	/**
	 * Returns is the range is modifiable.
	 * @return	True if the range is modifiable.
	 */
	public boolean isRangeModifiable();

	/**
	 * Returns if the spell can be launch behind obstacle.
	 * @return	True if the spell can be launch behind obstacle.
	 */
	public boolean isRangeViewRequired();

	/**
	 * Return the range mode ; LINE, DIAGONAL, NORMAL, ...
	 * @return	The range mode.
	 */
	public IRangeMode getRangeMode();

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
	public List<ICondition> getConditions();

	/**
	 * Returns the effect area used for display purpose.
	 * @return	The effect area.
	 */
	public IEffectArea getDisplayEffectArea();
}
