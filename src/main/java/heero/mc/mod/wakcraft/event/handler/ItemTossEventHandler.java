package heero.mc.mod.wakcraft.event.handler;

import heero.mc.mod.wakcraft.spell.ISpell;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemTossEventHandler {
    @SubscribeEvent
    public void onItemTossEvent(final ItemTossEvent event) {
        if (event == null) {
            return;
        }

        if (event.entityItem == null) {
            return;
        }

        final ItemStack stack = event.entityItem.getEntityItem();
        if (stack == null) {
            return;
        }

        if (stack.getItem() instanceof ISpell) {
            event.setCanceled(true);
        }
    }
}
