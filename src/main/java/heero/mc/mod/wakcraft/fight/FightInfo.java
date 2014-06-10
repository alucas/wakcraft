package heero.mc.mod.wakcraft.fight;


import java.util.List;
import java.util.Set;

import net.minecraft.entity.EntityLivingBase;

import com.sun.istack.internal.Nullable;

public class FightInfo {
	protected List<List<EntityLivingBase>> fighters;
	protected Set<FightBlockCoordinates> fightBlocks;
	protected List<FightBlockCoordinates> startBlocks;

	public FightInfo(List<List<EntityLivingBase>> fighters, @Nullable Set<FightBlockCoordinates> fightBlocks, List<FightBlockCoordinates> startBlocks) {
		this.fighters = fighters;
		this.fightBlocks = fightBlocks;
		this.startBlocks = startBlocks;
	}

	public Set<FightBlockCoordinates> getFightBlocks() {
		return fightBlocks;
	}
}
