package heero.wakcraft.inventory;

import heero.wakcraft.WakcraftItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerHavenGemWorkbench extends Container {
	public IInventory hgContainer;
	private World worldObj;

	public ContainerHavenGemWorkbench(InventoryPlayer inventory, World world, IInventory hgContainer) {
		this.worldObj = world;
		this.hgContainer = hgContainer;

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new HGSlot(this.hgContainer, i, 60 + 20 * (i % 3), 15 + 20 * (i / 3)));
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

	public class HGSlot extends Slot {
		public HGSlot(IInventory inventory, int slotId, int x, int y) {
			super(inventory, slotId, x, y);
		}

		/**
		 * Check if the stack is a valid item for this slot. Always true beside
		 * for the armor slots.
		 */
		@Override
		public boolean isItemValid(ItemStack stack) {
			if (stack == null) {
				return false;
			}

			int itemId = Item.getIdFromItem(stack.getItem());
			if (itemId == Item.getIdFromItem(WakcraftItems.decoHG)
					|| itemId == Item.getIdFromItem(WakcraftItems.merchantHG)
					|| itemId == Item.getIdFromItem(WakcraftItems.craftHG)
					|| itemId == Item.getIdFromItem(WakcraftItems.gardenHG)) {
				return true;
			}

			return false;
		}

		/**
		 * Returns the maximum stack size for a given slot (usually the same as
		 * getInventoryStackLimit(), but 1 in the case of armor slots)
		 */
		public int getSlotStackLimit() {
			return 2;
		}
	}
}
