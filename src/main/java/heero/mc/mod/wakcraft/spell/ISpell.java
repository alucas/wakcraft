package heero.mc.mod.wakcraft.spell;

import heero.mc.mod.wakcraft.spell.effect.IEffect;

import java.util.List;

public interface ISpell {
	public ISpell setEffect(final IEffect effect);
	public List<IEffect> getEffects();
	public ISpell setEffectCritical(final IEffect effect);
	public List<IEffect> getEffectsCritical();
}
