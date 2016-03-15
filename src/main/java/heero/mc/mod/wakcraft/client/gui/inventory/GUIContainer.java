package heero.mc.mod.wakcraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class GUIContainer extends GuiContainer {
    public GUIContainer(Container container) {
        super(container);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY,
     * renderPartialTicks
     */
    @SuppressWarnings("unchecked")
    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        drawDefaultBackground();

        drawGuiContainerBackgroundLayer(renderPartialTicks, mouseX, mouseY);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        // GuiScreen code start
        int k;
        for (k = 0; k < this.buttonList.size(); ++k) {
            ((GuiButton) this.buttonList.get(k)).drawButton(this.mc, mouseX, mouseY);
        }

        for (k = 0; k < this.labelList.size(); ++k) {
            ((GuiLabel) this.labelList.get(k)).drawLabel(this.mc, mouseX, mouseY);
        }
        // GuiScreen code end

        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef((float) guiLeft, (float) guiTop, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) 240 / 1.0F, (float) 240 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.theSlot = getHoveredSlot(mouseX, mouseY);

        drawSlots(inventorySlots.inventorySlots, (this.theSlot == null) ? -1 : this.theSlot.slotNumber);

        // Forge: Force lighting to be disabled as there are some issue where
        // lighting would
        // incorrectly be applied based on items that are in the inventory.
        GL11.glDisable(GL11.GL_LIGHTING);
        this.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GL11.glEnable(GL11.GL_LIGHTING);

        InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
        ItemStack itemstack = this.draggedStack == null ? inventoryplayer.getItemStack() : this.draggedStack;

        // Draw hold itemStack
        if (itemstack != null) {
            byte b0 = 8;
            int k1 = this.draggedStack == null ? 8 : 16;
            String format = null;

            if (this.draggedStack != null && this.isRightMouseClick) {
                itemstack = itemstack.copy();
                itemstack.stackSize = MathHelper.ceiling_float_int((float) itemstack.stackSize / 2.0F);
            } else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
                itemstack = itemstack.copy();
                itemstack.stackSize = this.dragSplittingRemnant;

                if (itemstack.stackSize == 0) {
                    format = "" + EnumChatFormatting.YELLOW + "0";
                }
            }

            this.drawItemStack(itemstack, mouseX - guiLeft - b0, mouseY - guiTop - k1, format);
        }

        if (this.returningStack != null) {
            float f1 = (float) (Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;

            if (f1 >= 1.0F) {
                f1 = 1.0F;
                this.returningStack = null;
            }

            int k1 = this.returningStackDestSlot.xDisplayPosition - this.touchUpX;
            int j2 = this.returningStackDestSlot.yDisplayPosition - this.touchUpY;
            int l1 = this.touchUpX + (int) ((float) k1 * f1);
            int i2 = this.touchUpY + (int) ((float) j2 * f1);
            this.drawItemStack(this.returningStack, l1, i2, null);
        }

        GL11.glPopMatrix();

        // Render tooltips
        if (inventoryplayer.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack()) {
            ItemStack itemstack1 = this.theSlot.getStack();
            this.renderToolTip(itemstack1, mouseX, mouseY);
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
    }

    protected void drawSlots(List<Slot> slots, int hoveredSlotId) {
        for (int i = 0; i < inventorySlots.inventorySlots.size(); ++i) {
            Slot slot = (Slot) inventorySlots.inventorySlots.get(i);

            drawSlot(slot);

            if (slot.slotNumber == hoveredSlotId) {
                drawSlotOverlay(slot);
            }
        }
    }

    protected void drawSlotOverlay(Slot slot) {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        int slotX = slot.xDisplayPosition;
        int slotY = slot.yDisplayPosition;

        GL11.glColorMask(true, true, true, false);

        drawRect(slotX, slotY, slotX + 16, slotY + 16, 0x80FFFFFF);

        GL11.glColorMask(true, true, true, true);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    protected Slot getHoveredSlot(int mouseX, int mouseY) {
        for (int i = 0; i < inventorySlots.inventorySlots.size(); ++i) {
            Slot slot = (Slot) inventorySlots.inventorySlots.get(i);

            if (isMouseOverSlot(slot, mouseX, mouseY) && slot.canBeHovered()) {
                return slot;
            }
        }

        return null;
    }
}