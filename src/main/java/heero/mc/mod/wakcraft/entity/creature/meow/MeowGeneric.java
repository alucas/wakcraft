package heero.mc.mod.wakcraft.entity.creature.meow;


import heero.mc.mod.wakcraft.WItems;
import heero.mc.mod.wakcraft.entity.creature.EntityWCreature;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public abstract class MeowGeneric extends EntityWCreature {
    protected final AnimalChest inventory = new AnimalChest("Inventory", 10);

    protected static final Map<Integer, Integer> dropRate = new HashMap<>();

    static {
        dropRate.put(Item.getIdFromItem(WItems.gobballWool), 50);
        dropRate.put(Item.getIdFromItem(WItems.gobballSkin), 50);
        dropRate.put(Item.getIdFromItem(WItems.gobballHorn), 50);
        dropRate.put(Item.getIdFromItem(WItems.woollyKey), 5);
    }

    public MeowGeneric(World world) {
        super(world);

        // 1.2 block wide/tall
        this.setSize(0.6F, 0.6F);

        this.inventory.setInventorySlotContents(0, new ItemStack(WItems.gobballWool, 1, 0));
        this.inventory.setInventorySlotContents(1, new ItemStack(WItems.gobballHorn, 1, 0));
        this.inventory.setInventorySlotContents(2, new ItemStack(WItems.gobballSkin, 1, 0));
        this.inventory.setInventorySlotContents(3, new ItemStack(WItems.woollyKey, 1, 0));
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }

    /**
     * Called frequently so the entity can update its state every tick as
     * required. For example, zombies and skeletons use this to react to
     * sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity
     * has recently been hit by a player. @param par2 - Level of Looting used to
     * kill this mob.
     */
    @Override
    protected void dropFewItems(boolean hitByPlayer, int lootingLevel) {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {

            ItemStack itemStack = inventory.getStackInSlot(i);
            if (itemStack == null)
                continue;

            Integer rate = (Integer) dropRate.get(Item.getIdFromItem(itemStack.getItem()));
            if (rate == null) {
                System.out.println("This item (" + (itemStack.getItem().getUnlocalizedName()) + ") has no registered drop rate.");

                this.entityDropItem(itemStack, 0.0F);
            } else {
                if (rate > this.worldObj.rand.nextInt(100)) {
                    this.entityDropItem(itemStack, 0.0F);
                }
            }
        }
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow,
     * gets into the saddle on a pig.
     */
    @Override
    public boolean interact(EntityPlayer player) {
        return super.interact(player);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected String getLivingSound() {
        return "mob.sheep.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound() {
        return "mob.sheep.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound() {
        return "mob.sheep.say";
    }

    /**
     * Play step sound.
     */
    @Override
    protected void playStepSound(BlockPos blockPos, Block block) {
        this.playSound("mob.sheep.step", 0.15F, 1.0F);
    }
}
