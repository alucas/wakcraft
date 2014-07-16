package heero.mc.mod.wakcraft.spell.effect;

/**
 * Generic areas of effect.
 */
public enum EffectArea implements IEffectArea {
	/** Area of one block. */
	POINT,
	/** All 8 blocks around the specified block. */
	AROUND,
	/** A cross of 5 blocks. */
	CROSS
}
