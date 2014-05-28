package heero.mc.mod.wakcraft.manager;

import java.util.Set;

public class FightMap {
	protected Set<FightBlockCoordinates> fightBlocks;
	protected Set<FightBlockCoordinates> startBlocks;

	public FightMap(Set<FightBlockCoordinates> fightBlocks, Set<FightBlockCoordinates> startBlocks) {
		this.fightBlocks = fightBlocks;
		this.startBlocks = startBlocks;
	}

	public Set<FightBlockCoordinates> getFightBlocks() {
		return fightBlocks;
	}
}
