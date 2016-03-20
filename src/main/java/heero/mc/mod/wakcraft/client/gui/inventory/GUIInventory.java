package heero.mc.mod.wakcraft.client.gui.inventory;

import heero.mc.mod.wakcraft.Reference;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIInventory extends InventoryEffectRenderer {
    private static final ResourceLocation background = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/inventory.png");

    public GUIInventory(Container container) {
        super(container);

        allowUserInput = true;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicks, int mouseX, int mouseY) {
        drawInventoryBackground(mouseX, mouseY);
    }

    protected void drawInventoryBackground(int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        GuiInventory.drawEntityOnScreen(guiLeft + 51, guiTop + 75, 30,
                (float) (guiLeft + 51) - mouseX,
                (float) (guiTop + 75 - 50) - mouseY,
                mc.thePlayer);
    }

    /**
     * Draws either a gradient over the background screen (when it exists) or a
     * flat gradient over background.png
     */
    @Override
    public void drawDefaultBackground() {
    }
}