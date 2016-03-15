package heero.mc.mod.wakcraft.tileentity;

import heero.mc.mod.wakcraft.block.BlockHavenBagChest;
import heero.mc.mod.wakcraft.havenbag.ChestType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final String[] CHESTS_TAGS = new String[]{
            TAG_CHEST_NORMAL, TAG_CHEST_SMALL, TAG_CHEST_ADVENTURER,
            TAG_CHEST_KIT, TAG_CHEST_COLLECTOR, TAG_CHEST_GOLDEN,
            TAG_CHEST_EMERALD};
    /**
     * The current angle of the lid (between 0 and 1)
     */
    public float lidAngle;
    /**
     * The angle of the lid last tick
     */
    public float prevLidAngle;
    /**
     * The number of players currently using this chest
     */
    public int numPlayersUsing;
    private Map<Integer, ItemStack> chestContents;
    private Map<ChestType, Boolean> chestUnlocked;
    private int inventorySize;
    /**
     * Server sync counter (once per 20 ticks)
     */
    private int ticksSinceSync;
    /**
     * The type of the chest
     */
    private int cachedChestType;
    private String customName;
    private boolean hasCustomName;

    public TileEntityHavenBagChest() {
        this(-1);
    }

    public TileEntityHavenBagChest(int chestType) {
        cachedChestType = chestType;
        chestContents = new HashMap<Integer, ItemStack>();
        chestUnlocked = new HashMap<ChestType, Boolean>();
        inventorySize = 0;
        customName = "container.chest";
        hasCustomName = true;

        for (ChestType chestId : ChestType.values()) {
            inventorySize += chestId.chestSize;
            chestUnlocked.put(chestId, (chestId == ChestType.CHEST_NORMAL));
        }
    }

    public boolean isChestUnlocked(ChestType chestId) {
        return chestUnlocked.get(chestId);
    }

    public void unlockChest(ChestType chestId) {
        chestUnlocked.put(chestId, true);
    }

    @Override
    public int getSizeInventory() {
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

    @Override
    public ItemStack removeStackFromSlot(int slotId) {
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

    @Override
    public void readFromNBT(NBTTagCompound tagRoot) {
        super.readFromNBT(tagRoot);

        int inventorySize = 0;
        for (ChestType chestId : ChestType.values()) {
            NBTTagCompound tagChest = tagRoot.getCompoundTag(CHESTS_TAGS[chestId.ordinal()]);
            NBTTagList tagItems = tagChest.getTagList(TAG_ITEMS, 10);

            Boolean unlocked = tagChest.getBoolean(TAG_UNLOCKED);
            chestUnlocked.put(chestId, unlocked);

            if (unlocked) {
                for (int i = 0; i < tagItems.tagCount(); ++i) {
                    NBTTagCompound tagItem = tagItems.getCompoundTagAt(i);
                    int slotId = tagItem.getByte(TAG_SLOT) & 255;

                    if (slotId >= 0 && slotId < chestId.chestSize) {
                        chestContents.put(inventorySize + slotId, ItemStack.loadItemStackFromNBT(tagItem));
                    }
                }
            }

            inventorySize += chestId.chestSize;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagRoot) {
        super.writeToNBT(tagRoot);

        int inventorySize = 0;
        for (ChestType chestId : ChestType.values()) {
            Boolean unlocked = chestUnlocked.get(chestId);

            NBTTagCompound tagChest = new NBTTagCompound();
            NBTTagList tagItems = new NBTTagList();

            if (unlocked) {
                for (int slotId = 0; slotId < chestId.chestSize; slotId++) {
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
            tagRoot.setTag(CHESTS_TAGS[chestId.ordinal()], tagChest);

            inventorySize += chestId.chestSize;
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();

        writeToNBT(nbtTag);

        return new S35PacketUpdateTileEntity(pos, 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.getNbtCompound());
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
        return this.worldObj.getTileEntity(pos) != this ? false : player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D;
    }

    //@Override
    public void update() {
        ++this.ticksSinceSync;
        float f;

        if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + pos.getX() + pos.getY() + pos.getZ()) % 200 == 0) {
            this.numPlayersUsing = 0;
            f = 5.0F;
            List list = this.worldObj.getEntitiesWithinAABB(
                    EntityPlayer.class,
                    AxisAlignedBB.fromBounds(
                            (double) ((float) pos.getX() - f),
                            (double) ((float) pos.getY() - f),
                            (double) ((float) pos.getZ() - f),
                            (double) ((float) (pos.getX() + 1) + f),
                            (double) ((float) (pos.getY() + 1) + f),
                            (double) ((float) (pos.getZ() + 1) + f)));

            for (Object aList : list) {
                EntityPlayer entityplayer = (EntityPlayer) aList;

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

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
            this.worldObj.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
            float f1 = this.lidAngle;

            this.lidAngle += (this.numPlayersUsing > 0) ? f : -f;

            if (this.lidAngle > 1.0F) {
                this.lidAngle = 1.0F;
            }

            float f2 = 0.5F;

            if (this.lidAngle < f2 && f1 >= f2) {
                this.worldObj.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
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
    public void openInventory(EntityPlayer playerIn) {
        if (playerIn.isSpectator()) {
            return;
        }

        if (this.numPlayersUsing < 0) {
            this.numPlayersUsing = 0;
        }

        ++this.numPlayersUsing;
        this.worldObj.addBlockEvent(pos, this.getBlockType(), 1, this.numPlayersUsing);
        this.worldObj.notifyNeighborsOfStateChange(pos, this.getBlockType());
        this.worldObj.notifyNeighborsOfStateChange(pos.down(), this.getBlockType());
    }

    @Override
    public void closeInventory(EntityPlayer playerIn) {
        if (playerIn.isSpectator() || !(this.getBlockType() instanceof BlockHavenBagChest)) {
            return;
        }

        --this.numPlayersUsing;
        this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
        this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
        this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring
     * stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int slotId, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

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

    @Override
    public String getName() {
        return customName;
    }

    @Override
    public boolean hasCustomName() {
        return hasCustomName;
    }

    @Override
    public IChatComponent getDisplayName() {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName(), new Object[0]);
    }
}