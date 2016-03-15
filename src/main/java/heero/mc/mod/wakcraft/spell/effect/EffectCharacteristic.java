package heero.mc.mod.wakcraft.spell.effect;

import heero.mc.mod.wakcraft.characteristic.Characteristic;
import net.minecraft.util.StatCollector;

/**
 * Main IEffectCharacteristic implementation.
 */
public class EffectCharacteristic implements IEffectCharacteristic, IEffectProbability {
    private final IEffectArea effectArea;

    private final Characteristic characteristic;
    private int amountBase;
    private float amountFactor;

    private final float probabilityBase;
    private final float probabilityFactor;


    /**
     * Main constructor.
     *
     * @param characteristic    The characteristic to update.
     * @param amountBase        The amount to add to the characteristic (at spell's level 1).
     * @param amountFactor      The factor to increase the amount to add, used with spell's level.
     * @param probabilityBase   The probability to apply the state (at spell's level 1.
     * @param probabilityFactor The factor to increase the probability to apply a state, used with spell's level.
     * @param effectArea        The area of effect of the effect.
     */
    public EffectCharacteristic(final Characteristic characteristic, final int amountBase, final float amountFactor, final float probabilityBase, final float probabilityFactor, IEffectArea effectArea) {
        this.characteristic = characteristic;
        this.amountBase = amountBase;
        this.amountFactor = amountFactor;
        this.probabilityBase = probabilityBase;
        this.probabilityFactor = probabilityFactor;
        this.effectArea = effectArea;
    }

    /**
     * Constructor with default area : POINT. @see #EffectCharacteristic
     */
    public EffectCharacteristic(final Characteristic characteristic, final int amountBase, final float amountFactor, final float probabilityBase, final float probabilityFactor) {
        this(characteristic, amountBase, amountFactor, probabilityBase, probabilityFactor, EffectArea.POINT);
    }

    @Override
    public IEffectArea getZone() {
        return effectArea;
    }

    @Override
    public float getProbability(final int spellLevel) {
        return probabilityBase + probabilityFactor * spellLevel;
    }

    @Override
    public Characteristic getCharacteristic() {
        return characteristic;
    }

    @Override
    public int getValue(int spellLevel) {
        return (int) (amountBase + amountFactor * spellLevel);
    }

    @Override
    public String getDescription(int spellLevel) {
        final float probability = getProbability(spellLevel);
        return StatCollector.translateToLocalFormatted("effect.characteristic." + characteristic + ".description", getValue(spellLevel), (probability >= 1) ? 100 : (int) (probability * 100));
    }
}
