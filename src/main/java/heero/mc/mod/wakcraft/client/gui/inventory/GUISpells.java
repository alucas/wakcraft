package heero.mc.mod.wakcraft.client.gui.inventory;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.inventory.ContainerSpells;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUISpells extends InventoryEffectRenderer {
    private static final ResourceLocation background = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/spells.png");

    public GUISpells(ContainerSpells container) {
        super(container);

        allowUserInput = true;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(StatCollector.translateToLocal("spell.category.elemental"), 10, 9, 0x404040);
        fontRendererObj.drawString(StatCollector.translateToLocal("spell.category.speciality"), 10, 80, 0x404040);
        fontRendererObj.drawString(StatCollector.translateToLocal("spell.category.shortcut"), 10, 132, 0x404040);
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