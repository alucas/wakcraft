package heero.mc.mod.wakcraft.fight;


import java.util.List;
import java.util.Set;

import net.minecraft.entity.EntityLivingBase;

import com.sun.istack.internal.Nullable;

public class FightInfo {
	public enum Stage {
		UNKNOW,
		PREFIGHT,
		FIGHT;
	}

	protected List<List<EntityLivingBase>> fighters;
	protected Set<FightBlockCoordinates> fightBlocks;
	protected List<List<FightBlockCoordinates>> startBlocks;
	public Stage stage;
	public int timer;

	public FightInfo(List<List<EntityLivingBase>> fighters, @Nullable Set<FightBlockCoordinates> fightBlocks, List<List<FightBlockCoordinates>> startBlocks) {
		this.fighters = fighters;
		this.fightBlocks = fightBlocks;
		this.startBlocks = startBlocks;
		this.stage = Stage.UNKNOW;
	}

	public Set<FightBlockCoordinates> getFightBlocks() {
		return fightBlocks;
	}
}
