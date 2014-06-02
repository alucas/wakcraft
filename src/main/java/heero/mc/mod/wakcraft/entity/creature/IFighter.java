package heero.mc.mod.wakcraft.entity.creature;

import net.minecraft.util.ChunkCoordinates;

public interface IFighter {
	public void setStartPosition(ChunkCoordinates position);
	public ChunkCoordinates getStartPosition();
}
