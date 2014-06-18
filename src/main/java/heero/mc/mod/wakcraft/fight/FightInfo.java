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

	protected List<List<EntityLivingBase>> fightersByTeam;
	protected Set<FightBlockCoordinates> fightBlocks;
	protected List<List<FightBlockCoordinates>> startBlocks;
	public List<EntityLivingBase> fightersByFightOrder;
	public Stage stage;
	public int timer;

	public FightInfo(List<List<EntityLivingBase>> fightersByTeam, @Nullable Set<FightBlockCoordinates> fightBlocks, List<List<FightBlockCoordinates>> startBlocks) {
		this.fightersByTeam = fightersByTeam;
		this.fightBlocks = fightBlocks;
		this.startBlocks = startBlocks;
		this.stage = Stage.UNKNOW;
	}

	public Set<FightBlockCoordinates> getFightBlocks() {
		return fightBlocks;
	}
}
