package heero.wakcraft.inventory;

import heero.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHavenGemWorkbench extends Container {
	public IInventory hgContainer;

	public ContainerHavenGemWorkbench(InventoryPlayer inventory, IInventory hgContainer) {
		this.hgContainer = hgContainer;

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new HGSlot(hgContainer, i * 2 + 0, 55 + 25 * (i % 3), 05 + 25 * (i / 3)));
			this.addSlotToContainer(new HGSlot(hgContainer, i * 2 + 1, 60 + 25 * (i % 3), 10 + 25 * (i / 3)));
		}

		bindPlayerInventory(inventory);
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
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	public ItemStack slotClick(int slotId, int buttonId, int mode, EntityPlayer player) {
		if (slotId >= 0 && slotId < inventorySlots.size()) {
			Slot slot = (Slot) inventorySlots.get(slotId);
			if (slot instanceof HGSlot) {
				// left click
				if (buttonId != 0 || mode != 0) {
					return null;
				}

				ItemStack holdStack = player.inventory.getItemStack();
				ItemStack stack = slot.getStack();
				if (holdStack != null && stack != null) {
					return null;
				}

				// can't interact with slot 0
				if (slotId == 0) {
					return null;
				}

				// Can't pick up a gem in the lower slot if the upper slot isn't empty
				if (stack != null && slotId % 2 == 0 && ((Slot)inventorySlots.get(slotId + 1)).getHasStack()) {
					return null;
				}

				if (holdStack != null && slotId % 2 == 1) {
					Slot lowerSlot = (Slot)inventorySlots.get(slotId - 1);

					// Can't put a gem in the upper slot if the lower slot isn't empty
					if (!lowerSlot.getHasStack()) {
						return null;
					}

					// can't put different gem in upper and lower slots
					if (!lowerSlot.getStack().getItem().equals(holdStack.getItem())) {
						return null;
					}
				}

				if (holdStack == null) {
					player.inventory.setItemStack(stack);
					slot.putStack((ItemStack)null);

					//slot.onPickupFromSlot(player, holdStack);
				} else {
					player.inventory.setItemStack((ItemStack)null);
					slot.putStack(holdStack);
				}

				slot.onSlotChanged();

				return stack;
			}
		}

		return super.slotClick(slotId, buttonId, mode, player);
    }

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
		ItemStack stack = null;

		Slot slot = (Slot) this.inventorySlots.get(slotId);
		if (slot != null && slot.getHasStack()) {
			ItemStack stack_in_slot = slot.getStack();
			stack = stack_in_slot.copy();

			if (slotId >= 18 && slotId < 45) {
				if (!this.mergeItemStack(stack_in_slot, 45, 54, false)) {
					return null;
				}
			} else {
				if (!this.mergeItemStack(stack_in_slot, 18, 45, false)) {
					return null;
				}
			}

			if (stack_in_slot.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (stack_in_slot.stackSize == stack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, stack_in_slot);
		}

		return stack;
	}

	/**
	 * Returns true if the player can "drag-spilt" items into this slot,.
	 * returns true by default. Called to check if the slot can be added to a
	 * list of Slots to split the held ItemStack across.
	 */
	@Override
	public boolean canDragIntoSlot(Slot slot) {
		return !(slot instanceof HGSlot);
	}

	public class HGSlot extends Slot {
		public HGSlot(IInventory inventory, int slotId, int x, int y) {
			super(inventory, slotId, x, y);
		}

		@Override
		public void onSlotChanged() {
			super.onSlotChanged();

			if (inventory instanceof TileEntityHavenGemWorkbench) {
				((TileEntityHavenGemWorkbench) inventory).onSlotChanged(getSlotIndex());
			}
		}

		/**
	     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
	     */
		@Override
	    public boolean isItemValid(ItemStack stack) {
	        return inventory.isItemValidForSlot(getSlotIndex(), stack);
	    }

		/**
		 * Return whether this slot's stack can be taken from this slot.
		 */
		@Override
		public boolean canTakeStack(EntityPlayer player) {
			return true;
		}
	}
}
