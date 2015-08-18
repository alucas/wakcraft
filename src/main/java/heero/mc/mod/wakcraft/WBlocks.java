package heero.mc.mod.wakcraft;

import heero.mc.mod.wakcraft.block.BlockCenterCorner;
import heero.mc.mod.wakcraft.block.BlockClassConsole;
import heero.mc.mod.wakcraft.block.BlockFence1;
import heero.mc.mod.wakcraft.block.BlockFightStart;
import heero.mc.mod.wakcraft.block.BlockGeneric;
import heero.mc.mod.wakcraft.block.BlockPlant;
import heero.mc.mod.wakcraft.block.BlockScree;
import heero.mc.mod.wakcraft.block.BlockSlab;
import heero.mc.mod.wakcraft.block.BlockStairs2;
import heero.mc.mod.wakcraft.block.BlockYRotation;
import heero.mc.mod.wakcraft.item.ItemBlockSlab;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WBlocks {

    public static Block dragoexpress, phoenix, sufokiaColor, sufokiaSun,
            sufokiaWave, sufokiaWave2, sufokiaWave3, sufokiaWave4,
            sufokiaStair, ore1, ore2, ore3, ore4, carpet, sufokiaGround, sufokiaGround2, sufokiaGroundSlab, sufokiaGround2Slab,
            palisade, pillar, wood, hbStand, grass, grassSlab, dirt,
            dirtSlab, fence, polisher, havenGemWorkbench, invisiblewall,
            havenbag, hbChest, hbLock, hbBridge, hbVisitors, hbBarrier,
            hbCraft, hbCraft2, hbGarden, hbDeco, hbDeco2, hbMerchant,
            fightMovement, fightDirection, fightWall, classConsole, ground,
            ground2, ground3, ground3Slab, ground4,
            ground4Slab, ground5, ground5Slab, ground6, ground6Slab, ground7,
            ground7Slab, ground8, ground8Slab, ground9, ground9Slab, ground10,
            ground10Slab, ground11, ground11Slab, ground12, ground12Slab,
            ground13, ground13Slab, ground14, ground14Slab, box, plank,
            fightInsideWall, fightStart, fightStart2,
            plant, plant2,
            sufokiaWaveNorthSlab, sufokiaWaveSouthSlab, sufokiaWaveEastSlab,
            sufokiaWaveWestSlab, sufokiaSunSlab, pillarSlab,
            sufokiaWave2NorthSlab, sufokiaWave2SouthSlab, sufokiaWave2EastSlab,
            sufokiaWave2WestSlab, sufokiaWave3NorthSlab, sufokiaWave3SouthSlab,
            sufokiaWave3EastSlab, sufokiaWave3WestSlab, woodNorthSlab,
            woodSouthSlab, woodEastSlab, woodWestSlab, carpetNorthSlab,
            carpetSouthSlab, carpetEastSlab, carpetWestSlab, scree1,
            hbStandSlab, groundNorthSlab, groundWestSlab, groundEastSlab, groundSouthSlab,
            ground2NorthSlab, ground2WestSlab, ground2EastSlab, ground2SouthSlab;

    public static Block debug, debugSlab;

    public static void registerBlocks() {
        String modid_ = Reference.MODID.toLowerCase() + ".";

// 		GameRegistry.registerBlock(ore1 = (new BlockOre1()), ItemBlockOre1.class, "oreLvl1Block");
//		GameRegistry.registerBlock(ore2 = (new BlockOre2()), ItemBlockOre2.class, "blockOre2");
//		GameRegistry.registerBlock(ore3 = (new BlockOre3()), ItemBlockOre3.class, "blockOre3");
//		GameRegistry.registerBlock(ore4 = (new BlockOre4()), ItemBlockOre4.class, "blockOre4");
//		GameRegistry.registerBlock(palisade = (new BlockPalisade()), ItemBlockPalisade.class, "blockPalisade");
//		GameRegistry.registerBlock(invisiblewall = (new BlockInvisibleWall()), "blockInvisibleWall");
//		GameRegistry.registerBlock(havenbag = (new BlockHavenBag()), "blockHavenBag");
//		GameRegistry.registerBlock(hbChest = (new BlockHavenBagChest()), "blockHavenBagChest");
//		GameRegistry.registerBlock(hbLock = (new BlockHavenBagLock()), "blockHavenBagLock");
//		GameRegistry.registerBlock(hbVisitors = (new BlockHavenBagVisitors()), "blockHavenBagVisitors");
//		GameRegistry.registerBlock(hbBarrier = (new BlockHavenBagBarrier()), "blockHavenBagBarrier");
//		GameRegistry.registerBlock(fightWall = (new BlockFightWall()), "blockFightWall");
//		GameRegistry.registerBlock(fightInsideWall = (new BlockFightInsideWall()), "blockFightInsideWall");
//		GameRegistry.registerBlock(carpetNorthSlab = (new BlockSlab(Material.cloth, WBlocks.carpet.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(modid_ + "CarpetNorthSlab")), ItemBlockSlab.class, "blockCarpetNorthSlab");
//		GameRegistry.registerBlock(carpetSouthSlab = (new BlockSlab(Material.cloth, WBlocks.carpet.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(modid_ + "CarpetSouthSlab")), ItemBlockSlab.class, "blockCarpetSouthSlab");
//		GameRegistry.registerBlock(carpetEastSlab = (new BlockSlab(Material.cloth, WBlocks.carpet.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(modid_ + "CarpetEastSlab")), ItemBlockSlab.class, "blockCarpetEastSlab");
//		GameRegistry.registerBlock(carpetWestSlab = (new BlockSlab(Material.cloth, WBlocks.carpet.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(modid_ + "CarpetWestSlab")), ItemBlockSlab.class, "blockCarpetWestSlab");
		GameRegistry.registerBlock(scree1 = (new BlockScree()), "blockScree1");

        // Basic blocks
        GameRegistry.registerBlock(box = (new BlockGeneric(Material.wood).setUnlocalizedName(modid_ + "Box")), "blockBox");
        GameRegistry.registerBlock(carpet = (new BlockCenterCorner(Material.cloth).setUnlocalizedName(modid_ + "Carpet")), "blockCarpet");
        GameRegistry.registerBlock(classConsole = (new BlockClassConsole().setUnlocalizedName(modid_ + "ClassConsole")), "blockClassConsole");
        GameRegistry.registerBlock(debug = (new BlockGeneric(Material.wood).setUnlocalizedName(modid_ + "Debug")), "blockDebug");
        GameRegistry.registerBlock(debugSlab = (new BlockSlab(Material.wood, WBlocks.debug.getDefaultState()).setUnlocalizedName(modid_ + "DebugSlab")), ItemBlockSlab.class, "blockDebugSlab");
        GameRegistry.registerBlock(dirt = (new BlockGeneric(Material.ground).setUnlocalizedName(modid_ + "Dirt")), "blockDirt");
        GameRegistry.registerBlock(dirtSlab = (new BlockSlab(Material.ground, WBlocks.dirt.getDefaultState()).setUnlocalizedName(modid_ + "DirtSlab")), ItemBlockSlab.class, "blockDirtSlab");
        GameRegistry.registerBlock(fence = (new BlockFence1(Material.wood).setUnlocalizedName(modid_ + "Fence")), "blockFence");
        GameRegistry.registerBlock(fightDirection = (new BlockGeneric(Material.ground).setOpaque(false).setUnlocalizedName(modid_ + "FightDirection")), "blockFightDirection");
        GameRegistry.registerBlock(fightMovement = (new BlockGeneric(Material.ground).setOpaque(false).setUnlocalizedName(modid_ + "FightMovement")), "blockFightMovement");
        GameRegistry.registerBlock(fightStart = (new BlockFightStart().setUnlocalizedName(modid_ + "FightStart")), "blockFightStart");
        GameRegistry.registerBlock(fightStart2 = (new BlockFightStart().setUnlocalizedName(modid_ + "FightStart2")), "blockFightStart2");
        GameRegistry.registerBlock(grass = (new BlockGeneric(Material.grass).setUnlocalizedName(modid_ + "Grass")), "blockGrass");
        GameRegistry.registerBlock(grassSlab = (new BlockSlab(Material.grass, WBlocks.grass.getDefaultState()).setUnlocalizedName(modid_ + "GrassSlab")), ItemBlockSlab.class, "blockGrassSlab");
        GameRegistry.registerBlock(ground = (new BlockYRotation(Material.ground).setUnlocalizedName(modid_ + "Ground")), "blockGround");
        GameRegistry.registerBlock(groundNorthSlab = (new BlockSlab(Material.ground, WBlocks.ground.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(modid_ + "GroundNorthSlab")), ItemBlockSlab.class, "blockGroundNorthSlab");
        GameRegistry.registerBlock(groundEastSlab = (new BlockSlab(Material.ground, WBlocks.ground.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(modid_ + "GroundEastSlab")), ItemBlockSlab.class, "blockGroundEastSlab");
        GameRegistry.registerBlock(groundSouthSlab = (new BlockSlab(Material.ground, WBlocks.ground.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(modid_ + "GroundSouthSlab")), ItemBlockSlab.class, "blockGroundSouthSlab");
        GameRegistry.registerBlock(groundWestSlab = (new BlockSlab(Material.ground, WBlocks.ground.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(modid_ + "GroundWestSlab")), ItemBlockSlab.class, "blockGroundWestSlab");
        GameRegistry.registerBlock(ground2 = (new BlockYRotation(Material.ground).setUnlocalizedName(modid_ + "Ground2")), "blockGround2");
        GameRegistry.registerBlock(ground2NorthSlab = (new BlockSlab(Material.ground, WBlocks.ground2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(modid_ + "Ground2NorthSlab")), ItemBlockSlab.class, "blockGround2NorthSlab");
        GameRegistry.registerBlock(ground2EastSlab = (new BlockSlab(Material.ground, WBlocks.ground2.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(modid_ + "Ground2EastSlab")), ItemBlockSlab.class, "blockGround2EastSlab");
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
        GameRegistry.registerBlock(hbBridge = (new BlockGeneric(Material.iron).setOpaque(false).setUnlocalizedName(modid_ + "HBBridge")), "blockHBBridge");
        GameRegistry.registerBlock(hbCraft = (new BlockGeneric(Material.iron).setUnlocalizedName(modid_ + "HBCraft")), "blockHBCraft");
        GameRegistry.registerBlock(hbCraft2 = (new BlockGeneric(Material.iron).setUnlocalizedName(modid_ + "HBCraft2")), "blockHBCraft2");
        GameRegistry.registerBlock(hbDeco = (new BlockGeneric(Material.iron).setUnlocalizedName(modid_ + "HBDeco")), "blockHBDeco");
        GameRegistry.registerBlock(hbDeco2 = (new BlockGeneric(Material.iron).setUnlocalizedName(modid_ + "HBDeco2")), "blockHBDeco2");
        GameRegistry.registerBlock(hbGarden = (new BlockGeneric(Material.iron).setUnlocalizedName(modid_ + "HBGarden")), "blockHBGarden");
        GameRegistry.registerBlock(hbMerchant = (new BlockGeneric(Material.iron).setUnlocalizedName(modid_ + "HBMerchant")), "blockHBMerchant");
        GameRegistry.registerBlock(hbStand = (new BlockGeneric(Material.wood).setUnlocalizedName(modid_ + "HBStand")), "blockHBStand");
        GameRegistry.registerBlock(hbStandSlab = (new BlockSlab(Material.wood, WBlocks.hbStand.getDefaultState()).setUnlocalizedName(modid_ + "HBStandSlab")), ItemBlockSlab.class, "blockHBStandSlab");
        GameRegistry.registerBlock(pillar = (new BlockGeneric(Material.wood).setUnlocalizedName(modid_ + "Pillar")), "blockPillar");
        GameRegistry.registerBlock(pillarSlab = (new BlockSlab(Material.wood, WBlocks.pillar.getDefaultState()).setUnlocalizedName(modid_ + "PillarSlab")), ItemBlockSlab.class, "blockPillarSlab");
        GameRegistry.registerBlock(plank = (new BlockGeneric(Material.wood).setUnlocalizedName(modid_ + "Plank")), "blockPlank");
        GameRegistry.registerBlock(plant = (new BlockPlant(Material.plants).setUnlocalizedName(modid_ + "Plant")), "blockPlant");
        GameRegistry.registerBlock(plant2 = (new BlockPlant(Material.plants).setUnlocalizedName(modid_ + "Plant2")), "blockPlant2");
        GameRegistry.registerBlock(sufokiaColor = (new BlockGeneric(Material.sand).setUnlocalizedName(modid_ + "SufokiaColor")), "blockSufokiaColor");
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
        GameRegistry.registerBlock(sufokiaWave4 = (new BlockGeneric(Material.sand).setUnlocalizedName(modid_ + "SufokiaWave4")), "blockSufokiaWave4");
        GameRegistry.registerBlock(wood = (new BlockYRotation(Material.wood).setUnlocalizedName(modid_ + "Wood")), "blockWood");
        GameRegistry.registerBlock(woodEastSlab = (new BlockSlab(Material.wood, WBlocks.wood.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.EAST)).setUnlocalizedName(modid_ + "WoodEastSlab")), ItemBlockSlab.class, "blockWoodEastSlab");
        GameRegistry.registerBlock(woodNorthSlab = (new BlockSlab(Material.wood, WBlocks.wood.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.NORTH)).setUnlocalizedName(modid_ + "WoodNorthSlab")), ItemBlockSlab.class, "blockWoodNorthSlab");
        GameRegistry.registerBlock(woodSouthSlab = (new BlockSlab(Material.wood, WBlocks.wood.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.SOUTH)).setUnlocalizedName(modid_ + "WoodSouthSlab")), ItemBlockSlab.class, "blockWoodSouthSlab");
        GameRegistry.registerBlock(woodWestSlab = (new BlockSlab(Material.wood, WBlocks.wood.getDefaultState().withProperty(RotationUtil.PROP_Y_ROTATION, EnumFacing.WEST)).setUnlocalizedName(modid_ + "WoodWestSlab")), ItemBlockSlab.class, "blockWoodWestSlab");
//
//		// Special blocks
//		GameRegistry.registerBlock(polisher = (new BlockPolisher()), "BlockPolisher");
//		GameRegistry.registerBlock(dragoexpress = (new BlockDragoexpress()), "dragoexpressBlock");
//		GameRegistry.registerBlock(phoenix = (new BlockPhoenix()), "phoenixBlock");
//		GameRegistry.registerBlock(havenGemWorkbench = (new BlockHavenGemWorkbench()), "blockHavenGemWorkbench");
    }
}
