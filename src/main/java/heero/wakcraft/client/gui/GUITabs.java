package heero.wakcraft.client.gui;

import heero.wakcraft.WInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUITabs extends GuiScreen {
	private static final ResourceLocation tabButtonsTexture = new ResourceLocation(
			WInfo.MODID.toLowerCase(), "textures/gui/tabs.png");

	protected int selectedTab;
	protected GuiScreen tabs[];

	protected int tabButtonLeft;
	protected int tabButtonTop;

	public GUITabs(GuiScreen tabs[]) {
		super();

		this.allowUserInput = true;
		this.selectedTab = 0;
		this.tabs = tabs;
	}

	/**
	 * Causes the screen to lay out its subcomponents again. This is the
	 * equivalent of the Java call Container.validate()
	 */
	@Override
	public void setWorldAndResolution(Minecraft minecraft, int width, int height) {
		super.setWorldAndResolution(minecraft, width, height);

		for (int i = 0; i < tabs.length; i++) {
			tabs[i].setWorldAndResolution(minecraft, width, height);
		}
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		tabButtonLeft = width / 2 + 85;
		tabButtonTop = height / 2 - 70;

		super.initGui();
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
		drawDefaultBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);

		mc.getTextureManager().bindTexture(tabButtonsTexture);

		for (int i = 0; i < tabs.length; i++) {
			drawTexturedModalRect(tabButtonLeft, tabButtonTop + 30 * i,
					(selectedTab == i) ? 0 : 33, i * 28, 33, 28);
		}

		tabs[selectedTab].drawScreen(mouseX, mouseY, renderPartialTicks);
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() {
		tabs[selectedTab].handleMouseInput();

		super.handleMouseInput();
	}

	/**
	 * Handles keyboard input.
	 */
	@Override
	public void handleKeyboardInput() {
		tabs[selectedTab].handleKeyboardInput();

		super.handleKeyboardInput();
	}

	/**
	 * Called when the mouse is moved or a mouse button is released. Signature:
	 * (mouseX, mouseY, releasedButton)
	 */
	@Override
	protected void mouseMovedOrUp(int mouseX, int mouseY, int releasedButton) {
		if (releasedButton == 0) {
			int relativeMouseX = mouseX - tabButtonLeft;
			int relativeMouseY = mouseY - tabButtonTop;

			for (int i = 0; i < tabs.length; ++i) {
				if (relativeMouseX >= 0 && relativeMouseX < 29
						&& relativeMouseY > i * 30
						&& relativeMouseY < 30 + i * 30) {
					setSelectedTab(i);
				}
			}
		}
	}

	protected void setSelectedTab(int tabId) {
		if (tabId < 0 || tabId >= tabs.length) {
			return;
		}

		selectedTab = tabId;
	}
}