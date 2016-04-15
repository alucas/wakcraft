package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.inventory.InventoryArmors;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class InventoryProperty implements IExtendedEntityProperties, ISynchProperties {
    public static final String IDENTIFIER = Reference.MODID + "Inventory";

    protected static String TAG_WAKCRAFT_INVENTORY = "WInventory";
    protected static String TAG_ARMOR_INVENTORY = "Armor";
    protected static String TAG_ARMOR_ITEMS = "Items";
    protected static String TAG_SLOT_ID = "SlotId";

    protected IInventory armorInventory;

    @Override
    public void init(Entity entity, World world) {
        armorInventory = new InventoryArmors(entity);
    }

    @Override
    public void saveNBTData(NBTTagCompound tagRoot) {
        final NBTTagCompound tagInventory = new NBTTagCompound();
        final NBTTagCompound tagArmorInventory = new NBTTagCompound();
        final NBTTagList tagArmorItems = new NBTTagList();

        for (int i = 0; i < armorInventory.getSizeInventory(); i++) {
            final ItemStack armorItem = armorInventory.getStackInSlot(i);
            if (armorItem == null) {
                continue;
            }

            final NBTTagCompound tagArmorItem = new NBTTagCompound();
            armorItem.writeToNBT(tagArmorItem);
            tagArmorItem.setInteger(TAG_SLOT_ID, i);

            tagArmorItems.appendTag(tagArmorItem);
        }

        tagArmorInventory.setTag(TAG_ARMOR_ITEMS, tagArmorItems);
        tagInventory.setTag(TAG_ARMOR_INVENTORY, tagArmorInventory);
        tagRoot.setTag(TAG_WAKCRAFT_INVENTORY, tagInventory);
    }

    @Override
    public void loadNBTData(NBTTagCompound tagRoot) {
        final NBTTagCompound tagInventory = tagRoot.getCompoundTag(TAG_WAKCRAFT_INVENTORY);
        final NBTTagCompound tagArmorInventory = tagInventory.getCompoundTag(TAG_ARMOR_INVENTORY);
        final NBTTagList tagArmorItems = tagArmorInventory.getTagList(TAG_ARMOR_ITEMS, 10);

        for (int i = 0; i < tagArmorItems.tagCount(); i++) {
            final NBTTagCompound tagArmorItem = tagArmorItems.getCompoundTagAt(i);
            final Integer slotId = tagArmorItem.getInteger(TAG_SLOT_ID);
            final ItemStack armorItem = ItemStack.loadItemStackFromNBT(tagArmorItem);

            armorInventory.setInventorySlotContents(slotId, armorItem);
        }
    }

    @Override
    public NBTTagCompound getClientPacket() {
        final NBTTagCompound tagRoot = new NBTTagCompound();

        saveNBTData(tagRoot);

        return tagRoot;
    }

    @Override
    public void onClientPacket(NBTTagCompound tagRoot) {
        loadNBTData(tagRoot);
    }

    public IInventory getArmorInventory() {
        return armorInventory;
    }
}
