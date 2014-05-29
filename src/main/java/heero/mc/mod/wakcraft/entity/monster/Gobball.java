package heero.mc.mod.wakcraft.entity.monster;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.WItems;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Gobball extends EntityCreature {
	protected final AnimalChest inventory = new AnimalChest("Inventory", 10);
	// private EntityAIEatGrass field_146087_bs = new EntityAIEatGrass(this);
	protected static final Map<Integer, Integer> dropRate = new HashMap<Integer, Integer>();
	static {
		dropRate.put(Item.getIdFromItem(WItems.gobballWool), 50);
		dropRate.put(Item.getIdFromItem(WItems.gobballSkin), 50);
		dropRate.put(Item.getIdFromItem(WItems.gobballHorn), 50);
		dropRate.put(Item.getIdFromItem(WItems.woollyKey), 5);
	}

	public Gobball(World world) {
		super(world);

		// 1.2 block wide/tall
		this.setSize(0.8F, 0.8F);

		this.getNavigator().setAvoidsWater(true);

		this.tasks.addTask(0, new EntityAISwimming(this));
		// this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
		// this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		// this.tasks.addTask(3, new EntityAITempt(this, 1.1D, Items.wheat,
		// false));
		// this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
		// this.tasks.addTask(5, this.field_146087_bs);
		this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this,
				EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));

		this.inventory.setInventorySlotContents(0, new ItemStack(WItems.gobballWool, 1, 0));
		this.inventory.setInventorySlotContents(1, new ItemStack(WItems.gobballHorn, 1, 0));
		this.inventory.setInventorySlotContents(2, new ItemStack(WItems.gobballSkin, 1, 0));
		this.inventory.setInventorySlotContents(3, new ItemStack(WItems.woollyKey, 1, 0));
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	protected boolean isAIEnabled() {
		return true;
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

	@SideOnly(Side.CLIENT)
	@Override
	public void handleHealthUpdate(byte type) {
		System.out.println("HandleHealthUpdate : " + type); // 2 : hurt, 3 : die
															// ?

		super.handleHealthUpdate(type);
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
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound tagRoot) {
		super.writeEntityToNBT(tagRoot);

		// par1NBTTagCompound.setByte("Color", (byte) this.getFleeceColor());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound tagRoot) {
		super.readEntityFromNBT(tagRoot);

		// this.setFleeceColor(par1NBTTagCompound.getByte("Color"));
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

	@Override
	protected void func_145780_a(int x, int y, int z, Block block) {
		this.playSound("mob.sheep.step", 0.15F, 1.0F);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData entity) {
		return super.onSpawnWithEgg(entity);
	}

	@SideOnly(Side.CLIENT)
	public static class RenderGobball extends RenderLiving {
		private static final ResourceLocation texture = new ResourceLocation(WInfo.MODID.toLowerCase(), "textures/mobs/bouftou.png");

		public RenderGobball(ModelBase model, float shadowSize) {
			super(model, shadowSize);
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity entity) {
			return texture;
		}
	}
}
