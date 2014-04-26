package heero.wakcraft.crafting;

import heero.wakcraft.WakcraftItems;
import heero.wakcraft.profession.ProfessionManager.PROFESSION;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

public class CraftingManager {
    private static final CraftingManager instance = new CraftingManager();
    
    private List recipes = new ArrayList();


	private CraftingManager() {
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

	public void addRecipe(PROFESSION prefession, int level, ItemStack result, Object... recipe) {
		ArrayList arraylist = new ArrayList();

		int nbIngredients = recipe.length;
		for (int i = 0; i < nbIngredients; i++) {
			Object ingredient = recipe[i];

			if (ingredient instanceof ItemStack) {
				arraylist.add(((ItemStack) ingredient).copy());
			} else if (ingredient instanceof Item) {
				arraylist.add(new ItemStack((Item) ingredient));
			} else if (ingredient instanceof Block) {
				arraylist.add(new ItemStack((Block) ingredient));
			} else {
				throw new RuntimeException("Invalid shapeless recipy!");
			}
		}

		recipes.add(new ShapelessRecipes(result, arraylist));
	}

	public ItemStack findMatchingRecipe(InventoryCrafting inventory, World world) {
		for (int i = 0; i < recipes.size(); ++i) {
			IRecipe irecipe = (IRecipe) recipes.get(i);

			if (irecipe.matches(inventory, world)) {
				return irecipe.getCraftingResult(inventory);
			}
		}

		return null;
	}

	public List getRecipeList(PROFESSION profession) {
		return recipes;
	}
}