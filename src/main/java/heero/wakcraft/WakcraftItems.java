package heero.wakcraft;

import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.item.ClefLaineuse;
import heero.wakcraft.item.ItemOre1;
import heero.wakcraft.item.ItemOre2;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class WakcraftItems extends Items {
	public static Item gobballWool, gobballSkin, gobballHorn, woollyKey,
			itemOre1, itemOre2;

	public static void registerItems() {
		GameRegistry.registerItem(gobballWool = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GobballWool").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":gobballwool")), "GobballWool");
		GameRegistry.registerItem(gobballSkin = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GobballSkin").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":gobballskin")), "GobballSkin");
		GameRegistry.registerItem(gobballHorn = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GobballHorn").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":gobballhorn")), "GobballHorn");
		GameRegistry.registerItem(woollyKey = (new ClefLaineuse()), "WoollyKey");
		GameRegistry.registerItem(itemOre1 = (new ItemOre1()), "ItemOre1");
		GameRegistry.registerItem(itemOre2 = (new ItemOre2()), "ItemOre2");
	}
}
