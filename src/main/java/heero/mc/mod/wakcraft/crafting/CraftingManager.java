package heero.mc.mod.wakcraft.crafting;

import heero.mc.mod.wakcraft.WItems;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CraftingManager {
    INSTANCE;

    private final Map<PROFESSION, List<IRecipeWithLevel>> recipes = new HashMap<>();

    private CraftingManager() {
        for (PROFESSION profession : PROFESSION.values()) {
            recipes.put(profession, new ArrayList<IRecipeWithLevel>());
        }

        addRecipe(PROFESSION.FARMER, 0, 100, new ItemStack(WItems.wheatFlour), new ItemStack(WItems.wheatGrain, 3));

        addRecipe(PROFESSION.MINER, 0, 100, new ItemStack(WItems.bomb), new ItemStack(WItems.canoonPowder), new ItemStack(WItems.clay, 3), new ItemStack(WItems.ore1, 3));
        addRecipe(PROFESSION.MINER, 0, 100, new ItemStack(WItems.fossil), new ItemStack(WItems.waterBucket, 2), new ItemStack(WItems.driedDung, 3));
        addRecipe(PROFESSION.MINER, 10, 100, new ItemStack(WItems.shamPearl), new ItemStack(WItems.pearl, 3), new ItemStack(WItems.waterBucket, 2));
        addRecipe(PROFESSION.MINER, 15, 100, new ItemStack(WItems.verbalaSalt), new ItemStack(WItems.ore1, 3, 2));
        addRecipe(PROFESSION.MINER, 20, 100, new ItemStack(WItems.gumgum), new ItemStack(WItems.ore1, 15, 3));
        addRecipe(PROFESSION.MINER, 20, 100, new ItemStack(WItems.polishedMoonstone), new ItemStack(WItems.waterBucket, 2), new ItemStack(WItems.moonstone, 3));
        addRecipe(PROFESSION.MINER, 25, 100, new ItemStack(WItems.shadowyBlue), new ItemStack(WItems.ore1, 3, 5));
    }

    public void addRecipe(final PROFESSION profession, final int level, final int xp, final ItemStack result, final Object... recipe) {
        final ArrayList<ItemStack> recipeList = new ArrayList<>();

        for (Object ingredient : recipe) {
            if (ingredient instanceof ItemStack) {
                recipeList.add(((ItemStack) ingredient).copy());
            } else if (ingredient instanceof Item) {
                recipeList.add(new ItemStack((Item) ingredient));
            } else if (ingredient instanceof Block) {
                recipeList.add(new ItemStack((Block) ingredient));
            } else {
                throw new IllegalArgumentException("Invalid shapeless recipe!");
            }
        }

        recipes.get(profession).add(new RecipeWithLevel(result, recipeList, level, xp));
    }

    public ItemStack findMatchingRecipe(final PROFESSION profession, final InventoryCrafting inventory, final World world) {
        final IRecipeWithLevel recipe = getMatchingRecipe(profession, inventory, world);
        if (recipe != null) {
            return recipe.getCraftingResult(inventory);
        }

        return null;
    }

    public IRecipeWithLevel getMatchingRecipe(final PROFESSION profession, final InventoryCrafting inventory, final World world) {
        final List<IRecipeWithLevel> professionRecipes = getRecipeList(profession);
        for (IRecipeWithLevel recipe : professionRecipes) {
            if (recipe.matches(inventory, world)) {
                return recipe;
            }
        }

        return null;
    }

    public List<IRecipeWithLevel> getRecipeList(PROFESSION profession) {
        return recipes.get(profession);
    }
}