package heero.mc.mod.wakcraft.fight;


import java.util.List;
import java.util.Set;

import net.minecraft.entity.EntityLivingBase;

import com.sun.istack.internal.Nullable;

public class FightInfo {
	public enum Stage {
		PREFIGHT,
		FIGHT;
	}

	protected List<List<EntityLivingBase>> fighters;
	protected Set<FightBlockCoordinates> fightBlocks;
	protected List<FightBlockCoordinates> startBlocks;
	protected int tickStart;
	protected Stage stage;

	public FightInfo(List<List<EntityLivingBase>> fighters, @Nullable Set<FightBlockCoordinates> fightBlocks, List<FightBlockCoordinates> startBlocks, int tickStart) {
		this.fighters = fighters;
		this.fightBlocks = fightBlocks;
		this.startBlocks = startBlocks;
		this.tickStart = tickStart;
		this.stage = Stage.PREFIGHT;
	}

	public Set<FightBlockCoordinates> getFightBlocks() {
		return fightBlocks;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public int getTickStart() {
		return tickStart;
	}
}
