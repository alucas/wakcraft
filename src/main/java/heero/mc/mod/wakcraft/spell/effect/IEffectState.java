package heero.mc.mod.wakcraft.spell.effect;

import heero.mc.mod.wakcraft.spell.state.IState;

/**
 * State application effect.
 */
public interface IEffectState extends IEffect {
    /**
     * Returns the state to apply.
     *
     * @return The state to apply.
     */
    public IState getState();

    /**
     * Returns the level of the state to apply or add.
     *
     * @param spellLevel Value used to retrieve the level of the state to apply.
     * @return The level of the state to apply or add.
     */
    public int getStateLevel(final int spellLevel);
}
