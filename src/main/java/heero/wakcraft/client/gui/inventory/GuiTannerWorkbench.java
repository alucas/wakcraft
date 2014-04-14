package heero.wakcraft.client.gui.inventory;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.inventory.ContainerTannerWorkbench;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class GuiTannerWorkbench extends GuiContainer {
	private static final ResourceLocation craftingTableGuiTextures = new ResourceLocation(
			WakcraftInfo.MODID.toLowerCase(),
			"/textures/gui/container/crafting_table.png");

	public GuiTannerWorkbench(InventoryPlayer player_inventory, World world) {
		super(new ContainerTannerWorkbench(player_inventory, world));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		this.fontRendererObj.drawString("Tuto Gui", 6, 6, 0xffffff);
		this.fontRendererObj.drawString(
				StatCollector.translateToLocal("container.inventory"), 6,
				ySize - 96 + 2, 0xffffff);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(craftingTableGuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
}
