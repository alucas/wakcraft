package heero.mc.mod.wakcraft.inventory;

import heero.mc.mod.wakcraft.spell.ISpell;
import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSpells extends Container {

    public ContainerSpells(EntityPlayer player) {
        for (int line = 0; line < 3; line++) {
            for (int column = 0; column < 5; column++) {
                this.addSlotToContainer(new SlotInfinite(FightUtil.getSpellsInventory(player), column + line * 5, column * 20 + 60, line * 20 + 20));
            }
        }

        for (int line = 0; line < 2; line++) {
            for (int column = 0; column < 5; column++) {
                this.addSlotToContainer(new SlotInfinite(FightUtil.getSpellsInventory(player), column + line * 5 + 15, column * 20 + 60, line * 20 + 91));
            }
        }

        for (int line = 0; line < 9; line++) {
            this.addSlotToContainer(new Slot(FightUtil.getSpellsInventory(player), line + 25, 8 + line * 18, 143));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
        Slot slot = this.inventorySlots.get(slotId);
        if (slot == null || !slot.getHasStack()) {
            return null;
        }

        final ItemStack oldStack = slot.getStack();
        final ItemStack mergedStack = oldStack.copy();

        if (slotId >= 0 && slotId < 25) {
            if (!this.mergeItemStack(mergedStack, 25, 34, false)) {
                return null;
            }

            mergedStack.stackSize = 1;
        } else {
            mergedStack.stackSize = 0;
        }

        if (mergedStack.stackSize == 0) {
            slot.putStack(null);
        } else {
            slot.onSlotChanged();
        }

        if (mergedStack.stackSize == oldStack.stackSize) {
            return null;
        }

        slot.onPickupFromSlot(player, mergedStack);

        return oldStack;
    }

    protected class SlotInfinite extends Slot {
        public SlotInfinite(IInventory inventory, int id, int x, int y) {
            super(inventory, id, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem() instanceof ISpell;
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public ItemStack decrStackSize(int par1) {
            return super.getStack().copy();
        }

        @Override
        public void putStack(ItemStack par1ItemStack) {
            return;
        }
    }
}
