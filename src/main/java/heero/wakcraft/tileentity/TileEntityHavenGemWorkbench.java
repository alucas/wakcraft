package heero.wakcraft.tileentity;

import heero.wakcraft.WakcraftItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHavenGemWorkbench extends TileEntity {
	private static final String ID_GEMS = "gems";

	protected IInventory havenGems = new InventoryBasic("HGContainer", false, 9);

	public void readFromNBT(NBTTagCompound reader) {
		super.readFromNBT(reader);

		int[] gemIds = reader.getIntArray("gems");
		if (gemIds.length != 18) {
			System.err.println("Wrong haven gem NBT array size");

			return;
		}

		for (int i = 0; i < gemIds.length; i += 2) {
			if (gemIds[i] == Item.getIdFromItem(WakcraftItems.decoHG)) {
				havenGems.setInventorySlotContents(i % 2, new ItemStack(WakcraftItems.decoHG, 0, gemIds[i + 1]));
			} else if (gemIds[i] == Item.getIdFromItem(WakcraftItems.merchantHG)) {
				havenGems.setInventorySlotContents(i % 2, new ItemStack(WakcraftItems.merchantHG, 0, gemIds[i + 1]));
			} else if (gemIds[i] == Item.getIdFromItem(WakcraftItems.gardenHG)) {
				havenGems.setInventorySlotContents(i % 2, new ItemStack(WakcraftItems.gardenHG, 0, gemIds[i + 1]));
			} else if (gemIds[i] == Item.getIdFromItem(WakcraftItems.craftHG)) {
				havenGems.setInventorySlotContents(i % 2, new ItemStack(WakcraftItems.craftHG, 0, gemIds[i + 1]));
			} else if (gemIds[i] == 0) {
				continue;
			} else {
				System.err.println("Unknow haven gem identifier");

				continue;
			}
		}
	}

	public void writeToNBT(NBTTagCompound writer) {
		super.writeToNBT(writer);

		int[] gemIds = new int[18];
		for (int i = 0; i < gemIds.length; i += 2) {
			ItemStack stack = havenGems.getStackInSlot(i % 2);
			if (stack == null) {
				continue;
			}

			gemIds[i] = Item.getIdFromItem(stack.getItem());
			gemIds[i + 1] = stack.stackSize;
		}

		writer.setIntArray("gems", gemIds);
	}

	public IInventory getInventory() {
		return havenGems;
	}
}
