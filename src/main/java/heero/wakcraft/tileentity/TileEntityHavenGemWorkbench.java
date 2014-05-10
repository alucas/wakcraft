package heero.wakcraft.tileentity;

import heero.wakcraft.WakcraftItems;
import heero.wakcraft.havenbag.HavenBagGenerationHelper;
import heero.wakcraft.havenbag.HavenBagHelper;
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

		havenGems.setInventorySlotContents(0, new ItemStack(WakcraftItems.merchantHG));
	}

	public void readFromNBT(NBTTagCompound tagRoot) {
		super.readFromNBT(tagRoot);

		NBTTagList tagGems = tagRoot.getTagList(TAG_GEMS, 10);

		for (int i = 0; i < tagGems.tagCount(); ++i) {
			NBTTagCompound tagGem = tagGems.getCompoundTagAt(i);

			int slotId = tagGem.getByte(TAG_SLOT) & 255;

			if (slotId == 0) {
				havenGems.setInventorySlotContents(0, new ItemStack(WakcraftItems.merchantHG));
			} else if (slotId > 0 && slotId < havenGems.getSizeInventory()) {
				ItemStack stack = ItemStack.loadItemStackFromNBT(tagGem);

				int itemId = Item.getIdFromItem(stack.getItem());
				if (itemId == Item.getIdFromItem(WakcraftItems.decoHG)
						|| itemId == Item.getIdFromItem(WakcraftItems.merchantHG)
						|| itemId == Item.getIdFromItem(WakcraftItems.craftHG)
						|| itemId == Item.getIdFromItem(WakcraftItems.gardenHG)) {
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
				if (itemId == Item.getIdFromItem(WakcraftItems.decoHG)
						|| itemId == Item.getIdFromItem(WakcraftItems.merchantHG)
						|| itemId == Item.getIdFromItem(WakcraftItems.craftHG)
						|| itemId == Item.getIdFromItem(WakcraftItems.gardenHG)) {
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

	@Override
	public int getSizeInventory() {
		return havenGems.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slotId) {
		return havenGems.getStackInSlot(slotId);
	}

	@Override
	public ItemStack decrStackSize(int slotId, int quantity) {
		return havenGems.decrStackSize(slotId, quantity);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotId) {
		return havenGems.getStackInSlotOnClosing(slotId);
	}

	@Override
	public void setInventorySlotContents(int slotId, ItemStack stack) {
		havenGems.setInventorySlotContents(slotId, stack);
	}

	@Override
	public String getInventoryName() {
		return havenGems.getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return havenGems.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

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

	@Override
	public boolean isItemValidForSlot(int slotId, ItemStack stack) {
		if (stack == null) {
			return false;
		}

		int itemId = Item.getIdFromItem(stack.getItem());
		if (slotId == 0 && slotId == 1) {
			if (itemId == Item.getIdFromItem(WakcraftItems.merchantHG)) {
				return true;
			}
		} else if (itemId == Item.getIdFromItem(WakcraftItems.decoHG)
				|| itemId == Item.getIdFromItem(WakcraftItems.merchantHG)
				|| itemId == Item.getIdFromItem(WakcraftItems.craftHG)
				|| itemId == Item.getIdFromItem(WakcraftItems.gardenHG)) {
			return true;
		}

		return false;
	}

	public void onSlotChanged(int slotId) {
		if (!worldObj.isRemote) {
			int uid = HavenBagHelper.getUIDFromCoord(xCoord, yCoord, zCoord);

			HavenBagGenerationHelper.updateHavenBag(uid, havenGems, getStackInSlot(slotId), slotId);
		}
	}
}
