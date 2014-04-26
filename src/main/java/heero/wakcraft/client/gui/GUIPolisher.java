package heero.wakcraft.client.gui;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.crafting.CraftingManager;
import heero.wakcraft.profession.ProfessionManager;
import heero.wakcraft.profession.ProfessionManager.PROFESSION;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GUIPolisher extends GuiContainer {

	int scrollIndex;

	protected PROFESSION profession;

	private static final ResourceLocation textures = new ResourceLocation(WakcraftInfo.MODID.toLowerCase(), "textures/gui/workbench.png");

	public GUIPolisher(Container container, PROFESSION profession) {
		super(container);

		this.profession = profession;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		super.initGui();

		guiLeft -= 72;
		scrollIndex = 0;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(StatCollector.translateToLocal("profession.miner"), 34, 10, 0xffffff);

		List recipes = CraftingManager.getInstance().getRecipeList(profession);

		for (int i = 0; i < 5; i++) {
			if (scrollIndex + i >= recipes.size())
				break;

			ShapelessRecipes recipe = (ShapelessRecipes) recipes.get(scrollIndex + i);

			fontRendererObj.drawString(I18n.format("message.itemAndLevel", recipe.getRecipeOutput().getDisplayName(), 0), xSize + 11, i * 40 - 5, 0xffffff);
			fontRendererObj.drawString("=", xSize + 31, 15 + i * 40 - 5, 0xffffff);

			for (int j = 0; j < recipe.recipeItems.size(); j++) {
				ItemStack itemStack = (ItemStack) recipe.recipeItems.get(j);
				int quantity = 0;

				for (ItemStack inventoryItem : mc.thePlayer.inventory.mainInventory) {
					if (inventoryItem != null && inventoryItem.getItem() == itemStack.getItem() && inventoryItem.getItemDamage() == itemStack.getItemDamage()) {
						quantity += inventoryItem.stackSize;
					}
				}

				fontRendererObj.drawStringWithShadow(Integer.toString(itemStack.stackSize), xSize + 50 + j * 20, 13 + i * 40, (quantity < itemStack.stackSize) ? 0xff0000 : 0xffffff);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float renderPartialTicks, int mouseX, int mouseY) {
		int xp = ProfessionManager.getXp(mc.thePlayer, profession);
		int level = ProfessionManager.getLevelFromXp(xp);
		int xpPreviousLevel = ProfessionManager.getXpFromLevel(level);
		int xpNextLevel = ProfessionManager.getXpFromLevel(level + 1);
		float factor = (float) (xp - xpPreviousLevel) / (xpNextLevel - xpPreviousLevel);

		mc.getTextureManager().bindTexture(textures);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		drawTexturedModalRect(guiLeft + 27, guiTop + 20, 0, ySize + 1, (int) (147 * factor), 6);
		drawTexturedModalRect(guiLeft + 10, guiTop + 10, 0, ySize + 7, 16, 16);

		List recipes = CraftingManager.getInstance().getRecipeList(profession);
		int nbRecipes = recipes.size();

		GL11.glEnable(GL11.GL_BLEND);

		for (int i = 0; i < 5; i++) {
			if (scrollIndex + i >= recipes.size())
				break;

			drawTexturedModalRect(guiLeft + xSize + 5, guiTop - 10 + i * 40, 0, ySize + 39, 140, 35);

			ShapelessRecipes recipe = (ShapelessRecipes) recipes.get(scrollIndex + i);

			drawTexturedModalRect(guiLeft + xSize + 11, guiTop + 4 + i * 40, 0, ySize + 23, 16, 16);

			for (int j = 0; j < recipe.recipeItems.size(); j++) {
				drawTexturedModalRect(guiLeft + xSize + 40 + j * 20, guiTop + 4 + i * 40, 0, ySize + 23, 16, 16);
			}
		}

		mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);

		for (int i = 0; i < 5; i++) {
			if (scrollIndex + i >= recipes.size())
				break;

			ShapelessRecipes recipe = (ShapelessRecipes) recipes.get(scrollIndex + i);

			drawTexturedModelRectFromIcon(guiLeft + xSize + 11, guiTop + 4 + i * 40, recipe.getRecipeOutput().getItem().getIcon(recipe.getRecipeOutput(), 0), 16, 16);

			for (int j = 0; j < recipe.recipeItems.size(); j++) {
				ItemStack itemStack = (ItemStack) recipe.recipeItems.get(j);
				drawTexturedModelRectFromIcon(guiLeft + xSize + 40 + j * 20, guiTop + 4 + i * 40, itemStack.getItem().getIcon(itemStack, 0), 16, 16);
			}
		}

		GL11.glDisable(GL11.GL_BLEND);
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();

		int wheel = Mouse.getDWheel();
		if (wheel > 0 && scrollIndex > 0) {
			scrollIndex -= 1;
		} else if (wheel < 0 && scrollIndex < CraftingManager.getInstance().getRecipeList(profession).size() - 1) {
			scrollIndex += 1;
		}
	}
}
