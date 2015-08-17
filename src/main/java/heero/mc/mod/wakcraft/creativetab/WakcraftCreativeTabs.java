package heero.mc.mod.wakcraft.creativetab;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.WItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WakcraftCreativeTabs {

	public static final CreativeTabs tabBlock = new CreativeTabs(
			"buildingBlocks") {
		@SideOnly(Side.CLIENT)
		@Override
//		public Item getTabIconItem() {
//			return Item.getItemFromBlock(WBlocks.sufokiaWave);
//		}
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.dirt);
        }
	};
	public static final CreativeTabs tabSpecialBlock = new CreativeTabs(
			"specialBlocks") {
		@Override
		@SideOnly(Side.CLIENT)
//		public Item getTabIconItem() {
//			return Item.getItemFromBlock(WBlocks.dragoexpress);
//		}
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.dirt);
        }
	};
	public static final CreativeTabs tabOreBlock = new CreativeTabs(
			"oreBlocks") {
		@Override
		@SideOnly(Side.CLIENT)
//		public Item getTabIconItem() {
//			return Item.getItemFromBlock(WBlocks.ore1);
//		}
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.dirt);
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
	public static final CreativeTabs tabSpells = new CreativeTabs("spells") {
		@SideOnly(Side.CLIENT)
		@Override
		public Item getTabIconItem() {
			return Items.diamond_sword;
		}
	};
}
