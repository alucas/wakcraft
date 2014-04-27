package heero.wakcraft.inventory;

import heero.wakcraft.crafting.CraftingManager;
import heero.wakcraft.crafting.IExtendedRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

public class SlotComplexCrafting extends SlotCrafting {
	private InventoryCrafting inventoryCrafting;
	private World world;

	public SlotComplexCrafting(EntityPlayer player, World world, InventoryCrafting inventoryCrafting, IInventory inventory, int slotId, int x, int y) {
		super(player, inventoryCrafting, inventory, slotId, x, y);

		this.inventoryCrafting = inventoryCrafting;
		this.world = world;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		FMLCommonHandler.instance().firePlayerCraftingEvent(player, stack, inventoryCrafting);
		this.onCrafting(stack);

		IExtendedRecipe recipe = CraftingManager.getInstance().getMatchingRecipe(inventoryCrafting, world);
		if (recipe == null) {
			// Something is not right !
			return;
		}

		ItemStack result = recipe.getCraftingResult(inventoryCrafting);
		if (result.getItem() != stack.getItem()) {
			// Something is not right !
			return;
		}

		for (int i = 0; i < this.inventoryCrafting.getSizeInventory(); ++i) {
			ItemStack itemstack1 = this.inventoryCrafting.getStackInSlot(i);

			if (itemstack1 != null) {
				for (ItemStack recipeStack : recipe.getRecipeComponents()) {
					if (recipeStack.getItem() == itemstack1.getItem()) {
						this.inventoryCrafting.decrStackSize(i, recipeStack.stackSize);
					}
				}
			}
		}
	}
}