package heero.mc.mod.wakcraft;

import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.mc.mod.wakcraft.entity.creature.gobball.BlackGobbly;
import heero.mc.mod.wakcraft.entity.creature.gobball.Gobball;
import heero.mc.mod.wakcraft.entity.creature.gobball.GobballWC;
import heero.mc.mod.wakcraft.entity.creature.gobball.Gobbette;
import heero.mc.mod.wakcraft.entity.creature.gobball.WhiteGobbly;
import heero.mc.mod.wakcraft.item.ItemBlockYRotation;
import heero.mc.mod.wakcraft.item.ItemIkiakit;
import heero.mc.mod.wakcraft.item.ItemOre1;
import heero.mc.mod.wakcraft.item.ItemOre2;
import heero.mc.mod.wakcraft.item.ItemWArmor;
import heero.mc.mod.wakcraft.item.ItemWArmor.TYPE;
import heero.mc.mod.wakcraft.item.ItemWCreatureSeeds;
import heero.mc.mod.wakcraft.item.ItemWithLevel;
import heero.mc.mod.wakcraft.item.ItemWoollyKey;
import heero.mc.mod.wakcraft.spell.ActiveSpecialitySpell;
import heero.mc.mod.wakcraft.spell.ElementalSpell;
import heero.mc.mod.wakcraft.spell.PassiveSpecialitySpell;
import heero.mc.mod.wakcraft.spell.RangeMode;
import heero.mc.mod.wakcraft.spell.effect.EffectArea;
import heero.mc.mod.wakcraft.spell.effect.EffectCharacteristic;
import heero.mc.mod.wakcraft.spell.effect.EffectDamage;
import heero.mc.mod.wakcraft.spell.effect.EffectElement;
import heero.mc.mod.wakcraft.spell.effect.EffectMovement;
import heero.mc.mod.wakcraft.spell.effect.EffectState;
import heero.mc.mod.wakcraft.spell.state.State;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class WItems extends Items {
	public static Item gobballWool, gobballSkin, gobballHorn, woollyKey,
			itemOre1, itemOre2, canoonPowder, clay, waterBucket, driedDung,
			pearl, moonstone, bomb, fossil, shamPearl, verbalasalt, gumgum,
			polishedmoonstone, shadowyBlue, merchantHG, decoHG, craftHG,
			gardenHG, gobballBreastplate, gobboots, gobballEpaulettes,
			gobballCape, gobballBelt, gobballHeadgear, gobballAmulet,
			bouzeLiteYeahsRing, gobballSeed,
			// Tofu Resources
			tofuFeather, tofuBlood,
			// Tofu Armors
			helmetofu,tofuBreastplate,tofuCloak,tofuEpaulettes,tofuBelt,tofuAmulet,tofuRing,tofuBoots;

	// ItemBlock
	public static Item sufokiaWave1, sufokiaWave2, sufokiaWave3,
			ground1, ground2, carpet1, wood1;

	// Ikiakits
	public static ItemIkiakit ikiakitSmall, ikiakitAdventurer, ikiakitKit,
			ikiakitCollector, ikiakitGolden, ikiakitEmerald;

	// Iop spells
	public static ElementalSpell spellShaker, spellRocknoceros, spellImpact,
			spellCharge, spellDevastate, spellThunderbolt, spellJudgment,
			spellSuperIopPunch, spellCelestialSword, spellIopsWrath, spellJabs,
			spellFlurry, spellIntimidation, spellGuttingGust, spellUppercut;

	public static ActiveSpecialitySpell spellJump, spellDefensiveStance, spellFlatten,
			spellBraveryStandard, spellIncrease;
	public static PassiveSpecialitySpell spellVirility, spellCompulsion, spellAuthority,
			spellShowOff, spellLockingPro;

	public static void registerItems() {
		String modid = WInfo.MODID.toLowerCase() + ":";

		GameRegistry.registerItem(gobballWool = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GobballWool").setTextureName(modid + "gobballwool")), "GobballWool");
		GameRegistry.registerItem(gobballSkin = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GobballSkin").setTextureName(modid + "gobballskin")), "GobballSkin");
		GameRegistry.registerItem(gobballHorn = ((new ItemWithLevel(15)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GobballHorn").setTextureName(modid + "gobballhorn")), "GobballHorn");
		GameRegistry.registerItem(woollyKey = (new ItemWoollyKey()), "WoollyKey");
		
		GameRegistry.registerItem(tofuFeather = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("TofuFeather").setTextureName(modid + "tofufeather")), "TofuFeather");
		GameRegistry.registerItem(tofuBlood = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("TofuBlood").setTextureName(modid + "tofublood")), "TofuBlood");
		
		GameRegistry.registerItem(itemOre1 = (new ItemOre1()), "ItemOre1");
		GameRegistry.registerItem(itemOre2 = (new ItemOre2()), "ItemOre2");
		GameRegistry.registerItem(canoonPowder = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("CanoonPowder").setTextureName(modid + "canoonpowder")), "ItemCanoonPowder");
		GameRegistry.registerItem(clay = ((new ItemWithLevel(4)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Clay").setTextureName(modid + "clay")), "ItemClay");
		GameRegistry.registerItem(waterBucket = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("WaterBucket").setTextureName(modid + "waterbucket")), "ItemWaterBucket");
		GameRegistry.registerItem(driedDung = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("DriedDung").setTextureName(modid + "drieddung")), "ItemDriedDung");
		GameRegistry.registerItem(pearl = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Pearl").setTextureName(modid + "pearl")), "ItemPearl");
		GameRegistry.registerItem(moonstone = ((new ItemWithLevel(20)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Moonstone").setTextureName(modid + "moonstone")), "ItemMoonstone");
		GameRegistry.registerItem(bomb = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Bomb").setTextureName(modid + "bomb")), "ItemBomb");
		GameRegistry.registerItem(fossil = ((new ItemWithLevel(5)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("Fossil").setTextureName(modid + "fossil")), "ItemFossil");
		GameRegistry.registerItem(shamPearl = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("ShamPearl").setTextureName(modid + "shampearl")), "ItemShamPearl");
		GameRegistry.registerItem(verbalasalt = ((new ItemWithLevel(15)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("VerbalaSalt").setTextureName(modid + "verbalasalt")), "ItemVerbalaSalt");
		GameRegistry.registerItem(gumgum= ((new ItemWithLevel(15)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GumGum").setTextureName(modid + "gumgum")), "ItemGumGum");
		GameRegistry.registerItem(polishedmoonstone = ((new ItemWithLevel(20)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("PolishedMoonstone").setTextureName(modid + "polishedmoonstone")), "ItemPolishedMoonstone");
		GameRegistry.registerItem(shadowyBlue = ((new ItemWithLevel(25)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("ShadowyBlue").setTextureName(modid + "shadowyblue")), "ItemShadowyBlue");
		GameRegistry.registerItem(merchantHG = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("MerchantHG").setTextureName(modid + "merchanthg").setMaxStackSize(1)), "ItemMerchantHG");
		GameRegistry.registerItem(decoHG = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("DecoHG").setTextureName(modid + "decohg").setMaxStackSize(1)), "ItemDecoHG");
		GameRegistry.registerItem(craftHG = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("CraftHG").setTextureName(modid + "crafthg").setMaxStackSize(1)), "ItemCraftHG");
		GameRegistry.registerItem(gardenHG = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName("GardenHG").setTextureName(modid + "gardenhg").setMaxStackSize(1)), "ItemGardenHG");
		GameRegistry.registerItem(ikiakitSmall = new ItemIkiakit("SmallIkiakit"), "ItemSmallIkiakit");
		GameRegistry.registerItem(ikiakitGolden = new ItemIkiakit("GoldenIkiakit"), "ItemGoldenIkiakit");
		GameRegistry.registerItem(ikiakitKit = new ItemIkiakit("KitIkiakit"), "ItemKitIkiakit");
		GameRegistry.registerItem(ikiakitAdventurer = new ItemIkiakit("AdventurerIkiakit"), "ItemAdventurerIkiakit");
		GameRegistry.registerItem(ikiakitCollector = new ItemIkiakit("CollectorIkiakit"), "ItemCollectorIkiakit");
		GameRegistry.registerItem(ikiakitEmerald = new ItemIkiakit("EmeraldIkiakit"), "ItemEmeraldIkiakit");
		GameRegistry.registerItem(gobballSeed = new ItemWCreatureSeeds(0, "GobballSeed", "gobballseed").addCreature('G', Gobball.class).addCreature('B', BlackGobbly.class).addCreature('W', WhiteGobbly.class).addCreature('E', Gobbette.class).addCreature('C', GobballWC.class).addPatern("EWB", 0.5F), "ItemGobballSeed");

		// ItemBlock
		GameRegistry.registerItem(sufokiaWave1 = new ItemBlockYRotation(WBlocks.sufokiaWave1NorthSlab, WBlocks.sufokiaWave1EastSlab, WBlocks.sufokiaWave1SouthSlab, WBlocks.sufokiaWave1WestSlab).setCreativeTab(WakcraftCreativeTabs.tabBlock), "ItemSufokiaWave1");
		GameRegistry.registerItem(sufokiaWave2 = new ItemBlockYRotation(WBlocks.sufokiaWave2NorthSlab, WBlocks.sufokiaWave2EastSlab, WBlocks.sufokiaWave2SouthSlab, WBlocks.sufokiaWave2WestSlab).setCreativeTab(WakcraftCreativeTabs.tabBlock), "ItemSufokiaWave2");
		GameRegistry.registerItem(sufokiaWave3 = new ItemBlockYRotation(WBlocks.sufokiaWave3NorthSlab, WBlocks.sufokiaWave3EastSlab, WBlocks.sufokiaWave3SouthSlab, WBlocks.sufokiaWave3WestSlab).setCreativeTab(WakcraftCreativeTabs.tabBlock), "ItemSufokiaWave3");
		GameRegistry.registerItem(ground1 = new ItemBlockYRotation(WBlocks.ground1Slab, WBlocks.ground6Slab, WBlocks.ground5Slab, WBlocks.ground7Slab).setCreativeTab(WakcraftCreativeTabs.tabBlock), "ItemGround1");
		GameRegistry.registerItem(ground2 = new ItemBlockYRotation(WBlocks.ground2Slab, WBlocks.ground9Slab, WBlocks.ground8Slab, WBlocks.ground10Slab).setCreativeTab(WakcraftCreativeTabs.tabBlock), "ItemGround2");
		GameRegistry.registerItem(carpet1 = new ItemBlockYRotation(WBlocks.carpet1NorthSlab, WBlocks.carpet1EastSlab, WBlocks.carpet1SouthSlab, WBlocks.carpet1WestSlab).setCreativeTab(WakcraftCreativeTabs.tabBlock), "ItemCarpet1");
		GameRegistry.registerItem(wood1 = new ItemBlockYRotation(WBlocks.wood1NorthSlab, WBlocks.wood1EastSlab, WBlocks.wood1SouthSlab, WBlocks.wood1WestSlab).setCreativeTab(WakcraftCreativeTabs.tabBlock), "ItemWood1");

		// ARMORS
		
		// Goball Armors
		GameRegistry.registerItem(gobballBreastplate = (new ItemWArmor(TYPE.CHESTPLATE, 15).setCharacteristic(Characteristic.HEALTH, 15).setCharacteristic(Characteristic.INITIATIVE, 5).setCharacteristic(Characteristic.FIRE_ATT, 3).setCharacteristic(Characteristic.EARTH_ATT, 3).setUnlocalizedName("GobballBreastplate").setTextureName(modid + "gobball_breastplate")), "ItemGobballBreastplate");
		GameRegistry.registerItem(gobboots = (new ItemWArmor(TYPE.BOOTS, 15).setCharacteristic(Characteristic.HEALTH, 10).setCharacteristic(Characteristic.LOCK, 8).setCharacteristic(Characteristic.INITIATIVE, 6).setUnlocalizedName("Gobboots").setTextureName(modid + "gobboots")), "ItemGobboots");
		GameRegistry.registerItem(gobballEpaulettes = (new ItemWArmor(TYPE.EPAULET, 14).setCharacteristic(Characteristic.LOCK, 5).setCharacteristic(Characteristic.FIRE_ATT, 2).setCharacteristic(Characteristic.EARTH_ATT, 2).setUnlocalizedName("GobballEpaulettes").setTextureName(modid + "gobball_epaulettes")), "ItemGobballEpaulettes");
		GameRegistry.registerItem(gobballCape = (new ItemWArmor(TYPE.CAPE, 14).setCharacteristic(Characteristic.HEALTH, 12).setCharacteristic(Characteristic.LOCK, 8).setCharacteristic(Characteristic.FIRE_ATT, 2).setCharacteristic(Characteristic.EARTH_ATT, 2).setUnlocalizedName("GobballCape").setTextureName(modid + "gobball_cape")), "ItemGobballCape");
		GameRegistry.registerItem(gobballBelt = (new ItemWArmor(TYPE.BELT, 13).setCharacteristic(Characteristic.FIRE_ATT, 3).setCharacteristic(Characteristic.EARTH_ATT, 3).setUnlocalizedName("GobballBelt").setTextureName(modid + "gobball_belt")), "ItemGobballBelt");
		GameRegistry.registerItem(gobballHeadgear = (new ItemWArmor(TYPE.HELMET, 13).setCharacteristic(Characteristic.HEALTH, 13).setCharacteristic(Characteristic.LOCK, 5).setCharacteristic(Characteristic.FIRE_ATT, 3).setCharacteristic(Characteristic.EARTH_ATT, 3).setUnlocalizedName("GobballHeadgear").setTextureName(modid + "gobball_headgear")), "ItemGobballHeadgear");
		GameRegistry.registerItem(gobballAmulet = (new ItemWArmor(TYPE.AMULET, 12).setCharacteristic(Characteristic.HEALTH, 7).setCharacteristic(Characteristic.LOCK, 5).setUnlocalizedName("GobballAmulet").setTextureName(modid + "gobball_amulet")), "ItemGobballAmulet");
		GameRegistry.registerItem(bouzeLiteYeahsRing = (new ItemWArmor(TYPE.RING, 12).setCharacteristic(Characteristic.HEALTH, 6).setCharacteristic(Characteristic.INITIATIVE, 4).setUnlocalizedName("BouzeLiteYeahsRing").setTextureName(modid + "bouze_lite_yeahs_ring")), "ItemBouzeLiteYeahsRing");
		
		// Tofu Armors
		GameRegistry.registerItem(helmetofu = (new ItemWArmor(TYPE.HELMET, 13).setCharacteristic(Characteristic.HEALTH, 9).setCharacteristic(Characteristic.INITIATIVE, 3).setCharacteristic(Characteristic.WATER_ATT, 4).setCharacteristic(Characteristic.AIR_ATT, 4).setUnlocalizedName("Helmetofu").setTextureName(modid + "helmetofu")), "ItemHelmetofu");
		GameRegistry.registerItem(tofuBreastplate = (new ItemWArmor(TYPE.CHESTPLATE, 15).setCharacteristic(Characteristic.HEALTH, 13).setCharacteristic(Characteristic.INITIATIVE, 6).setCharacteristic(Characteristic.WATER_ATT, 3).setCharacteristic(Characteristic.AIR_ATT, 3).setUnlocalizedName("TofuBreastplate").setTextureName(modid + "tofu_breastplate")), "ItemTofuBreastplate");
		GameRegistry.registerItem(tofuCloak = (new ItemWArmor(TYPE.CAPE, 14).setCharacteristic(Characteristic.HEALTH, 12).setCharacteristic(Characteristic.DODGE, 4).setCharacteristic(Characteristic.WATER_ATT, 3).setCharacteristic(Characteristic.AIR_ATT, 3).setUnlocalizedName("TofuCloak").setTextureName(modid + "tofu_cloak")), "ItemTofuCloak");
		GameRegistry.registerItem(tofuEpaulettes = (new ItemWArmor(TYPE.EPAULET, 14).setCharacteristic(Characteristic.DODGE, 5).setCharacteristic(Characteristic.WATER_ATT, 2).setCharacteristic(Characteristic.AIR_ATT, 2).setUnlocalizedName("TofuEpaulettes").setTextureName(modid + "tofu_epaulettes")), "ItemTofuEpaulettes");
		GameRegistry.registerItem(tofuBelt = (new ItemWArmor(TYPE.BELT, 13).setCharacteristic(Characteristic.WATER_ATT, 3).setCharacteristic(Characteristic.AIR_ATT, 3).setUnlocalizedName("TofuBelt").setTextureName(modid + "tofu_belt")), "ItemTofuBelt");
		GameRegistry.registerItem(tofuAmulet = (new ItemWArmor(TYPE.AMULET, 12).setCharacteristic(Characteristic.HEALTH, 7).setCharacteristic(Characteristic.DODGE, 5).setUnlocalizedName("TofuAmulet").setTextureName(modid + "tofu_amulet")), "ItemTofuAmulet");
		GameRegistry.registerItem(tofuRing = (new ItemWArmor(TYPE.RING, 12).setCharacteristic(Characteristic.HEALTH, 8).setCharacteristic(Characteristic.INITIATIVE, 3).setUnlocalizedName("TofuRing").setTextureName(modid + "tofu_ring")), "ItemTofuRing");
		GameRegistry.registerItem(tofuBoots = (new ItemWArmor(TYPE.BOOTS, 15).setCharacteristic(Characteristic.HEALTH, 8).setCharacteristic(Characteristic.DODGE, 7).setCharacteristic(Characteristic.INITIATIVE, 8).setUnlocalizedName("TofuBoots").setTextureName(modid + "tofu_boots")), "ItemTofuBoots");
		
		// SPELLS
		// Iop spells
		GameRegistry.registerItem(spellShaker = (new ElementalSpell("Shaker", 4, 0, 0).setRange(1, 1, true, false, RangeMode.LINE).setEffect(new EffectDamage(4, 134, EffectElement.EARTH)).setEffectCritical(new EffectDamage(7, 199, EffectElement.EARTH))), "SpellShaker");
		GameRegistry.registerItem(spellRocknoceros = (new ElementalSpell("Rocknoceros", 5, 0, 0).setRange(2, 3, false, false, RangeMode.DEFAULT).setEffect(new EffectDamage(4, 126, EffectElement.EARTH)).setEffect(new EffectState(State.STUNNED, 1, 0, 0.1F, 0)).setEffectCritical(new EffectDamage(6, 190, EffectElement.EARTH)).setEffectCritical(new EffectState(State.STUNNED, 1, 0, 0.15F, 0))), "SpellRocknoceros");
		GameRegistry.registerItem(spellImpact = (new ElementalSpell("Impact", 2, 0, 0, EffectArea.CROSS).setRange(2, 3, true, false, RangeMode.LINE).setEffect(new EffectDamage(2, 50, EffectElement.EARTH, EffectArea.CROSS)).setEffect(new EffectCharacteristic(Characteristic.MOVEMENT, -1, 0, 0.1F, 0, EffectArea.CROSS)).setEffectCritical(new EffectDamage(3, 75, EffectElement.EARTH, EffectArea.CROSS)).setEffectCritical(new EffectCharacteristic(Characteristic.MOVEMENT, -1, 0, 0.15F, 0, EffectArea.CROSS))), "SpellImpact");
		GameRegistry.registerItem(spellCharge = (new ElementalSpell("Charge", 4, 1, 0).setRange(1, 3, true, false, RangeMode.LINE).setEffect(new EffectMovement(2, 0)).setEffect(new EffectDamage(3, 103, EffectElement.EARTH)).setEffect(new EffectState(State.STUNNED, 1, 0, 0.10F, 0)).setEffectCritical(new EffectMovement(2, 0)).setEffectCritical(new EffectDamage(5, 151, EffectElement.EARTH)).setEffectCritical(new EffectState(State.STUNNED, 1, 0, 0.15F, 0))), "SpellCharge");
		GameRegistry.registerItem(spellDevastate = (new ElementalSpell("Devastate", 5, 1, 0, EffectArea.AROUND).setEffect(new EffectDamage(7, 143, EffectElement.EARTH, EffectArea.AROUND)).setEffect(new EffectCharacteristic(Characteristic.MOVEMENT, -1, 0, 0.3F, 0, EffectArea.AROUND)).setEffectCritical(new EffectDamage(10, 214, EffectElement.EARTH, EffectArea.AROUND)).setEffectCritical(new EffectCharacteristic(Characteristic.MOVEMENT, -1, 0, 0.45F, 0, EffectArea.AROUND))), "SpellDevastate");
		GameRegistry.registerItem(spellThunderbolt = (new ElementalSpell("Thunderbolt", 3, 0, 0).setRange(1, 1).setEffect(new EffectDamage(3, 85, EffectElement.FIRE)).setEffect(new EffectState(State.SCALDED, 1, 38 / 200.0F)).setEffectCritical(new EffectDamage(5, 127, EffectElement.FIRE)).setEffectCritical(new EffectState(State.SCALDED, 2, 56 / 200.0F))), "SpellThunderbolt");
		GameRegistry.registerItem(spellJudgment = (new ElementalSpell("Judgment", 4, 1, 0, EffectArea.LINE_V_3).setRange(1, 1).setEffect(new EffectDamage(4, 136, EffectElement.FIRE, EffectArea.LINE_V_3)).setEffect(new EffectState(State.DISORIENTED, 1, 0, 0.01F, 0.99F / 200.0F)).setEffectCritical(new EffectDamage(6, 204, EffectElement.FIRE, EffectArea.LINE_V_3)).setEffectCritical(new EffectState(State.DISORIENTED, 1, 0, 0.01F, 1.50F / 200.0F))), "SpellJudgment");
		GameRegistry.registerItem(spellSuperIopPunch = (new ElementalSpell("SuperIopPunch", 5, 0, 0, EffectArea.CROSS).setRange(2, 3, true, false, RangeMode.LINE).setEffect(new EffectMovement(2, 0)).setEffect(new EffectDamage(5, 145, EffectElement.FIRE, EffectArea.CROSS)).setEffect(new EffectState(State.EXPLOSION, 0, 100 / 200.0F, 0.3F, 0)).setEffectCritical(new EffectMovement(2, 0)).setEffectCritical(new EffectDamage(8, 217, EffectElement.FIRE, EffectArea.CROSS)).setEffectCritical(new EffectState(State.EXPLOSION, 0, 100 / 200.0F, 0.45F, 0))), "SpellSuperIopPunch");
		GameRegistry.registerItem(spellCelestialSword = (new ElementalSpell("CelestialSword", 3, 0, 0, EffectArea.CROSS).setEffect(new EffectDamage(2, 70, EffectElement.FIRE, EffectArea.CROSS)).setEffect(new EffectState(State.FLAMING)).setEffectCritical(new EffectDamage(4, 102, EffectElement.FIRE, EffectArea.CROSS)).setEffectCritical(new EffectState(State.FLAMING))), "SpellCelestialSword");
		GameRegistry.registerItem(spellIopsWrath = (new ElementalSpell("IopsWrath", 6, 0, 1, EffectArea.AREA_2).setEffect(new EffectDamage(8, 172, EffectElement.FIRE, EffectArea.AREA_2)).setEffect(new EffectState(State.EXPLOSION, 0, 100 / 200.0F, 0.66F, 0)).setEffectCritical(new EffectDamage(12, 258, EffectElement.FIRE, EffectArea.AREA_2)).setEffectCritical(new EffectState(State.EXPLOSION, 0, 100 / 200.0F))), "SpellIopsWrath");
		GameRegistry.registerItem(spellJabs = (new ElementalSpell("Jabs", 2, 0, 0)), "SpellJabs");
		GameRegistry.registerItem(spellFlurry = (new ElementalSpell("Flurry", 1, 0, 0).setEffect(new EffectState(State.AERIAL, 1, 0)).setEffectCritical(new EffectState(State.AERIAL, 2, 0))), "SpellFlurry");
		GameRegistry.registerItem(spellIntimidation = (new ElementalSpell("Intimidation", 3, 0, 0)), "SpellIntimidation");
		GameRegistry.registerItem(spellGuttingGust = (new ElementalSpell("GuttingGust", 0, 1, 0)), "SpellGuttingGust");
		GameRegistry.registerItem(spellUppercut = (new ElementalSpell("Uppercut", 0, 0, 1)), "SpellUppercut");
		GameRegistry.registerItem(spellJump = (new ActiveSpecialitySpell("Jump")), "SpellJump");
		GameRegistry.registerItem(spellDefensiveStance = (new ActiveSpecialitySpell("DefensiveStance")), "SpellDefensiveStance");
		GameRegistry.registerItem(spellFlatten = (new ActiveSpecialitySpell("Flatten")), "SpellFlatten");
		GameRegistry.registerItem(spellBraveryStandard = (new ActiveSpecialitySpell("BraveryStandard")), "SpellBraveryStandard");
		GameRegistry.registerItem(spellIncrease = (new ActiveSpecialitySpell("Increase")), "SpellIncrease");
		GameRegistry.registerItem(spellVirility = (new PassiveSpecialitySpell("Virility")), "SpellVirility");
		GameRegistry.registerItem(spellCompulsion = (new PassiveSpecialitySpell("Compulsion")), "SpellCompulsion");
		GameRegistry.registerItem(spellAuthority = (new PassiveSpecialitySpell("Authority")), "SpellAuthority");
		GameRegistry.registerItem(spellShowOff = (new PassiveSpecialitySpell("ShowOff")), "SpellShowOff");
		GameRegistry.registerItem(spellLockingPro = (new PassiveSpecialitySpell("LockingPro")), "SpellLockingPro");
	}
}
