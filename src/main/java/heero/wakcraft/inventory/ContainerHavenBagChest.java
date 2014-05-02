package heero.wakcraft.inventory;

import heero.wakcraft.tileentity.TileEntityHavenBagChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerHavenBagChest extends Container {
	private World worldObj;
	private InventoryPlayer inventory;
	private TileEntityHavenBagChest tileEntity;

	public ContainerHavenBagChest(InventoryPlayer inventory, World world,
			TileEntityHavenBagChest tileEntity) {
		this.worldObj = world;
		this.inventory = inventory;
		this.tileEntity = tileEntity;

		setCurrentChest(TileEntityHavenBagChest.CHEST_NORMAL);
		initSlots();
	}

	protected void initSlots() {
        this.inventorySlots.clear();
        this.inventoryItemStacks.clear();

		for (int i = 0; i < tileEntity.getSizeInventory(); i++) {
			this.addSlotToContainer(new Slot(tileEntity, i, 8 + (i % 9) * 18, 18 + (i / 9) * 18));
		}

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 198));
		}
	}

	public void setCurrentChest(int chestId) {
		tileEntity.setCurrentChest(chestId);

		initSlots();
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

			int chestSize = tileEntity.getSizeInventory();
			if (chestSize > 0) {
				if (slotId >= 0 && slotId < chestSize) {
					if (!this.mergeItemStack(stack_in_slot, chestSize,
							chestSize + 36, false)) {
						return null;
					}
				} else {
					if (!this
							.mergeItemStack(stack_in_slot, 0, chestSize, false)) {
						return null;
					}
				}
			} else {
				if (slotId >= 0 && slotId < 27) {
					if (!this.mergeItemStack(stack_in_slot, 27, 36, false)) {
						return null;
					}
				} else {
					if (!this.mergeItemStack(stack_in_slot, 0, 27, false)) {
						return null;
					}
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
}
