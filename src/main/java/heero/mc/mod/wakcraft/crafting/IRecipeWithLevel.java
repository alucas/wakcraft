package heero.mc.mod.wakcraft.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.List;

public interface IRecipeWithLevel extends IRecipe {
    public List<ItemStack> getRecipeComponents();
    public int getXp();
    public int getLevel();
}
