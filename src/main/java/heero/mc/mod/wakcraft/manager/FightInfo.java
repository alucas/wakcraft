package heero.mc.mod.wakcraft.manager;

import java.util.List;
import java.util.Set;

import net.minecraft.entity.EntityLivingBase;

public class FightInfo {
	protected List<List<EntityLivingBase>> fighters;
	protected Set<FightBlockCoordinates> fightBlocks;
	protected List<FightBlockCoordinates> startBlocks;

	public FightInfo(List<List<EntityLivingBase>> fighters, Set<FightBlockCoordinates> fightBlocks, List<FightBlockCoordinates> startBlocks) {
		this.fighters = fighters;
		this.fightBlocks = fightBlocks;
		this.startBlocks = startBlocks;
	}

	public FightInfo(List<List<EntityLivingBase>> fighters, List<FightBlockCoordinates> startBlocks) {
		this.fighters = fighters;
		this.startBlocks = startBlocks;
	}

	public Set<FightBlockCoordinates> getFightBlocks() {
		return fightBlocks;
	}
}
