package heero.mc.mod.wakcraft.spell.effect;

import java.util.List;

import net.minecraft.util.ChunkCoordinates;


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
	public List<ChunkCoordinates> getEffectCoors(final ChunkCoordinates fighterPosition, final int targetPosX, final int targetPosY, final int targetPosZ);
}
