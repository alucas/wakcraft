package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.entity.npc.EntityWNPC;
import heero.mc.mod.wakcraft.quest.Quest;
import heero.mc.mod.wakcraft.quest.QuestManager;
import heero.mc.mod.wakcraft.quest.QuestTask;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestInstanceProperty implements IExtendedEntityProperties, ISynchProperties {
    public static final String IDENTIFIER = Reference.MODID + "QuestInstance";

    protected static final String TAG_QUESTS = "Quests";
    protected static final String TAG_QUEST_ID = "QuestId";
    protected static final String TAG_TASK_ID = "Task";

    protected Map<Integer, Integer> currentTasksId;

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        final NBTTagList tagQuests = new NBTTagList();
        for (final Integer questId : currentTasksId.keySet()) {
            final NBTTagCompound tagQuest = new NBTTagCompound();
            tagQuest.setInteger(TAG_QUEST_ID, questId);
            tagQuest.setInteger(TAG_TASK_ID, currentTasksId.get(questId));

            tagQuests.appendTag(tagQuest);
        }

        compound.setTag(TAG_QUESTS, tagQuests);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        final NBTTagList tagQuests = compound.getTagList(TAG_QUESTS, 10);
        for (int i = 0; i < tagQuests.tagCount(); i++) {
            final NBTTagCompound tagQuest = tagQuests.getCompoundTagAt(i);
            final Integer questId = tagQuest.getInteger(TAG_QUEST_ID);
            final Integer taskId = tagQuest.getInteger(TAG_TASK_ID);

            currentTasksId.put(questId, taskId);
        }
    }

    @Override
    public void init(Entity entity, World world) {
        currentTasksId = new HashMap<>();
    }

    public List<Quest> getQuests() {
        final List<Quest> quests = new ArrayList<>();
        for (final Integer questId : currentTasksId.keySet()) {
            quests.add(QuestManager.INSTANCE.getQuest(questId));
        }

        return quests;
    }

    public Quest getQuest(final Integer questId) {
        if (!currentTasksId.containsKey(questId)) {
            WLog.warning("The player doesn't know the quest with id : " + questId);
            return null;
        }

        return QuestManager.INSTANCE.getQuest(questId);
    }

    public Quest getOnGoingQuest(final EntityWNPC npc) {
        final String npcId = EntityList.getEntityString(npc);
        if (npcId == null || npcId.trim().isEmpty()) {
            WLog.warning("Id not found for NPC : " + npc.getName());
            return null;
        }

        for (final Integer questId : currentTasksId.keySet()) {
            final Quest quest = QuestManager.INSTANCE.getQuest(questId);
            if (quest == null) {
                return null;
            }

            if (isQuestTerminated(quest)) {
                continue;
            }

            final QuestTask task = getCurrentTask(quest);
            if (task == null) {
                return null;
            }

            if (npcId.equals(task.to)) {
                return quest;
            }
        }

        return null;
    }

    public boolean isQuestTerminated(final Quest quest) {
        return currentTasksId.get(quest.id) >= quest.tasks.length;
    }

    public QuestTask getCurrentTask(final Quest quest) {
        final QuestTask task = quest.getTask(currentTasksId.get(quest.id));
        if (task == null) {
            WLog.warning("Current task not found for quest : " + quest.name);
            return null;
        }

        return task;
    }

    public Quest getNewQuest(final EntityWNPC npc) {
        final String npcId = EntityList.getEntityString(npc);
        if (npcId == null || npcId.trim().isEmpty()) {
            WLog.warning("Id not found for NPC : " + npc.getName());
            return null;
        }

        final Map<Integer, Quest> allQuests = QuestManager.INSTANCE.getQuests();
        for (final Integer questId : allQuests.keySet()) {
            if (currentTasksId.containsKey(questId)) {
                continue;
            }

            final Quest quest = allQuests.get(questId);
            if (!npcId.equals(quest.by)) {
                continue;
            }

            return quest;
        }

        return null;
    }

    public void startNewQuest(final Quest quest) {
        currentTasksId.put(quest.id, 0);
    }

    public void advance(final Quest quest) {
        currentTasksId.put(quest.id, currentTasksId.get(quest.id) + 1);
    }

    @Override
    public NBTTagCompound getClientPacket() {
        NBTTagCompound tagRoot = new NBTTagCompound();

        saveNBTData(tagRoot);

        return tagRoot;
    }

    @Override
    public void onClientPacket(NBTTagCompound tagRoot) {
        loadNBTData(tagRoot);
    }
}
