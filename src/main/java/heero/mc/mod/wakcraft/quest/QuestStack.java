package heero.mc.mod.wakcraft.quest;

import heero.mc.mod.wakcraft.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class QuestStack {
    public String name;
    public Integer meta;
    public Integer quantity;

    public ItemStack toItemStack() {
        final Item item = GameRegistry.findItem(Reference.MODID, name);
        if (item == null) {
            return null;
        }

        return new ItemStack(
                item,
                quantity == null || quantity <= 0 ? 1 : quantity,
                meta == null || meta <= 0 ? 0 : meta);
    }
}
