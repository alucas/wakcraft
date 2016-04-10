package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.entity.creature.EntityWCreature;
import heero.mc.mod.wakcraft.entity.misc.EntitySeedsPile;
import heero.mc.mod.wakcraft.profession.ProfessionManager;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import heero.mc.mod.wakcraft.util.ItemInUseUtil;
import heero.mc.mod.wakcraft.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ItemWCreatureSeeds extends ItemWithLevel {
    protected static final int USE_DURATION = 100;

    public Map<Character, Class<? extends EntityWCreature>> creatures;
    public Map<String, Float> patterns;

    public ItemWCreatureSeeds(int level) {
        super(level);

        creatures = new HashMap<>();
        patterns = new HashMap<>();

        setCreativeTab(WCreativeTabs.tabResource);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
                             BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!WorldUtil.isMainWorld(world)) {
            return false;
        }

        if (side != EnumFacing.UP) {
            return false;
        }

        int trapperLevel = ProfessionManager.getLevel(player, PROFESSION.TRAPPER);
        if (trapperLevel < getItemLevel(0)) {
            if (world.isRemote) {
                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("message.seed.insufficientLevel", getItemStackDisplayName(stack), getItemLevel(0))));
            }

            return false;
        }

        for (Object entity : world.loadedEntityList) {
            if (entity instanceof EntitySeedsPile) {
                EntitySeedsPile entitySeeds = (EntitySeedsPile) entity;
                if (entitySeeds.getPosition().equals(pos.up())) {
                    return false;
                }
            }
        }

        ItemInUseUtil.saveCoords(player, pos);
        player.stopUsingItem();
        player.setItemInUse(stack, USE_DURATION);

        return true;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        MovingObjectPosition mop = player.rayTrace(10, 1.0F);
        BlockPos previousPos = ItemInUseUtil.getCoords(player);
        if (mop == null || mop.sideHit != EnumFacing.UP || !mop.getBlockPos().equals(previousPos)) {
            player.stopUsingItem();
            return;
        }

        if (player.motionX != 0 || Math.abs(player.motionY) > 0.1 || player.motionZ != 0) {
            player.stopUsingItem();
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            stack.stackSize--;
        }

        final BlockPos pos = ItemInUseUtil.getCoords(player);
        world.spawnEntityInWorld(new EntitySeedsPile(world, pos.up(), this));

        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return USE_DURATION;
    }

    public <T extends EntityWCreature> ItemWCreatureSeeds addCreature(Character key, Class<T> creature) {
        creatures.put(key, creature);

        return this;
    }

    public ItemWCreatureSeeds addPatern(String patern, Float probability) {
        patterns.put(patern, probability);

        return this;
    }
}