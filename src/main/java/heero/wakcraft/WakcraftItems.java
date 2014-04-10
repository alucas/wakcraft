package heero.wakcraft;

import cpw.mods.fml.common.registry.GameRegistry;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.entity.item.ClefLaineuse;
import heero.wakcraft.reference.References;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class WakcraftItems extends Items {
	public final static Item gobballWool = (Item) Item.itemRegistry.getObject(References.MODID.toLowerCase() + ":GobballWool");
	public final static Item gobballSkin = (Item) Item.itemRegistry.getObject(References.MODID.toLowerCase() + ":GobballSkin");
	public final static Item gobballHorn = (Item) Item.itemRegistry.getObject(References.MODID.toLowerCase() + ":GobballHorn");
	public final static Item woollyKey = (Item) Item.itemRegistry.getObject(References.MODID.toLowerCase() + ":WoollyKey");
	
	public static void registerItems() {
		GameRegistry.registerItem((new Item()).setCreativeTab(WakcraftCreativeTabs.tabMisc).setUnlocalizedName("GobballWool").setTextureName(References.MODID.toLowerCase() + ":gobballwool"), "GobballWool");
		GameRegistry.registerItem((new Item()).setCreativeTab(WakcraftCreativeTabs.tabMisc).setUnlocalizedName("GobballSkin").setTextureName(References.MODID.toLowerCase() + ":gobballskin"), "GobballSkin");
		GameRegistry.registerItem((new Item()).setCreativeTab(WakcraftCreativeTabs.tabMisc).setUnlocalizedName("GobballHorn").setTextureName(References.MODID.toLowerCase() + ":gobballhorn"), "GobballHorn");
		GameRegistry.registerItem(new ClefLaineuse(), "WoollyKey");
	}
}
