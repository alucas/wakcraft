package heero.wakcraft.inventory;

import heero.wakcraft.entity.property.InventoryProperty;
import heero.wakcraft.item.ItemWArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerPlayerInventory extends Container {

	public ContainerPlayerInventory(EntityPlayer player) {
		InventoryProperty properties = (InventoryProperty) player.getExtendedProperties(InventoryProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading the inventory of player " + player.getDisplayName());
			return;
		}

		for (int i = 0; i < properties.inventoryArmors.getSizeInventory() ; ++i) {
			this.addSlotToContainer(new SlotArmor(properties.inventoryArmors, i, 8 + (i / 4) * 18 + (i >= 4 ? 61 : 0), 8 + (i % 4) * 18));
		}

		bindPlayerInventory(player.inventory);
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

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
		ItemStack stack = null;

		Slot slot = (Slot) this.inventorySlots.get(slotId);
		if (slot != null && slot.getHasStack()) {
			ItemStack stack_in_slot = slot.getStack();
			stack = stack_in_slot.copy();

			if (slotId >= 12 && slotId < 39) {
				if (!this.mergeItemStack(stack_in_slot, 39, 48, false)) {
					return null;
				}
			}else {
				if (!this.mergeItemStack(stack_in_slot, 12, 39, false)) {
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

	protected class SlotArmor extends Slot {
		public SlotArmor(IInventory inventory, int id, int x, int y) {
			super(inventory, id, x, y);
		}

		/**
		 * Returns the maximum stack size for a given slot (usually the same as
		 * getInventoryStackLimit(), but 1 in the case of armor slots)
		 */
		@Override
		public int getSlotStackLimit() {
			return inventory.getInventoryStackLimit();
		}

		/**
		 * Check if the stack is a valid item for this slot. Always true beside
		 * for the armor slots.
		 */
		@Override
		public boolean isItemValid(ItemStack stack) {
			return inventory.isItemValidForSlot(slotNumber, stack);
		}

		/**
		 * Returns the placeholder icon
		 */
		@SideOnly(Side.CLIENT)
		@Override
		public IIcon getBackgroundIconIndex() {
			if (!(inventory instanceof InventoryArmors)) {
				return null;
			}

			return ItemWArmor.getPlaceholderIcon(((InventoryArmors) inventory).getSlotType(slotNumber));
		}
	}
}
