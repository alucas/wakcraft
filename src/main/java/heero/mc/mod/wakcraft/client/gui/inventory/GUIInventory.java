package heero.mc.mod.wakcraft.client.gui.inventory;

import heero.mc.mod.wakcraft.WInfo;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIInventory extends InventoryEffectRenderer {
	private static final ResourceLocation background = new ResourceLocation(WInfo.MODID.toLowerCase(), "textures/gui/inventory.png");

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
//		fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float renderPartialTicks,
			int mouseX, int mouseY) {
		drawInventoryBackground(mouseX, mouseY);
	}

	protected void drawInventoryBackground(int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(background);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		GuiInventory.func_147046_a(guiLeft + 51, guiTop + 75, 30,
				(float) (guiLeft + 51) - mouseX, (float) (guiTop + 75 - 50)
						- mouseY, mc.thePlayer);
	}

	/**
	 * Draws either a gradient over the background screen (when it exists) or a
	 * flat gradient over background.png
	 */
	public void drawDefaultBackground() {
	}
}