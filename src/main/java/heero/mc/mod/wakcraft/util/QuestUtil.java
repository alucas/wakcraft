package heero.mc.mod.wakcraft.util;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.entity.npc.EntityWNPC;
import heero.mc.mod.wakcraft.entity.property.QuestInstanceProperty;
import heero.mc.mod.wakcraft.quest.Quest;
import heero.mc.mod.wakcraft.quest.Task;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;
import java.util.Map;

public class QuestUtil {
    protected static QuestInstanceProperty getQuestInstanceProperty(final EntityPlayer player) {
        final QuestInstanceProperty property = (QuestInstanceProperty) player.getExtendedProperties(QuestInstanceProperty.IDENTIFIER);
        if (property == null) {
            WLog.warning("Property not found : " + QuestInstanceProperty.IDENTIFIER);
            return null;
        }

        return property;
    }

    public static Quest getNewQuest(final EntityPlayer player, final EntityWNPC npc) {
        final QuestInstanceProperty property = getQuestInstanceProperty(player);
        if (property == null) {
            return null;
        }

        return property.getNewQuest(npc);
    }

    public static Quest getCurrentQuest(final EntityPlayer player, final EntityWNPC npc) {
        final QuestInstanceProperty property = getQuestInstanceProperty(player);
        if (property == null) {
            return null;
        }

        return property.getCurrentQuest(npc);
    }

    public static Task getLastTask(EntityPlayer player, Quest quest) {
        final QuestInstanceProperty property = getQuestInstanceProperty(player);
        if (property == null) {
            return null;
        }

        return property.getLastTask(quest);
    }

    public static void advance(final EntityPlayer player, final Quest quest) {
        final QuestInstanceProperty property = getQuestInstanceProperty(player);
        if (property == null) {
            return;
        }

        property.advance(quest);
    }

    public static List<Quest> getQuests(final EntityPlayer player) {
        final QuestInstanceProperty property = getQuestInstanceProperty(player);
        if (property == null) {
            return null;
        }

        return property.getQuests();
    }
}
