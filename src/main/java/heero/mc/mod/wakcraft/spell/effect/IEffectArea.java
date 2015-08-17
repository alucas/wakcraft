package heero.mc.mod.wakcraft.spell.effect;

import net.minecraft.util.BlockPos;

import java.util.List;


/**
 * Area of effect interface.
 */
public interface IEffectArea {
	/**
	 * Returns the coordinates of the blocks affected by the effect.
	 * @param fighterPosition	The Fighter coordinates.
	 * @param targetPosX	The target X position.
	 * @param targetPosY	The target Y position.
	 * @param targetPosZ	The target Z position.
	 * @return	The coordinates of the blocks affected by the effect.
	 */
	public List<BlockPos> getEffectCoors(final BlockPos fighterPosition, final int targetPosX, final int targetPosY, final int targetPosZ);

    /**
     * Return the coordinates of the blocks affected by the effect.
     * @param fighterPosition   The Fighter coordinates.
     * @param targetPosition    The Target coordinates.
     * @return  The coordinates of the blocks affected by the effect.
     */
    public List<BlockPos> getEffectCoors(final BlockPos fighterPosition, final BlockPos targetPosition);
}
