package heero.mc.mod.wakcraft.entity.creature;

import java.util.List;
import java.util.UUID;

import net.minecraft.util.ChunkCoordinates;

public interface IFighter {
	public void setStartPosition(ChunkCoordinates position);
	public ChunkCoordinates getStartPosition();
	public void setGroup(List<UUID> group);
	public List<UUID> getGroup();
	public int getId();
}
