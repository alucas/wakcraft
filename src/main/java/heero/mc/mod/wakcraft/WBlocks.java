package heero.mc.mod.wakcraft;

import heero.mc.mod.wakcraft.block.*;
import heero.mc.mod.wakcraft.block.ore.BlockOre1;
import heero.mc.mod.wakcraft.block.ore.BlockOre2;
import heero.mc.mod.wakcraft.block.ore.BlockOre3;
import heero.mc.mod.wakcraft.block.ore.BlockOre4;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.item.ItemBlockOre;
import heero.mc.mod.wakcraft.item.ItemBlockSlab;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WBlocks {

    public static Block debug, debugSlab;
    public static Block
            box,
            carpet, carpetEastSlab, carpetNorthSlab, carpetSouthSlab, carpetWestSlab,
            classConsole,
            dirt,
            dirtSlab,
            dragoexpress,
            fence,
            fightDirection,
            fightInsideWall,
            fightMovement,
            fightStart,
            fightStart2,
            fightWall,
            grass, grassSlab,
            ground, groundEastSlab, groundNorthSlab, groundSouthSlab, groundWestSlab,
            ground2, ground2EastSlab, ground2NorthSlab, ground2SouthSlab, ground2WestSlab,
            ground3, ground3Slab,
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
            invisiblewall,
            ore1,
            ore2,
            ore3,
            ore4,
            palisade,
            palisade2,
            phoenix,
            pillar,
            pillarSlab,
            plank,
            plant,
            plant2,
            polisher,
            scree1,
            sufokiaColor,
            sufokiaGround, sufokiaGroundSlab,
            sufokiaGround2, sufokiaGround2Slab,
            sufokiaStair,
            sufokiaSun, sufokiaSunSlab,
            sufokiaWave, sufokiaWaveEastSlab, sufokiaWaveNorthSlab, sufokiaWaveSouthSlab, sufokiaWaveWestSlab,
            sufokiaWave2, sufokiaWave2EastSlab, sufokiaWave2NorthSlab, sufokiaWave2SouthSlab, sufokiaWave2WestSlab,
            sufokiaWave3, sufokiaWave3EastSlab, sufokiaWave3NorthSlab, sufokiaWave3SouthSlab, sufokiaWave3WestSlab,
            sufokiaWave4,
            wood, woodNorthSlab, woodEastSlab, woodSouthSlab, woodWestSlab;

    public static void registerBlocks() {

        String modid_ = Reference.MODID.toLowerCase() + ".";

        GameRegistry.registerBlock(ore1 = (new BlockOre1()), ItemBlockOre.class, "block_ore_1");
        GameRegistry.registerBlock(ore2 = (new BlockOre2()), ItemBlockOre.class, "block_ore_2");
        GameRegistry.registerBlock(ore3 = (new BlockOre3()), ItemBlockOre.class, "block_ore_3");
        GameRegistry.registerBlock(ore4 = (new BlockOre4()), ItemBlockOre.class, "block_ore_4");
//        GameRegistry.registerBlock(invisiblewall = (new BlockInvisibleWall()), "blockInvisibleWall");
//        GameRegistry.registerBlock(havenbag = (new BlockHavenBag()), "blockHavenBag");
//        GameRegistry.registerBlock(hbChest = (new BlockHavenBagChest()), "blockHavenBagChest");
//        GameRegistry.registerBlock(hbLock = (new BlockHavenBagLock()), "blockHavenBagLock");
//        GameRegistry.registerBlock(hbVisitors = (new BlockHavenBagVisitors()), "blockHavenBagVisitors");
//        GameRegistry.registerBlock(fightWall = (new BlockFightWall()), "blockFightWall");
//        GameRegistry.registerBlock(fightInsideWall = (new BlockFightInsideWall()), "blockFightInsideWall");
//        GameRegistry.registerBlock(scree1 = (new BlockScree()), "blockScree1");

        // Debug Blocks
        GameRegistry.registerBlock(debug = (new BlockGeneric(Material.wood).setUnlocalizedName(modid_ + "Debug")), "blockDebug");
        GameRegistry.registerBlock(debugSlab = (new BlockSlab(Material.wood, WBlocks.debug.getDefaultState()).setUnlocalizedName(modid_ + "DebugSlab")), ItemBlockSlab.class, "blockDebugSlab");

        // Special blocks
        GameRegistry.registerBlock(classConsole = (new BlockClassConsole().setUnlocalizedName(modid_ + "ClassConsole")), "blockClassConsole");

        // Fight Blocks
        GameRegistry.registerBlock(fightDirection = (new BlockGeneric(Material.ground).setOpaque(false).setUnlocalizedName(modid_ + "FightDirection")), "blockFightDirection");
        GameRegistry.registerBlock(fightMovement = (new BlockGeneric(Material.ground).setOpaque(false).setUnlocalizedName(modid_ + "FightMovement")), "blockFightMovement");
        GameRegistry.registerBlock(fightStart = (new BlockFightStart().setUnlocalizedName(modid_ + "FightStart")), "blockFightStart");
        GameRegistry.registerBlock(fightStart2 = (new BlockFightStart().setUnlocalizedName(modid_ + "FightStart2")), "blockFightStart2");

        // HavenBag
//        GameRegistry.registerBlock(hbBarrier = (new BlockHavenBagBarrier()), "blockHavenBagBarrier");
        GameRegistry.registerBlock(hbBridge = (new BlockGeneric(Material.iron, WCreativeTabs.tabBlock).setOpaque(false).setUnlocalizedName(modid_ + "HBBridge")), "blockHBBridge");
        GameRegistry.registerBlock(hbCraft = (new BlockGeneric(Material.iron, WCreativeTabs.tabBlock).setUnlocalizedName(modid_ + "HBCraft")), "blockHBCraft");
        GameRegistry.registerBlock(hbCraft2 = (new BlockGeneric(Material.iron, WCreativeTabs.tabBlock).setUnlocalizedName(modid_ + "HBCraft2")), "blockHBCraft2");
        GameRegistry.registerBlock(hbDeco = (new BlockGeneric(Material.iron, WCreativeTabs.tabBlock).setUnlocalizedName(modid_ + "HBDeco")), "blockHBDeco");
        GameRegistry.registerBlock(hbDeco2 = (new BlockGeneric(Material.iron, WCreativeTabs.tabBlock).setUnlocalizedName(modid_ + "HBDeco2")), "blockHBDeco2");
        GameRegistry.registerBlock(hbGarden = (new BlockGeneric(Material.iron, WCreativeTabs.tabBlock).setUnlocalizedName(modid_ + "HBGarden")), "blockHBGarden");
        GameRegistry.registerBlock(hbMerchant = (new BlockGeneric(Material.iron, WCreativeTabs.tabBlock).setUnlocalizedName(modid_ + "HBMerchant")), "blockHBMerchant");
        GameRegistry.registerBlock(hbStand = (new BlockGeneric(Material.wood, WCreativeTabs.tabBlock).setUnlocalizedName(modid_ + "HBStand")), "blockHBStand");

        // Basic blocks
        GameRegistry.registerBlock(box = (new BlockGeneric(Material.wood, WCreativeTabs.tabBlock).setUnlocalizedName(modid_ + "Box")), "blockBox");
        GameRegistry.registerBlock(fence = (new BlockWFence(Material.wood).setUnlocalizedName(modid_ + "SufokiaFence")), "blockSufokiaFence");

        // Basic slabs
        GameRegistry.registerBlock(carpet = (new BlockCenterCorner(Material.cloth).setUnlocalizedName(modid_ + "Carpet")), "blockCarpet");
        GameRegistry.registerBlock(carpetNorthSlab = (new BlockSlab(Material.cloth, WBlocks.carpet.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(modid_ + "CarpetNorthSlab")), ItemBlockSlab.class, "blockCarpetNorthSlab");
        GameRegistry.registerBlock(carpetSouthSlab = (new BlockSlab(Material.cloth, WBlocks.carpet.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(modid_ + "CarpetSouthSlab")), ItemBlockSlab.class, "blockCarpetSouthSlab");
        GameRegistry.registerBlock(carpetEastSlab = (new BlockSlab(Material.cloth, WBlocks.carpet.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(modid_ + "CarpetEastSlab")), ItemBlockSlab.class, "blockCarpetEastSlab");
        GameRegistry.registerBlock(carpetWestSlab = (new BlockSlab(Material.cloth, WBlocks.carpet.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(modid_ + "CarpetWestSlab")), ItemBlockSlab.class, "blockCarpetWestSlab");
        GameRegistry.registerBlock(dirt = (new BlockGeneric(Material.ground).setUnlocalizedName(modid_ + "Dirt")), "blockDirt");
        GameRegistry.registerBlock(dirtSlab = (new BlockSlab(Material.ground, WBlocks.dirt.getDefaultState()).setUnlocalizedName(modid_ + "DirtSlab")), ItemBlockSlab.class, "blockDirtSlab");
        GameRegistry.registerBlock(grass = (new BlockGeneric(Material.grass).setUnlocalizedName(modid_ + "Grass")), "blockGrass");
        GameRegistry.registerBlock(grassSlab = (new BlockSlab(Material.grass, WBlocks.grass.getDefaultState()).setUnlocalizedName(modid_ + "GrassSlab")), ItemBlockSlab.class, "blockGrassSlab");
        GameRegistry.registerBlock(ground = (new BlockYRotation(Material.ground).setUnlocalizedName(modid_ + "Ground")), "blockGround");
        GameRegistry.registerBlock(groundEastSlab = (new BlockSlab(Material.ground, WBlocks.ground.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(modid_ + "GroundEastSlab")), ItemBlockSlab.class, "blockGroundEastSlab");
        GameRegistry.registerBlock(groundNorthSlab = (new BlockSlab(Material.ground, WBlocks.ground.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(modid_ + "GroundNorthSlab")), ItemBlockSlab.class, "blockGroundNorthSlab");
        GameRegistry.registerBlock(groundSouthSlab = (new BlockSlab(Material.ground, WBlocks.ground.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(modid_ + "GroundSouthSlab")), ItemBlockSlab.class, "blockGroundSouthSlab");
        GameRegistry.registerBlock(groundWestSlab = (new BlockSlab(Material.ground, WBlocks.ground.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(modid_ + "GroundWestSlab")), ItemBlockSlab.class, "blockGroundWestSlab");
        GameRegistry.registerBlock(ground2 = (new BlockYRotation(Material.ground).setUnlocalizedName(modid_ + "Ground2")), "blockGround2");
        GameRegistry.registerBlock(ground2EastSlab = (new BlockSlab(Material.ground, WBlocks.ground2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(modid_ + "Ground2EastSlab")), ItemBlockSlab.class, "blockGround2EastSlab");
        GameRegistry.registerBlock(ground2NorthSlab = (new BlockSlab(Material.ground, WBlocks.ground2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(modid_ + "Ground2NorthSlab")), ItemBlockSlab.class, "blockGround2NorthSlab");
        GameRegistry.registerBlock(ground2SouthSlab = (new BlockSlab(Material.ground, WBlocks.ground2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(modid_ + "Ground2SouthSlab")), ItemBlockSlab.class, "blockGround2SouthSlab");
        GameRegistry.registerBlock(ground2WestSlab = (new BlockSlab(Material.ground, WBlocks.ground2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(modid_ + "Ground2WestSlab")), ItemBlockSlab.class, "blockGround2WestSlab");
        GameRegistry.registerBlock(ground3 = (new BlockGeneric(Material.ground).setUnlocalizedName(modid_ + "Ground3")), "blockGround3");
        GameRegistry.registerBlock(ground3Slab = (new BlockSlab(Material.ground, WBlocks.ground3.getDefaultState()).setUnlocalizedName(modid_ + "Ground3Slab")), ItemBlockSlab.class, "blockGround3Slab");
        GameRegistry.registerBlock(ground4 = (new BlockGeneric(Material.ground).setUnlocalizedName(modid_ + "Ground4")), "blockGround4");
        GameRegistry.registerBlock(ground4Slab = (new BlockSlab(Material.ground, WBlocks.ground4.getDefaultState()).setUnlocalizedName(modid_ + "Ground4Slab")), ItemBlockSlab.class, "blockGround4Slab");
        GameRegistry.registerBlock(ground11 = (new BlockGeneric(Material.ground).setUnlocalizedName(modid_ + "Ground11")), "blockGround11");
        GameRegistry.registerBlock(ground11Slab = (new BlockSlab(Material.ground, WBlocks.ground11.getDefaultState()).setUnlocalizedName(modid_ + "Ground11Slab")), ItemBlockSlab.class, "blockGround11Slab");
        GameRegistry.registerBlock(ground12 = (new BlockGeneric(Material.ground).setUnlocalizedName(modid_ + "Ground12")), "blockGround12");
        GameRegistry.registerBlock(ground12Slab = (new BlockSlab(Material.ground, WBlocks.ground12.getDefaultState()).setUnlocalizedName(modid_ + "Ground12Slab")), ItemBlockSlab.class, "blockGround12Slab");
        GameRegistry.registerBlock(ground13 = (new BlockGeneric(Material.ground).setUnlocalizedName(modid_ + "Ground13")), "blockGround13");
        GameRegistry.registerBlock(ground13Slab = (new BlockSlab(Material.ground, WBlocks.ground13.getDefaultState()).setUnlocalizedName(modid_ + "Ground13Slab")), ItemBlockSlab.class, "blockGround13Slab");
        GameRegistry.registerBlock(ground14 = (new BlockGeneric(Material.ground).setUnlocalizedName(modid_ + "Ground14")), "blockGround14");
        GameRegistry.registerBlock(ground14Slab = (new BlockSlab(Material.ground, WBlocks.ground14.getDefaultState()).setUnlocalizedName(modid_ + "Ground14Slab")), ItemBlockSlab.class, "blockGround14Slab");
        GameRegistry.registerBlock(hbStandSlab = (new BlockSlab(Material.wood, WBlocks.hbStand.getDefaultState()).setUnlocalizedName(modid_ + "HBStandSlab")), ItemBlockSlab.class, "blockHBStandSlab");
        GameRegistry.registerBlock(palisade = (new BlockPalisade(Material.wood).setUnlocalizedName(modid_ + "Palisade")), "blockPalisade");
        GameRegistry.registerBlock(palisade2 = (new BlockPalisade(Material.wood).setUnlocalizedName(modid_ + "Palisade2")), "blockPalisade2");
        GameRegistry.registerBlock(pillar = (new BlockGeneric(Material.wood).setUnlocalizedName(modid_ + "Pillar")), "blockPillar");
        GameRegistry.registerBlock(pillarSlab = (new BlockSlab(Material.wood, WBlocks.pillar.getDefaultState()).setUnlocalizedName(modid_ + "PillarSlab")), ItemBlockSlab.class, "blockPillarSlab");
        GameRegistry.registerBlock(plank = (new BlockGeneric(Material.wood, WCreativeTabs.tabBlock).setUnlocalizedName(modid_ + "Plank")), "blockPlank");
        GameRegistry.registerBlock(plant = (new BlockPlant(Material.plants).setUnlocalizedName(modid_ + "Plant")), "blockPlant");
        GameRegistry.registerBlock(plant2 = (new BlockPlant(Material.plants).setUnlocalizedName(modid_ + "Plant2")), "blockPlant2");
        GameRegistry.registerBlock(sufokiaColor = (new BlockGeneric(Material.sand, WCreativeTabs.tabBlock).setUnlocalizedName(modid_ + "SufokiaColor")), "blockSufokiaColor");
        GameRegistry.registerBlock(sufokiaGround = (new BlockGeneric(Material.ground).setUnlocalizedName(modid_ + "SufokiaGround")), "blockSufokiaGround");
        GameRegistry.registerBlock(sufokiaGroundSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaGround.getDefaultState()).setUnlocalizedName(modid_ + "SufokiaGroundSlab")), ItemBlockSlab.class, "blockSufokiaGroundSlab");
        GameRegistry.registerBlock(sufokiaGround2 = (new BlockGeneric(Material.ground).setUnlocalizedName(modid_ + "SufokiaGround2")), "blockSufokiaGround2");
        GameRegistry.registerBlock(sufokiaGround2Slab = (new BlockSlab(Material.ground, WBlocks.sufokiaGround2.getDefaultState()).setUnlocalizedName(modid_ + "SufokiaGround2Slab")), ItemBlockSlab.class, "blockSufokiaGround2Slab");
        GameRegistry.registerBlock(sufokiaStair = ((new BlockStairs2(WBlocks.sufokiaColor.getDefaultState())).setUnlocalizedName(modid_ + "SufokiaStair")), "blockSufokiaStair");
        GameRegistry.registerBlock(sufokiaSun = (new BlockGeneric(Material.sand).setUnlocalizedName(modid_ + "SufokiaSun")), "blockSufokiaSun");
        GameRegistry.registerBlock(sufokiaSunSlab = (new BlockSlab(Material.sand, WBlocks.sufokiaSun.getDefaultState()).setUnlocalizedName(modid_ + "SufokiaSunSlab")), ItemBlockSlab.class, "blockSufokiaSunSlab");
        GameRegistry.registerBlock(sufokiaWave = (new BlockYRotation(Material.sand).setUnlocalizedName(modid_ + "SufokiaWave")), "blockSufokiaWave");
        GameRegistry.registerBlock(sufokiaWaveEastSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(modid_ + "SufokiaWaveEastSlab")), ItemBlockSlab.class, "blockSufokiaWaveEastSlab");
        GameRegistry.registerBlock(sufokiaWaveNorthSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(modid_ + "SufokiaWaveNorthSlab")), ItemBlockSlab.class, "blockSufokiaWaveNorthSlab");
        GameRegistry.registerBlock(sufokiaWaveSouthSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(modid_ + "SufokiaWaveSouthSlab")), ItemBlockSlab.class, "blockSufokiaWaveSouthSlab");
        GameRegistry.registerBlock(sufokiaWaveWestSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(modid_ + "SufokiaWaveWestSlab")), ItemBlockSlab.class, "blockSufokiaWaveWestSlab");
        GameRegistry.registerBlock(sufokiaWave2 = (new BlockYRotation(Material.sand).setUnlocalizedName(modid_ + "SufokiaWave2")), "blockSufokiaWave2");
        GameRegistry.registerBlock(sufokiaWave2EastSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(modid_ + "SufokiaWave2EastSlab")), ItemBlockSlab.class, "blockSufokiaWave2EastSlab");
        GameRegistry.registerBlock(sufokiaWave2NorthSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(modid_ + "SufokiaWave2NorthSlab")), ItemBlockSlab.class, "blockSufokiaWave2NorthSlab");
        GameRegistry.registerBlock(sufokiaWave2SouthSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(modid_ + "SufokiaWave2SouthSlab")), ItemBlockSlab.class, "blockSufokiaWave2SouthSlab");
        GameRegistry.registerBlock(sufokiaWave2WestSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(modid_ + "SufokiaWave2WestSlab")), ItemBlockSlab.class, "blockSufokiaWave2WestSlab");
        GameRegistry.registerBlock(sufokiaWave3 = (new BlockCenterCorner(Material.sand).setUnlocalizedName(modid_ + "SufokiaWave3")), "blockSufokiaWave3");
        GameRegistry.registerBlock(sufokiaWave3EastSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave3.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(modid_ + "SufokiaWave3EastSlab")), ItemBlockSlab.class, "blockSufokiaWave3EastSlab");
        GameRegistry.registerBlock(sufokiaWave3NorthSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave3.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(modid_ + "SufokiaWave3NorthSlab")), ItemBlockSlab.class, "blockSufokiaWave3NorthSlab");
        GameRegistry.registerBlock(sufokiaWave3SouthSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave3.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(modid_ + "SufokiaWave3SouthSlab")), ItemBlockSlab.class, "blockSufokiaWave3SouthSlab");
        GameRegistry.registerBlock(sufokiaWave3WestSlab = (new BlockSlab(Material.ground, WBlocks.sufokiaWave3.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(modid_ + "SufokiaWave3WestSlab")), ItemBlockSlab.class, "blockSufokiaWave3WestSlab");
        GameRegistry.registerBlock(sufokiaWave4 = (new BlockGeneric(Material.sand, WCreativeTabs.tabBlock).setUnlocalizedName(modid_ + "SufokiaWave4")), "blockSufokiaWave4");
        GameRegistry.registerBlock(wood = (new BlockYRotation(Material.wood).setUnlocalizedName(modid_ + "Wood")), "blockWood");
        GameRegistry.registerBlock(woodEastSlab = (new BlockSlab(Material.wood, WBlocks.wood.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(modid_ + "WoodEastSlab")), ItemBlockSlab.class, "blockWoodEastSlab");
        GameRegistry.registerBlock(woodNorthSlab = (new BlockSlab(Material.wood, WBlocks.wood.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(modid_ + "WoodNorthSlab")), ItemBlockSlab.class, "blockWoodNorthSlab");
        GameRegistry.registerBlock(woodSouthSlab = (new BlockSlab(Material.wood, WBlocks.wood.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(modid_ + "WoodSouthSlab")), ItemBlockSlab.class, "blockWoodSouthSlab");
        GameRegistry.registerBlock(woodWestSlab = (new BlockSlab(Material.wood, WBlocks.wood.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(modid_ + "WoodWestSlab")), ItemBlockSlab.class, "blockWoodWestSlab");

//        // Special blocks
//        GameRegistry.registerBlock(polisher = (new BlockPolisher()), "blockPolisher");
//        GameRegistry.registerBlock(dragoexpress = (new BlockDragoexpress()), "blockDragoexpress");
//        GameRegistry.registerBlock(phoenix = (new BlockPhoenix()), "blockPhoenix");
//        GameRegistry.registerBlock(hbGemWorkbench = (new BlockHavenGemWorkbench()), "blockHavenGemWorkbench");
    }
}
