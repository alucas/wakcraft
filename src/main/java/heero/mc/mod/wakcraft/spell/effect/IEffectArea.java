package heero.mc.mod.wakcraft.spell.effect;

import java.util.List;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MovingObjectPosition;


/**
 * Area of effect interface.
 */
public interface IEffectArea {
	/**
	 * Returns the coordinates of the blocks affected by the effect.
	 * @param fighterPosition	The Fighter coordinates.
	 * @param aimingPosition	The aiming coordinates.
	 * @return	The coordinates of the blocks affected by the effect.
	 */
	public List<ChunkCoordinates> getEffectCoors(final ChunkCoordinates fighterPosition, final MovingObjectPosition aimingPosition);
}
