package heero.mc.mod.wakcraft.client.gui.fight;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiFightOverlay extends Gui {
    private static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/widgets.png");
    private static final ResourceLocation CHARACTERISTICS = new ResourceLocation(Reference.MODID, "textures/gui/fightoverlay.png");

    protected final Minecraft minecraft;
    protected final RenderItem itemRenderer;

    public GuiFightOverlay(final Minecraft minecraft) {
        this.minecraft = minecraft;
        this.itemRenderer = minecraft.getRenderItem();
    }

    public void renderFighterHotbar(final EntityLivingBase fighter, final ScaledResolution resolution, final float partialTicks) {
        minecraft.mcProfiler.startSection("actionBar");

        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();

        // Render selected item overlay
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        minecraft.renderEngine.bindTexture(WIDGETS);

        InventoryPlayer inventory = minecraft.thePlayer.inventory;
        drawTexturedModalRect(width / 2 - 91, height - 22, 0, 0, 182, 22);
        drawTexturedModalRect(width / 2 - 91 - 1 + inventory.currentItem * 20,
                height - 22 - 1, 0, 22, 24, 22);

        GL11.glDisable(GL11.GL_BLEND);

        // Render items
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();

        for (int i = 0; i < 9; ++i) {
            int x = width / 2 - 90 + i * 20 + 2;
            int z = height - 16 - 3;
            renderInventorySlot(fighter, i + 25, x, z, partialTicks);
        }

        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);

        minecraft.mcProfiler.endSection();
    }

    /**
     * Renders the specified item of the inventory slot at the specified
     * location. Args: slot, x, y, partialTick
     */
    protected void renderInventorySlot(final EntityLivingBase fighter, final int slotId, final int x, final int y, final float partialTick) {
        ItemStack itemstack = FightUtil.getSpellsInventory(fighter).getStackInSlot(slotId);
//		ItemStack itemstack = this.minecraft.thePlayer.inventory.mainInventory[slotId];

        if (itemstack != null) {
            float f1 = (float) itemstack.animationsToGo - partialTick;

            if (f1 > 0.0F) {
                GL11.glPushMatrix();
                float f2 = 1.0F + f1 / 5.0F;
                GL11.glTranslatef((float) (x + 8), (float) (y + 12), 0.0F);
                GL11.glScalef(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
                GL11.glTranslatef((float) (-(x + 8)),
                        (float) (-(y + 12)), 0.0F);
            }

            itemRenderer.func_175030_a(this.minecraft.fontRendererObj, itemstack, x, y);

            if (f1 > 0.0F) {
                GL11.glPopMatrix();
            }

            itemRenderer.func_175030_a(this.minecraft.fontRendererObj, itemstack, x, y);
        }
    }

    public void renderCharacteristics(final EntityLivingBase fighter, final ScaledResolution resolution) {
        minecraft.mcProfiler.startSection("characteristics");

        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        minecraft.renderEngine.bindTexture(CHARACTERISTICS);

        drawTexturedModalRect(width / 2 - 114, height - 44, 0, 0, 100, 100);

        int pointAP = FightUtil.getFightCharacteristic(fighter, Characteristic.ACTION);
        int pointMP = FightUtil.getFightCharacteristic(fighter, Characteristic.MOVEMENT);
        int pointWP = FightUtil.getFightCharacteristic(fighter, Characteristic.WAKFU);

        drawString(minecraft.fontRendererObj, Integer.toString(pointAP), width / 2 - 86, height - 38, 0xBBBBBB);
        drawString(minecraft.fontRendererObj, Integer.toString(pointMP), width / 2 - 101, height - 32, 0xBBBBBB);
        drawString(minecraft.fontRendererObj, Integer.toString(pointWP), width / 2 - 107, height - 17, 0xBBBBBB);

        GL11.glDisable(GL11.GL_BLEND);

        minecraft.mcProfiler.endSection();
    }
}
