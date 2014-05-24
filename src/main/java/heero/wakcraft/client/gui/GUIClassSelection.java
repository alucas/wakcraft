package heero.wakcraft.client.gui;

import cpw.mods.fml.common.FMLLog;
import heero.wakcraft.WInfo;
import heero.wakcraft.entity.property.CharacterProperty;
import heero.wakcraft.entity.property.CharacterProperty.CLASS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIClassSelection extends GuiScreen {
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
		try {
			if (!result) {
				return;
			}

			CharacterProperty properties = (CharacterProperty) player.getExtendedProperties(CharacterProperty.IDENTIFIER);
			if (properties == null) {
				FMLLog.warning("Error while loading the character properties of the player : " + player.getDisplayName());
				return;
			}

			properties.setCharacterClass(CLASS.values()[buttonId + 1]);
		} finally {
			Minecraft.getMinecraft().displayGuiScreen(this);
		}
	}
}
