package heero.wakcraft;

import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.item.ItemWoollyKey;
import heero.wakcraft.item.ItemOre1;
import heero.wakcraft.item.ItemOre2;
import heero.wakcraft.item.ItemWithLevel;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class WakcraftItems extends Items {
	public static Item gobballWool, gobballSkin, gobballHorn, woollyKey,
			itemOre1, itemOre2, canoonPowder, clay, waterBucket, driedDung,
			pearl, moonstone, bomb, fossil, shamPearl, verbalasalt, gumgum,
			polishedmoonstone, shadowyBlue, merchantHG, decoHG, craftHG,
			gardenHG, smallikiakit, goldenikiakit, kitikiakit,
			adventurerikiakit, collectorikiakit, emeraldikiakit;

	public static void registerItems() {
		GameRegistry.registerItem(gobballWool = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GobballWool").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":gobballwool")), "GobballWool");
		GameRegistry.registerItem(gobballSkin = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GobballSkin").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":gobballskin")), "GobballSkin");
		GameRegistry.registerItem(gobballHorn = ((new ItemWithLevel(15)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GobballHorn").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":gobballhorn")), "GobballHorn");
		GameRegistry.registerItem(woollyKey = (new ItemWoollyKey()), "WoollyKey");
		GameRegistry.registerItem(itemOre1 = (new ItemOre1()), "ItemOre1");
		GameRegistry.registerItem(itemOre2 = (new ItemOre2()), "ItemOre2");
		GameRegistry.registerItem(canoonPowder = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("CanoonPowder").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":canoonpowder")), "ItemCanoonPowder");
		GameRegistry.registerItem(clay = ((new ItemWithLevel(4)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Clay").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":clay")), "ItemClay");
		GameRegistry.registerItem(waterBucket = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("WaterBucket").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":waterbucket")), "ItemWaterBucket");
		GameRegistry.registerItem(driedDung = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("DriedDung").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":drieddung")), "ItemDriedDung");
		GameRegistry.registerItem(pearl = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Pearl").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":pearl")), "ItemPearl");
		GameRegistry.registerItem(moonstone = ((new ItemWithLevel(20)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Moonstone").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":moonstone")), "ItemMoonstone");
		GameRegistry.registerItem(bomb = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Bomb").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":bomb")), "ItemBomb");
		GameRegistry.registerItem(fossil = ((new ItemWithLevel(5)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Fossil").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":fossil")), "ItemFossil");
		GameRegistry.registerItem(shamPearl = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("ShamPearl").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":shampearl")), "ItemShamPearl");
		GameRegistry.registerItem(verbalasalt = ((new ItemWithLevel(15)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("VerbalaSalt").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":verbalasalt")), "ItemVerbalaSalt");
		GameRegistry.registerItem(gumgum= ((new ItemWithLevel(15)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GumGum").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":gumgum")), "ItemGumGum");
		GameRegistry.registerItem(polishedmoonstone = ((new ItemWithLevel(20)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("PolishedMoonstone").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":polishedmoonstone")), "ItemPolishedMoonstone");
		GameRegistry.registerItem(shadowyBlue = ((new ItemWithLevel(25)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("ShadowyBlue").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":shadowyblue")), "ItemShadowyBlue");
		GameRegistry.registerItem(merchantHG = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("MerchantHG").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":merchanthg").setMaxStackSize(1)), "ItemMerchantHG");
		GameRegistry.registerItem(decoHG = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("DecoHG").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":decohg").setMaxStackSize(1)), "ItemDecoHG");
		GameRegistry.registerItem(craftHG = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("CraftHG").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":crafthg").setMaxStackSize(1)), "ItemCraftHG");
		GameRegistry.registerItem(gardenHG = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GardenHG").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":gardenhg").setMaxStackSize(1)), "ItemGardenHG");
		GameRegistry.registerItem(smallikiakit = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("SmallIkiakit").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":smallikiakit").setMaxStackSize(1)), "ItemSmallIkiakit");
		GameRegistry.registerItem(goldenikiakit = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GoldenIkiakit").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":goldenikiakit").setMaxStackSize(1)), "ItemGoldenIkiakit");
		GameRegistry.registerItem(kitikiakit = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("KitIkiakit").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":kitikiakit").setMaxStackSize(1)), "ItemKitIkiakit");
		GameRegistry.registerItem(adventurerikiakit = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("AdventurerIkiakit").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":adventurerikiakit").setMaxStackSize(1)), "ItemAdventurerIkiakit");
		GameRegistry.registerItem(collectorikiakit = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("CollectorIkiakit").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":collectorikiakit").setMaxStackSize(1)), "ItemCollectorIkiakit");
		GameRegistry.registerItem(emeraldikiakit = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("EmeraldIkiakit").setTextureName(WakcraftInfo.MODID.toLowerCase() + ":emeraldikiakit").setMaxStackSize(1)), "ItemEmeraldIkiakit");
	}
}
