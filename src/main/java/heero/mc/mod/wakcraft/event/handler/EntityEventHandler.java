package heero.mc.mod.wakcraft.event.handler;

import heero.mc.mod.wakcraft.entity.npc.EntityWNPC;
import heero.mc.mod.wakcraft.entity.property.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityEventHandler {
    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            player.registerExtendedProperties(ProfessionProperty.IDENTIFIER, new ProfessionProperty());
            player.registerExtendedProperties(HavenBagProperty.IDENTIFIER, new HavenBagProperty());
            player.registerExtendedProperties(InventoryProperty.IDENTIFIER, new InventoryProperty());
            player.registerExtendedProperties(CharacterProperty.IDENTIFIER, new CharacterProperty());
            player.registerExtendedProperties(ItemInUseProperty.IDENTIFIER, new ItemInUseProperty());
            player.registerExtendedProperties(ZaapProperty.IDENTIFIER, new ZaapProperty());
            player.registerExtendedProperties(QuestInstanceProperty.IDENTIFIER, new QuestInstanceProperty());
        }

        if (event.entity instanceof EntityWNPC) {
            event.entity.registerExtendedProperties(QuestProperty.IDENTIFIER, new QuestProperty());
        }
    }
}