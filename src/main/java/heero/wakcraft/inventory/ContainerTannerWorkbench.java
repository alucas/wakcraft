package heero.wakcraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;

public class ContainerTannerWorkbench extends Container {
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 1, 1);
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;

	public ContainerTannerWorkbench(InventoryPlayer player_inventory, World world) {
		this.worldObj = world;

		this.addSlotToContainer(new SlotCrafting(player_inventory.player,
				this.craftMatrix, this.craftResult, 0, 124, 35));
		this.addSlotToContainer(new Slot(this.craftMatrix, 0, 48, 35));

		bindPlayerInventory(player_inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	protected void bindPlayerInventory(InventoryPlayer player_inventory) {
		int var6;
		int var7;
		for (var6 = 0; var6 < 3; ++var6) {
			for (var7 = 0; var7 < 9; ++var7) {
				this.addSlotToContainer(new Slot(player_inventory, var7 + var6
						* 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
			}
		}

		for (var6 = 0; var6 < 9; ++var6) {
			this.addSlotToContainer(new Slot(player_inventory, var6,
					8 + var6 * 18, 142));
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	public void onCraftMatrixChanged(IInventory par1IInventory) {
		// this.craftResult.setInventorySlotContents(
		// 0,
		// TutoCraftingTableCrafting.getInstance().findMatchingRecipe(
		// this.craftMatrix, this.worldObj));
		this.craftResult.setInventorySlotContents(
				0,
				CraftingManager.getInstance().findMatchingRecipe(
						this.craftMatrix, this.worldObj));
	}

	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);

		if (!this.worldObj.isRemote) {
			for (int i = 0; i < 1; ++i) {
				ItemStack itemstack = this.craftMatrix
						.getStackInSlotOnClosing(i);

				if (itemstack != null) {
					par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack,
							false);
				}
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer,
			int slot_index) {

		System.err.println("je passe par la avec l'index " + slot_index);

		ItemStack stack = null;

		Slot slot_object = (Slot) this.inventorySlots.get(slot_index);
		if (slot_object != null && slot_object.getHasStack()) {
			ItemStack stack_in_slot = slot_object.getStack();
			stack = stack_in_slot.copy();

			if (slot_index == 0) {
				if (!this.mergeItemStack(stack_in_slot, 2, 28, true)) {
					return null;
				}

				slot_object.onSlotChange(stack_in_slot, stack);

			} else if (slot_index >= 2 && slot_index < 28) {
				if (!this.mergeItemStack(stack_in_slot, 29, 38, false)) {
					return null;
				}
			} else if (slot_index >= 29 && slot_index < 38) {
				if (!this.mergeItemStack(stack_in_slot, 2, 28, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(stack_in_slot, 2, 28, false)) {
				return null;
			}

			if (stack_in_slot.stackSize == 0) {
				slot_object.putStack((ItemStack) null);
			} else {
				slot_object.onSlotChanged();
			}

			if (stack_in_slot.stackSize == stack.stackSize) {
				return null;
			}

			slot_object.onPickupFromSlot(par1EntityPlayer, stack_in_slot);
		}

		return stack;
	}
}
