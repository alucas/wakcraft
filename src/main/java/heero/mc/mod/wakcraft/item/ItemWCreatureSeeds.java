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
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ItemWCreatureSeeds extends ItemWithLevel {
    protected static final int USE_DURATION = 100;

    public Map<Character, Class<? extends EntityWCreature>> creatures;
    public Map<String, Float> paterns;

    public ItemWCreatureSeeds(int level) {
        super(level);

        creatures = new HashMap<>();
        paterns = new HashMap<>();

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
        if (trapperLevel < getLevel(0)) {
            if (world.isRemote) {
                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("message.seed.insufficientLevel", new Object[]{getItemStackDisplayName(stack), getLevel(0)})));
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

        ItemInUseUtil.saveCoords(player, pos.up());
        player.setItemInUse(stack, USE_DURATION);

        return true;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            stack.stackSize--;
        }

        if (!world.isRemote) {
            int coords[] = ItemInUseUtil.getCoords(player);
            world.spawnEntityInWorld(new EntitySeedsPile(world, coords[0], coords[1], coords[2], this));
        }

        return stack;
    }


    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (player.motionX != 0 || Math.abs(player.motionY) > 0.1 || player.motionZ != 0) {
            player.stopUsingItem();
        }
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
        paterns.put(patern, probability);

        return this;
    }
}