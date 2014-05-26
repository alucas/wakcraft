package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WItems;

import java.util.Random;

import net.minecraft.item.Item;


public class BlockOre2 extends BlockOre {
	public BlockOre2() {
		super();
		
		colors = new float[][]{{0.63F, 0.66F, 0.70F}, {0.63F, 0.66F, 0.70F}, {0.92F, 0.95F, 0.94F}, {0.2F, 0.2F, 0.2F}, {0.93F, 0.78F, 0.27F}, {0.55F, 0.65F, 0.65F}, {0.88F, 0.8F, 0.56F}, {0.88F, 0.8F, 0.56F}};
		levels = new int[] { 40, 45, 50, 55, 60, 65, 70, 70 };
	}

	@Override
	public Item getItemDropped(int metadata, Random random, int fortuneLvl) {
		return WItems.itemOre1;
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and
	 * wood.
	 */
	@Override
	public int damageDropped(int metadata) {
		return metadata >> 1 + 8;
	}
}
