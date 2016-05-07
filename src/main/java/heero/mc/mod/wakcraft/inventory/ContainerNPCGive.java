package heero.mc.mod.wakcraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerNPCGive extends Container {
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 5, 1);
    private World worldObj;

    public ContainerNPCGive(InventoryPlayer inventory, World world) {
        this.worldObj = world;

        for (int i = 0; i < 5; ++i) {
            this.addSlotToContainer(new Slot(this.craftMatrix, i, 25 + 20 * i, 42));
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

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

        if (!this.worldObj.isRemote) {
            for (int i = 0; i < this.craftMatrix.getSizeInventory(); ++i) {
                ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);

                if (itemstack != null) {
                    player.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot_index) {
        ItemStack stack = null;

        Slot slot_object = this.inventorySlots.get(slot_index);
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
                slot_object.putStack(null);
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

    @Override
    /**
     * @param slotId Index of the slot
     * @buttonId 0 = left click, 1 = right click, 2 = wheel click
     * @specialKey 3 = pick block, 1 = shift key
     * @return
     */
    public ItemStack slotClick(int slotId, int buttonId, int specialKey, EntityPlayer player) {
        ItemStack stack = super.slotClick(slotId, buttonId, specialKey, player);

        onCraftMatrixChanged(this.craftMatrix);

        return stack;
    }
}
