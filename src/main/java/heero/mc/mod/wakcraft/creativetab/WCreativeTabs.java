package heero.mc.mod.wakcraft.creativetab;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.WItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WCreativeTabs {

    public static final CreativeTabs tabBlock = new CreativeTabs("building_blocks") {
        @SideOnly(Side.CLIENT)
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(WBlocks.sufokiaSunSlab);
        }
    };

    public static final CreativeTabs tabSpecialBlock = new CreativeTabs("special_blocks") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Item.getItemFromBlock(WBlocks.classConsole);
        }
    };

    public static final CreativeTabs tabOreBlock = new CreativeTabs("ore_blocks") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Item.getItemFromBlock(WBlocks.vein1);
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
            return WItems.gobballHeadgear;
        }
    };

    public static final CreativeTabs tabSpells = new CreativeTabs("spells") {
        @SideOnly(Side.CLIENT)
        @Override
        public Item getTabIconItem() {
            return WItems.spellCelestialSword;
        }
    };

    public static final CreativeTabs tabOther = new CreativeTabs("other") {
        @SideOnly(Side.CLIENT)
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.cobblestone);
        }
    };
}
