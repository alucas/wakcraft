package heero.mc.mod.wakcraft.spell.effect;

import net.minecraft.util.StatCollector;

/**
 * Main IEffectDamage implementation.
 */
public class EffectMovement implements IEffectMovement {
	private final int movementBase;
	private final float movementFactor;
	private final IEffectArea effectArea;

	/**
	 * Main constructor.
	 * @param movementBase		The movement base value.
	 * @param movementFactor	The movement factor value.
	 * @param effectArea		The area of the effect.
	 */
	public EffectMovement(final int movementBase, final float movementFactor, IEffectArea effectArea) {
		this.movementBase = movementBase;
		this.movementFactor = movementFactor;
		this.effectArea = effectArea;
	}

	/**
	 * Constructor with default value of effect's area : CASTER
	 * @param movementBase		The movement vase value.
	 * @param movementFactor	The movement factor value.
	 */
	public EffectMovement(final int movementBase, final float movementFactor) {
		this(movementBase, movementFactor, EffectArea.CASTER);
	}

	@Override
	public int getValue(final int spellLevel) {
		return (int) (movementBase + movementFactor * spellLevel);
	}

	@Override
	public IEffectArea getZone() {
		return effectArea;
	}

	@Override
	public String getDescription(int spellLevel) {
		return StatCollector.translateToLocalFormatted("effect.movement.description", getValue(spellLevel));
	}
}
