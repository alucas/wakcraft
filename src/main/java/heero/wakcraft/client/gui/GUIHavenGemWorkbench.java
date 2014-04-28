package heero.wakcraft.client.gui;

import heero.wakcraft.WakcraftInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIHavenGemWorkbench extends GuiContainer {
	private static final ResourceLocation background = new ResourceLocation(WakcraftInfo.MODID.toLowerCase(), "textures/gui/havengem.png");

	public GUIHavenGemWorkbench(Container container) {
		super(container);
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(background);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}