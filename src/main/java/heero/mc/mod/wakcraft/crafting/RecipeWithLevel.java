package heero.mc.mod.wakcraft.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RecipeWithLevel implements IRecipeWithLevel {
    public final int level;
    public final int xp;
    public final ItemStack result;
    public final List<ItemStack> components;

    public RecipeWithLevel(final ItemStack result, final List<ItemStack> components, final int level, final int xp) {
        this.level = level;
        this.result = result;
        this.components = components;
        this.xp = xp;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        ArrayList<ItemStack> components = new ArrayList<ItemStack>(this.components);

        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = inventory.getStackInSlot(i);
            if (itemstack == null) {
                continue;
            }

            boolean flag = false;
            for (ItemStack stack : components) {
                if (itemstack.getItem() == stack.getItem()
                        && itemstack.getItemDamage() == stack.getItemDamage()
                        && itemstack.stackSize >= stack.stackSize) {
                    flag = true;
                    components.remove(stack);
                    break;
                }
            }

            if (!flag) {
                return false;
            }
        }

        return components.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
        return result.copy();
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize() {
        return components.size();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return result;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        return new ItemStack[0];
    }

    @Override
    public List<ItemStack> getRecipeComponents() {
        return components;
    }

    @Override
    public int getXp() {
        return xp;
    }

    @Override
    public int getLevel() {
        return level;
    }
}
