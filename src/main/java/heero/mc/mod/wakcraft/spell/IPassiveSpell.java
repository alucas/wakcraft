package heero.mc.mod.wakcraft.spell;

import heero.mc.mod.wakcraft.spell.effect.IEffect;

import java.util.List;

/**
 * Passive spells main interface.
 */
public interface IPassiveSpell extends ISpell {
    /**
     * Add an effect to the spell.
     *
     * @param effect The effect to add to the spell.
     * @return This instance, for usage commodity.
     */
    public ISpell setEffect(final IEffect effect);

    /**
     * Returns the list of effects of this spell.
     *
     * @return The list of effects of this spell.
     */
    public List<IEffect> getEffects();
}
