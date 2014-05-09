package heero.wakcraft.crafting;

import heero.wakcraft.WakcraftItems;
import heero.wakcraft.profession.ProfessionManager.PROFESSION;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CraftingManager {
    private static final CraftingManager instance = new CraftingManager();
    
    private Map<PROFESSION, List<IExtendedRecipe>> recipes = new HashMap<PROFESSION, List<IExtendedRecipe>>();


	private CraftingManager() {
		for (PROFESSION profession : PROFESSION.values()) {
			recipes.put(profession, new ArrayList<IExtendedRecipe>());
		}

		addRecipe(PROFESSION.MINER, 0, new ItemStack(WakcraftItems.bomb), new ItemStack(WakcraftItems.canoonPowder), new ItemStack(WakcraftItems.clay, 3), new ItemStack(WakcraftItems.itemOre1, 3));
		addRecipe(PROFESSION.MINER, 0, new ItemStack(WakcraftItems.fossil), new ItemStack(WakcraftItems.waterBucket, 2), new ItemStack(WakcraftItems.driedDung, 3));
		addRecipe(PROFESSION.MINER, 10, new ItemStack(WakcraftItems.shamPearl), new ItemStack(WakcraftItems.pearl, 3), new ItemStack(WakcraftItems.waterBucket, 2));
		addRecipe(PROFESSION.MINER, 15, new ItemStack(WakcraftItems.verbalasalt), new ItemStack(WakcraftItems.itemOre1, 3, 2));
		addRecipe(PROFESSION.MINER, 20, new ItemStack(WakcraftItems.gumgum), new ItemStack(WakcraftItems.itemOre1, 15, 3));
		addRecipe(PROFESSION.MINER, 20, new ItemStack(WakcraftItems.polishedmoonstone), new ItemStack(WakcraftItems.waterBucket, 2), new ItemStack(WakcraftItems.moonstone, 3));
		addRecipe(PROFESSION.MINER, 25, new ItemStack(WakcraftItems.shadowyBlue), new ItemStack(WakcraftItems.itemOre1, 3, 5));
	}

	public static final CraftingManager getInstance() {
		return instance;
	}

	public void addRecipe(PROFESSION profession, int level, ItemStack result, Object... recipe) {
		ArrayList<ItemStack> recipeList = new ArrayList<ItemStack>();

		int nbIngredients = recipe.length;
		for (int i = 0; i < nbIngredients; i++) {
			Object ingredient = recipe[i];

			if (ingredient instanceof ItemStack) {
				recipeList.add(((ItemStack) ingredient).copy());
			} else if (ingredient instanceof Item) {
				recipeList.add(new ItemStack((Item) ingredient));
			} else if (ingredient instanceof Block) {
				recipeList.add(new ItemStack((Block) ingredient));
			} else {
				throw new RuntimeException("Invalid shapeless recipy!");
			}
		}

		recipes.get(profession).add(new RecipeWithLevel(result, recipeList, level));
	}

	public ItemStack findMatchingRecipe(PROFESSION profession, InventoryCrafting inventory, World world) {
		IExtendedRecipe recipe = getMatchingRecipe(profession, inventory, world);
		if (recipe != null) {
			return recipe.getCraftingResult(inventory);
		}

		return null;
	}

	public IExtendedRecipe getMatchingRecipe(PROFESSION profession, InventoryCrafting inventory, World world) {
		List<IExtendedRecipe> professionRecipes = getRecipeList(profession);
		for (int i = 0; i < professionRecipes.size(); ++i) {
			IExtendedRecipe IExtendedRecipe = (IExtendedRecipe) professionRecipes.get(i);
			if (IExtendedRecipe.matches(inventory, world)) {
				return IExtendedRecipe;
			}
		}

		return null;
	}

	public List<IExtendedRecipe> getRecipeList(PROFESSION profession) {
		return recipes.get(profession);
	}
}