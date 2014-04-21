package heero.wakcraft.client.gui;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.profession.ProfessionManager;
import heero.wakcraft.profession.ProfessionManager.PROFESSION;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIProfession extends GuiScreen {
	private static final ResourceLocation professionBackground = new ResourceLocation(
			WakcraftInfo.MODID.toLowerCase(), "textures/gui/profession.png");

	/**
	 * The X size of the window in pixels.
	 */
	protected int xSize = 176;
	/**
	 * The Y size of the window in pixels.
	 */
	protected int ySize = 166;
	/**
	 * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiLeft;
	/**
	 * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiTop;
	protected EntityPlayer player;

	public GUIProfession(EntityPlayer player) {
		super();

		this.player = player;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;

		super.initGui();
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
		mc.getTextureManager().bindTexture(professionBackground);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int xp = ProfessionManager.getXp(player, PROFESSION.MINER);
		int level = ProfessionManager.getLevelFromXp(xp);
		int xpLevel = ProfessionManager.getXpFromLevel(level);
		int xpNextLevel = ProfessionManager.getXpFromLevel(level + 1);

		drawTexturedModalRect(guiLeft, guiTop + 84, 0, ySize + 1, (int) (xSize - (170 * (1 - (xp - xpLevel) / (float) (xpNextLevel - xpLevel)))), 6);
		drawCenteredString(fontRendererObj, Integer.toString(level), guiLeft + 80, guiTop + 75, 0xFFFFFF);
	}
}