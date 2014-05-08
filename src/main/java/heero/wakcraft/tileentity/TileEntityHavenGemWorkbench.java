package heero.wakcraft.tileentity;

import heero.wakcraft.WakcraftBlocks;
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
			ItemStack stack = getStackInSlot(slotId);
			Block block = getHBBlock(stack);

			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if ((slotId % 2 == 1) && (i == 4 || j == 4) || (slotId % 2 == 0 && i != 4 && j != 4)) {
						setHBBBlock(- 2 + i + ((slotId / 2) % 3) * 6, - 1, 5 + j + (slotId / 6) * 6, block, 0);
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
							setHBBBlock(- 2 + i + column * 6, - 1, 4 + row * 6, block, 0);
						}
					} else if (stack1Lower == null || stack2Lower == null) {
						block = getHBBlock(null);
						for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
							for (int i = 0; i < 5; i++) {
								setHBBBlock(- 2 + i + column * 6, - 1, 4 - j + row * 6, block, 0);
							}
						}
					} else {
						for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
							for (int i = 0; i < 5; i++) {
								if (i == 1 || i == 2) {
									setHBBBlock(- 2 + i + column * 6, - 1, 4 - j + row * 6, WakcraftBlocks.havenbagbridge, 0);
								} else {
									setHBBBlock(- 2 + i + column * 6, - 1, 4 - j + row * 6, WakcraftBlocks.invisiblewall, 0);
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
							setHBBBlock(- 3 + column * 6, - 1, 5 + i + row * 6, block, 0);
						}
					} else if (stack1Lower == null || stack2Lower == null) {
						block = getHBBlock(null);
						for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
							for (int i = 0; i < 5; i++) {
								setHBBBlock(- 3 - j + column * 6, - 1, 5 + i + row * 6, block, 0);
							}
						}
					} else {
						for (int j = 0; j < ((stack2Upper == null) ? 2 : 1); j++) {
							for (int i = 0; i < 5; i++) {
								if (i == 1 || i == 2) {
									setHBBBlock(- 3 - j + column * 6, - 1, 5 + i + row * 6, WakcraftBlocks.havenbagbridge, 0);
								} else {
									setHBBBlock(- 3 - j + column * 6, - 1, 5 + i + row * 6, WakcraftBlocks.invisiblewall, 0);
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
					setHBBBlock(3 + (i % 3) * 6, - 1, 10 + (i / 3) * 6, getHBBlock(getStackInSlot(i * 2)), 0);
				} else {
					setHBBBlock(3 + (i % 3) * 6, - 1, 10 + (i / 3) * 6, WakcraftBlocks.invisiblewall, 0);
				}
			}
		}

		return;
	}

	private Block getHBBlock(ItemStack stack) {
		return (stack == null) ? WakcraftBlocks.invisiblewall
				: (stack.getItem() == WakcraftItems.craftHG) ? Blocks.stone
						: (stack.getItem() == WakcraftItems.merchantHG) ? Blocks.planks
								: (stack.getItem() == WakcraftItems.decoHG) ? Blocks.log
										: (stack.getItem() == WakcraftItems.gardenHG) ? Blocks.grass
												: Blocks.lapis_block;
	}

	private void setHBBBlock(int x, int y, int z, Block block, int metadata) {
		if (block.equals(WakcraftBlocks.invisiblewall)) {
			for (int i = 0; i < 4; i++) {
				worldObj.setBlock(xCoord + x, yCoord + y + i, zCoord + z, block, 0, 2);
			}
		} else if (block.equals(WakcraftBlocks.havenbagbridge)) {
			worldObj.setBlock(xCoord + x, yCoord + y, zCoord + z, WakcraftBlocks.havenbagbridge, 0, 2);

			for (int i = 1; i < 4; i++) {
				worldObj.setBlock(xCoord + x, yCoord + y + i, zCoord + z, WakcraftBlocks.havenbagbarrier, 0, 2);
			}
		} else {
			worldObj.setBlock(xCoord + x, yCoord + y, zCoord + z, block, metadata, 2);

			for (int i = 1; i < 4; i++) {
				worldObj.setBlock(xCoord + x, yCoord + y + i, zCoord + z, Blocks.air, 0, 2);
			}
		}
	}
}
