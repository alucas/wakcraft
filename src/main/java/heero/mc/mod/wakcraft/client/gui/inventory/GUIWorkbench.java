package heero.mc.mod.wakcraft.client.gui.inventory;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.crafting.CraftingManager;
import heero.mc.mod.wakcraft.crafting.IExtendedRecipe;
import heero.mc.mod.wakcraft.crafting.RecipeWithLevel;
import heero.mc.mod.wakcraft.profession.ProfessionManager;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GUIWorkbench extends GuiContainer {

	int scrollIndex;

	protected PROFESSION profession;

	private static final ResourceLocation textures = new ResourceLocation(WInfo.MODID.toLowerCase(), "textures/gui/workbench.png");

	public GUIWorkbench(Container container, PROFESSION profession) {
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

		List<IExtendedRecipe> recipes = CraftingManager.getInstance().getRecipeList(profession);

		for (int i = 0; i < 5; i++) {
			if (scrollIndex + i >= recipes.size())
				break;

			IExtendedRecipe recipe = recipes.get(scrollIndex + i);

			fontRendererObj.drawString(I18n.format("message.itemAndLevel", recipe.getRecipeOutput().getDisplayName(), ((RecipeWithLevel) recipe).recipeLevel), xSize + 11, i * 40 - 5, 0xffffff);
			fontRendererObj.drawString("=", xSize + 31, 15 + i * 40 - 5, 0xffffff);

			for (int j = 0; j < recipe.getRecipeComponents().size(); j++) {
				ItemStack itemStack = (ItemStack) recipe.getRecipeComponents().get(j);
				int quantity = 0;

				for (ItemStack inventoryItem : mc.thePlayer.inventory.mainInventory) {
					if (inventoryItem != null && inventoryItem.getItem() == itemStack.getItem() && inventoryItem.getItemDamage() == itemStack.getItemDamage()) {
						quantity += inventoryItem.stackSize;
					}
				}

				fontRendererObj.drawStringWithShadow(Integer.toString(itemStack.stackSize), xSize + 50 + j * 20, 13 + i * 40, (quantity < itemStack.stackSize) ? 0xff0000 : 0xffffff);
			}
		}

		for (int i = 0; i < 5; i++) {
			if (scrollIndex + i >= recipes.size())
				break;

			IExtendedRecipe recipe = recipes.get(scrollIndex + i);
			int x = guiLeft + xSize + 11;
			int y = guiTop + 5 + i * 40;
			if (mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16) {
				ItemStack itemStack = recipe.getRecipeOutput();
				renderToolTip(itemStack, mouseX - guiLeft, mouseY - guiTop);
			}

			for (int j = 0; j < recipe.getRecipeComponents().size(); j++) {
				x = guiLeft + xSize + 40 + j * 20;
				if (mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16) {
					ItemStack itemStack = (ItemStack) recipe.getRecipeComponents().get(j);
					renderToolTip(itemStack, mouseX - guiLeft, mouseY - guiTop);
				}
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

		List<IExtendedRecipe> recipes = CraftingManager.getInstance().getRecipeList(profession);

		GL11.glEnable(GL11.GL_BLEND);

		for (int i = 0; i < 5; i++) {
			if (scrollIndex + i >= recipes.size())
				break;

			drawTexturedModalRect(guiLeft + xSize + 5, guiTop - 10 + i * 40, 0, ySize + 39, 140, 35);

			IExtendedRecipe recipe = recipes.get(scrollIndex + i);

			drawTexturedModalRect(guiLeft + xSize + 11, guiTop + 4 + i * 40, 0, ySize + 23, 16, 16);

			for (int j = 0; j < recipe.getRecipeComponents().size(); j++) {
				drawTexturedModalRect(guiLeft + xSize + 40 + j * 20, guiTop + 4 + i * 40, 0, ySize + 23, 16, 16);
			}
		}

		mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);

		for (int i = 0; i < 5; i++) {
			if (scrollIndex + i >= recipes.size())
				break;

			RecipeWithLevel recipe = (RecipeWithLevel) recipes.get(scrollIndex + i);

			drawTexturedModelRectFromIcon(guiLeft + xSize + 11, guiTop + 4 + i * 40, recipe.getRecipeOutput().getItem().getIcon(recipe.getRecipeOutput(), 0), 16, 16);

			for (int j = 0; j < recipe.getRecipeComponents().size(); j++) {
				ItemStack itemStack = (ItemStack) recipe.getRecipeComponents().get(j);
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
