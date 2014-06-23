package heero.mc.mod.wakcraft.spell;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Spell extends Item {
	public Spell(String name) {
		setCreativeTab(WakcraftCreativeTabs.tabSpells);
		setUnlocalizedName("Spell" + name);
		setTextureName(WInfo.MODID.toLowerCase() + ":spells/" + name.toLowerCase());
	}

	/**
	 * This is called when the item is used, before the block is activated.
	 * 
	 * @param stack		The Item Stack
	 * @param player	The Player that used the item
	 * @param world		The Current World
	 * @param x			Target X Position
	 * @param y			Target Y Position
	 * @param z			Target Z Position
	 * @param side		The side of the target hit
	 * @return Return	true to prevent any further processing.
	 */
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
}