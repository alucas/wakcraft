package heero.mc.mod.wakcraft.client.gui;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.entity.property.CharacterProperty;
import heero.mc.mod.wakcraft.entity.property.CharacterProperty.CLASS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.FMLLog;

public class GUIClassSelection extends GuiScreen implements GuiYesNoCallback {
	protected static final ResourceLocation background = new ResourceLocation(WInfo.MODID.toLowerCase(), "textures/gui/background.png");

	protected EntityPlayer player;
	protected int guiWidth = 176;
	protected int guiHeight = 166;
	protected int guiLeft;
	protected int guiTop;

	public GUIClassSelection(EntityPlayer player) {
		super();

		this.player = player;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();

		guiLeft = (width - guiWidth) / 2;
		guiTop = (height - guiHeight) / 2;

		CLASS[] classes = CharacterProperty.CLASS.values();
		for (int i = 0; i < classes.length - 1; i++) {
			buttonList.add(new GuiButton(i, guiLeft + 5 + 85 * (i % 2), guiTop + 7 + 19 * (i / 2), 80, 20, classes[i + 1].toString()));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(background);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, guiWidth, guiHeight);

		super.drawScreen(mouseX, mouseY, partialTick);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		Minecraft.getMinecraft().displayGuiScreen(new GuiYesNo(this, "You have selected the class : " + CLASS.values()[button.id + 1], "If you confirm, your character will be reset !", button.id));
	}

	@Override
	public void confirmClicked(boolean result, int buttonId) {
		do {
			if (!result) {
				break;
			}

			CharacterProperty properties = (CharacterProperty) player.getExtendedProperties(CharacterProperty.IDENTIFIER);
			if (properties == null) {
				FMLLog.warning("Error while loading the character properties of the player : " + player.getDisplayName());
				break;
			}

			properties.setCharacterClass(CLASS.values()[buttonId + 1]);

			Minecraft.getMinecraft().displayGuiScreen(null);

			return;
		} while(false);

		Minecraft.getMinecraft().displayGuiScreen(this);
	}
}
