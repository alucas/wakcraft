package heero.mc.mod.wakcraft.spell;

import heero.mc.mod.wakcraft.spell.effect.IEffect;

import java.util.List;

public interface IPassiveSpell extends ISpell {
	public ISpell setEffect(final IEffect effect);
	public List<IEffect> getEffects();
}
