package heero.mc.mod.wakcraft.spell.effect;

import heero.mc.mod.wakcraft.spell.state.IState;

/**
 * Main IEffectState implementation.
 */
public class EffectState implements IEffectState {
	private final IEffectArea effectArea;

	private final IState state;
	private final int stateLevel;
	private final int probability;

	/**
	 * Main constructor.
	 * @param state	The state to apply with this effect.
	 * @param stateLevel	The level of the state to apply or to add.
	 * @param probability	The probability to apply the state.
	 * @param effectArea	The area of effect of the effect.
	 */
	public EffectState(final IState state, final int stateLevel, final int probability, IEffectArea effectArea) {
		this.state = state;
		this.stateLevel = stateLevel;
		this.probability = probability;
		this.effectArea = effectArea;
	}

	/**
	 * Constructor with default value of effect's area : POINT
	 */
	public EffectState(final IState state, final int stateLevel, final int probability) {
		this(state, stateLevel, probability, EffectArea.POINT);
	}

	/**
	 * Constructor with default value of effect's area : POINT, and application probability : 100%
	 */
	public EffectState(final IState state, final int stateLevel) {
		this(state, stateLevel, 100, EffectArea.POINT);
	}

	/**
	 * Constructor with default value of effect's area : POINT, and application probability : 100% and stateLevel : 1
	 */
	public EffectState(final IState state) {
		this(state, 1, 100, EffectArea.POINT);
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
	public int getStateLevel() {
		return stateLevel;
	}

	@Override
	public int getProbability() {
		return probability;
	}
}
