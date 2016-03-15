package heero.mc.mod.wakcraft.spell.state;

import heero.mc.mod.wakcraft.spell.effect.IEffect;

import java.util.List;

/**
 * Main state interface.
 */
public interface IState {
    /**
     * Returns the name of the state.
     *
     * @return The name of the state.
     */
    public String getName();

    /**
     * Returns the description of the state.
     *
     * @return The description of the state.
     */
    public String getDescription();

    /**
     * Returns the level of the state.
     *
     * @param metadata Value used to retrieve the state's level.
     * @return The level of the state.
     */
    public int getLevel(final int metadata);

    /**
     * Add an effect to the state.
     *
     * @param effect The effect to add to the state.
     * @return This instance, for usage commodity.
     */
    public IState setEffect(final IEffect effect);

    /**
     * Returns the list of effects of this state.
     *
     * @return The list of effects of this state.
     */
    public List<IEffect> getEffects();
}
