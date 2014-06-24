package heero.mc.mod.wakcraft.inventory;

import heero.mc.mod.wakcraft.helper.ChestType;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBagChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHavenBagChest extends Container {
	private TileEntityHavenBagChest tileEntity;

	public ContainerHavenBagChest(InventoryPlayer inventory,
			TileEntityHavenBagChest tileEntity) {
		this.tileEntity = tileEntity;

		tileEntity.openInventory();

		int havenBagChestId = 0;
		for (ChestType chestId : ChestType.values()) {
			for (int i = 0; i < chestId.chestSize; i++) {
				this.addSlotToContainer(new HavenBagChestSlot(tileEntity, havenBagChestId++, chestId, 8 + (i % 9) * 18, 18 + (i / 9) * 18));
			}
		}

		bindPlayerInventory(inventory);
	}

	protected void bindPlayerInventory(InventoryPlayer inventory) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 198));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	public void updateHBSlots(ChestType selectedChestId) {
		for (int slotId = 0; slotId < inventorySlots.size(); slotId++) {
			Slot slot = getSlot(slotId);
			if (slot instanceof HavenBagChestSlot) {
				((HavenBagChestSlot) slot).conceal = (((HavenBagChestSlot) slot).chestId != selectedChestId || !tileEntity.isChestUnlocked(selectedChestId));
			}
		}
	}

	public boolean isChestUnlocked(ChestType chestId) {
		return tileEntity.isChestUnlocked(chestId);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
		ItemStack stack = null;

		Slot slot = (Slot) this.inventorySlots.get(slotId);
		if (slot != null && slot.getHasStack()) {
			ItemStack stack_in_slot = slot.getStack();
			stack = stack_in_slot.copy();

			int inventorySize = tileEntity.getSizeInventory();
			if (slotId >= 0 && slotId < inventorySize) {
				if (!this.mergeItemStack(stack_in_slot, inventorySize, inventorySize + 36, false)) {
					return null;
				}
			} else {
				int inventoryIndex = 0;
				for (ChestType chestId : ChestType.values()) {
					int chestSize = chestId.chestSize;

					if (tileEntity.isChestUnlocked(chestId)) {
						if (mergeItemStack(stack_in_slot, inventoryIndex, inventoryIndex + chestSize, false)) {
							break;
						}
					}

					inventoryIndex += chestSize;
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
	 * Called when the container is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

		tileEntity.closeInventory();
	}

	public class HavenBagChestSlot extends Slot {
		public boolean conceal;
		public ChestType chestId;

		public HavenBagChestSlot(IInventory inventory, int inventoryId, ChestType chestId, int posX, int posY) {
			super(inventory, inventoryId, posX, posY);

			this.chestId = chestId;
			this.conceal = true;
		}
	}
}
