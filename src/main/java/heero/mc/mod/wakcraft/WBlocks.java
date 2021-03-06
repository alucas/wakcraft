package heero.mc.mod.wakcraft;

import heero.mc.mod.wakcraft.block.*;
import heero.mc.mod.wakcraft.block.vein.*;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.item.ItemBlockSlab;
import heero.mc.mod.wakcraft.item.ItemBlockVein;
import heero.mc.mod.wakcraft.profession.ProfessionManager;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WBlocks {
    protected static final String MODID_ = Reference.MODID.toLowerCase() + ".";

    public static Block debug, debugSlab;
    public static BlockVein vein1, vein2, vein3, vein4;
    public static Block artichoke, tuberbulb, wheat;
    public static Block
            box,
            carpet, carpetEastSlab, carpetNorthSlab, carpetSouthSlab, carpetWestSlab,
            classConsole,
            dirt,
            dirtSlab,
            dragoExpress,
            fightDirection,
            fightInsideWall,
            fightMovement,
            fightRange,
            fightStart,
            fightStart2,
            fightWall,
            grass, grassSlab,
            ground, groundEastSlab, groundNorthSlab, groundSouthSlab, groundWestSlab,
            ground2, ground2EastSlab, ground2NorthSlab, ground2SouthSlab, ground2WestSlab,
            ground3, ground3Slab, ground3Stair,
            ground4, ground4Slab,
            ground5, ground5Slab,
            ground6, ground6Slab,
            ground7, ground7Slab,
            ground8, ground8Slab,
            ground9, ground9Slab,
            ground10, ground10Slab,
            ground11, ground11Slab,
            ground12, ground12Slab,
            ground13, ground13Slab,
            ground14, ground14Slab,
            havenbag,
            hbBridge,
            hbBarrier,
            hbChest,
            hbCraft,
            hbCraft2,
            hbDeco,
            hbDeco2,
            hbGarden,
            hbGemWorkbench,
            hbLock,
            hbMerchant,
            hbStand, hbStandSlab,
            hbVisitors,
            invisibleWall,
            palisade,
            palisade2,
            phoenix,
            pillar,
            pillarSlab,
            plank,
            plant,
            plant2,
            workbenchGrindIt, workbenchPolisher,
            scree1,
            sufokiaCloth,
            sufokiaColor, sufokiaColorFence, sufokiaColorSlab, sufokiaColorStair,
            sufokiaGround, sufokiaGroundSlab,
            sufokiaGround2, sufokiaGround2Slab,
            sufokiaSand, sufokiaSandSlab,
            sufokiaSun, sufokiaSunSlab,
            sufokiaWave, sufokiaWaveEastSlab, sufokiaWaveNorthSlab, sufokiaWaveSouthSlab, sufokiaWaveWestSlab,
            sufokiaWave2, sufokiaWave2EastSlab, sufokiaWave2NorthSlab, sufokiaWave2SouthSlab, sufokiaWave2WestSlab,
            sufokiaWave3, sufokiaWave3EastSlab, sufokiaWave3NorthSlab, sufokiaWave3SouthSlab, sufokiaWave3WestSlab,
            sufokiaWave4,
            sufokiaWood, sufokiaWoodFence, sufokiaWoodSlab,
            wood, woodNorthSlab, woodEastSlab, woodSouthSlab, woodWestSlab,
            zaap;

    public static void registerBlocks() {
        // Debug Blocks
        registerBlock(debug = (new BlockGeneric(Material.wood).setUnlocalizedName(MODID_ + "debug")));
        registerBlock(debugSlab = (new BlockSlab(Material.wood, WBlocks.debug.getDefaultState()).setUnlocalizedName(MODID_ + "debug_slab")), ItemBlockSlab.class);

        // Ore
        registerBlock(vein1 = (BlockVein) new BlockVein1().setUnlocalizedName(MODID_ + "vein"), ItemBlockVein.class);
        registerBlock(vein2 = (BlockVein) new BlockVein2().setUnlocalizedName(MODID_ + "vein_2"), ItemBlockVein.class);
        registerBlock(vein3 = (BlockVein) new BlockVein3().setUnlocalizedName(MODID_ + "vein_3"), ItemBlockVein.class);
        registerBlock(vein4 = (BlockVein) new BlockVein4().setUnlocalizedName(MODID_ + "vein_4"), ItemBlockVein.class);

        // Farmer
        registerBlock(wheat = (new BlockCrop(0, 100).setUnlocalizedName(MODID_ + "crop_wheat")));
        registerBlock(artichoke = (new BlockCrop(10, 100).setUnlocalizedName(MODID_ + "crop_artichoke")));
        registerBlock(tuberbulb = (new BlockCrop(0, 100).setUnlocalizedName(MODID_ + "crop_tuberbulb")));

        // Fight Blocks
        registerBlock(fightDirection = (new BlockGenericTransparent(Material.ground).setUnlocalizedName(MODID_ + "fight_direction")));
        registerBlock(fightInsideWall = (new BlockFightInsideWall().setUnlocalizedName(MODID_ + "fight_inside_wall")));
        registerBlock(fightMovement = (new BlockGenericTransparent(Material.ground).setUnlocalizedName(MODID_ + "fight_movement")));
        registerBlock(fightRange = (new BlockGenericTransparent(Material.ground).setUnlocalizedName(MODID_ + "fight_range")));
        registerBlock(fightStart = (new BlockFightStart().setUnlocalizedName(MODID_ + "fight_start")));
        registerBlock(fightStart2 = (new BlockFightStart().setUnlocalizedName(MODID_ + "fight_start_2")));
        registerBlock(fightWall = (new BlockFightWall().setUnlocalizedName(MODID_ + "fight_wall")));
        registerBlock(invisibleWall = (new BlockInvisibleWall().setUnlocalizedName(MODID_ + "invisible_wall")));

        // HavenBag
        registerBlock(havenbag = (new BlockHavenBag().setUnlocalizedName(MODID_ + "haven_bag")));
        registerBlock(hbBarrier = (new BlockHavenBagBarrier().setUnlocalizedName(MODID_ + "hb_barrier")));
        registerBlock(hbBridge = (new BlockGenericTransparent(Material.iron, WCreativeTabs.tabBlock).setUnlocalizedName(MODID_ + "hb_bridge")));
        registerBlock(hbChest = (new BlockHavenBagChest().setUnlocalizedName(MODID_ + "hb_chest")));
        registerBlock(hbCraft = (new BlockGeneric(Material.iron, WCreativeTabs.tabBlock).setUnlocalizedName(MODID_ + "hb_craft")));
        registerBlock(hbCraft2 = (new BlockGeneric(Material.iron, WCreativeTabs.tabBlock).setUnlocalizedName(MODID_ + "hb_craft_2")));
        registerBlock(hbDeco = (new BlockGeneric(Material.iron, WCreativeTabs.tabBlock).setUnlocalizedName(MODID_ + "hb_deco")));
        registerBlock(hbDeco2 = (new BlockGeneric(Material.iron, WCreativeTabs.tabBlock).setUnlocalizedName(MODID_ + "hb_deco_2")));
        registerBlock(hbGarden = (new BlockGeneric(Material.iron, WCreativeTabs.tabBlock).setUnlocalizedName(MODID_ + "hb_garden")));
        registerBlock(hbGemWorkbench = (new BlockHavenGemWorkbench().setUnlocalizedName(MODID_ + "hb_gem_workbench")));
        registerBlock(hbLock = (new BlockHavenBagLock().setUnlocalizedName(MODID_ + "hb_lock")));
        registerBlock(hbMerchant = (new BlockGeneric(Material.iron, WCreativeTabs.tabBlock).setUnlocalizedName(MODID_ + "hb_merchant")));
        registerBlock(hbVisitors = (new BlockHavenBagVisitors().setUnlocalizedName(MODID_ + "hb_visitors")));

        // Special blocks
        registerBlock(classConsole = (new BlockClassConsole().setUnlocalizedName(MODID_ + "class_console")));
        registerBlock(dragoExpress = (new BlockDragoExpress().setUnlocalizedName(MODID_ + "drago_express")));
        registerBlock(workbenchGrindIt = (new BlockWorkbench(ProfessionManager.PROFESSION.FARMER).setUnlocalizedName(MODID_ + "workbench_grind_it")));
        registerBlock(workbenchPolisher = (new BlockWorkbench(ProfessionManager.PROFESSION.MINER).setUnlocalizedName(MODID_ + "workbench_polisher")));
        registerBlock(phoenix = (new BlockPhoenix().setUnlocalizedName(MODID_ + "phoenix")));
        registerBlock(zaap = (new BlockZaap().setUnlocalizedName(MODID_ + "zaap")));

        // Basic blocks
        registerBlock(box = (new BlockGeneric(Material.wood, WCreativeTabs.tabBlock).setUnlocalizedName(MODID_ + "box")));

        // Basic slabs
        registerBlock(carpet = (new BlockCenterCorner(Material.cloth).setUnlocalizedName(MODID_ + "carpet")));
        registerBlock(carpetNorthSlab = (new BlockSlab(Material.cloth, WBlocks.carpet.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(MODID_ + "carpet_north_slab")), ItemBlockSlab.class);
        registerBlock(carpetSouthSlab = (new BlockSlab(Material.cloth, WBlocks.carpet.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(MODID_ + "carpet_south_slab")), ItemBlockSlab.class);
        registerBlock(carpetEastSlab = (new BlockSlab(Material.cloth, WBlocks.carpet.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(MODID_ + "carpet_east_slab")), ItemBlockSlab.class);
        registerBlock(carpetWestSlab = (new BlockSlab(Material.cloth, WBlocks.carpet.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(MODID_ + "carpet_west_slab")), ItemBlockSlab.class);
        registerBlock(dirt = (new BlockGeneric(Material.ground).setUnlocalizedName(MODID_ + "dirt")));
        registerBlock(dirtSlab = (new BlockSlab(Material.ground, WBlocks.dirt.getDefaultState()).setUnlocalizedName(MODID_ + "dirt_slab")), ItemBlockSlab.class);
        registerBlock(grass = (new BlockGrass().setUnlocalizedName(MODID_ + "grass")));
        registerBlock(grassSlab = (new BlockSlab(Material.grass, WBlocks.grass.getDefaultState()).setUnlocalizedName(MODID_ + "grass_slab")), ItemBlockSlab.class);
        registerBlock(ground = (new BlockYRotation(Material.ground).setUnlocalizedName(MODID_ + "ground")));
        registerBlock(groundEastSlab = (new BlockSlab(Material.ground, WBlocks.ground.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(MODID_ + "ground_east_slab")), ItemBlockSlab.class);
        registerBlock(groundNorthSlab = (new BlockSlab(Material.ground, WBlocks.ground.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(MODID_ + "ground_north_slab")), ItemBlockSlab.class);
        registerBlock(groundSouthSlab = (new BlockSlab(Material.ground, WBlocks.ground.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(MODID_ + "ground_south_slab")), ItemBlockSlab.class);
        registerBlock(groundWestSlab = (new BlockSlab(Material.ground, WBlocks.ground.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(MODID_ + "ground_west_slab")), ItemBlockSlab.class);
        registerBlock(ground2 = (new BlockYRotation(Material.ground).setUnlocalizedName(MODID_ + "ground_2")));
        registerBlock(ground2EastSlab = (new BlockSlab(Material.ground, WBlocks.ground2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(MODID_ + "ground_2_east_slab")), ItemBlockSlab.class);
        registerBlock(ground2NorthSlab = (new BlockSlab(Material.ground, WBlocks.ground2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(MODID_ + "ground_2_north_slab")), ItemBlockSlab.class);
        registerBlock(ground2SouthSlab = (new BlockSlab(Material.ground, WBlocks.ground2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(MODID_ + "ground_2_south_slab")), ItemBlockSlab.class);
        registerBlock(ground2WestSlab = (new BlockSlab(Material.ground, WBlocks.ground2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(MODID_ + "ground_2_west_slab")), ItemBlockSlab.class);
        registerBlock(ground3 = (new BlockGeneric(Material.ground).setUnlocalizedName(MODID_ + "ground_3")));
        registerBlock(ground3Slab = (new BlockSlab(Material.ground, WBlocks.ground3.getDefaultState()).setUnlocalizedName(MODID_ + "ground_3_slab")), ItemBlockSlab.class);
        registerBlock(ground3Stair = ((new BlockStairsGeneric(WBlocks.ground3.getDefaultState())).setUnlocalizedName(MODID_ + "ground_3_stair")));
        registerBlock(ground4 = (new BlockGeneric(Material.ground).setUnlocalizedName(MODID_ + "ground_4")));
        registerBlock(ground4Slab = (new BlockSlab(Material.ground, WBlocks.ground4.getDefaultState()).setUnlocalizedName(MODID_ + "ground_4_slab")), ItemBlockSlab.class);
        registerBlock(ground11 = (new BlockGeneric(Material.ground).setUnlocalizedName(MODID_ + "ground_11")));
        registerBlock(ground11Slab = (new BlockSlab(Material.ground, WBlocks.ground11.getDefaultState()).setUnlocalizedName(MODID_ + "ground_11_slab")), ItemBlockSlab.class);
        registerBlock(ground12 = (new BlockGeneric(Material.ground).setUnlocalizedName(MODID_ + "ground_12")));
        registerBlock(ground12Slab = (new BlockSlab(Material.ground, WBlocks.ground12.getDefaultState()).setUnlocalizedName(MODID_ + "ground_12_slab")), ItemBlockSlab.class);
        registerBlock(ground13 = (new BlockGeneric(Material.ground).setUnlocalizedName(MODID_ + "ground_13")));
        registerBlock(ground13Slab = (new BlockSlab(Material.ground, WBlocks.ground13.getDefaultState()).setUnlocalizedName(MODID_ + "ground_13_slab")), ItemBlockSlab.class);
        registerBlock(ground14 = (new BlockGeneric(Material.ground).setUnlocalizedName(MODID_ + "ground_14")));
        registerBlock(ground14Slab = (new BlockSlab(Material.ground, WBlocks.ground14.getDefaultState()).setUnlocalizedName(MODID_ + "ground_14_slab")), ItemBlockSlab.class);
        registerBlock(hbStand = (new BlockGeneric(Material.wood).setUnlocalizedName(MODID_ + "hb_stand")));
        registerBlock(hbStandSlab = (new BlockSlab(Material.wood, WBlocks.hbStand.getDefaultState()).setUnlocalizedName(MODID_ + "hb_stand_slab")), ItemBlockSlab.class);
        registerBlock(palisade = (new BlockPalisade(Material.wood).setUnlocalizedName(MODID_ + "palisade")));
        registerBlock(palisade2 = (new BlockPalisade(Material.wood).setUnlocalizedName(MODID_ + "palisade_2")));
        registerBlock(pillar = (new BlockGeneric(Material.wood).setUnlocalizedName(MODID_ + "pillar")));
        registerBlock(pillarSlab = (new BlockSlab(Material.wood, WBlocks.pillar.getDefaultState()).setUnlocalizedName(MODID_ + "pillar_slab")), ItemBlockSlab.class);
        registerBlock(plank = (new BlockGeneric(Material.wood, WCreativeTabs.tabBlock).setUnlocalizedName(MODID_ + "plank")));
        registerBlock(plant = (new BlockPlant(Material.plants).setUnlocalizedName(MODID_ + "plant")));
        registerBlock(plant2 = (new BlockPlant(Material.plants).setUnlocalizedName(MODID_ + "plant_2")));
//        GameRegistry.registerBlock(scree1 = (new BlockScree()), "blockScree1");
        registerBlock(sufokiaCloth = (new BlockCloth().setUnlocalizedName(MODID_ + "sufokia_cloth")));
        registerBlock(sufokiaColor = (new BlockGeneric(Material.sand).setUnlocalizedName(MODID_ + "sufokia_color")));
        registerBlock(sufokiaColorFence = (new BlockFenceGeneric(Material.wood).setUnlocalizedName(MODID_ + "sufokia_color_fence")));
        registerBlock(sufokiaColorSlab = (new BlockSlab(Material.sand, WBlocks.sufokiaColor.getDefaultState()).setUnlocalizedName(MODID_ + "sufokia_color_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaColorStair = ((new BlockStairsGeneric(WBlocks.sufokiaColor.getDefaultState())).setUnlocalizedName(MODID_ + "sufokia_color_stair")));
        registerBlock(sufokiaGround = (new BlockGeneric(Material.ground).setUnlocalizedName(MODID_ + "sufokia_ground")));
        registerBlock(sufokiaGroundSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaGround.getDefaultState()).setUnlocalizedName(MODID_ + "sufokia_ground_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaGround2 = (new BlockGeneric(Material.ground).setUnlocalizedName(MODID_ + "sufokia_ground_2")));
        registerBlock(sufokiaGround2Slab = (new BlockSlab(Material.ground, WBlocks.sufokiaGround2.getDefaultState()).setUnlocalizedName(MODID_ + "sufokia_ground_2_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaSand = (new BlockGeneric(Material.sand).setUnlocalizedName(MODID_ + "sufokia_sand")));
        registerBlock(sufokiaSandSlab = (new BlockSlab(Material.sand, WBlocks.sufokiaSand.getDefaultState()).setUnlocalizedName(MODID_ + "sufokia_sand_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaSun = (new BlockGeneric(Material.sand).setUnlocalizedName(MODID_ + "sufokia_sun")));
        registerBlock(sufokiaSunSlab = (new BlockSlab(Material.sand, WBlocks.sufokiaSun.getDefaultState()).setUnlocalizedName(MODID_ + "sufokia_sun_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaWave = (new BlockYRotation(Material.sand).setUnlocalizedName(MODID_ + "sufokia_wave")));
        registerBlock(sufokiaWaveEastSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(MODID_ + "sufokia_wave_east_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaWaveNorthSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(MODID_ + "sufokia_wave_north_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaWaveSouthSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(MODID_ + "sufokia_wave_south_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaWaveWestSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(MODID_ + "sufokia_wave_west_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaWave2 = (new BlockYRotation(Material.sand).setUnlocalizedName(MODID_ + "sufokia_wave_2")));
        registerBlock(sufokiaWave2EastSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(MODID_ + "sufokia_wave_2_east_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaWave2NorthSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(MODID_ + "sufokia_wave_2_north_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaWave2SouthSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(MODID_ + "sufokia_wave_2_south_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaWave2WestSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(MODID_ + "sufokia_wave_2_west_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaWave3 = (new BlockCenterCorner(Material.sand).setUnlocalizedName(MODID_ + "sufokia_wave_3")));
        registerBlock(sufokiaWave3EastSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave3.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(MODID_ + "sufokia_wave_3_east_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaWave3NorthSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave3.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(MODID_ + "sufokia_wave_3_north_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaWave3SouthSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave3.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(MODID_ + "sufokia_wave_3_south_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaWave3WestSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave3.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(MODID_ + "sufokia_wave_3_west_slab")), ItemBlockSlab.class);
        registerBlock(sufokiaWave4 = (new BlockGeneric(Material.sand, WCreativeTabs.tabBlock).setUnlocalizedName(MODID_ + "sufokia_wave_4")));
        registerBlock(sufokiaWood = (new BlockGeneric(Material.wood).setUnlocalizedName(MODID_ + "sufokia_wood")));
        registerBlock(sufokiaWoodFence = (new BlockFenceGeneric(Material.wood).setUnlocalizedName(MODID_ + "sufokia_wood_fence")));
        registerBlock(sufokiaWoodSlab = (new BlockSlab(Material.wood, WBlocks.sufokiaWood.getDefaultState()).setUnlocalizedName(MODID_ + "sufokia_wood_slab")), ItemBlockSlab.class);
        registerBlock(wood = (new BlockYRotation(Material.wood).setUnlocalizedName(MODID_ + "wood")));
        registerBlock(woodEastSlab = (new BlockSlab(Material.wood, WBlocks.wood.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(MODID_ + "wood_east_slab")), ItemBlockSlab.class);
        registerBlock(woodNorthSlab = (new BlockSlab(Material.wood, WBlocks.wood.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(MODID_ + "wood_north_slab")), ItemBlockSlab.class);
        registerBlock(woodSouthSlab = (new BlockSlab(Material.wood, WBlocks.wood.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(MODID_ + "wood_south_slab")), ItemBlockSlab.class);
        registerBlock(woodWestSlab = (new BlockSlab(Material.wood, WBlocks.wood.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(MODID_ + "wood_west_slab")), ItemBlockSlab.class);
    }

    public static String makeRegistryName(final Block block) {
        return block.getUnlocalizedName().substring("tile.".length() + MODID_.length());
    }

    public static void registerBlock(final Block block) {
        GameRegistry.registerBlock(block, makeRegistryName(block));
    }

    public static void registerBlock(final Block block, final Class<? extends ItemBlock> itemBlockClass) {
        GameRegistry.registerBlock(block, itemBlockClass, makeRegistryName(block));
    }
}
