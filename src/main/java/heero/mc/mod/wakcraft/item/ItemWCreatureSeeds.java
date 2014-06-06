package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.mc.mod.wakcraft.entity.misc.EntitySeedsPile;
import heero.mc.mod.wakcraft.helper.ItemInUseHelper;
import heero.mc.mod.wakcraft.manager.ProfessionManager;
import heero.mc.mod.wakcraft.manager.ProfessionManager.PROFESSION;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemWCreatureSeeds extends ItemWithLevel {
	protected static final int USE_DURATION = 100;

	public ItemWCreatureSeeds(int level, String name, String textureName) {
		super(level);

		setCreativeTab(WakcraftCreativeTabs.tabResource);
		setUnlocalizedName(name);
		setTextureName(WInfo.MODID.toLowerCase() + ":" + textureName);
	}

	@Override
	public boolean onItemUse(ItemStack stack,
			EntityPlayer player, World world, int x, int y,
			int z, int side, float hitX, float hitY, float hitZ) {
		if (world.provider.dimensionId != 0) {
			return false;
		}

		if (side != EnumFacing.UP.ordinal()) {
			return false;
		}

		int trapperLevel = ProfessionManager.getLevel(player, PROFESSION.TRAPPER);
		if (trapperLevel < getLevel(0)) {
			if (world.isRemote) {
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("message.seed.insufficientLevel", new Object[]{getItemStackDisplayName(stack), getLevel(0)})));
			}

			return false;
		}

		for (Object entity : world.getLoadedEntityList()) {
			if (entity instanceof EntitySeedsPile) {
				EntitySeedsPile entitySeeds = (EntitySeedsPile) entity;
				if (entitySeeds.posX == x && entitySeeds.posY == y + 1 && entitySeeds.posZ == z) {
					return false;
				}
			}
		}

		ItemInUseHelper.saveCoords(player, x, y + 1, z);
		player.setItemInUse(stack, USE_DURATION);

		return true;
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			stack.stackSize--;
		}

		if (!world.isRemote) {
			int coords[] = ItemInUseHelper.getCoords(player);
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
}