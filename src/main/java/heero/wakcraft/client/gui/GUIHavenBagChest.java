package heero.wakcraft.client.gui;

import heero.wakcraft.WakcraftInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIHavenBagChest extends GuiContainer {
	private static final ResourceLocation textureBackground = new ResourceLocation(WakcraftInfo.MODID.toLowerCase(), "textures/gui/havenbagchest.png");

	public GUIHavenBagChest(Container container) {
		super(container);

		ySize = 222;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick,
			int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(textureBackground);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}