package heero.mc.mod.wakcraft.inventory;

import heero.mc.mod.wakcraft.characteristic.CharacteristicsManager;
import heero.mc.mod.wakcraft.item.ItemWArmor;
import heero.mc.mod.wakcraft.item.ItemWArmor.TYPE;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

public class InventoryArmors extends InventoryBasic {
    protected static TYPE[] types = new TYPE[]{
            TYPE.HELMET, TYPE.CHESTPLATE, TYPE.BELT, TYPE.BOOTS,
            TYPE.AMULET, TYPE.CAPE, TYPE.RING, TYPE.WEAPON,
            TYPE.EPAULET, TYPE.PET, TYPE.RING, TYPE.WEAPON};

    protected Entity entity;

    public InventoryArmors(final Entity entity) {
        super("Armors", false, 12);

        this.entity = entity;
    }

    public TYPE getSlotType(final int slotId) {
        return types[slotId];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number
     * (second arg) of items and returns them in a new stack.
     */
    @Override
    public ItemStack decrStackSize(final int slotId, final int quantity) {
        if (quantity < 1) {
            return null;
        }

        ItemStack itemstack = getStackInSlot(slotId);
        if (itemstack == null) {
            return null;
        }

        setInventorySlotContents(slotId, null);

        this.markDirty();
        return itemstack;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be
     * crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(final int slotId, final ItemStack stack) {
        if (stack != null && !(stack.getItem() instanceof ItemWArmor)) {
            return;
        }

        ItemStack oldStack = getStackInSlot(slotId);
        if (oldStack != null) {
            final ItemWArmor item = (ItemWArmor) oldStack.getItem();

            CharacteristicsManager.unequipItem(entity, item);
        }

        if (stack != null) {
            final ItemWArmor item = (ItemWArmor) stack.getItem();

            CharacteristicsManager.equipItem(entity, item);
        }

        super.setInventorySlotContents(slotId, stack);
    }

    /**
     * Returns the maximum stack size for a inventory slot.
     */
    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring
     * stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(final int slotId, final ItemStack stack) {
        if (stack == null) {
            return false;
        }

        if (!(stack.getItem() instanceof ItemWArmor)) {
            return false;
        }

        return ((ItemWArmor) (stack.getItem())).getArmorType() == types[slotId];
    }

    @Override
    public void clear() {
        for (int i = 0; i < getSizeInventory(); ++i) {
            setInventorySlotContents(i, null);
        }
    }
}