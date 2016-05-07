package heero.mc.mod.wakcraft.client.gui.inventory;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.quest.QuestItem;
import heero.mc.mod.wakcraft.quest.QuestTask;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GUINPCGive extends GuiContainer {
    protected static final ResourceLocation textures = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/npc_give.png");

    protected final QuestTask task;

    public GUINPCGive(Container container, QuestTask task) {
        super(container);

        this.task = task;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        for (int i = 0; i < task.what.length; i++) {
            final QuestItem questItem = task.what[i];
            final Item item = GameRegistry.findItem(Reference.MODID, questItem.name);
            if (item == null) {
                continue;
            }

            drawItemStack(new ItemStack(item, questItem.quantity), 25 + i * 20, 22, null);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(textures);

        // Background
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
