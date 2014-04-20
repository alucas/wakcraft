package heero.wakcraft.client.gui.inventory;

import heero.wakcraft.WakcraftInfo;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIInventory extends InventoryEffectRenderer {
	private static final ResourceLocation professionBackground = new ResourceLocation(WakcraftInfo.MODID.toLowerCase(), "textures/gui/profession.png");

	public GUIInventory(EntityPlayer player) {
		super(player.inventoryContainer);
		
		allowUserInput = true;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float renderPartialTicks,
			int mouseX, int mouseY) {
		drawInventoryBackground(mouseX, mouseY);
	}
	
	protected void drawInventoryBackground(int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(field_147001_a);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		GuiInventory.func_147046_a(guiLeft + 51, guiTop + 75, 30,
				(float) (guiLeft + 51) - mouseX, (float) (guiTop + 75 - 50)
						- mouseY, mc.thePlayer);
	}
	
	protected void drawProfessionBackground() {
		mc.getTextureManager().bindTexture(professionBackground);
		
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	/**
     * Draws either a gradient over the background screen (when it exists) or a flat gradient over background.png
     */
    public void drawDefaultBackground() {}
}