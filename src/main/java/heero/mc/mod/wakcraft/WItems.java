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
import heero.mc.mod.wakcraft.item.ItemWArmor;
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
import net.minecraftforge.fml.common.registry.GameRegistry;

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
	public static Item sufokiaWave, sufokiaWave2, sufokiaWave3,
            groundSlab, ground2Slab, carpet1, wood;

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
		final String modid_ = Reference.MODID.toLowerCase() + ".";

		GameRegistry.registerItem(gobballWool = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "GobballWool")), "GobballWool");
		GameRegistry.registerItem(gobballSkin = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "GobballSkin")), "GobballSkin");
		GameRegistry.registerItem(gobballHorn = ((new ItemWithLevel(15)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "GobballHorn")), "GobballHorn");
		GameRegistry.registerItem(woollyKey = ((new ItemWoollyKey(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "WoollyKey")), "WoollyKey");

		GameRegistry.registerItem(tofuFeather = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "TofuFeather")), "TofuFeather");
		GameRegistry.registerItem(tofuBlood = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "TofuBlood")), "TofuBlood");

//		GameRegistry.registerItem(itemOre1 = (new ItemOre1()), "ItemOre1");
//		GameRegistry.registerItem(itemOre2 = (new ItemOre2()), "ItemOre2");
		GameRegistry.registerItem(canoonPowder = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "CanoonPowder")), "CanoonPowder");
		GameRegistry.registerItem(clay = ((new ItemWithLevel(4)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "Clay")), "Clay");
		GameRegistry.registerItem(waterBucket = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "WaterBucket")), "WaterBucket");
		GameRegistry.registerItem(driedDung = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "DriedDung")), "DriedDung");
		GameRegistry.registerItem(pearl = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "Pearl")), "Pearl");
		GameRegistry.registerItem(moonstone = ((new ItemWithLevel(20)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "Moonstone")), "Moonstone");
		GameRegistry.registerItem(bomb = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "Bomb")), "Bomb");
		GameRegistry.registerItem(fossil = ((new ItemWithLevel(5)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "Fossil")), "Fossil");
		GameRegistry.registerItem(shamPearl = ((new ItemWithLevel(10)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "ShamPearl")), "ShamPearl");
		GameRegistry.registerItem(verbalasalt = ((new ItemWithLevel(15)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "VerbalaSalt")), "VerbalaSalt");
		GameRegistry.registerItem(gumgum= ((new ItemWithLevel(15)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "GumGum")), "GumGum");
		GameRegistry.registerItem(polishedmoonstone = ((new ItemWithLevel(20)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "PolishedMoonstone")), "PolishedMoonstone");
		GameRegistry.registerItem(shadowyBlue = ((new ItemWithLevel(25)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "ShadowyBlue")), "ShadowyBlue");
		GameRegistry.registerItem(merchantHG = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "MerchantHG").setMaxStackSize(1)), "MerchantHG");
		GameRegistry.registerItem(decoHG = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "DecoHG").setMaxStackSize(1)), "DecoHG");
		GameRegistry.registerItem(craftHG = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "CraftHG").setMaxStackSize(1)), "CraftHG");
		GameRegistry.registerItem(gardenHG = ((new ItemWithLevel(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "GardenHG").setMaxStackSize(1)), "GardenHG");
		GameRegistry.registerItem(ikiakitSmall = (ItemIkiakit) (new ItemIkiakit(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "SmallIkiakit"), "SmallIkiakit");
		GameRegistry.registerItem(ikiakitGolden = (ItemIkiakit) (new ItemIkiakit(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "GoldenIkiakit"), "GoldenIkiakit");
		GameRegistry.registerItem(ikiakitKit = (ItemIkiakit) (new ItemIkiakit(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "KitIkiakit"), "KitIkiakit");
		GameRegistry.registerItem(ikiakitAdventurer = (ItemIkiakit) (new ItemIkiakit(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "AdventurerIkiakit"), "AdventurerIkiakit");
		GameRegistry.registerItem(ikiakitCollector = (ItemIkiakit) (new ItemIkiakit(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "CollectorIkiakit"), "CollectorIkiakit");
		GameRegistry.registerItem(ikiakitEmerald = (ItemIkiakit) (new ItemIkiakit(1)).setCreativeTab(WakcraftCreativeTabs.tabResource).setUnlocalizedName(modid_ + "EmeraldIkiakit"), "EmeraldIkiakit");
		GameRegistry.registerItem(gobballSeed = new ItemWCreatureSeeds(0, "GobballSeed", "gobballseed").addCreature('G', Gobball.class).addCreature('B', BlackGobbly.class).addCreature('W', WhiteGobbly.class).addCreature('E', Gobbette.class).addCreature('C', GobballWC.class).addPatern("EWB", 0.5F), "GobballSeed");

		// ItemBlock
		GameRegistry.registerItem(sufokiaWave = new ItemBlockYRotation(WBlocks.sufokiaWaveNorthSlab, WBlocks.sufokiaWaveEastSlab, WBlocks.sufokiaWaveSouthSlab, WBlocks.sufokiaWaveWestSlab).setUnlocalizedName(modid_ + "SufokiaWaveSlab"), "blockSufokiaWaveSlab");
		GameRegistry.registerItem(sufokiaWave2 = new ItemBlockYRotation(WBlocks.sufokiaWave2NorthSlab, WBlocks.sufokiaWave2EastSlab, WBlocks.sufokiaWave2SouthSlab, WBlocks.sufokiaWave2WestSlab).setUnlocalizedName(modid_ + "SufokiaWave2Slab"), "blockSufokiaWave2Slab");
		GameRegistry.registerItem(sufokiaWave3 = new ItemBlockYRotation(WBlocks.sufokiaWave3NorthSlab, WBlocks.sufokiaWave3EastSlab, WBlocks.sufokiaWave3SouthSlab, WBlocks.sufokiaWave3WestSlab).setUnlocalizedName(modid_ + "SufokiaWave3Slab"), "blockSufokiaWave3Slab");
		GameRegistry.registerItem(groundSlab = new ItemBlockYRotation(WBlocks.groundNorthSlab, WBlocks.groundEastSlab, WBlocks.groundSouthSlab, WBlocks.groundWestSlab).setUnlocalizedName(modid_ + "GroundSlab"), "blockGroundSlab");
		GameRegistry.registerItem(ground2Slab = new ItemBlockYRotation(WBlocks.ground2NorthSlab, WBlocks.ground2EastSlab, WBlocks.ground2SouthSlab, WBlocks.ground2WestSlab).setUnlocalizedName(modid_ + "Ground2Slab"), "blockGround2Slab");
//		GameRegistry.registerItem(carpet1 = new ItemBlockYRotation(WBlocks.carpetNorthSlab, WBlocks.carpetEastSlab, WBlocks.carpetSouthSlab, WBlocks.carpetWestSlab).setCreativeTab(WakcraftCreativeTabs.tabBlock), "Carpet1");
		GameRegistry.registerItem(wood = new ItemBlockYRotation(WBlocks.woodNorthSlab, WBlocks.woodEastSlab, WBlocks.woodSouthSlab, WBlocks.woodWestSlab).setUnlocalizedName(modid_ + "WoodSlab"), "blockWoodSlab");

		// ARMORS

		// Goball Armors
		GameRegistry.registerItem(gobballBreastplate = (new ItemWArmor(ItemWArmor.TYPE.CHESTPLATE, 15).setCharacteristic(Characteristic.HEALTH, 15).setCharacteristic(Characteristic.INITIATIVE, 5).setCharacteristic(Characteristic.FIRE_ATT, 3).setCharacteristic(Characteristic.EARTH_ATT, 3).setUnlocalizedName(modid_ + "GobballBreastplate")), "GobballBreastplate");
		GameRegistry.registerItem(gobboots = (new ItemWArmor(ItemWArmor.TYPE.BOOTS, 15).setCharacteristic(Characteristic.HEALTH, 10).setCharacteristic(Characteristic.LOCK, 8).setCharacteristic(Characteristic.INITIATIVE, 6).setUnlocalizedName(modid_ + "Gobboots")), "Gobboots");
		GameRegistry.registerItem(gobballEpaulettes = (new ItemWArmor(ItemWArmor.TYPE.EPAULET, 14).setCharacteristic(Characteristic.LOCK, 5).setCharacteristic(Characteristic.FIRE_ATT, 2).setCharacteristic(Characteristic.EARTH_ATT, 2).setUnlocalizedName(modid_ + "GobballEpaulettes")), "GobballEpaulettes");
		GameRegistry.registerItem(gobballCape = (new ItemWArmor(ItemWArmor.TYPE.CAPE, 14).setCharacteristic(Characteristic.HEALTH, 12).setCharacteristic(Characteristic.LOCK, 8).setCharacteristic(Characteristic.FIRE_ATT, 2).setCharacteristic(Characteristic.EARTH_ATT, 2).setUnlocalizedName(modid_ + "GobballCape")), "GobballCape");
		GameRegistry.registerItem(gobballBelt = (new ItemWArmor(ItemWArmor.TYPE.BELT, 13).setCharacteristic(Characteristic.FIRE_ATT, 3).setCharacteristic(Characteristic.EARTH_ATT, 3).setUnlocalizedName(modid_ + "GobballBelt")), "GobballBelt");
		GameRegistry.registerItem(gobballHeadgear = (new ItemWArmor(ItemWArmor.TYPE.HELMET, 13).setCharacteristic(Characteristic.HEALTH, 13).setCharacteristic(Characteristic.LOCK, 5).setCharacteristic(Characteristic.FIRE_ATT, 3).setCharacteristic(Characteristic.EARTH_ATT, 3).setUnlocalizedName(modid_ + "GobballHeadgear")), "GobballHeadgear");
		GameRegistry.registerItem(gobballAmulet = (new ItemWArmor(ItemWArmor.TYPE.AMULET, 12).setCharacteristic(Characteristic.HEALTH, 7).setCharacteristic(Characteristic.LOCK, 5).setUnlocalizedName(modid_ + "GobballAmulet")), "GobballAmulet");
		GameRegistry.registerItem(bouzeLiteYeahsRing = (new ItemWArmor(ItemWArmor.TYPE.RING, 12).setCharacteristic(Characteristic.HEALTH, 6).setCharacteristic(Characteristic.INITIATIVE, 4).setUnlocalizedName(modid_ + "BouzeLiteYeahsRing")), "BouzeLiteYeahsRing");

		// Tofu Armors
		GameRegistry.registerItem(helmetofu = (new ItemWArmor(ItemWArmor.TYPE.HELMET, 13).setCharacteristic(Characteristic.HEALTH, 9).setCharacteristic(Characteristic.INITIATIVE, 3).setCharacteristic(Characteristic.WATER_ATT, 4).setCharacteristic(Characteristic.AIR_ATT, 4).setUnlocalizedName(modid_ + "Helmetofu")), "Helmetofu");
		GameRegistry.registerItem(tofuBreastplate = (new ItemWArmor(ItemWArmor.TYPE.CHESTPLATE, 15).setCharacteristic(Characteristic.HEALTH, 13).setCharacteristic(Characteristic.INITIATIVE, 6).setCharacteristic(Characteristic.WATER_ATT, 3).setCharacteristic(Characteristic.AIR_ATT, 3).setUnlocalizedName(modid_ + "TofuBreastplate")), "TofuBreastplate");
		GameRegistry.registerItem(tofuCloak = (new ItemWArmor(ItemWArmor.TYPE.CAPE, 14).setCharacteristic(Characteristic.HEALTH, 12).setCharacteristic(Characteristic.DODGE, 4).setCharacteristic(Characteristic.WATER_ATT, 3).setCharacteristic(Characteristic.AIR_ATT, 3).setUnlocalizedName(modid_ + "TofuCloak")), "TofuCloak");
		GameRegistry.registerItem(tofuEpaulettes = (new ItemWArmor(ItemWArmor.TYPE.EPAULET, 14).setCharacteristic(Characteristic.DODGE, 5).setCharacteristic(Characteristic.WATER_ATT, 2).setCharacteristic(Characteristic.AIR_ATT, 2).setUnlocalizedName(modid_ + "TofuEpaulettes")), "TofuEpaulettes");
		GameRegistry.registerItem(tofuBelt = (new ItemWArmor(ItemWArmor.TYPE.BELT, 13).setCharacteristic(Characteristic.WATER_ATT, 3).setCharacteristic(Characteristic.AIR_ATT, 3).setUnlocalizedName(modid_ + "TofuBelt")), "TofuBelt");
		GameRegistry.registerItem(tofuAmulet = (new ItemWArmor(ItemWArmor.TYPE.AMULET, 12).setCharacteristic(Characteristic.HEALTH, 7).setCharacteristic(Characteristic.DODGE, 5).setUnlocalizedName(modid_ + "TofuAmulet")), "TofuAmulet");
		GameRegistry.registerItem(tofuRing = (new ItemWArmor(ItemWArmor.TYPE.RING, 12).setCharacteristic(Characteristic.HEALTH, 8).setCharacteristic(Characteristic.INITIATIVE, 3).setUnlocalizedName(modid_ + "TofuRing")), "TofuRing");
		GameRegistry.registerItem(tofuBoots = (new ItemWArmor(ItemWArmor.TYPE.BOOTS, 15).setCharacteristic(Characteristic.HEALTH, 8).setCharacteristic(Characteristic.DODGE, 7).setCharacteristic(Characteristic.INITIATIVE, 8).setUnlocalizedName(modid_ + "TofuBoots")), "TofuBoots");

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
