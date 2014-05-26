package heero.mc.mod.wakcraft.creativetab;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.WItems;
import net.minecraft.creativetab.CreativeTabs;
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
			return Item.getItemFromBlock(WBlocks.sufokiaWave);
		}
	};
	public static final CreativeTabs tabSpecialBlock = new CreativeTabs(
			"specialBlocks") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(WBlocks.dragoexpress);
		}
	};
	public static final CreativeTabs tabOreBlock = new CreativeTabs(
			"oreBlocks") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(WBlocks.ore1);
		}
	};
	public static final CreativeTabs tabResource = new CreativeTabs("resources") {
		@SideOnly(Side.CLIENT)
		@Override
		public Item getTabIconItem() {
			return WItems.gobballHorn;
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
