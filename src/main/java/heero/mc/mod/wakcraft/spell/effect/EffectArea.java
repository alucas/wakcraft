package heero.mc.mod.wakcraft.spell.effect;

/**
 * Generic areas of effect.
 */
public enum EffectArea implements IEffectArea {
	/** The caster of the spell */
	CASTER,
	/** Area of one block. */
	POINT,
	/** All 8 blocks around the specified block. */
	AROUND,
	/** A cross, size 1. */
	CROSS,
	/** Vertical line, size 3. */
	LINE_V_3,
	/** The default area, size 2. */
	AREA_2
}
