package heero.mc.mod.wakcraft.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecipeWithLevel implements IExtendedRecipe {
    public int recipeLevel;
    public ItemStack recipeResult;
    public List<ItemStack> recipeComponents;

    public RecipeWithLevel(ItemStack result, List<ItemStack> components, int level) {
        this.recipeLevel = level;
        this.recipeResult = result;
        this.recipeComponents = components;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        ArrayList<ItemStack> components = new ArrayList<ItemStack>(recipeComponents);

        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = inventory.getStackInSlot(i);
            if (itemstack != null) {
                boolean flag = false;
                Iterator<ItemStack> iterator = components.iterator();

                while (iterator.hasNext()) {
                    ItemStack itemstack1 = (ItemStack) iterator.next();

                    if (itemstack.getItem() == itemstack1.getItem()
                            && itemstack.getItemDamage() == itemstack1.getItemDamage()
                            && itemstack.stackSize >= itemstack1.stackSize) {
                        flag = true;
                        components.remove(itemstack1);
                        break;
                    }
                }

                if (!flag) {
                    return false;
                }
            }
        }

        return components.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
        return recipeResult.copy();
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize() {
        return recipeComponents.size();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipeResult;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        return new ItemStack[0];
    }

    @Override
    public List<ItemStack> getRecipeComponents() {
        return recipeComponents;
    }
}
