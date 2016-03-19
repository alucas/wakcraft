package heero.mc.mod.wakcraft.client.gui.inventory;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.crafting.CraftingManager;
import heero.mc.mod.wakcraft.crafting.IExtendedRecipe;
import heero.mc.mod.wakcraft.crafting.RecipeWithLevel;
import heero.mc.mod.wakcraft.profession.ProfessionManager;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.List;

public class GUIWorkbench extends GuiContainer {
    protected static final ResourceLocation textures = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/workbench.png");
    protected static final int NB_DISPLAYED_RECIPE = 5;

    protected PROFESSION profession;
    protected int scrollIndex;

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

        for (int i = 0; i < NB_DISPLAYED_RECIPE; i++) {
            if (scrollIndex + i >= recipes.size()) {
                break;
            }

            RecipeWithLevel recipe = (RecipeWithLevel) recipes.get(scrollIndex + i);

            // Recipe output name + Recipe level
            fontRendererObj.drawString(I18n.format("message.itemAndLevel", recipe.getRecipeOutput().getDisplayName(), recipe.recipeLevel), xSize + 11, i * 40 - 5, 0xffffff);
            // Draw a tiny "=" between the recipe output and the recipe elements
            fontRendererObj.drawString("=", xSize + 31, 15 + i * 40 - 5, 0xffffff);
        }

        for (int i = 0; i < NB_DISPLAYED_RECIPE; i++) {
            if (scrollIndex + i >= recipes.size()) {
                break;
            }

            RecipeWithLevel recipe = (RecipeWithLevel) recipes.get(scrollIndex + i);

            // Recipe output item
            drawItemStack(recipe.getRecipeOutput(), xSize + 11, 4 + i * 40, null);

            for (int j = 0; j < recipe.getRecipeComponents().size(); j++) {
                ItemStack itemStack = recipe.getRecipeComponents().get(j);

                int quantity = 0;
                if (itemStack.isItemEqual(mc.thePlayer.inventory.getItemStack())) {
                    quantity += mc.thePlayer.inventory.getItemStack().stackSize;
                }

                for (ItemStack stack : inventorySlots.getInventory()) {
                    if (itemStack.isItemEqual(stack)) {
                        quantity += stack.stackSize;
                    }
                }

                // Recipe element item
                itemRender.renderItemAndEffectIntoGUI(itemStack, xSize + 40 + j * 20, 4 + i * 40);
                // Recipe element quantity
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableBlend();
                fontRendererObj.drawString(String.valueOf(itemStack.stackSize), xSize + 50 + j * 20, 13 + i * 40, (quantity < itemStack.stackSize) ? 0xff0000 : 0xffffff, true);
                GlStateManager.enableBlend();
                GlStateManager.enableDepth();
                GlStateManager.enableLighting();
            }
        }

        for (int i = 0; i < NB_DISPLAYED_RECIPE; i++) {
            if (scrollIndex + i >= recipes.size()) {
                break;
            }

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
                    ItemStack itemStack = recipe.getRecipeComponents().get(j);
                    renderToolTip(itemStack, mouseX - guiLeft, mouseY - guiTop);
                }
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicks, int mouseX, int mouseY) {
        final int xp = ProfessionManager.getXp(mc.thePlayer, profession);
        final int level = ProfessionManager.getLevelFromXp(xp);
        final int xpPreviousLevel = ProfessionManager.getXpFromLevel(level);
        final int xpNextLevel = ProfessionManager.getXpFromLevel(level + 1);
        final float factorNextLevel = (float) (xp - xpPreviousLevel) / (xpNextLevel - xpPreviousLevel);

        mc.getTextureManager().bindTexture(textures);

        // Background
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        // XP bar
        drawTexturedModalRect(guiLeft + 27, guiTop + 20, 0, ySize + 1, (int) (147 * factorNextLevel), 6);
        // Profession Icon
        drawTexturedModalRect(guiLeft + 10, guiTop + 10, 0, ySize + 7, 16, 16);

        final List<IExtendedRecipe> recipes = CraftingManager.getInstance().getRecipeList(profession);

        GlStateManager.enableBlend();

        for (int i = 0; i < NB_DISPLAYED_RECIPE; i++) {
            if (scrollIndex + i >= recipes.size()) {
                break;
            }

            IExtendedRecipe recipe = recipes.get(scrollIndex + i);

            // Recipe background
            drawTexturedModalRect(guiLeft + xSize + 5, guiTop - 10 + i * 40, 0, ySize + 39, 140, 35);

            // Recipe output background
            drawTexturedModalRect(guiLeft + xSize + 11, guiTop + 4 + i * 40, 0, ySize + 23, 16, 16);

            for (int j = 0; j < recipe.getRecipeComponents().size(); j++) {
                // Recipe element background
                drawTexturedModalRect(guiLeft + xSize + 40 + j * 20, guiTop + 4 + i * 40, 0, ySize + 23, 16, 16);
            }
        }

        GlStateManager.disableBlend();
    }

    /**
     * Handles mouse input.
     */
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        int wheel = Mouse.getDWheel();
        if (wheel > 0 && scrollIndex > 0) {
            scrollIndex -= 1;
        } else if (wheel < 0 && scrollIndex < CraftingManager.getInstance().getRecipeList(profession).size() - 1) {
            scrollIndex += 1;
        }
    }
}
