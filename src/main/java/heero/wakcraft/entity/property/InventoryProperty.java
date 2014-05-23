package heero.wakcraft.entity.property;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class InventoryProperty implements IExtendedEntityProperties, ISynchProperties {
	public static final String IDENTIFIER = "inventory";

	protected static String TAG_WINVENTORY = "WIventory";
	protected static String TAG_ARMOR = "Armor";
	protected static String TAG_ARMOR_ITEMS = "Items";
	protected static String TAG_SLOT = "Slot";

	public InventoryBasic armors;

	@Override
	public void init(Entity entity, World world) {
		armors = new InventoryBasic("Armors", false, 12);
	}

	@Override
	public void saveNBTData(NBTTagCompound tagRoot) {
		NBTTagCompound tagInventory = new NBTTagCompound();

		NBTTagCompound tagArmor = new NBTTagCompound();
		NBTTagList tagAmorsItems = new NBTTagList();

		for (int i = 0; i < armors.getSizeInventory(); i++) {
			if (armors.getStackInSlot(i) == null) {
				continue;
			}

			NBTTagCompound tagItem = new NBTTagCompound();
			armors.getStackInSlot(i).writeToNBT(tagItem);
			tagItem.setInteger(TAG_SLOT, i);

			tagAmorsItems.appendTag(tagItem);
		}

		tagArmor.setTag(TAG_ARMOR_ITEMS, tagAmorsItems);
		tagInventory.setTag(TAG_ARMOR, tagArmor);
		tagRoot.setTag(TAG_WINVENTORY, tagInventory);
	}

	@Override
	public void loadNBTData(NBTTagCompound tagRoot) {
		NBTTagCompound tagInventory = tagRoot.getCompoundTag(TAG_WINVENTORY);
		NBTTagCompound tagArmor = tagInventory.getCompoundTag(TAG_ARMOR);
		NBTTagList tagArmorItems = tagArmor.getTagList(TAG_ARMOR_ITEMS, 10);

		for (int i = 0; i < tagArmorItems.tagCount(); i++) {
			NBTTagCompound tagItem = tagArmorItems.getCompoundTagAt(i);
			Integer slot = tagItem.getInteger(TAG_SLOT);
			ItemStack stack = ItemStack.loadItemStackFromNBT(tagItem);

			armors.setInventorySlotContents(slot, stack);
		}
	}

	@Override
	public NBTTagCompound getClientPacket() {
		NBTTagCompound tagRoot = new NBTTagCompound();

		saveNBTData(tagRoot);

		return tagRoot;
	}

	@Override
	public void onClientPacket(NBTTagCompound tagRoot) {
		loadNBTData(tagRoot);
	}
}
