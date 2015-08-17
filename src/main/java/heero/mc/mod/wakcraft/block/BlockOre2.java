package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WItems;

import java.util.Random;

import heero.mc.mod.wakcraft.item.EnumOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;


public class BlockOre2 extends BlockOre {
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortuneLvl) {
		return WItems.itemOre1;
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and
	 * wood.
	 */
    @Override
    public int damageDropped(IBlockState state) {
        return ((EnumOre) state.getValue(PROP_ORE)).ordinal() % 16;
    }
}
