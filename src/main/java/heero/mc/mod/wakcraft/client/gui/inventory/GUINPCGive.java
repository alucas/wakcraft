package heero.mc.mod.wakcraft.client.gui.inventory;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.inventory.ContainerNPCGive;
import heero.mc.mod.wakcraft.quest.Quest;
import heero.mc.mod.wakcraft.quest.QuestTask;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUINPCGive extends GuiContainer {
    protected static final ResourceLocation textures = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/npc_give.png");

    protected final Quest quest;
    protected final QuestTask task;
    protected final ContainerNPCGive container;

    public GUINPCGive(final ContainerNPCGive container, final Quest quest, final QuestTask task) {
        super(container);

        this.quest = quest;
        this.task = task;
        this.container = container;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        if (container.isTaskDone()) {
            Wakcraft.proxy.closeGUI();
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        final int nbItem = task.what.length;
        final int left = xSize / 2 - nbItem * 20 / 2;

        for (int i = 0; i < nbItem; i++) {
            final ItemStack stack = task.what[i].toItemStack();
            if (stack == null) {
                continue;
            }

            drawItemStack(stack, left + i * 20, 22, null);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(textures);

        // Background
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        // Recipe/Slot background
        final int nbItem = task.what.length;
        final int left = guiLeft + xSize / 2 - nbItem * 20 / 2;

        GlStateManager.enableBlend();
        for (int i = 0; i < nbItem; i++) {
            drawTexturedModalRect(left + i * 20, guiTop + 22, 0, ySize + 1, 16, 16);
            drawTexturedModalRect(left + i * 20, guiTop + 42, 0, ySize + 1, 16, 16);
        }
        GlStateManager.disableBlend();
    }
}
