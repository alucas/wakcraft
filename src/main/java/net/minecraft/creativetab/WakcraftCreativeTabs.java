package net.minecraft.creativetab;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WakcraftCreativeTabs {

	public static final CreativeTabs tabBlock = new CreativeTabs(
			"buildingBlocks") {
		@SideOnly(Side.CLIENT)
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(Blocks.brick_block);
		}
	};
	public static final CreativeTabs tabMisc = new CreativeTabs("misc") {
		@SideOnly(Side.CLIENT)
		@Override
		public Item getTabIconItem() {
			return Items.lava_bucket;
		}
	};
	public static final CreativeTabs tabCombat = new CreativeTabs("combat") {
		@SideOnly(Side.CLIENT)
		@Override
		public Item getTabIconItem() {
			return Items.golden_sword;
		}
	};
}
