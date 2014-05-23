package heero.wakcraft.inventory;

import heero.wakcraft.entity.property.InventoryProperty;
import heero.wakcraft.item.ItemWArmor;
import heero.wakcraft.item.ItemWArmor.TYPE;
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

		TYPE[] types = new TYPE[]{TYPE.HELMET, TYPE.CHESTPLATE, TYPE.BELT, TYPE.BOOTS, TYPE.AMULET, TYPE.CAPE, TYPE.RING, TYPE.WEAPON, TYPE.EPAULET, TYPE.PET, TYPE.RING, TYPE.WEAPON};
		for (int i = 0; i < properties.armors.getSizeInventory() ; ++i) {
			this.addSlotToContainer(new SlotArmor(types[i], properties.armors, i, 8 + (i / 4) * 18 + (i >= 4 ? 61 : 0), 8 + (i % 4) * 18));
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
		protected final TYPE armorType;

		public SlotArmor(TYPE type, IInventory inventory, int id, int x, int y) {
			super(inventory, id, x, y);

			this.armorType = type;
		}

		/**
		 * Returns the maximum stack size for a given slot (usually the same as
		 * getInventoryStackLimit(), but 1 in the case of armor slots)
		 */
		@Override
		public int getSlotStackLimit() {
			return 1;
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

			if (!(stack.getItem() instanceof ItemWArmor)) {
				return false;
			}

			return ((ItemWArmor) (stack.getItem())).getArmorType() == armorType;
		}

		/**
		 * Returns the placeholder icon
		 */
		@SideOnly(Side.CLIENT)
		@Override
		public IIcon getBackgroundIconIndex() {
			return ItemWArmor.getPlaceholderIcon(armorType);
		}
	}
}
