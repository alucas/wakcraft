package heero.wakcraft.tileentity;

import heero.wakcraft.WakcraftItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHavenGemWorkbench extends TileEntity implements IInventory {
	private static final String ID_GEMS = "gems";

	protected IInventory havenGems = new InventoryBasic("HGContainer", false, 9);

	public void readFromNBT(NBTTagCompound reader) {
		super.readFromNBT(reader);

		NBTTagList nbttaglist = reader.getTagList("Gems", 10);

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);

			int slotId = nbttagcompound.getByte("Slot") & 255;

			if (slotId >= 0 && slotId < havenGems.getSizeInventory()) {
				ItemStack stack = ItemStack.loadItemStackFromNBT(nbttagcompound);

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

	public void writeToNBT(NBTTagCompound writer) {
		super.writeToNBT(writer);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < havenGems.getSizeInventory(); ++i) {
			ItemStack stack = havenGems.getStackInSlot(i);

			if (stack != null) {
				int itemId = Item.getIdFromItem(stack.getItem());
				if (itemId == Item.getIdFromItem(WakcraftItems.decoHG)
						|| itemId == Item.getIdFromItem(WakcraftItems.merchantHG)
						|| itemId == Item.getIdFromItem(WakcraftItems.craftHG)
						|| itemId == Item.getIdFromItem(WakcraftItems.gardenHG)) {
					NBTTagCompound nbttagcompound = new NBTTagCompound();
					nbttagcompound.setByte("Slot", (byte) i);

					stack.writeToNBT(nbttagcompound);

					nbttaglist.appendTag(nbttagcompound);
				} else {
					System.err.println("This item is not a haven gem identifier");

					continue;
				}
			}
		}

		writer.setTag("Gems", nbttaglist);
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
		return havenGems.getInventoryStackLimit();
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
		return havenGems.isItemValidForSlot(slotId, stack);
	}
}
