package heero.mc.mod.wakcraft.fight;


import java.util.List;
import java.util.Set;

import net.minecraft.entity.EntityLivingBase;

import com.sun.istack.internal.Nullable;

public class FightInfo {
	protected static final int PREFIGHT_DURATION = 60;
	protected static final int FIGHTTURN_DURATION = 60;

	public enum Stage {
		UNKNOW,
		PREFIGHT,
		FIGHT;
	}

	protected List<List<EntityLivingBase>> fightersByTeam;
	protected Set<FightBlockCoordinates> fightBlocks;
	protected List<List<FightBlockCoordinates>> startBlocks;
	public List<EntityLivingBase> fightersByFightOrder;
	public int currentFighterIndex;
	public Stage stage;
	public int timer;

	public FightInfo(List<List<EntityLivingBase>> fightersByTeam, @Nullable Set<FightBlockCoordinates> fightBlocks, List<List<FightBlockCoordinates>> startBlocks) {
		this(fightersByTeam, fightBlocks, startBlocks, Stage.PREFIGHT, PREFIGHT_DURATION);
	}

	public FightInfo(List<List<EntityLivingBase>> fightersByTeam, @Nullable Set<FightBlockCoordinates> fightBlocks, List<List<FightBlockCoordinates>> startBlocks, Stage stage, int timer) {
		this.fightersByTeam = fightersByTeam;
		this.fightBlocks = fightBlocks;
		this.startBlocks = startBlocks;
		this.stage = stage;
		this.timer = timer;
	}

	public Set<FightBlockCoordinates> getFightBlocks() {
		return fightBlocks;
	}

	public void setStage(Stage stage) {
		setStage(stage, 0);
	}

	public void setStage(Stage stage, int duration) {
		switch (stage) {
		case PREFIGHT:
			this.stage = stage;
			this.timer = duration;
			this.currentFighterIndex = 0;
			
			break;

		default:
			break;
		}
	}
}
