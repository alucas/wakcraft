package heero.mc.mod.wakcraft.entity.misc;

import heero.mc.mod.wakcraft.entity.creature.EntityWCreature;
import heero.mc.mod.wakcraft.item.ItemWCreatureSeeds;
import io.netty.buffer.ByteBuf;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntitySeedsPile extends Entity implements IEntityAdditionalSpawnData {
	protected static final int GROW_DURATION = 300;
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

		if (age > GROW_DURATION) {
			spawnGroup();
			setDead();
		}
	}

	protected void spawnGroup() {
		if (!worldObj.isRemote) {
			for (String patern : itemSeeds.paterns.keySet()) {
				if (rand.nextFloat() < itemSeeds.paterns.get(patern)) {
					List<UUID> group = new ArrayList<UUID>();

					for (Character c : patern.toCharArray()) {
						Class<? extends EntityWCreature> creatureClass = itemSeeds.creatures.get(c);

						try {
							EntityWCreature creature = creatureClass.getConstructor(World.class).newInstance(worldObj);
							group.add(creature.getUniqueID());

							creature.setGroup(group);
							creature.setPositionAndUpdate(posX - 3 + rand.nextInt(7), posY, posZ - 3 + rand.nextInt(7));
							worldObj.spawnEntityInWorld(creature);
						} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
							e.printStackTrace();
							break;
						}
					}

					break;
				}
			}
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
