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
	protected int minerLevel;

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

		minerLevel = ProfessionManager.getLevel(player, PROFESSION.MINER);

		super.initGui();
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
		mc.getTextureManager().bindTexture(professionBackground);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 200, 200);
		fontRendererObj.drawString("Lvl : " + minerLevel, guiLeft + 40,
				guiTop + 20, 421075);
	}
}