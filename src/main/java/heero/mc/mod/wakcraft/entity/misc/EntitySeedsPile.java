package heero.mc.mod.wakcraft.entity.misc;

import heero.mc.mod.wakcraft.item.ItemWCreatureSeeds;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntitySeedsPile extends Entity implements IEntityAdditionalSpawnData {
	protected static final String TAG_AGE = "age";
	protected static final String TAG_ITEM = "item";

	protected int age;
	protected ItemWCreatureSeeds itemSeeds;

	public EntitySeedsPile(World world) {
		super(world);
	}

	public EntitySeedsPile(World world, int x, int y, int z, ItemWCreatureSeeds itemSeeds) {
		super(world);

		this.setSize(1, 1);
		this.setPosition(x, y, z);

		this.itemSeeds = itemSeeds;
	}

	@Override
	protected void entityInit() {
		age = 0;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagRoot) {
		age = tagRoot.getInteger(TAG_AGE);
		itemSeeds = (ItemWCreatureSeeds) Item.getItemById(tagRoot.getInteger(TAG_ITEM));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagRoot) {
		tagRoot.setInteger(TAG_AGE, age);
		tagRoot.setInteger(TAG_ITEM, Item.getIdFromItem(itemSeeds));
	}

	@Override
	public void onEntityUpdate() {
		age++;

		if (age > 300) {
			setDead();
		}
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(Item.getIdFromItem(itemSeeds));
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		itemSeeds = (ItemWCreatureSeeds) Item.getItemById(buffer.readInt());
	}

	public ItemWCreatureSeeds getItemSeeds() {
		return itemSeeds;
	}
}
