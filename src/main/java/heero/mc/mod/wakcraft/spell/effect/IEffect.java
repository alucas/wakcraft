package heero.mc.mod.wakcraft.spell.effect;

/**
 * Main effect interface
 */
public interface IEffect {
    /**
     * Returns the area of effect of the effect.
     *
     * @return The area of effect.
     */
    public IEffectArea getZone();

    /**
     * Returns the description of the effect.
     *
     * @param spellLevel The spell's level.
     * @return The description of the effect.
     */
    public String getDescription(final int spellLevel);
}
