package heero.wakcraft.tileentity;

import heero.wakcraft.block.BlockHavenBagChest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityHavenBagChest extends TileEntity implements IInventory {
	private static final String TAG_CHEST_NORMAL = "ChestNormal";
	private static final String TAG_CHEST_SMALL = "ChestSmall";
	private static final String TAG_CHEST_ADVENTURER = "ChestAdventurer";
	private static final String TAG_CHEST_KIT = "ChestKit";
	private static final String TAG_CHEST_COLLECTOR = "ChestCollector";
	private static final String TAG_CHEST_GOLDEN = "ChestGolden";
	private static final String TAG_CHEST_EMERALD = "ChestEmerald";
	private static final String TAG_ITEMS = "Items";
	private static final String TAG_SLOT = "Slot";
	private static final String TAG_UNLOCKED = "Unlocked";

	public static final int CHEST_NORMAL = 0;
	public static final int CHEST_SMALL = 1;
	public static final int CHEST_ADVENTURER = 2;
	public static final int CHEST_KIT = 3;
	public static final int CHEST_COLLECTOR = 4;
	public static final int CHEST_GOLDEN = 5;
	public static final int CHEST_EMERALD = 6;

	public static final int[] CHESTS = new int[] { CHEST_NORMAL, CHEST_SMALL,
			CHEST_ADVENTURER, CHEST_KIT, CHEST_COLLECTOR, CHEST_GOLDEN,
			CHEST_EMERALD };
	private static final String[] CHESTS_TAGS = new String[] {
			TAG_CHEST_NORMAL, TAG_CHEST_SMALL, TAG_CHEST_ADVENTURER,
			TAG_CHEST_KIT, TAG_CHEST_COLLECTOR, TAG_CHEST_GOLDEN,
			TAG_CHEST_EMERALD };
	private static final int[] CHESTS_SIZES = new int[] { 14, 14, 21, 21, 21,
			28, 28 };

	private Map<Integer, ItemStack> chestContents;
	private Map<Integer, Boolean> chestUnlocked;

	/** The current angle of the lid (between 0 and 1) */
	public float lidAngle;
	/** The angle of the lid last tick */
	public float prevLidAngle;
	/** The number of players currently using this chest */
	public int numPlayersUsing;
	/** Server sync counter (once per 20 ticks) */
	private int ticksSinceSync;
	/** The type of the chest */
	private int cachedChestType;

	public TileEntityHavenBagChest() {
		this(-1);
	}

	@SideOnly(Side.CLIENT)
	public TileEntityHavenBagChest(int chestType) {
		cachedChestType = chestType;
		chestContents = new HashMap<Integer, ItemStack>();
		chestUnlocked = new HashMap<Integer, Boolean>();

		for (int chestId : CHESTS) {
			if (chestId == CHEST_NORMAL) {
				chestUnlocked.put(0, true);
			}
		}
	}

	public int getSizeInventory(int chestId) {
		return CHESTS_SIZES[chestId];
	}

	public boolean isChestUnlocked(int chestId) {
		return chestUnlocked.get(chestId);
	}

	@Override
	public int getSizeInventory() {
		int inventorySize = 0;
		for (int size : CHESTS_SIZES) {
			inventorySize += size;
		}

		return inventorySize;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(int slotId) {
		return chestContents.get(slotId);
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number
	 * (second arg) of items and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int slotId, int quantity) {
		ItemStack currentStack = chestContents.get(slotId);
		if (currentStack != null) {
			if (currentStack.stackSize <= quantity) {
				chestContents.remove(slotId);
				markDirty();

				return currentStack;
			} else {
				ItemStack itemstack = currentStack.splitStack(quantity);

				if (currentStack.stackSize == 0) {
					chestContents.remove(slotId);
				}

				markDirty();

				return itemstack;
			}
		} else {
			return null;
		}
	}

	/**
	 * When some containers are closed they call this on each slot, then drop
	 * whatever it returns as an EntityItem - like when you close a workbench
	 * GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int slotId) {
		ItemStack currentStack = chestContents.get(slotId);
		if (currentStack != null) {
			chestContents.remove(slotId);

			markDirty();

			return currentStack;
		}

		return null;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int slotId, ItemStack stack) {
		if (stack == null) {
			chestContents.remove(slotId);
		} else {
			chestContents.put(slotId, stack);
		}

		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}

		markDirty();
	}

	/**
	 * Returns the name of the inventory
	 */
	@Override
	public String getInventoryName() {
		return "container.chest";
	}

	/**
	 * Returns if the inventory is named
	 */
	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagRoot) {
		super.readFromNBT(tagRoot);
		
		int inventorySize = 0;
		for (int chestId : CHESTS) {
			NBTTagCompound tagChest = tagRoot.getCompoundTag(CHESTS_TAGS[chestId]);
			NBTTagList tagItems = tagChest.getTagList(TAG_ITEMS, 10);

			Boolean unlocked = tagRoot.getBoolean(TAG_UNLOCKED);
			chestUnlocked.put(chestId, unlocked);

			if (unlocked) {
				for (int i = 0; i < tagItems.tagCount() && i < CHESTS_SIZES[chestId]; ++i) {
					NBTTagCompound tagItem = tagItems.getCompoundTagAt(i);
					int slotId = tagItem.getByte(TAG_SLOT) & 255;

					if (slotId >= 0 && slotId < CHESTS_SIZES[chestId]) {
						chestContents.put(inventorySize + slotId, ItemStack.loadItemStackFromNBT(tagItem));
					}
				}
			}

			inventorySize += CHESTS_SIZES[chestId];
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagRoot) {
		super.writeToNBT(tagRoot);

		int inventorySize = 0;
		for (int chestId : CHESTS) {
			Boolean unlocked = chestUnlocked.get(chestId);
			if (unlocked == null) {
				unlocked = false;
			}

			NBTTagCompound tagChest = new NBTTagCompound();
			NBTTagList tagItems = new NBTTagList();

			if (unlocked) {
				for (int slotId = 0; slotId < CHESTS_SIZES[chestId]; slotId++) {
					ItemStack stack = chestContents.get(inventorySize + slotId);
					if (stack == null) {
						continue;
					}

					NBTTagCompound tagItem = new NBTTagCompound();
					tagItem.setByte(TAG_SLOT, (byte) slotId);
					stack.writeToNBT(tagItem);

					tagItems.appendTag(tagItem);
				}
			}

			tagChest.setTag(TAG_ITEMS, tagItems);
			tagChest.setBoolean(TAG_UNLOCKED, unlocked);
			tagRoot.setTag(CHESTS_TAGS[chestId], tagChest);

			inventorySize += CHESTS_SIZES[chestId];
		}
	}

	/**
	 * Returns the maximum stack size for a inventory slot.
	 */
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes
	 * with Container
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

	public void updateEntity() {
		super.updateEntity();
		++this.ticksSinceSync;
		float f;

		if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0) {
			this.numPlayersUsing = 0;
			f = 5.0F;
			List list = this.worldObj.getEntitiesWithinAABB(
					EntityPlayer.class,
					AxisAlignedBB.getAABBPool().getAABB(
							(double) ((float) this.xCoord - f),
							(double) ((float) this.yCoord - f),
							(double) ((float) this.zCoord - f),
							(double) ((float) (this.xCoord + 1) + f),
							(double) ((float) (this.yCoord + 1) + f),
							(double) ((float) (this.zCoord + 1) + f)));
			Iterator iterator = list.iterator();

			while (iterator.hasNext()) {
				EntityPlayer entityplayer = (EntityPlayer) iterator.next();

				if (entityplayer.openContainer instanceof ContainerChest) {
					IInventory iinventory = ((ContainerChest) entityplayer.openContainer).getLowerChestInventory();

					if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest) iinventory).isPartOfLargeChest(this)) {
						++this.numPlayersUsing;
					}
				}
			}
		}

		this.prevLidAngle = this.lidAngle;
		f = 0.1F;
		double d2;

		if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
			this.worldObj.playSoundEffect((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
			float f1 = this.lidAngle;

			this.lidAngle += (this.numPlayersUsing > 0) ? f : -f;

			if (this.lidAngle > 1.0F) {
				this.lidAngle = 1.0F;
			}

			float f2 = 0.5F;

			if (this.lidAngle < f2 && f1 >= f2) {
				this.worldObj.playSoundEffect((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (this.lidAngle < 0.0F) {
				this.lidAngle = 0.0F;
			}
		}
	}

	/**
	 * Called when a client event is received with the event number and
	 * argument, see World.sendClientEvent
	 */
	@Override
	public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_) {
		if (p_145842_1_ == 1) {
			this.numPlayersUsing = p_145842_2_;
			return true;
		} else {
			return super.receiveClientEvent(p_145842_1_, p_145842_2_);
		}
	}

	@Override
	public void openInventory() {
		if (this.numPlayersUsing < 0) {
			this.numPlayersUsing = 0;
		}

		++this.numPlayersUsing;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
	}

	@Override
	public void closeInventory() {
		if (this.getBlockType() instanceof BlockHavenBagChest) {
			--this.numPlayersUsing;
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
			this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
			this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
		}
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring
	 * stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(int slotId, ItemStack stack) {
		return true;
	}

	public int getChestType() {
		if (this.cachedChestType == -1) {
			if (this.worldObj == null || !(this.getBlockType() instanceof BlockHavenBagChest)) {
				return 0;
			}

			this.cachedChestType = ((BlockHavenBagChest) this.getBlockType()).chestType;
		}

		return this.cachedChestType;
	}
}