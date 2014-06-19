package heero.mc.mod.wakcraft.fight;


import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.helper.CharacteristicsHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.EntityLivingBase;

import com.sun.istack.internal.Nullable;

public class FightInfo {
	protected static final int PREFIGHT_DURATION = 60;
	protected static final int FIGHTTURN_DURATION = 60;

	public enum FightStage {
		UNKNOW,
		PREFIGHT,
		FIGHT;
	}

	protected List<List<EntityLivingBase>> fightersByTeam;
	protected Set<FightBlockCoordinates> fightBlocks;
	protected List<List<FightBlockCoordinates>> startBlocks;
	protected List<EntityLivingBase> fightersByFightOrder;
	protected int currentFighterIndex;
	protected FightStage stage;
	protected int timer;

	public FightInfo(List<List<EntityLivingBase>> fightersByTeam, @Nullable Set<FightBlockCoordinates> fightBlocks, List<List<FightBlockCoordinates>> startBlocks) {
		this(fightersByTeam, fightBlocks, startBlocks, FightStage.PREFIGHT, PREFIGHT_DURATION);
	}

	public FightInfo(List<List<EntityLivingBase>> fightersByTeam, @Nullable Set<FightBlockCoordinates> fightBlocks, List<List<FightBlockCoordinates>> startBlocks, FightStage stage, int timer) {
		this.fightersByTeam = fightersByTeam;
		this.fightBlocks = fightBlocks;
		this.startBlocks = startBlocks;

		setStage(stage, timer);
	}

	public List<List<EntityLivingBase>> getFightersByTeam() {
		return this.fightersByTeam;
	}

	public Set<FightBlockCoordinates> getFightBlocks() {
		return fightBlocks;
	}

	public List<List<FightBlockCoordinates>> getStartBlocks() {
		return this.startBlocks;
	}

	public void setStage(FightStage stage) {
		setStage(stage, 0);
	}

	public void setStage(FightStage stage, int duration) {
		switch (stage) {
		case FIGHT:
			this.fightersByFightOrder = getFightersByFightOrder(this.getFightersByTeam());
			this.currentFighterIndex = 0;
			
			break;

		default:
			break;
		}

		this.timer = duration;
		this.stage = stage;
	}

	public FightStage getStage() {
		return this.stage;
	}

	public void updateStageDuration(int duration) {
		this.timer = duration;
	}

	public List<EntityLivingBase> getFightersByFightOrder() {
		return this.fightersByFightOrder;
	}

	public int getCurrentFighterId() {
		return this.currentFighterIndex;
	}

	public void setCurrentFighterId(int currentFighterId) {
		this.currentFighterIndex = currentFighterId;
	}

	/**
	 * Update the timer.
	 * @return True if the timer is expired.
	 */
	public boolean tick() {
		return (--this.timer) <= 0;
	}

	public int getFightersCount() {
		if (fightersByFightOrder != null) {
			return fightersByFightOrder.size();
		}

		int count = 0;
		for (List<EntityLivingBase> team : fightersByTeam) {
			count += team.size();
		}

		return count;
	}

	protected static List<EntityLivingBase> getFightersByFightOrder(List<List<EntityLivingBase>> fighters) {
		List<List<EntityLivingBase>> fightersTmp = new ArrayList<List<EntityLivingBase>>();
		for (List<EntityLivingBase> team : fighters) {
			fightersTmp.add(new ArrayList<EntityLivingBase>(team));
		}

		for (List<EntityLivingBase> team : fightersTmp) {
			Collections.sort(team, new Comparator<EntityLivingBase>(){
				@Override
				public int compare(EntityLivingBase a, EntityLivingBase b) {
					int initiativeA = CharacteristicsHelper.getCharacteristic(a, Characteristic.INITIATIVE);
					int initiativeB = CharacteristicsHelper.getCharacteristic(b, Characteristic.INITIATIVE);
					return initiativeA == initiativeB ? 0 : initiativeA > initiativeB ? -1 : 1;
				}
			} );
		}

		Collections.sort(fightersTmp, new Comparator<List<EntityLivingBase>>(){
			@Override
			public int compare(List<EntityLivingBase> a, List<EntityLivingBase> b) {
				int initiativeA = CharacteristicsHelper.getCharacteristic(a.get(0), Characteristic.INITIATIVE);
				int initiativeB = CharacteristicsHelper.getCharacteristic(b.get(0), Characteristic.INITIATIVE);
				return initiativeA == initiativeB ? 0 : initiativeA > initiativeB ? -1 : 1;
			}
		} );

		List<EntityLivingBase> fightersSorted = new ArrayList<EntityLivingBase>();
		List<Iterator<EntityLivingBase>> iterators = new ArrayList<Iterator<EntityLivingBase>>();
		for (List<EntityLivingBase> team : fighters) {
			iterators.add(team.iterator());
		}

		int previousListSize = 0;
		do {
			previousListSize = fightersSorted.size();

			for (Iterator<EntityLivingBase> iterator : iterators) {
				
				if (iterator.hasNext()) {
					fightersSorted.add(iterator.next());
				}
			}
		} while(previousListSize != fightersSorted.size());

		return fightersSorted;
	}
}
