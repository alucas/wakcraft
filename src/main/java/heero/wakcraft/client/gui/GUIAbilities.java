package heero.wakcraft.client.gui;

import heero.wakcraft.WInfo;
import heero.wakcraft.entity.property.AbilitiesProperty.ABILITY;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIAbilities extends GuiScreen {
	private static final ResourceLocation background = new ResourceLocation(
			WInfo.MODID.toLowerCase(), "textures/gui/ability.png");

	protected static final int BUTTON_DOWN = 0;
	protected static final int BUTTON_UP = 1;

	protected int guiWidth = 176;
	protected int guiHeight = 166;
	protected int guiLeft;
	protected int guiTop;

	protected EntityPlayer player;
	protected int scroll;

	public GUIAbilities(EntityPlayer player) {
		super();

		this.player = player;
		this.scroll = 0;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		guiLeft = (width - guiWidth) / 2;
		guiTop = (height - guiHeight) / 2;

		buttonList.clear();
		buttonList.add(new GuiButton(BUTTON_DOWN, guiLeft + 150, guiTop + 115, 20, 20, "-"));
		buttonList.add(new GuiButton(BUTTON_UP, guiLeft + 150, guiTop + 140, 20, 20, "+"));

		super.initGui();
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
		mc.getTextureManager().bindTexture(background);

		// Background
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, guiWidth, guiHeight);

		// Profession levels
		ABILITY[] abilities = ABILITY.values();
		for (int i = scroll; i < scroll + 7 && i >= 0 && i < abilities.length; i++) {
			drawString(fontRendererObj, I18n.format("abilities." + abilities[i], new Object[0]), guiLeft + 5, guiTop + 25 + (i - scroll) * 20, 0xFFFFFF);
		}

		// Titles
		drawString(fontRendererObj, I18n.format("abilities.abilities", new Object[0]), guiLeft + 5, guiTop + 5, 0xFFFFFF);

		super.drawScreen(mouseX, mouseY, renderPartialTicks);
	}

	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case BUTTON_DOWN:
			scroll--;
			if (scroll < 0) scroll = 0;
			break;

		case BUTTON_UP:
			scroll++;
			if (scroll >= ABILITY.values().length) scroll = ABILITY.values().length - 1;
			break;

		default:
			break;
		}
	}
}