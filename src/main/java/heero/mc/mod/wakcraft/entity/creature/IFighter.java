package heero.mc.mod.wakcraft.entity.creature;

import java.util.Set;
import java.util.UUID;

import net.minecraft.util.ChunkCoordinates;

public interface IFighter {
	public void setStartPosition(ChunkCoordinates position);
	public ChunkCoordinates getStartPosition();
	public void setGroup(Set<UUID> group);
	public Set<UUID> getGroup();
	public int getId();
}
