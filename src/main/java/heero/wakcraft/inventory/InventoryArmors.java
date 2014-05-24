package heero.wakcraft.inventory;

import heero.wakcraft.ability.AbilityManager;
import heero.wakcraft.item.ItemWArmor;
import heero.wakcraft.item.ItemWArmor.TYPE;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryArmors implements IInventory {
	protected static TYPE[] types = new TYPE[] { TYPE.HELMET, TYPE.CHESTPLATE,
			TYPE.BELT, TYPE.BOOTS, TYPE.AMULET, TYPE.CAPE, TYPE.RING,
			TYPE.WEAPON, TYPE.EPAULET, TYPE.PET, TYPE.RING, TYPE.WEAPON };

	protected boolean useCustomName;
	protected String customName;
	protected int slotsCount;
	protected ItemStack[] inventoryContents;
	protected Entity entity;

	public InventoryArmors(Entity entity) {
		this.customName = "Armors";
		this.useCustomName = false;
		this.slotsCount = 12;
		this.inventoryContents = new ItemStack[slotsCount];
		this.entity = entity;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(int slotId) {
		return this.inventoryContents[slotId];
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number
	 * (second arg) of items and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int slotId, int quantity) {
		if (quantity < 1 || this.inventoryContents[slotId] == null) {
			return null;
		}

		ItemStack itemstack = this.inventoryContents[slotId];
		setInventorySlotContents(slotId, null);

		this.markDirty();

		return itemstack;
	}

	/**
	 * When some containers are closed they call this on each slot, then drop
	 * whatever it returns as an EntityItem - like when you close a workbench
	 * GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int slotId) {
		if (this.inventoryContents[slotId] == null) {
			return null;
		}

		ItemStack itemstack = this.inventoryContents[slotId];
		setInventorySlotContents(slotId, null);

		return itemstack;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int slotId, ItemStack stack) {
		if (stack != null && !(stack.getItem() instanceof ItemWArmor)) {
			return;
		}

		if (this.inventoryContents[slotId] != null) {
			ItemWArmor item = (ItemWArmor) this.inventoryContents[slotId].getItem();

			AbilityManager.unequipItem(entity, item);
		}

		if (stack != null) {
			ItemWArmor item = (ItemWArmor) stack.getItem();

			AbilityManager.equipItem(entity, item);
		}

		this.inventoryContents[slotId] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}

		this.markDirty();
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return this.slotsCount;
	}

	/**
	 * Returns the name of the inventory
	 */
	@Override
	public String getInventoryName() {
		return this.customName;
	}

	/**
	 * Returns if the inventory is named
	 */
	@Override
	public boolean hasCustomInventoryName() {
		return this.useCustomName;
	}

	public void func_110133_a(String customName) {
		this.useCustomName = true;
		this.customName = customName;
	}

	/**
	 * Returns the maximum stack size for a inventory slot.
	 */
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	/**
	 * For tile entities, ensures the chunk containing the tile entity is saved
	 * to disk later - the game won't think it hasn't changed and skip it.
	 */
	@Override
	public void markDirty() {
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes
	 * with Container
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
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

		if (!(stack.getItem() instanceof ItemWArmor)) {
			return false;
		}

		return ((ItemWArmor) (stack.getItem())).getArmorType() == types[slotId];
	}

	public TYPE getSlotType(int slotId) {
		return types[slotId];
	}
}