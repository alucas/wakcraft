package heero.wakcraft.entity.monster;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.WakcraftItems;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Gobball extends EntityAnimal {
	protected final AnimalChest inventory = new AnimalChest("Inventory", 10);
	// private EntityAIEatGrass field_146087_bs = new EntityAIEatGrass(this);
	private static final String __OBFID = "CL_00001648";
	protected static final Map dropRate = new HashMap<Integer, Integer>();
	static {
		dropRate.put(Item.getIdFromItem(WakcraftItems.gobballWool), 50);
		dropRate.put(Item.getIdFromItem(WakcraftItems.gobballSkin), 50);
		dropRate.put(Item.getIdFromItem(WakcraftItems.gobballHorn), 50);
		dropRate.put(Item.getIdFromItem(WakcraftItems.woollyKey), 5);
	}

	public Gobball(World par1World) {
		super(par1World);

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

		this.inventory.setInventorySlotContents(0, new ItemStack(WakcraftItems.gobballWool, 1, 0));
		this.inventory.setInventorySlotContents(1, new ItemStack(WakcraftItems.gobballHorn, 1, 0));
		this.inventory.setInventorySlotContents(2, new ItemStack(WakcraftItems.gobballSkin, 1, 0));
		this.inventory.setInventorySlotContents(3, new ItemStack(WakcraftItems.woollyKey, 1, 0));
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	protected boolean isAIEnabled() {
		return true;
	}

	protected void updateAITasks() {
		super.updateAITasks();
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
	}

	protected void entityInit() {
		super.entityInit();
	}

	/**
	 * Drop 0-2 items of this living's type. @param par1 - Whether this entity
	 * has recently been hit by a player. @param par2 - Level of Looting used to
	 * kill this mob.
	 */
	protected void dropFewItems(boolean par1, int par2) {
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
	public void handleHealthUpdate(byte par1) {
		System.out.println("HandleHealthUpdate : " + par1); // 2 : hurt, 3 : die
															// ?

		super.handleHealthUpdate(par1);
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	public boolean interact(EntityPlayer par1EntityPlayer) {
		return super.interact(par1EntityPlayer);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);

		// par1NBTTagCompound.setByte("Color", (byte) this.getFleeceColor());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT(par1NBTTagCompound);

		// this.setFleeceColor(par1NBTTagCompound.getByte("Color"));
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound() {
		return "mob.sheep.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound() {
		return "mob.sheep.say";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound() {
		return "mob.sheep.say";
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_,
			int p_145780_3_, Block p_145780_4_) {
		this.playSound("mob.sheep.step", 0.15F, 1.0F);
	}

	public Gobball createChild(EntityAgeable par1EntityAgeable) {
		return new Gobball(this.worldObj);
	}

	public IEntityLivingData onSpawnWithEgg(
			IEntityLivingData par1EntityLivingData) {
		return super.onSpawnWithEgg(par1EntityLivingData);
	}

	@SideOnly(Side.CLIENT)
	public static class RenderBouftou extends RenderLiving {
		private static final ResourceLocation bouftou = new ResourceLocation(
				WakcraftInfo.MODID.toLowerCase(), "textures/mobs/bouftou.png");

		public RenderBouftou(ModelBase par1ModelBase, float par2) {
			super(par1ModelBase, par2);
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity var1) {
			return bouftou;
		}

	}
}
