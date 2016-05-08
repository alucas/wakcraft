package heero.mc.mod.wakcraft.util;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.entity.npc.EntityWNPC;
import heero.mc.mod.wakcraft.entity.property.QuestInstanceProperty;
import heero.mc.mod.wakcraft.network.packet.PacketExtendedEntityProperty;
import heero.mc.mod.wakcraft.quest.Quest;
import heero.mc.mod.wakcraft.quest.QuestTask;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;

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

    public static void startNewQuest(final EntityPlayer player, final Quest quest) {
        final QuestInstanceProperty property = getQuestInstanceProperty(player);
        if (property == null) {
            return;
        }

        property.startNewQuest(quest);
    }

    public static Quest getOnGoingQuest(final EntityPlayer player, final EntityWNPC npc) {
        final QuestInstanceProperty property = getQuestInstanceProperty(player);
        if (property == null) {
            return null;
        }

        return property.getOnGoingQuest(npc);
    }

    public static boolean isQuestTerminated(EntityPlayer player, Quest quest) {
        final QuestInstanceProperty property = getQuestInstanceProperty(player);
        if (property == null) {
            return true;
        }

        return property.isQuestTerminated(quest);
    }

    public static QuestTask getCurrentTask(EntityPlayer player, Quest quest) {
        final QuestInstanceProperty property = getQuestInstanceProperty(player);
        if (property == null) {
            return null;
        }

        return property.getCurrentTask(quest);
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

    public static Quest getQuest(final EntityPlayer player, final Integer questId) {
        final QuestInstanceProperty property = getQuestInstanceProperty(player);
        if (property == null) {
            return null;
        }

        return property.getQuest(questId);
    }

    public static void sendAdvancementToClient(final EntityPlayer player) {
        if (player.worldObj.isRemote) {
            return;
        }

        Wakcraft.packetPipeline.sendTo(new PacketExtendedEntityProperty(player, QuestInstanceProperty.IDENTIFIER), (EntityPlayerMP) player);
    }
}
