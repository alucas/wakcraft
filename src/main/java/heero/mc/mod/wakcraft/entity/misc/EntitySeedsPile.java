package heero.mc.mod.wakcraft.entity.misc;

import heero.mc.mod.wakcraft.client.renderer.entity.RendererSeedsPile;
import heero.mc.mod.wakcraft.entity.creature.EntityWCreature;
import heero.mc.mod.wakcraft.item.ItemWCreatureSeeds;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EntitySeedsPile extends Entity implements IEntityAdditionalSpawnData {
    protected static final int GROW_DURATION = 300;
    protected static final String TAG_AGE = "age";
    protected static final String TAG_ITEM = "item";

    protected int age;
    protected ItemWCreatureSeeds itemSeeds;

    public EntitySeedsPile(final World world) {
        super(world);
    }

    public EntitySeedsPile(final World world, final BlockPos pos, ItemWCreatureSeeds itemSeeds) {
        super(world);

        this.setSize(1, 1);
        this.setPosition(pos.getX(), pos.getY(), pos.getZ());

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
        if (worldObj.isRemote) {
            return;
        }

        for (String pattern : itemSeeds.patterns.keySet()) {
            if (rand.nextFloat() >= itemSeeds.patterns.get(pattern)) {
                continue;
            }

            Set<UUID> group = new HashSet<>();

            for (Character c : pattern.toCharArray()) {
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

    @SideOnly(Side.CLIENT)
    public static class RenderFactorySeedPile implements IRenderFactory<EntitySeedsPile> {
        @Override
        public Render<? super EntitySeedsPile> createRenderFor(RenderManager manager) {
            return new RendererSeedsPile(manager, Minecraft.getMinecraft().getRenderItem());
        }
    }
}
