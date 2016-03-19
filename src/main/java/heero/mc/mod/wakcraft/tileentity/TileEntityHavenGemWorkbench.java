package heero.mc.mod.wakcraft.tileentity;

import heero.mc.mod.wakcraft.WItems;
import heero.mc.mod.wakcraft.havenbag.HavenBagGenerationHelper;
import heero.mc.mod.wakcraft.util.HavenBagUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;

public class TileEntityHavenGemWorkbench extends TileEntity implements IInventory {
    private static final String TAG_GEMS = "Gems";
    private static final String TAG_SLOT = "Slot";

    protected IInventory havenGems = new InventoryBasic("HGContainer", false, 18);

    public TileEntityHavenGemWorkbench() {
        super();

        havenGems.setInventorySlotContents(0, new ItemStack(WItems.merchantHG));
    }

    public void readFromNBT(NBTTagCompound tagRoot) {
        super.readFromNBT(tagRoot);

        NBTTagList tagGems = tagRoot.getTagList(TAG_GEMS, 10);

        for (int i = 0; i < tagGems.tagCount(); ++i) {
            NBTTagCompound tagGem = tagGems.getCompoundTagAt(i);

            int slotId = tagGem.getByte(TAG_SLOT) & 255;

            if (slotId == 0) {
                havenGems.setInventorySlotContents(0, new ItemStack(WItems.merchantHG));
            } else if (slotId > 0 && slotId < havenGems.getSizeInventory()) {
                ItemStack stack = ItemStack.loadItemStackFromNBT(tagGem);

                int itemId = Item.getIdFromItem(stack.getItem());
                if (itemId == Item.getIdFromItem(WItems.decoHG)
                        || itemId == Item.getIdFromItem(WItems.merchantHG)
                        || itemId == Item.getIdFromItem(WItems.craftHG)
                        || itemId == Item.getIdFromItem(WItems.gardenHG)) {
                    havenGems.setInventorySlotContents(slotId, stack);
                } else {
                    System.err.println("This item is not a haven gem identifier");

                    continue;
                }
            }
        }
    }

    public void writeToNBT(NBTTagCompound tagRoot) {
        super.writeToNBT(tagRoot);

        NBTTagList tagGems = new NBTTagList();

        for (int i = 0; i < havenGems.getSizeInventory(); ++i) {
            ItemStack stack = havenGems.getStackInSlot(i);

            if (stack != null) {
                int itemId = Item.getIdFromItem(stack.getItem());
                if (itemId == Item.getIdFromItem(WItems.decoHG)
                        || itemId == Item.getIdFromItem(WItems.merchantHG)
                        || itemId == Item.getIdFromItem(WItems.craftHG)
                        || itemId == Item.getIdFromItem(WItems.gardenHG)) {
                    NBTTagCompound tagGem = new NBTTagCompound();
                    tagGem.setByte(TAG_SLOT, (byte) i);

                    stack.writeToNBT(tagGem);

                    tagGems.appendTag(tagGem);
                } else {
                    System.err.println("This item is not a haven gem identifier");

                    continue;
                }
            }
        }

        tagRoot.setTag(TAG_GEMS, tagGems);
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return havenGems.getSizeInventory();
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int slotId) {
        return havenGems.getStackInSlot(slotId);
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number
     * (second arg) of items and returns them in a new stack.
     */
    @Override
    public ItemStack decrStackSize(int slotId, int quantity) {
        return havenGems.decrStackSize(slotId, quantity);
    }

    @Override
    public ItemStack removeStackFromSlot(int slotId) {
        return havenGems.removeStackFromSlot(slotId);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be
     * crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int slotId, ItemStack stack) {
        havenGems.setInventorySlotContents(slotId, stack);
    }

    /**
     * Returns the maximum stack size for a inventory slot.
     */
    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes
     * with Container
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return havenGems.isUseableByPlayer(player);
    }

    @Override
    public void openInventory(EntityPlayer playerIn) {
        havenGems.openInventory(playerIn);
    }

    @Override
    public void closeInventory(EntityPlayer playerIn) {
        havenGems.closeInventory(playerIn);
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring
     * stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int slotId, ItemStack stack) {
        if (stack == null) {
            return false;
        }

        int itemId = Item.getIdFromItem(stack.getItem());
        if (slotId == 0 && slotId == 1) {
            if (itemId == Item.getIdFromItem(WItems.merchantHG)) {
                return true;
            }
        } else if (itemId == Item.getIdFromItem(WItems.decoHG)
                || itemId == Item.getIdFromItem(WItems.merchantHG)
                || itemId == Item.getIdFromItem(WItems.craftHG)
                || itemId == Item.getIdFromItem(WItems.gardenHG)) {
            return true;
        }

        return false;
    }

    @Override
    public int getField(int id) {
        return havenGems.getField(id);
    }

    @Override
    public void setField(int id, int value) {
        havenGems.setField(id, value);
    }

    @Override
    public int getFieldCount() {
        return havenGems.getFieldCount();
    }

    @Override
    public void clear() {
        havenGems.clear();
    }

    public void onSlotChanged(int slotId) {
        if (worldObj.isRemote) {
            return;
        }

        int uid = HavenBagUtil.getUIDFromCoord(pos);

        HavenBagGenerationHelper.updateGem(worldObj, uid, getStackInSlot(slotId), slotId);
        HavenBagGenerationHelper.updateBridge(worldObj, uid, havenGems);
    }

    @Override
    public String getName() {
        return havenGems.getName();
    }

    @Override
    public boolean hasCustomName() {
        return havenGems.hasCustomName();
    }

    @Override
    public IChatComponent getDisplayName() {
        return havenGems.getDisplayName();
    }
}
