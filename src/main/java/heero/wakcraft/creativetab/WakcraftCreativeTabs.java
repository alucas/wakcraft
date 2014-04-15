package heero.wakcraft.creativetab;

import heero.wakcraft.WakcraftBlocks;
import heero.wakcraft.WakcraftItems;
import net.minecraft.creativetab.CreativeTabs;
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
	public static final CreativeTabs tabSpecialBlock = new CreativeTabs(
			"specialBlocks") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(WakcraftBlocks.dragoexpress);
		}
	};
	public static final CreativeTabs tabOreBlock = new CreativeTabs(
			"oreBlocks") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(WakcraftBlocks.ore1);
		}
	};
	public static final CreativeTabs tabResource = new CreativeTabs("resources") {
		@SideOnly(Side.CLIENT)
		@Override
		public Item getTabIconItem() {
			return WakcraftItems.gobballHorn;
		}
	};
	public static final CreativeTabs tabCombat = new CreativeTabs("combat") {
		@SideOnly(Side.CLIENT)
		@Override
		public Item getTabIconItem() {
			return Items.diamond_sword;
		}
	};
}
