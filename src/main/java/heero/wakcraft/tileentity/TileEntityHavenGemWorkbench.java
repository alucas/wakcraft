package heero.wakcraft.tileentity;

import heero.wakcraft.WakcraftItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHavenGemWorkbench extends TileEntity implements IInventory {
	private static final String ID_GEMS = "gems";

	protected IInventory havenGems = new InventoryBasic("HGContainer", false, 18);

	public TileEntityHavenGemWorkbench() {
		super();

		havenGems.setInventorySlotContents(0, new ItemStack(WakcraftItems.merchantHG));
	}

	public void readFromNBT(NBTTagCompound reader) {
		super.readFromNBT(reader);

		NBTTagList nbttaglist = reader.getTagList("Gems", 10);

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);

			int slotId = nbttagcompound.getByte("Slot") & 255;

			if (slotId == 0) {
				havenGems.setInventorySlotContents(0, new ItemStack(WakcraftItems.merchantHG));
			} else if (slotId > 0 && slotId < havenGems.getSizeInventory()) {
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
			ItemStack stack = getStackInSlot(slotId);
			Block block = getHBBlock(stack);

			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if ((slotId % 2 == 1) && (i == 4 || j == 4) || (slotId % 2 == 0 && i != 4 && j != 4)) {
						worldObj.setBlock(xCoord - 2 + i + ((slotId / 2) % 3) * 6, yCoord - 1, zCoord + 5 + j + (slotId / 6) * 6, block, 0, 2);
					}
				}
			}

			for (int column = 2; column >= 0; --column) {
				for (int row = 2; row >= 1; --row) {
					ItemStack stack1Lower = getStackInSlot((row * 3 + column) * 2);
					ItemStack stack1Upper = getStackInSlot((row * 3 + column) * 2 + 1);
					ItemStack stack2Lower = getStackInSlot(((row - 1) * 3 + column) * 2);
					ItemStack stack2Upper = getStackInSlot(((row - 1) * 3 + column) * 2 + 1);

					if (stack1Upper != null && stack2Upper != null && stack1Upper.getItem().equals(stack2Upper.getItem())) {
						block = getHBBlock(stack1Lower);
						for (int i = 0; i < 5; i++) {
							worldObj.setBlock(xCoord - 2 + i + column * 6, yCoord - 1, zCoord + 4 + row * 6, block, 0, 2);
						}
					} else if (stack1Lower == null || stack2Lower == null) {
						block = getHBBlock(null);
						for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
							for (int i = 0; i < 5; i++) {
								worldObj.setBlock(xCoord - 2 + i + column * 6, yCoord - 1, zCoord + 4 - j + row * 6, block, 0, 2);
							}
						}
					} else {
						for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
							for (int i = 0; i < 5; i++) {
								if (i == 1 || i == 2) {
									worldObj.setBlock(xCoord - 2 + i + column * 6, yCoord - 1, zCoord + 4 - j + row * 6, Blocks.stone_slab, 12, 2);
								} else {
									worldObj.setBlock(xCoord - 2 + i + column * 6, yCoord - 1, zCoord + 4 - j + row * 6, Blocks.glass_pane, 0, 2);
								}
							}
						}
					}
				}
			}

			for (int column = 2; column >= 1; --column) {
				for (int row = 2; row >= 0; --row) {
					ItemStack stack1Lower = getStackInSlot((row * 3 + column) * 2);
					ItemStack stack1Upper = getStackInSlot((row * 3 + column) * 2 + 1);
					ItemStack stack2Lower = getStackInSlot((row * 3 + (column - 1)) * 2);
					ItemStack stack2Upper = getStackInSlot((row * 3 + (column - 1)) * 2 + 1);

					if (stack1Upper != null && stack2Upper != null && stack1Upper.getItem().equals(stack2Upper.getItem())) {
						block = getHBBlock(stack1Lower);
						for (int i = 0; i < 5; i++) {
							worldObj.setBlock(xCoord - 3 + column * 6, yCoord - 1, zCoord + 5 + i + row * 6, block, 0, 2);
						}
					} else if (stack1Lower == null || stack2Lower == null) {
						block = getHBBlock(null);
						for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
							for (int i = 0; i < 5; i++) {
								worldObj.setBlock(xCoord - 3 - j + column * 6, yCoord - 1, zCoord + 5 + i + row * 6, block, 0, 2);
							}
						}
					} else {
						for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
							for (int i = 0; i < 5; i++) {
								if (i == 1 || i == 2) {
									worldObj.setBlock(xCoord - 3 - j + column * 6, yCoord - 1, zCoord + 5 + i + row * 6, Blocks.stone_slab, 12, 2);
								} else {
									worldObj.setBlock(xCoord - 3 - j + column * 6, yCoord - 1, zCoord + 5 + i + row * 6, Blocks.glass_pane, 0, 2);
								}
							}
						}
					}
				}
			}

			for (int i = 0; i < 5; i++) {
				if (i == 2) continue;

				ItemStack stack1 = getStackInSlot(i * 2);
				ItemStack stack2 = getStackInSlot((i + 1) * 2 + 1);
				ItemStack stack3 = getStackInSlot((i + 3) * 2 + 1);
				ItemStack stack4 = getStackInSlot((i + 4) * 2 + 1);
				if (stack1 != null && stack2 != null && stack3 != null && stack4 != null && stack1.getItem().equals(stack2.getItem()) && stack1.getItem().equals(stack3.getItem()) && stack1.getItem().equals(stack4.getItem())) {
					worldObj.setBlock(xCoord + 3 + (i % 3) * 6, yCoord - 1, zCoord + 10 + (i / 3) * 6, getHBBlock(getStackInSlot(i * 2)), 0, 2);
				} else {
					worldObj.setBlock(xCoord + 3 + (i % 3) * 6, yCoord - 1, zCoord + 10 + (i / 3) * 6, Blocks.glass_pane, 0, 2);
				}
			}
		}

		return;
	}

	private Block getHBBlock(ItemStack stack) {
		return (stack == null) ? Blocks.glass_pane
				: (stack.getItem() == WakcraftItems.craftHG) ? Blocks.stone
						: (stack.getItem() == WakcraftItems.merchantHG) ? Blocks.planks
								: (stack.getItem() == WakcraftItems.decoHG) ? Blocks.log
										: (stack.getItem() == WakcraftItems.gardenHG) ? Blocks.grass
												: Blocks.lapis_block;
	}
}
