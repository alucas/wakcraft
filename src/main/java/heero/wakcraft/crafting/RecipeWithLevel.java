package heero.wakcraft.crafting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeWithLevel implements IRecipe {
	public int recipeLevel;
	public ItemStack recipeResult;
	public List recipeItems;

	public RecipeWithLevel(ItemStack result, List recipe, int level) {
		this.recipeLevel = level;
		this.recipeResult = result;
		this.recipeItems = recipe;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		ArrayList arraylist = new ArrayList(recipeItems);

		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			ItemStack itemstack = inventory.getStackInSlot(i);
			if (itemstack != null) {
				boolean flag = false;
				Iterator iterator = arraylist.iterator();

				while (iterator.hasNext()) {
					ItemStack itemstack1 = (ItemStack) iterator.next();

					if (itemstack.getItem() == itemstack1.getItem()
							&& itemstack.getItemDamage() == itemstack1.getItemDamage()
							&& itemstack.stackSize >= itemstack1.stackSize) {
						flag = true;
						arraylist.remove(itemstack1);
						break;
					}
				}

				if (!flag) {
					return false;
				}
			}
		}

		return arraylist.isEmpty();
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
    {
        return recipeResult.copy();
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize()
    {
        return recipeItems.size();
    }

	@Override
	public ItemStack getRecipeOutput() {
		return recipeResult;
	}
}
