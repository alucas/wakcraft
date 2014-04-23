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
			itemOre1, itemOre2, canoonPowder, clay, waterBucket, driedDung,
			pearl, moonstone, bomb, fossil, shamPearl, verbalasalt, gumgum,
			polishedmoonstone, shadowyBlue;

	public static void registerItems() {
		GameRegistry.registerItem(gobballWool = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GobballWool").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":gobballwool")), "GobballWool");
		GameRegistry.registerItem(gobballSkin = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GobballSkin").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":gobballskin")), "GobballSkin");
		GameRegistry.registerItem(gobballHorn = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GobballHorn").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":gobballhorn")), "GobballHorn");
		GameRegistry.registerItem(woollyKey = (new ClefLaineuse()), "WoollyKey");
		GameRegistry.registerItem(itemOre1 = (new ItemOre1()), "ItemOre1");
		GameRegistry.registerItem(itemOre2 = (new ItemOre2()), "ItemOre2");
		GameRegistry.registerItem(canoonPowder = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("CanoonPowder").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":canoonpowder")), "ItemCanoonPowder");
		GameRegistry.registerItem(clay = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Clay").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":clay")), "ItemClay");
		GameRegistry.registerItem(waterBucket = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("WaterBucket").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":waterbucket")), "ItemWaterBucket");
		GameRegistry.registerItem(driedDung = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("DriedDung").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":drieddung")), "ItemDriedDung");
		GameRegistry.registerItem(pearl = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Pearl").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":pearl")), "ItemPearl");
		GameRegistry.registerItem(moonstone = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Moonstone").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":moonstone")), "ItemMoonstone");
		GameRegistry.registerItem(bomb = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Bomb").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":bomb")), "ItemBomb");
		GameRegistry.registerItem(fossil = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Fossil").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":fossil")), "ItemFossil");
		GameRegistry.registerItem(shamPearl = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("ShamPearl").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":shampearl")), "ItemShamPearl");
		GameRegistry.registerItem(verbalasalt = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("VerbalaSalt").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":verbalasalt")), "ItemVerbalaSalt");
		GameRegistry.registerItem(polishedmoonstone = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("PolishedMoonstone").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":polishedmoonstone")), "ItemPolishedMoonstone");
		GameRegistry.registerItem(shadowyBlue = ((new Item()).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("ShadowyBlue").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":shadowyblue")), "ItemShadowyBlue");
	}
}
