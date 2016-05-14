package heero.mc.mod.wakcraft;

import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.entity.creature.gobball.*;
import heero.mc.mod.wakcraft.item.*;
import heero.mc.mod.wakcraft.profession.ProfessionManager;
import heero.mc.mod.wakcraft.spell.ActiveSpecialitySpell;
import heero.mc.mod.wakcraft.spell.ElementalSpell;
import heero.mc.mod.wakcraft.spell.PassiveSpecialitySpell;
import heero.mc.mod.wakcraft.spell.RangeMode;
import heero.mc.mod.wakcraft.spell.effect.*;
import heero.mc.mod.wakcraft.spell.state.State;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Map;

public class WItems extends Items {
    protected final static String MODID_ = Reference.MODID + ".";

    // Resource
    public static Item
            artichoke,
            bomb,
            canoonPowder,
            clay,
            driedDung,
            fossil,
            gobballHorn,
            gobballSeed,
            gobballSkin,
            gobballWool,
            gumgum,
            moonstone,
            ore1,
            ore2,
            pearl,
            polishedMoonstone,
            shadowyBlue,
            shamPearl,
            tofuBlood,
            tuberbulb,
            tofuFeather,
            verbalaSalt,
            waterBucket,
            wheat,
            wheatFlour,
            woollyKey;

    // Farmer seeds
    public static Map<String, Item> FARMER_SEEDS;
    public static Item
            artichokeSeed,
            babbageSeed,
            beanSeed,
            blackberrySeed,
            blackCawwotSeed,
            cawwotSeed,
            chiliSeed,
            cornSeed,
            curarareSeed,
            desertTruffleSeed,
            barleyGrain,
            vanillaRiceGrain,
            jollyflowerSeed,
            juteySeed,
            makoffeeSeed,
            melonSeed,
            mushraySeed,
            oatGrain,
            palmCaneSeed,
            pumpkinSeed,
            ryeGrain,
            sunflowerSeed,
            tuberbulbSeed,
            watermelonSeed,
            wheatGrain;

    // HG
    public static Item
            craftHG,
            decoHG,
            gardenHG,
            merchantHG;

    // Armor
    public static Item
            bouzeLiteYeahsRing,
            gobballAmulet,
            gobballBelt,
            gobballBreastplate,
            gobballCape,
            gobballEpaulettes,
            gobballHeadgear,
            gobboots,
            helmetofu,
            theOne,
            tofuAmulet,
            tofuBelt,
            tofuBoots,
            tofuBreastplate,
            tofuCloak,
            tofuEpaulettes,
            tofuRing;

    // ItemBlock
    public static Item
            sufokiaWave,
            sufokiaWave2,
            sufokiaWave3,
            groundSlab,
            ground2Slab,
            carpet,
            wood;

    // Ikiakits
    public static ItemIkiakit
            ikiakitSmall,
            ikiakitAdventurer,
            ikiakitKit,
            ikiakitCollector,
            ikiakitGolden,
            ikiakitEmerald;

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
        GameRegistry.registerItem(artichoke = ((new ItemWithLevel(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "artichoke")), "artichoke");
        GameRegistry.registerItem(bomb = ((new ItemWithLevel(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "bomb")), "bom");
        GameRegistry.registerItem(canoonPowder = ((new ItemWithLevel(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "canoon_powder")), "canoon_powder");
        GameRegistry.registerItem(clay = ((new ItemWithLevel(4)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "clay")), "clay");
        GameRegistry.registerItem(driedDung = ((new ItemWithLevel(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "dried_dung")), "dried_dung");
        GameRegistry.registerItem(fossil = ((new ItemWithLevel(5)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "fossil")), "fossil");
        GameRegistry.registerItem(gobballHorn = ((new ItemWithLevel(15)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "gobball_horn")), "gobball_horn");
        GameRegistry.registerItem(gobballSkin = ((new ItemWithLevel(10)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "gobball_skin")), "gobball_skin");
        GameRegistry.registerItem(gobballWool = ((new ItemWithLevel(10)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "gobball_wool")), "gobball_wool");
        GameRegistry.registerItem(gumgum = ((new ItemWithLevel(15)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "gum_gum")), "gum_gum");
        GameRegistry.registerItem(moonstone = ((new ItemWithLevel(20)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "moonstone")), "moonstone");
        GameRegistry.registerItem(ore1 = (new ItemOre(0)), "ore");
        GameRegistry.registerItem(ore2 = (new ItemOre(1)), "ore_2");
        GameRegistry.registerItem(pearl = ((new ItemWithLevel(10)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "pearl")), "pearl");
        GameRegistry.registerItem(polishedMoonstone = ((new ItemWithLevel(20)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "polished_moonstone")), "polished_moonstone");
        GameRegistry.registerItem(shadowyBlue = ((new ItemWithLevel(25)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "shadowy_blue")), "shadowy_blue");
        GameRegistry.registerItem(shamPearl = ((new ItemWithLevel(10)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "sham_pearl")), "sham_pearl");
        GameRegistry.registerItem(tofuBlood = ((new ItemWithLevel(10)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "tofu_blood")), "tofu_blood");
        GameRegistry.registerItem(tofuFeather = ((new ItemWithLevel(10)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "tofu_feather")), "tofu_feather");
        GameRegistry.registerItem(tuberbulb = ((new ItemWithLevel(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "tuberbulb")), "tuberbulb");
        GameRegistry.registerItem(verbalaSalt = ((new ItemWithLevel(15)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "verbala_salt")), "verbala_salt");
        GameRegistry.registerItem(waterBucket = ((new ItemWithLevel(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "water_bucket")), "water_bucket");
        GameRegistry.registerItem(wheat = ((new ItemWithLevel(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "wheat")), "wheat");
        GameRegistry.registerItem(wheatFlour = ((new ItemWithLevel(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "wheat_flour")), "wheat_flour");
        GameRegistry.registerItem(woollyKey = ((new ItemWithLevel(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "woolly_key")), "woolly_key");

        // Monster Seed
        GameRegistry.registerItem(gobballSeed = new ItemWCreatureSeeds(0).addCreature('G', Gobball.class).addCreature('B', BlackGobbly.class).addCreature('W', WhiteGobbly.class).addCreature('E', Gobbette.class).addCreature('C', GobballWC.class).addPatern("EWB", 0.5F).setUnlocalizedName(MODID_ + "gobball_seed"), "gobball_seed");

        // HG
        GameRegistry.registerItem(craftHG = ((new ItemWithLevel(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "hg_craft").setMaxStackSize(1)), "hg_craft");
        GameRegistry.registerItem(decoHG = ((new ItemWithLevel(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "hg_deco").setMaxStackSize(1)), "hg_deco");
        GameRegistry.registerItem(gardenHG = ((new ItemWithLevel(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "hg_garden").setMaxStackSize(1)), "hg_garden");
        GameRegistry.registerItem(merchantHG = ((new ItemWithLevel(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "hg_merchant").setMaxStackSize(1)), "hg_merchant");

        // Ikiakit
        GameRegistry.registerItem(ikiakitEmerald = (ItemIkiakit) (new ItemIkiakit(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "emerald_ikiakit"), "emerald_ikiakit");
        GameRegistry.registerItem(ikiakitSmall = (ItemIkiakit) (new ItemIkiakit(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "small_ikiakit"), "small_ikiakit");
        GameRegistry.registerItem(ikiakitGolden = (ItemIkiakit) (new ItemIkiakit(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "golden_ikiakit"), "golden_ikiakit");
        GameRegistry.registerItem(ikiakitKit = (ItemIkiakit) (new ItemIkiakit(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "kit_ikiakit"), "kit_ikiakit");
        GameRegistry.registerItem(ikiakitAdventurer = (ItemIkiakit) (new ItemIkiakit(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "adventurer_ikiakit"), "adventurer_ikiakit");
        GameRegistry.registerItem(ikiakitCollector = (ItemIkiakit) (new ItemIkiakit(1)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "collector_ikiakit"), "collector_ikiakit");

        // ItemBlock
        GameRegistry.registerItem(carpet = new ItemBlockYRotation(WBlocks.carpetNorthSlab, WBlocks.carpetEastSlab, WBlocks.carpetSouthSlab, WBlocks.carpetWestSlab).setUnlocalizedName(MODID_ + "carpet_slab"), "block_carpet_slab");
        GameRegistry.registerItem(groundSlab = new ItemBlockYRotation(WBlocks.groundNorthSlab, WBlocks.groundEastSlab, WBlocks.groundSouthSlab, WBlocks.groundWestSlab).setUnlocalizedName(MODID_ + "ground_slab"), "block_ground_slab");
        GameRegistry.registerItem(ground2Slab = new ItemBlockYRotation(WBlocks.ground2NorthSlab, WBlocks.ground2EastSlab, WBlocks.ground2SouthSlab, WBlocks.ground2WestSlab).setUnlocalizedName(MODID_ + "ground_2_slab"), "block_ground_2_slab");
        GameRegistry.registerItem(sufokiaWave = new ItemBlockYRotation(WBlocks.sufokiaWaveNorthSlab, WBlocks.sufokiaWaveEastSlab, WBlocks.sufokiaWaveSouthSlab, WBlocks.sufokiaWaveWestSlab).setUnlocalizedName(MODID_ + "sufokia_wave_slab"), "block_sufokia_wave_slab");
        GameRegistry.registerItem(sufokiaWave2 = new ItemBlockYRotation(WBlocks.sufokiaWave2NorthSlab, WBlocks.sufokiaWave2EastSlab, WBlocks.sufokiaWave2SouthSlab, WBlocks.sufokiaWave2WestSlab).setUnlocalizedName(MODID_ + "sufokia_wave_2_slab"), "block_sufokia_wave_2_slab");
        GameRegistry.registerItem(sufokiaWave3 = new ItemBlockYRotation(WBlocks.sufokiaWave3NorthSlab, WBlocks.sufokiaWave3EastSlab, WBlocks.sufokiaWave3SouthSlab, WBlocks.sufokiaWave3WestSlab).setUnlocalizedName(MODID_ + "sufokia_wave_3_slab"), "block_sufokia_wave_3_slab");
        GameRegistry.registerItem(wood = new ItemBlockYRotation(WBlocks.woodNorthSlab, WBlocks.woodEastSlab, WBlocks.woodSouthSlab, WBlocks.woodWestSlab).setUnlocalizedName(MODID_ + "wood_slab"), "block_wood_slab");

        // JOBS

        // Farmer Seed
        GameRegistry.registerItem(artichokeSeed = ((new ItemSeed(ProfessionManager.PROFESSION.FARMER, 10, WBlocks.artichoke, WItems.artichoke)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "artichoke_seed")), "artichoke_seed");
        GameRegistry.registerItem(babbageSeed = ((new ItemWithLevel(40)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "babbage_seed")), "babbage_seed");
        GameRegistry.registerItem(barleyGrain = ((new ItemWithLevel(20)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "barley_grain")), "barley_grain");
        GameRegistry.registerItem(beanSeed = ((new ItemWithLevel(45)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "bean_seed")), "bean_seed");
        GameRegistry.registerItem(blackberrySeed = ((new ItemWithLevel(160)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "blackberry_seed")), "blackberry_seed");
        GameRegistry.registerItem(blackCawwotSeed = ((new ItemWithLevel(120)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "black_cawwot_seed")), "black_cawwot_seed");
        GameRegistry.registerItem(cawwotSeed = ((new ItemWithLevel(25)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "cawwot_seed")), "cawwot_seed");
        GameRegistry.registerItem(chiliSeed = ((new ItemWithLevel(90)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "chili_seed")), "chili_seed");
        GameRegistry.registerItem(cornSeed = ((new ItemWithLevel(70)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "corn_seed")), "corn_seed");
        GameRegistry.registerItem(curarareSeed = ((new ItemWithLevel(140)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "curarare_seed")), "curarare_seed");
        GameRegistry.registerItem(desertTruffleSeed = ((new ItemWithLevel(110)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "desert_truffle_seed")), "desert_truffle_seed");
        GameRegistry.registerItem(jollyflowerSeed = ((new ItemWithLevel(65)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "jollyflower_seed")), "jollyflower_seed");
        GameRegistry.registerItem(juteySeed = ((new ItemWithLevel(60)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "jutey_seed")), "jutey_seed");
        GameRegistry.registerItem(makoffeeSeed = ((new ItemWithLevel(150)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "makoffee_seed")), "makoffee_seed");
        GameRegistry.registerItem(melonSeed = ((new ItemWithLevel(75)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "melon_seed")), "melon_seed");
        GameRegistry.registerItem(mushraySeed = ((new ItemWithLevel(120)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "mushray_seed")), "mushray_seed");
        GameRegistry.registerItem(oatGrain = ((new ItemWithLevel(35)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "oat_grain")), "oat_grain");
        GameRegistry.registerItem(palmCaneSeed = ((new ItemWithLevel(170)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "palm_cane_seed")), "palm_cane_seed");
        GameRegistry.registerItem(pumpkinSeed = ((new ItemWithLevel(30)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "pumpkin_seed")), "pumpkin_seed");
        GameRegistry.registerItem(ryeGrain = ((new ItemWithLevel(50)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "rye_grain")), "rye_grain");
        GameRegistry.registerItem(sunflowerSeed = ((new ItemWithLevel(80)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "sunflower_seed")), "sunflower_seed");
        GameRegistry.registerItem(tuberbulbSeed = ((new ItemSeed(ProfessionManager.PROFESSION.FARMER, 0, WBlocks.tuberbulb, WItems.tuberbulb)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "tuberbulb_seed")), "tuberbulb_seed");
        GameRegistry.registerItem(vanillaRiceGrain = ((new ItemWithLevel(55)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "vanilla_rice_grain")), "vanilla_rice_grain");
        GameRegistry.registerItem(watermelonSeed = ((new ItemWithLevel(85)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "watermelon_seed")), "watermelon_seed");
        GameRegistry.registerItem(wheatGrain = ((new ItemSeed(ProfessionManager.PROFESSION.FARMER, 0, WBlocks.wheat, WItems.wheat)).setCreativeTab(WCreativeTabs.tabResource).setUnlocalizedName(MODID_ + "wheat_grain")), "wheat_grain");

        // ARMORS
        // -------------------------------

        GameRegistry.registerItem(theOne = (new ItemWArmor(ItemWArmor.TYPE.RING, 1000).setCharacteristic(Characteristic.ACTION, 25).setUnlocalizedName(MODID_ + "the_one")), "the_one");

        // Goball Armors
        GameRegistry.registerItem(gobballBreastplate = (new ItemWArmor(ItemWArmor.TYPE.CHEST_PLATE, 15).setCharacteristic(Characteristic.HEALTH, 15).setCharacteristic(Characteristic.INITIATIVE, 5).setCharacteristic(Characteristic.FIRE_ATT, 3).setCharacteristic(Characteristic.EARTH_ATT, 3).setUnlocalizedName(MODID_ + "gobball_breastplate")), "gobball_breastplate");
        GameRegistry.registerItem(gobboots = (new ItemWArmor(ItemWArmor.TYPE.BOOTS, 15).setCharacteristic(Characteristic.HEALTH, 10).setCharacteristic(Characteristic.LOCK, 8).setCharacteristic(Characteristic.INITIATIVE, 6).setUnlocalizedName(MODID_ + "gobboots")), "gobboots");
        GameRegistry.registerItem(gobballEpaulettes = (new ItemWArmor(ItemWArmor.TYPE.EPAULET, 14).setCharacteristic(Characteristic.LOCK, 5).setCharacteristic(Characteristic.FIRE_ATT, 2).setCharacteristic(Characteristic.EARTH_ATT, 2).setUnlocalizedName(MODID_ + "gobball_epaulettes")), "gobball_epaulettes");
        GameRegistry.registerItem(gobballCape = (new ItemWArmor(ItemWArmor.TYPE.CAPE, 14).setCharacteristic(Characteristic.HEALTH, 12).setCharacteristic(Characteristic.LOCK, 8).setCharacteristic(Characteristic.FIRE_ATT, 2).setCharacteristic(Characteristic.EARTH_ATT, 2).setUnlocalizedName(MODID_ + "gobball_cape")), "gobball_cape");
        GameRegistry.registerItem(gobballBelt = (new ItemWArmor(ItemWArmor.TYPE.BELT, 13).setCharacteristic(Characteristic.FIRE_ATT, 3).setCharacteristic(Characteristic.EARTH_ATT, 3).setUnlocalizedName(MODID_ + "gobball_belt")), "gobball_belt");
        GameRegistry.registerItem(gobballHeadgear = (new ItemWArmor(ItemWArmor.TYPE.HELMET, 13).setCharacteristic(Characteristic.HEALTH, 13).setCharacteristic(Characteristic.LOCK, 5).setCharacteristic(Characteristic.FIRE_ATT, 3).setCharacteristic(Characteristic.EARTH_ATT, 3).setUnlocalizedName(MODID_ + "gobball_headgear")), "gobball_headgear");
        GameRegistry.registerItem(gobballAmulet = (new ItemWArmor(ItemWArmor.TYPE.AMULET, 12).setCharacteristic(Characteristic.HEALTH, 7).setCharacteristic(Characteristic.LOCK, 5).setUnlocalizedName(MODID_ + "gobball_amulet")), "gobball_amulet");
        GameRegistry.registerItem(bouzeLiteYeahsRing = (new ItemWArmor(ItemWArmor.TYPE.RING, 12).setCharacteristic(Characteristic.HEALTH, 6).setCharacteristic(Characteristic.INITIATIVE, 4).setUnlocalizedName(MODID_ + "bouze_lite_yeahs_ring")), "bouze_lite_yeahs_ring");

        // Tofu Armors
        GameRegistry.registerItem(helmetofu = (new ItemWArmor(ItemWArmor.TYPE.HELMET, 13).setCharacteristic(Characteristic.HEALTH, 9).setCharacteristic(Characteristic.INITIATIVE, 3).setCharacteristic(Characteristic.WATER_ATT, 4).setCharacteristic(Characteristic.AIR_ATT, 4).setUnlocalizedName(MODID_ + "helmetofu")), "helmetofu");
        GameRegistry.registerItem(tofuBreastplate = (new ItemWArmor(ItemWArmor.TYPE.CHEST_PLATE, 15).setCharacteristic(Characteristic.HEALTH, 13).setCharacteristic(Characteristic.INITIATIVE, 6).setCharacteristic(Characteristic.WATER_ATT, 3).setCharacteristic(Characteristic.AIR_ATT, 3).setUnlocalizedName(MODID_ + "tofu_breastplate")), "tofu_breastplate");
        GameRegistry.registerItem(tofuCloak = (new ItemWArmor(ItemWArmor.TYPE.CAPE, 14).setCharacteristic(Characteristic.HEALTH, 12).setCharacteristic(Characteristic.DODGE, 4).setCharacteristic(Characteristic.WATER_ATT, 3).setCharacteristic(Characteristic.AIR_ATT, 3).setUnlocalizedName(MODID_ + "tofu_cloak")), "tofu_cloak");
        GameRegistry.registerItem(tofuEpaulettes = (new ItemWArmor(ItemWArmor.TYPE.EPAULET, 14).setCharacteristic(Characteristic.DODGE, 5).setCharacteristic(Characteristic.WATER_ATT, 2).setCharacteristic(Characteristic.AIR_ATT, 2).setUnlocalizedName(MODID_ + "tofu_epaulettes")), "tofu_epaulettes");
        GameRegistry.registerItem(tofuBelt = (new ItemWArmor(ItemWArmor.TYPE.BELT, 13).setCharacteristic(Characteristic.WATER_ATT, 3).setCharacteristic(Characteristic.AIR_ATT, 3).setUnlocalizedName(MODID_ + "tofu_belt")), "tofu_belt");
        GameRegistry.registerItem(tofuAmulet = (new ItemWArmor(ItemWArmor.TYPE.AMULET, 12).setCharacteristic(Characteristic.HEALTH, 7).setCharacteristic(Characteristic.DODGE, 5).setUnlocalizedName(MODID_ + "tofu_amulet")), "tofu_amulet");
        GameRegistry.registerItem(tofuRing = (new ItemWArmor(ItemWArmor.TYPE.RING, 12).setCharacteristic(Characteristic.HEALTH, 8).setCharacteristic(Characteristic.INITIATIVE, 3).setUnlocalizedName(MODID_ + "tofu_ring")), "tofu_ring");
        GameRegistry.registerItem(tofuBoots = (new ItemWArmor(ItemWArmor.TYPE.BOOTS, 15).setCharacteristic(Characteristic.HEALTH, 8).setCharacteristic(Characteristic.DODGE, 7).setCharacteristic(Characteristic.INITIATIVE, 8).setUnlocalizedName(MODID_ + "tofu_boots")), "tofu_boots");

        // SPELLS
        // Iop spells
        GameRegistry.registerItem(spellShaker = (new ElementalSpell("shaker", 4, 0, 0).setRange(1, 1, true, false, RangeMode.LINE).setEffect(new EffectDamage(4, 134, EffectElement.EARTH)).setEffectCritical(new EffectDamage(7, 199, EffectElement.EARTH))), "spell_shaker");
        GameRegistry.registerItem(spellRocknoceros = (new ElementalSpell("rocknoceros", 5, 0, 0).setRange(2, 3, false, false, RangeMode.DEFAULT).setEffect(new EffectDamage(4, 126, EffectElement.EARTH)).setEffect(new EffectState(State.STUNNED, 1, 0, 0.1F, 0)).setEffectCritical(new EffectDamage(6, 190, EffectElement.EARTH)).setEffectCritical(new EffectState(State.STUNNED, 1, 0, 0.15F, 0))), "spell_rocknoceros");
        GameRegistry.registerItem(spellImpact = (new ElementalSpell("impact", 2, 0, 0, EffectArea.CROSS).setRange(2, 3, true, false, RangeMode.LINE).setEffect(new EffectDamage(2, 50, EffectElement.EARTH, EffectArea.CROSS)).setEffect(new EffectCharacteristic(Characteristic.MOVEMENT, -1, 0, 0.1F, 0, EffectArea.CROSS)).setEffectCritical(new EffectDamage(3, 75, EffectElement.EARTH, EffectArea.CROSS)).setEffectCritical(new EffectCharacteristic(Characteristic.MOVEMENT, -1, 0, 0.15F, 0, EffectArea.CROSS))), "spell_impact");
        GameRegistry.registerItem(spellCharge = (new ElementalSpell("charge", 4, 1, 0).setRange(1, 3, true, false, RangeMode.LINE).setEffect(new EffectMovement(2, 0)).setEffect(new EffectDamage(3, 103, EffectElement.EARTH)).setEffect(new EffectState(State.STUNNED, 1, 0, 0.10F, 0)).setEffectCritical(new EffectMovement(2, 0)).setEffectCritical(new EffectDamage(5, 151, EffectElement.EARTH)).setEffectCritical(new EffectState(State.STUNNED, 1, 0, 0.15F, 0))), "spell_charge");
        GameRegistry.registerItem(spellDevastate = (new ElementalSpell("devastate", 5, 1, 0, EffectArea.AROUND).setEffect(new EffectDamage(7, 143, EffectElement.EARTH, EffectArea.AROUND)).setEffect(new EffectCharacteristic(Characteristic.MOVEMENT, -1, 0, 0.3F, 0, EffectArea.AROUND)).setEffectCritical(new EffectDamage(10, 214, EffectElement.EARTH, EffectArea.AROUND)).setEffectCritical(new EffectCharacteristic(Characteristic.MOVEMENT, -1, 0, 0.45F, 0, EffectArea.AROUND))), "spell_devastate");
        GameRegistry.registerItem(spellThunderbolt = (new ElementalSpell("thunderbolt", 3, 0, 0).setRange(1, 1).setEffect(new EffectDamage(3, 85, EffectElement.FIRE)).setEffect(new EffectState(State.SCALDED, 1, 38 / 200.0F)).setEffectCritical(new EffectDamage(5, 127, EffectElement.FIRE)).setEffectCritical(new EffectState(State.SCALDED, 2, 56 / 200.0F))), "spell_thunderbolt");
        GameRegistry.registerItem(spellJudgment = (new ElementalSpell("judgment", 4, 1, 0, EffectArea.LINE_V_3).setRange(1, 1).setEffect(new EffectDamage(4, 136, EffectElement.FIRE, EffectArea.LINE_V_3)).setEffect(new EffectState(State.DISORIENTED, 1, 0, 0.01F, 0.99F / 200.0F)).setEffectCritical(new EffectDamage(6, 204, EffectElement.FIRE, EffectArea.LINE_V_3)).setEffectCritical(new EffectState(State.DISORIENTED, 1, 0, 0.01F, 1.50F / 200.0F))), "spell_judgment");
        GameRegistry.registerItem(spellSuperIopPunch = (new ElementalSpell("super_iop_punch", 5, 0, 0, EffectArea.CROSS).setRange(2, 3, true, false, RangeMode.LINE).setEffect(new EffectMovement(2, 0)).setEffect(new EffectDamage(5, 145, EffectElement.FIRE, EffectArea.CROSS)).setEffect(new EffectState(State.EXPLOSION, 0, 100 / 200.0F, 0.3F, 0)).setEffectCritical(new EffectMovement(2, 0)).setEffectCritical(new EffectDamage(8, 217, EffectElement.FIRE, EffectArea.CROSS)).setEffectCritical(new EffectState(State.EXPLOSION, 0, 100 / 200.0F, 0.45F, 0))), "spell_super_iop_punch");
        GameRegistry.registerItem(spellCelestialSword = (new ElementalSpell("celestial_sword", 3, 0, 0, EffectArea.CROSS).setEffect(new EffectDamage(2, 70, EffectElement.FIRE, EffectArea.CROSS)).setEffect(new EffectState(State.FLAMING)).setEffectCritical(new EffectDamage(4, 102, EffectElement.FIRE, EffectArea.CROSS)).setEffectCritical(new EffectState(State.FLAMING))), "spell_celestial_sword");
        GameRegistry.registerItem(spellIopsWrath = (new ElementalSpell("iops_wrath", 6, 0, 1, EffectArea.AREA_2).setEffect(new EffectDamage(8, 172, EffectElement.FIRE, EffectArea.AREA_2)).setEffect(new EffectState(State.EXPLOSION, 0, 100 / 200.0F, 0.66F, 0)).setEffectCritical(new EffectDamage(12, 258, EffectElement.FIRE, EffectArea.AREA_2)).setEffectCritical(new EffectState(State.EXPLOSION, 0, 100 / 200.0F))), "spell_iops_wrath");
        GameRegistry.registerItem(spellJabs = (new ElementalSpell("jabs", 2, 0, 0)), "spell_jabs");
        GameRegistry.registerItem(spellFlurry = (new ElementalSpell("flurry", 1, 0, 0).setEffect(new EffectState(State.AERIAL, 1, 0)).setEffectCritical(new EffectState(State.AERIAL, 2, 0))), "spell_flurry");
        GameRegistry.registerItem(spellIntimidation = (new ElementalSpell("intimidation", 3, 0, 0)), "spell_intimidation");
        GameRegistry.registerItem(spellGuttingGust = (new ElementalSpell("gutting_gust", 0, 1, 0)), "spell_gutting_gust");
        GameRegistry.registerItem(spellUppercut = (new ElementalSpell("uppercut", 0, 0, 1)), "spell_uppercut");
        GameRegistry.registerItem(spellJump = (new ActiveSpecialitySpell("jump")), "spell_jump");
        GameRegistry.registerItem(spellDefensiveStance = (new ActiveSpecialitySpell("defensive_stance")), "spell_defensive_stance");
        GameRegistry.registerItem(spellFlatten = (new ActiveSpecialitySpell("flatten")), "spell_flatten");
        GameRegistry.registerItem(spellBraveryStandard = (new ActiveSpecialitySpell("bravery_standard")), "spell_bravery_standard");
        GameRegistry.registerItem(spellIncrease = (new ActiveSpecialitySpell("increase")), "spell_increase");
        GameRegistry.registerItem(spellVirility = (new PassiveSpecialitySpell("virility")), "spell_virility");
        GameRegistry.registerItem(spellCompulsion = (new PassiveSpecialitySpell("compulsion")), "spell_compulsion");
        GameRegistry.registerItem(spellAuthority = (new PassiveSpecialitySpell("authority")), "spell_authority");
        GameRegistry.registerItem(spellShowOff = (new PassiveSpecialitySpell("show_off")), "spell_show_off");
        GameRegistry.registerItem(spellLockingPro = (new PassiveSpecialitySpell("locking_pro")), "spell_locking_pro");
    }
}
