package heero.mc.mod.wakcraft.entity.misc;

import heero.mc.mod.wakcraft.item.ItemWCreatureSeeds;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntitySeedsPile extends Entity implements IEntityAdditionalSpawnData {
	private int age;
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
	protected void readEntityFromNBT(NBTTagCompound var1) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
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
