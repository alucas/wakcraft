package heero.mc.mod.wakcraft.inventory;

import heero.mc.mod.wakcraft.quest.Quest;
import heero.mc.mod.wakcraft.quest.QuestTask;
import heero.mc.mod.wakcraft.util.QuestUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNPCGive extends Container {
    protected final InventoryCrafting craftMatrix;
    protected final Quest quest;
    protected final QuestTask task;
    protected final EntityPlayer player;
    protected boolean isTaskDone;

    public ContainerNPCGive(final EntityPlayer player, final Quest quest, final QuestTask task) {
        this.player = player;
        this.quest = quest;
        this.task = task;
        this.isTaskDone = false;

        final int sizeRecipe = task.what.length;
        this.craftMatrix = new InventoryCrafting(this, sizeRecipe, 1);

        final int left = 176 / 2 - sizeRecipe * 20 / 2;
        for (int i = 0; i < craftMatrix.getSizeInventory(); ++i) {
            this.addSlotToContainer(new Slot(this.craftMatrix, i, left + 20 * i, 42));
        }

        bindPlayerInventory(player.inventory);
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

        if (!player.worldObj.isRemote) {
            for (int i = 0; i < this.craftMatrix.getSizeInventory(); ++i) {
                ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);

                if (itemstack != null) {
                    player.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
        final Slot slot = this.inventorySlots.get(slotId);
        if (slot == null || !slot.getHasStack()) {
            return null;
        }

        final int sizeRecipe = task.what.length;
        final ItemStack stackInSlot = slot.getStack();
        final ItemStack stackInSlotCopy = stackInSlot.copy();

        if (slotId == 0) {
            if (!this.mergeItemStack(stackInSlot, 6, 42, true)) {
                return null;
            }

            slot.onSlotChange(stackInSlot, stackInSlotCopy);
        } else if (slotId >= 6 && slotId < 42) {
            if (!this.mergeItemStack(stackInSlot, 1, 6, false)) {
                return null;
            }
        } else if (!this.mergeItemStack(stackInSlot, 6, 42, false)) {
            return null;
        }

        if (stackInSlot.stackSize == 0) {
            slot.putStack(null);
        } else {
            slot.onSlotChanged();
        }

        if (stackInSlot.stackSize == stackInSlotCopy.stackSize) {
            return null;
        }

        slot.onPickupFromSlot(player, stackInSlot);

        return stackInSlotCopy;
    }

    @Override
    public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer playerIn) {
        final ItemStack stack = super.slotClick(slotId, clickedButton, mode, playerIn);
        if (isTaskDone || !testRecipe()) {
            return stack;
        }

        QuestUtil.advance(player, quest);
        QuestUtil.sendAdvancementToClient(player);

        craftMatrix.clear();

        this.isTaskDone = true;

        return stack;
    }

    public boolean testRecipe() {
        for (int i = 0; i < craftMatrix.getSizeInventory(); ++i) {
            final ItemStack stack = craftMatrix.getStackInSlot(i);
            final ItemStack questStack = task.what[i].toItemStack();
            if (stack == null || questStack == null || !ItemStack.areItemStacksEqual(stack, questStack)) {
                return false;
            }
        }

        return true;
    }

    public boolean isTaskDone() {
        return isTaskDone;
    }
}
