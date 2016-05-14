package heero.mc.mod.wakcraft.client.gui.fight;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiFightOverlay extends Gui {
    private static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/widgets.png");
    private static final ResourceLocation CHARACTERISTICS = new ResourceLocation(Reference.MODID, "textures/gui/fightoverlay.png");

    protected final Minecraft minecraft;
    protected final RenderItem itemRenderer;

    public GuiFightOverlay(final Minecraft minecraft) {
        this.minecraft = minecraft;
        this.itemRenderer = minecraft.getRenderItem();
    }

    public void renderFighterHotbar(final EntityPlayer fighter, final ScaledResolution resolution, final float partialTicks) {
        minecraft.mcProfiler.startSection("actionBar");

        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        minecraft.getTextureManager().bindTexture(WIDGETS);

        int centerX = width / 2;
        float oldZLevel = this.zLevel;
        this.zLevel = -90.0F;
        this.drawTexturedModalRect(centerX - 91, height - 22, 0, 0, 182, 22);
        this.drawTexturedModalRect(centerX - 91 - 1 + fighter.inventory.currentItem * 20, height - 22 - 1, 0, 22, 24, 22);
        this.zLevel = oldZLevel;

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();

        for (int i = 0; i < 9; ++i) {
            final int posX = width / 2 - 90 + i * 20 + 2;
            final int posY = height - 16 - 3;
            renderHotbarItem(25 + i, posX, posY, partialTicks, fighter);
        }

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();

        minecraft.mcProfiler.endSection();
    }

    /**
     * Renders the specified item of the inventory slot at the specified
     * location. Args: slot, x, y, partialTick
     */
    protected void renderHotbarItem(int index, int xPos, int yPos, float partialTicks, EntityPlayer fighter) {
        ItemStack itemstack = FightUtil.getSpellsInventory(fighter).getStackInSlot(index);
        if (itemstack == null) {
            return;
        }

        float f = (float) itemstack.animationsToGo - partialTicks;

        if (f > 0.0F) {
            GlStateManager.pushMatrix();
            float f1 = 1.0F + f / 5.0F;
            GlStateManager.translate((float) (xPos + 8), (float) (yPos + 12), 0.0F);
            GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
            GlStateManager.translate((float) (-(xPos + 8)), (float) (-(yPos + 12)), 0.0F);
        }

        this.itemRenderer.renderItemAndEffectIntoGUI(itemstack, xPos, yPos);

        if (f > 0.0F) {
            GlStateManager.popMatrix();
        }

        this.itemRenderer.renderItemOverlays(minecraft.fontRendererObj, itemstack, xPos, yPos);
    }

    public void renderCharacteristics(final EntityLivingBase fighter, final ScaledResolution resolution) {
        minecraft.mcProfiler.startSection("characteristics");

        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        minecraft.renderEngine.bindTexture(CHARACTERISTICS);

        drawTexturedModalRect(width / 2 - 114, height - 44, 0, 0, 100, 100);

        final Integer pointAP = FightUtil.getFightCharacteristic(fighter, Characteristic.ACTION);
        final Integer pointMP = FightUtil.getFightCharacteristic(fighter, Characteristic.MOVEMENT);
        final Integer pointWP = FightUtil.getFightCharacteristic(fighter, Characteristic.WAKFU);
        if (pointAP != null && pointMP != null && pointWP != null) {
            drawString(minecraft.fontRendererObj, Integer.toString(pointAP), width / 2 - 86, height - 38, 0xBBBBBB);
            drawString(minecraft.fontRendererObj, Integer.toString(pointMP), width / 2 - 101, height - 32, 0xBBBBBB);
            drawString(minecraft.fontRendererObj, Integer.toString(pointWP), width / 2 - 107, height - 17, 0xBBBBBB);
        }

        GlStateManager.disableBlend();

        minecraft.mcProfiler.endSection();
    }
}
