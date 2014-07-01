package heero.mc.mod.wakcraft.spell;

import heero.mc.mod.wakcraft.spell.effect.IEffect;

import java.util.List;

public interface IActiveSpell extends ISpell {
	public int getActionCost();
	public int getMovementCost();
	public int getWakfuCost();

	public ISpell setEffect(final IEffect effect);
	public List<IEffect> getEffects();
	public ISpell setEffectCritical(final IEffect effect);
	public List<IEffect> getEffectsCritical();
	public ISpell setCondition(final ICondition condition);
	public List<ICondition> getCondition();
}
