package heero.mc.mod.wakcraft.client.gui;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.ability.AbilityManager;
import heero.mc.mod.wakcraft.ability.AbilityManager.ABILITY;
import heero.mc.mod.wakcraft.entity.property.AbilitiesProperty;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIAbilities extends GuiScreen {
	private static final ResourceLocation background = new ResourceLocation(
			WInfo.MODID.toLowerCase(), "textures/gui/ability.png");

	protected static final int BUTTON_DOWN = 0;
	protected static final int BUTTON_UP = 1;

	protected static int NB_LINE = 6;

	protected int guiWidth = 176;
	protected int guiHeight = 166;
	protected int guiLeft;
	protected int guiTop;

	protected EntityPlayer player;
	protected AbilitiesProperty abilitiesManager;
	protected int scroll;

	public GUIAbilities(EntityPlayer player) {
		super();

		this.player = player;
		this.scroll = 0;
		this.abilitiesManager = (AbilitiesProperty) player.getExtendedProperties(AbilitiesProperty.IDENTIFIER);

		if (this.abilitiesManager == null) {
			FMLLog.warning("Error while loading the player's abilities (%s)", player.getDisplayName());
		}
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
		buttonList.add(new GuiButton(BUTTON_DOWN, guiLeft + (guiWidth / 2) - 25, guiTop + 140, 20, 20, "<"));
		buttonList.add(new GuiButton(BUTTON_UP, guiLeft + (guiWidth / 2) + 5, guiTop + 140, 20, 20, ">"));

		for (int i = 0; i < NB_LINE; i++) {
			GuiButton buttonRemove = new GuiButton(100 + i * 2 + 0, guiLeft + 110, guiTop + 20 + i * 20, 20, 20, "-");
			GuiButton buttonAdd = new GuiButton(100 + i * 2 + 1, guiLeft + 150, guiTop + 20 + i * 20, 20, 20, "+");

			buttonList.add(buttonRemove);
			buttonList.add(buttonAdd);
		}

		updateButtons();

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
		for (int i = scroll; i < scroll + NB_LINE && i >= 0 && i < abilities.length; i++) {
			drawString(fontRendererObj, I18n.format("abilities." + abilities[i], new Object[0]), guiLeft + 5, guiTop + 25 + (i - scroll) * 20, 0xFFFFFF);

			if (abilitiesManager != null) {
				drawCenteredString(fontRendererObj, Integer.toString(abilitiesManager.get(abilities[i])), guiLeft + 140, guiTop + 25 + (i - scroll) * 20, 0xFFFFFF);
			}
		}

		// Titles
		drawString(fontRendererObj, I18n.format("title.abilities", new Object[0]), guiLeft + 5, guiTop + 5, 0xFFFFFF);

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
			if (scroll > ABILITY.values().length - NB_LINE) scroll = ABILITY.values().length - NB_LINE;
			break;

		default:
			break;
		}

		updateButtons();
	}

	protected void updateButtons() {
		ABILITY[] abilities = ABILITY.values();
		for (int i = 0; i < NB_LINE; i++) {
			Boolean enabled = AbilityManager.isCustomizable(abilities[scroll + i]);
			((GuiButton) (buttonList.get(2 + i * 2 + 0))).visible = enabled;
			((GuiButton) (buttonList.get(2 + i * 2 + 1))).visible = enabled;
		}
	}
}