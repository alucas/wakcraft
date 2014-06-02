package heero.mc.mod.wakcraft.manager;

import java.util.List;
import java.util.Set;

public class FightInfo {
	protected List<List<Integer>> fighters;
	protected Set<FightBlockCoordinates> fightBlocks;
	protected List<FightBlockCoordinates> startBlocks;

	public FightInfo(List<List<Integer>> fighters, Set<FightBlockCoordinates> fightBlocks, List<FightBlockCoordinates> startBlocks) {
		this.fighters = fighters;
		this.fightBlocks = fightBlocks;
		this.startBlocks = startBlocks;
	}

	public FightInfo(List<List<Integer>> fighters) {
		this.fighters = fighters;
	}

	public Set<FightBlockCoordinates> getFightBlocks() {
		return fightBlocks;
	}
}
