package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.entity.npc.EntityWNPC;
import heero.mc.mod.wakcraft.quest.Quest;
import heero.mc.mod.wakcraft.quest.QuestManager;
import heero.mc.mod.wakcraft.quest.Task;
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

    protected Map<Integer, Integer> lastTasks;

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        final NBTTagList tagQuests = new NBTTagList();
        for (final Integer questId : lastTasks.keySet()) {
            final NBTTagCompound tagQuest = new NBTTagCompound();
            tagQuest.setInteger(TAG_QUEST_ID, questId);
            tagQuest.setInteger(TAG_TASK_ID, lastTasks.get(questId));

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

            lastTasks.put(questId, taskId);
        }
    }

    @Override
    public void init(Entity entity, World world) {
        lastTasks = new HashMap<>();
    }

    public List<Quest> getQuests() {
        final List<Quest> quests = new ArrayList<>();
        for (final Integer questId : lastTasks.keySet()) {
            quests.add(QuestManager.INSTANCE.getQuests().get(questId));
        }

        return quests;
    }

    public Quest getCurrentQuest(final EntityWNPC npc) {
        final String npcId = EntityList.getEntityString(npc);
        if (npcId == null || npcId.trim().isEmpty()) {
            WLog.warning("Id not found for NPC : " + npc.getName());
            return null;
        }

        final Map<Integer, Quest> allQuests = QuestManager.INSTANCE.getQuests();
        for (final Integer questId : allQuests.keySet()) {
            if (!lastTasks.containsKey(questId)) {
                continue;
            }

            final Quest quest = allQuests.get(questId);
            final Task task = getLastTask(quest);
            if (task == null) {
                return null;
            }

            if (npcId.equals(task.to)) {
                return quest;
            }
        }

        return null;
    }

    public Task getLastTask(final Quest quest) {
        final Task task = quest.getTask(lastTasks.get(quest.id));
        if (task == null) {
            WLog.warning("Last task not found for quest : " + quest.name);
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
            if (lastTasks.containsKey(questId)) {
                continue;
            }

            final Quest quest = allQuests.get(questId);
            if (!npcId.equals(quest.by)) {
                continue;
            }

            lastTasks.put(questId, 0);

            return quest;
        }

        return null;
    }

    public void advance(final Quest quest) {
        lastTasks.put(quest.id, lastTasks.get(quest.id) + 1);
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
