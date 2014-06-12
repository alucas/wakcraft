package heero.mc.mod.wakcraft.entity.creature;

import java.util.Set;
import java.util.UUID;

public interface IFighter {
	public void setGroup(Set<UUID> group);
	public Set<UUID> getGroup();
	public int getId();
}
