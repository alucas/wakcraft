package heero.wakcraft.inventory;

import heero.wakcraft.crafting.CraftingManager;
import heero.wakcraft.profession.ProfessionManager.PROFESSION;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerHavenGemWorkbench extends Container {
	public IInventory hgContainer = new InventoryBasic("HGContainer", false, 9);
	private World worldObj;

	public ContainerHavenGemWorkbench(InventoryPlayer inventory, World world) {
		this.worldObj = world;

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(this.hgContainer, i, 60 + 20 * (i % 3), 15 + 20 * (i / 3)));
		}

		bindPlayerInventory(inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	protected void bindPlayerInventory(InventoryPlayer inventory) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot_index) {
		ItemStack stack = null;

		Slot slot_object = (Slot) this.inventorySlots.get(slot_index);
		if (slot_object != null && slot_object.getHasStack()) {
			ItemStack stack_in_slot = slot_object.getStack();
			stack = stack_in_slot.copy();

			if (slot_index == 0) {
				if (!this.mergeItemStack(stack_in_slot, 6, 42, true)) {
					return null;
				}

				slot_object.onSlotChange(stack_in_slot, stack);
			} else if (slot_index >= 6 && slot_index < 42) {
				if (!this.mergeItemStack(stack_in_slot, 1, 6, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(stack_in_slot, 6, 42, false)) {
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

			slot_object.onPickupFromSlot(player, stack_in_slot);
		}

		return stack;
	}
}
