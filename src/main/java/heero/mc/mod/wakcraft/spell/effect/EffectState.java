package heero.mc.mod.wakcraft.spell.effect;

import heero.mc.mod.wakcraft.spell.state.IState;
import net.minecraft.util.StatCollector;

/**
 * Main IEffectState implementation.
 */
public class EffectState implements IEffectState, IEffectProbability {
	private final IEffectArea effectArea;

	private final IState state;
	private final int stateLevelBase;
	private final float stateLevelFactor;
	private final float probabilityBase;
	private final float probabilityFactor;

	/**
	 * Main constructor.
	 * @param state	The state to apply with this effect.
	 * @param stateLevelBase	The level of the state to apply or to add (at spell's level 1).
	 * @param stateLevelFactor	The factor to increase the level to apply, used with spell's level.
	 * @param probabilityBase	The probability to apply the state (at spell's level 1.
	 * @param probabilityFactor	The factor to increase the probability to apply a state, used with spell's level.
	 * @param effectArea	The area of effect of the effect.
	 */
	public EffectState(final IState state, final int stateLevelBase, final float stateLevelFactor, final float probabilityBase, final float probabilityFactor, IEffectArea effectArea) {
		this.state = state;
		this.stateLevelBase = stateLevelBase;
		this.stateLevelFactor = stateLevelFactor;
		this.probabilityBase = probabilityBase;
		this.probabilityFactor = probabilityFactor;
		this.effectArea = effectArea;
	}

	/**
	 * Constructor with default value of effect's area : POINT
	 * @param state	The state to apply with this effect.
	 * @param stateLevelBase	The level of the state to apply or to add (at spell's level 1).
	 * @param stateLevelFactor	The factor to increase the level to apply, used with spell's level.
	 * @param probabilityBase	The probability to apply the state (at spell's level 1.
	 * @param probabilityFactor	The factor to increase the probability to apply a state, used with spell's level.
	 */
	public EffectState(final IState state, final int stateLevelBase, final float stateLevelFactor, final float probabilityBase, final float probabilityFactor) {
		this(state, stateLevelBase, stateLevelFactor, probabilityBase, probabilityFactor, EffectArea.POINT);
	}

	/**
	 * Constructor with default value of effect's area : POINT, and application probability : 100%
	 * @param state	The state to apply with this effect.
	 * @param stateLevelBase	The level of the state to apply or to add (at spell's level 1).
	 * @param stateLevelFactor	The factor to increase the level to apply, used with spell's level.
	 */
	public EffectState(final IState state, final int stateLevelBase, final float stateLevelFactor) {
		this(state, stateLevelBase, stateLevelFactor, 1, 0, EffectArea.POINT);
	}

	/**
	 * Constructor with default value of effect's area : POINT, and application probability : 100%, stateLevelBase : 1 and stateLevelFactor : 0
	 * @param state	The state to apply with this effect.
	 */
	public EffectState(final IState state) {
		this(state, 1, 0, 1, 0, EffectArea.POINT);
	}

	@Override
	public IEffectArea getZone() {
		return effectArea;
	}

	@Override
	public IState getState() {
		return state;
	}

	@Override
	public int getStateLevel(final int spellLevel) {
		return (int) (stateLevelBase + stateLevelFactor * spellLevel);
	}

	@Override
	public float getProbability(final int spellLevel) {
		return probabilityBase + probabilityFactor * spellLevel;
	}

	@Override
	public String getDescription(int spellLevel) {
		return StatCollector.translateToLocalFormatted("effect.state.description", getState(), getStateLevel(spellLevel), getProbability(spellLevel));
	}
}
