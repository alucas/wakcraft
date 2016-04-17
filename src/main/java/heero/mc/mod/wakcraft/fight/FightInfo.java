package heero.mc.mod.wakcraft.fight;


import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.util.CharacteristicUtil;
import net.minecraft.entity.EntityLivingBase;

import javax.annotation.Nullable;
import java.util.*;

public class FightInfo {
    public enum FightStage {
        UNKNOWN,
        PRE_FIGHT,
        FIGHT
    }

    protected List<List<EntityLivingBase>> fightersByTeam;
    protected Set<FightBlockCoordinates> fightBlocks;
    protected List<List<FightBlockCoordinates>> startBlocks;
    protected List<EntityLivingBase> fightersByFightOrder;
    protected int currentFighterIndex;
    protected FightStage stage;
    protected int timer;

    public FightInfo(List<List<EntityLivingBase>> fightersByTeam, @Nullable Set<FightBlockCoordinates> fightBlocks, List<List<FightBlockCoordinates>> startBlocks) {
        this(fightersByTeam, fightBlocks, startBlocks, FightStage.PRE_FIGHT, WConfig.getWakfuFightPreFightDuration());
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

    public void setCurrentFighter(EntityLivingBase fighter) {
        this.currentFighterIndex = this.fightersByFightOrder.indexOf(fighter);
    }

    public EntityLivingBase getCurrentFighter() {
        return this.fightersByFightOrder.get(this.currentFighterIndex);
    }

    public EntityLivingBase getNextFighter(int increment) {
        return this.fightersByFightOrder.get((this.currentFighterIndex + increment) % this.fightersByFightOrder.size());
    }

    /**
     * Update the timer.
     *
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
        List<List<EntityLivingBase>> fightersTmp = new ArrayList<>();
        for (List<EntityLivingBase> team : fighters) {
            fightersTmp.add(new ArrayList<>(team));
        }

        for (List<EntityLivingBase> team : fightersTmp) {
            Collections.sort(team, new Comparator<EntityLivingBase>() {
                @Override
                public int compare(EntityLivingBase a, EntityLivingBase b) {
                    int initiativeA = CharacteristicUtil.getCharacteristic(a, Characteristic.INITIATIVE);
                    int initiativeB = CharacteristicUtil.getCharacteristic(b, Characteristic.INITIATIVE);
                    return initiativeA == initiativeB ? 0 : initiativeA > initiativeB ? -1 : 1;
                }
            });
        }

        Collections.sort(fightersTmp, new Comparator<List<EntityLivingBase>>() {
            @Override
            public int compare(List<EntityLivingBase> a, List<EntityLivingBase> b) {
                int initiativeA = CharacteristicUtil.getCharacteristic(a.get(0), Characteristic.INITIATIVE);
                int initiativeB = CharacteristicUtil.getCharacteristic(b.get(0), Characteristic.INITIATIVE);
                return initiativeA == initiativeB ? 0 : initiativeA > initiativeB ? -1 : 1;
            }
        });

        List<EntityLivingBase> fightersSorted = new ArrayList<>();
        List<Iterator<EntityLivingBase>> iterators = new ArrayList<>();
        for (List<EntityLivingBase> team : fighters) {
            iterators.add(team.iterator());
        }

        int previousListSize;
        do {
            previousListSize = fightersSorted.size();

            for (Iterator<EntityLivingBase> iterator : iterators) {

                if (iterator.hasNext()) {
                    fightersSorted.add(iterator.next());
                }
            }
        } while (previousListSize != fightersSorted.size());

        return fightersSorted;
    }
}
