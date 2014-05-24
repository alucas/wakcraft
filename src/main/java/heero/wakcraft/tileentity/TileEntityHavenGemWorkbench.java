package heero.wakcraft.tileentity;

import heero.wakcraft.WItems;
import heero.wakcraft.manager.HavenBagGenerationHelper;
import heero.wakcraft.manager.HavenBagHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

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

	/**
	 * When some containers are closed they call this on each slot, then drop
	 * whatever it returns as an EntityItem - like when you close a workbench
	 * GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int slotId) {
		return havenGems.getStackInSlotOnClosing(slotId);
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
	 * Returns the name of the inventory
	 */
	@Override
	public String getInventoryName() {
		return havenGems.getInventoryName();
	}

	/**
	 * Returns if the inventory is named
	 */
	@Override
	public boolean hasCustomInventoryName() {
		return havenGems.hasCustomInventoryName();
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
	public void openInventory() {
		havenGems.openInventory();
	}

	@Override
	public void closeInventory() {
		havenGems.closeInventory();
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

	public void onSlotChanged(int slotId) {
		if (!worldObj.isRemote) {
			int uid = HavenBagHelper.getUIDFromCoord(xCoord, yCoord, zCoord);

			HavenBagGenerationHelper.updateGem(worldObj, uid, getStackInSlot(slotId), slotId);
			HavenBagGenerationHelper.updateBridge(worldObj, uid, havenGems);
		}
	}

	/**
	 * Determines if this TileEntity requires update calls.
	 * 
	 * @return True if you want updateEntity() to be called, false if not
	 */
	@Override
	public boolean canUpdate() {
		return false;
	}
}
