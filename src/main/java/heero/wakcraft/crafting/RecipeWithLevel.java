package heero.wakcraft.crafting;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;

public class RecipeWithLevel extends ShapelessRecipes {
	public int level;

	public RecipeWithLevel(ItemStack result, List recipeList, int level) {
		super(result, recipeList);

		this.level = level;
	}
}
