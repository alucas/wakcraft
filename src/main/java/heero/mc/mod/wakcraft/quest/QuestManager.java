package heero.mc.mod.wakcraft.quest;

import com.google.gson.Gson;
import heero.mc.mod.wakcraft.WLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public enum QuestManager {
    INSTANCE;

    protected Map<Integer, Quest> quests;

    public void load(final String folderPath) {
        WLog.info("Loading quests");

        quests = new HashMap<>();

        try {
            final ClassLoader classLoader = getClass().getClassLoader();
            if (classLoader == null) {
                WLog.warning("ClassLoader not found");
                return;
            }

            final URL folderURL = classLoader.getResource(folderPath);
            if (folderURL == null) {
                WLog.warning("Folder '" + folderPath + "' not found");
                return;
            }

            final File folder = new File(folderURL.toURI());
            if (!folder.isDirectory()) {
                WLog.warning("'" + folderPath + "' is not a directory");
                return;
            }

            final File[] folderContent = folder.listFiles();
            if (folderContent == null) {
                WLog.warning("'" + folderPath + "' is not a directory");
                return;
            }

            for (final File fileEntry : folderContent) {
                if (fileEntry.isDirectory()) {
                    continue;
                }

                if (!fileEntry.getName().endsWith(".json")) {
                    continue;
                }

                final BufferedReader br = new BufferedReader(new FileReader(fileEntry));

                final Gson gson = new Gson();
                final Quest quest = gson.fromJson(br, Quest.class);

                if (quests.containsKey(quest.id)) {
                    throw new IllegalArgumentException("The quest '" + quests.get(quest.id).name + "' and '" + quest.name + "' are using the same ID '" + quest.id + "'");
                }

                quests.put(quest.id, quest);
            }
        } catch (URISyntaxException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Quest> getQuests() {
        return quests;
    }

    public Quest getQuest(final Integer questId) {
        if (!quests.containsKey(questId)) {
            WLog.warning("Quest not found for ID : " + questId);
            return null;
        }

        return quests.get(questId);
    }

    public QuestTask getTask(final Integer questId, final Integer taskId) {
        final Quest quest = quests.get(questId);
        if (quest == null) {
            return null;
        }

        final QuestTask task = quest.getTask(taskId);
        if (task == null) {
            return null;
        }

        return task;
    }
}
