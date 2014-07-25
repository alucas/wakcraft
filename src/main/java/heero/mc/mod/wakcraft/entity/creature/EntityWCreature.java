package heero.mc.mod.wakcraft.entity.creature;

import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.entity.ai.EntityAIFight;
import heero.mc.mod.wakcraft.entity.ai.EntityAIMoveOutWater;
import heero.mc.mod.wakcraft.helper.FightHelper;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityWCreature extends EntityCreature implements IWMob, IFighter{
	protected static final String TAG_GROUP = "Group";
	protected static final String TAG_UUIDMOST = "UUIDMost";
	protected static final String TAG_UUIDLEAST = "UUIDLeast";

	protected Set<UUID> group;

	public EntityWCreature(World world) {
		super(world);

		func_110163_bv(); // Enable persistence

		this.getNavigator().setAvoidsWater(true);

		this.tasks.addTask(00, new EntityAIFight(this, 1.0D));
		this.tasks.addTask(10, new EntityAISwimming(this));
		this.tasks.addTask(20, new EntityAIMoveOutWater(this, 0.9D));
		this.tasks.addTask(30, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(40, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(50, new EntityAILookIdle(this));
	}

	@Override
	public void setGroup(Set<UUID> group) {
		this.group = group;
	}

	@Override
	public Set<UUID> getGroup() {
		if (group == null) {
			group = new HashSet<UUID>();
			group.add(getUniqueID());
		}

		return group;
	}

	@Override
	public void onAttacked(final EntityLivingBase attacker, final ItemStack stack) {
		int health = FightHelper.getFightCharacteristic(this, Characteristic.HEALTH);

		if (health > 0) {
			FightHelper.updateDisplayName(this);

			this.worldObj.setEntityState(this, (byte) 2);
			playSoundHurt();
			return;
		}

		this.worldObj.setEntityState(this, (byte) 3);
		playSoundDeath();
		setHealth(0.0F);
		onDeath(DamageSource.generic);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagRoot) {
		super.writeToNBT(tagRoot);

		NBTTagList tagGroup = new NBTTagList();
		for (UUID fighterUUID : getGroup()) {
			NBTTagCompound tagFighter = new NBTTagCompound();
			tagFighter.setLong(TAG_UUIDMOST, fighterUUID.getMostSignificantBits());
			tagFighter.setLong(TAG_UUIDLEAST, fighterUUID.getLeastSignificantBits());

			tagGroup.appendTag(tagFighter);
		}

		tagRoot.setTag(TAG_GROUP, tagGroup);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagRoot) {
		super.readFromNBT(tagRoot);

		tagRoot.getTagList(TAG_GROUP, 10);
		NBTTagList tagGroup = tagRoot.getTagList(TAG_GROUP, 10);
		for (int i = 0; i < tagGroup.tagCount(); i++) {
			NBTTagCompound tagFighter = tagGroup.getCompoundTagAt(i);
			getGroup().add(new UUID(tagFighter.getLong(TAG_UUIDMOST), tagFighter.getLong(TAG_UUIDLEAST)));
		}
	}

	/**
	 * Can't be pushed by other entities
	 */
	@Override
	public boolean canBePushed() {
		return false;
	}

	/**
	 * Do not push other entities
	 */
	@Override
	protected void collideWithNearbyEntities() {
	}

	protected void playSoundDeath() {
		String sound = getDeathSound();

		if (sound != null) {
			playSound(sound, getSoundVolume(), getSoundPitch());
		}
	}

	protected void playSoundHurt() {
		String sound = getHurtSound();

		if (sound != null) {
			this.playSound(sound, getSoundVolume(), getSoundPitch());
		}
	}
}
