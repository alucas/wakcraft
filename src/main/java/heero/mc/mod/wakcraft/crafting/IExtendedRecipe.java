package heero.mc.mod.wakcraft.crafting;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public interface IExtendedRecipe extends IRecipe {
	public List<ItemStack> getRecipeComponents();
}
