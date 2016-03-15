package heero.mc.mod.wakcraft.client.gui.inventory;

import heero.mc.mod.wakcraft.Reference;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUISpells extends InventoryEffectRenderer {
    private static final ResourceLocation background = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/spells.png");

    public GUISpells(Container container) {
        super(container);

        allowUserInput = true;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(StatCollector.translateToLocal("spell.elemental"), 10, 9, 0x404040);
        fontRendererObj.drawString(StatCollector.translateToLocal("spell.specialities"), 10, 80, 0x404040);
        fontRendererObj.drawString(StatCollector.translateToLocal("spell.shortcuts"), 10, 132, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void drawDefaultBackground() {
    }
}