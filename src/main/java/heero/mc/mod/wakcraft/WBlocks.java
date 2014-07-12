package heero.mc.mod.wakcraft;

import heero.mc.mod.wakcraft.block.BlockCarpet;
import heero.mc.mod.wakcraft.block.BlockClassConsole;
import heero.mc.mod.wakcraft.block.BlockDebugSlab;
import heero.mc.mod.wakcraft.block.BlockDragoexpress;
import heero.mc.mod.wakcraft.block.BlockFence1;
import heero.mc.mod.wakcraft.block.BlockFightInsideWall;
import heero.mc.mod.wakcraft.block.BlockFightStart;
import heero.mc.mod.wakcraft.block.BlockFightWall;
import heero.mc.mod.wakcraft.block.BlockGeneric;
import heero.mc.mod.wakcraft.block.BlockHavenBag;
import heero.mc.mod.wakcraft.block.BlockHavenBagBarrier;
import heero.mc.mod.wakcraft.block.BlockHavenBagChest;
import heero.mc.mod.wakcraft.block.BlockHavenBagLock;
import heero.mc.mod.wakcraft.block.BlockHavenBagVisitors;
import heero.mc.mod.wakcraft.block.BlockHavenGemWorkbench;
import heero.mc.mod.wakcraft.block.BlockInvisibleWall;
import heero.mc.mod.wakcraft.block.BlockOre1;
import heero.mc.mod.wakcraft.block.BlockOre2;
import heero.mc.mod.wakcraft.block.BlockOre3;
import heero.mc.mod.wakcraft.block.BlockOre4;
import heero.mc.mod.wakcraft.block.BlockPalisade;
import heero.mc.mod.wakcraft.block.BlockPhoenix;
import heero.mc.mod.wakcraft.block.BlockPlant;
import heero.mc.mod.wakcraft.block.BlockPolisher;
import heero.mc.mod.wakcraft.block.BlockSlab;
import heero.mc.mod.wakcraft.block.BlockSlabGrass;
import heero.mc.mod.wakcraft.block.BlockStairs2;
import heero.mc.mod.wakcraft.block.BlockSufokiaColor;
import heero.mc.mod.wakcraft.block.BlockSufokiaGround;
import heero.mc.mod.wakcraft.block.BlockSufokiaWave;
import heero.mc.mod.wakcraft.block.BlockTransparent;
import heero.mc.mod.wakcraft.block.BlockYRotation;
import heero.mc.mod.wakcraft.block.BlockYRotationSlab;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.mc.mod.wakcraft.item.ItemBlockOre1;
import heero.mc.mod.wakcraft.item.ItemBlockOre2;
import heero.mc.mod.wakcraft.item.ItemBlockOre3;
import heero.mc.mod.wakcraft.item.ItemBlockOre4;
import heero.mc.mod.wakcraft.item.ItemBlockPalisade;
import heero.mc.mod.wakcraft.item.ItemBlockSlab;
import heero.mc.mod.wakcraft.item.ItemBlockSufokiaGround;
import heero.mc.mod.wakcraft.item.ItemBlockSufokiaWave;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.registry.GameRegistry;

public class WBlocks {

	public static Block dragoexpress, phoenix, sufokiaColor, sufokiaSun,
			sufokiaWave, sufokiaStair, ore1, ore2, ore3, ore4, carpet1,
			sufokiaGround, palisade, pillar, wood, hbstand, slabGrass,
			slabDirt, fence, polisher, havenGemWorkbench, invisiblewall,
			havenbag, hbChest, hbLock, hbBridge, hbVisitors, hbBarrier,
			hbCraft, hbCraft2, hbGarden, hbDeco, hbDeco2, hbMerchant,
			fightMovement, fightDirection, fightWall, classConsole, ground1,
			ground1Slab, ground2, ground2Slab, ground3, ground3Slab, ground4,
			ground4Slab, ground5, ground5Slab, ground6, ground6Slab, ground7,
			ground7Slab, ground8, ground8Slab, ground9, ground9Slab, ground10,
			ground10Slab, ground11, ground11Slab, ground12, ground12Slab,
			ground13, ground13Slab, ground14, ground14Slab, box, plank,
			fightInsideWall, fightStart1, fightStart2, ground15Slab,
			ground16Slab, ground17Slab, ground18Slab, plant1, plant2,
			sufokiaWave1NorthSlab, sufokiaWave1SouthSlab, sufokiaWave1EastSlab,
			sufokiaWave1WestSlab, sufokiaSunSlab, pillarSlab,
			sufokiaWave2NorthSlab, sufokiaWave2SouthSlab, sufokiaWave2EastSlab,
			sufokiaWave2WestSlab, sufokiaWave3NorthSlab, sufokiaWave3SouthSlab,
			sufokiaWave3EastSlab, sufokiaWave3WestSlab;
	public static Block debug, debugSlab;

	public static void registerBlocks() {
		String modid = WInfo.MODID.toLowerCase() + ":";
		// Basic blocks
		GameRegistry.registerBlock(sufokiaColor = (new BlockSufokiaColor()), "blockSufokiaColor");
		GameRegistry.registerBlock(sufokiaSun = (new BlockGeneric(Material.sand).setBlockTextureName("sufokiaSun").setBlockName("SufokiaSun")), "blockSufokiaSunBlock");
		GameRegistry.registerBlock(sufokiaSunSlab = (new BlockSlab(Material.sand, WBlocks.sufokiaSun).setBlockTextureName("sufokiaSun").setBlockName("SufokiaSunSlab")), ItemBlockSlab.class, "blockSufokiaSunSlab");
		GameRegistry.registerBlock(sufokiaWave = (new BlockSufokiaWave()), ItemBlockSufokiaWave.class, "blockSufokiaWaveBlock");
		GameRegistry.registerBlock(sufokiaStair = ((new BlockStairs2(sufokiaColor, 0)).setBlockName("SufokiaStair")), "blockSufokiaStair");
		GameRegistry.registerBlock(sufokiaGround = (new BlockSufokiaGround()), ItemBlockSufokiaGround.class, "sufokiaGroundBlock");
		GameRegistry.registerBlock(ore1 = (new BlockOre1()), ItemBlockOre1.class, "oreLvl1Block");
		GameRegistry.registerBlock(ore2 = (new BlockOre2()), ItemBlockOre2.class, "blockOre2");
		GameRegistry.registerBlock(ore3 = (new BlockOre3()), ItemBlockOre3.class, "blockOre3");
		GameRegistry.registerBlock(ore4 = (new BlockOre4()), ItemBlockOre4.class, "blockOre4");
		GameRegistry.registerBlock(carpet1 = (new BlockCarpet()), "carpet1Block");
		GameRegistry.registerBlock(palisade = (new BlockPalisade()), ItemBlockPalisade.class, "blockPalisade");
		GameRegistry.registerBlock(pillar = (new BlockGeneric(Material.wood).setBlockTextureName(ForgeDirection.UP, "pillarTop").setBlockTextureName("pillarSide").setBlockName("Pillar")), "blockPillar");
		GameRegistry.registerBlock(pillarSlab = (new BlockSlab(Material.wood, WBlocks.pillar).setBlockTextureName(ForgeDirection.UP, "pillarTop").setBlockTextureName("pillarSide").setBlockName("PillarSlab")), ItemBlockSlab.class, "blockPillarSlab");
		GameRegistry.registerBlock(wood = (new BlockYRotation(Material.wood).setBlockTextureName("wood").setBlockName("Wood").setCreativeTab(WakcraftCreativeTabs.tabBlock)), "blockWood");
		GameRegistry.registerBlock(hbstand = (new BlockYRotation(Material.wood).setBlockTextureName("hbstand").setBlockName("HBStand").setCreativeTab(WakcraftCreativeTabs.tabBlock)), "blockHBStand");
		GameRegistry.registerBlock(slabGrass = (new BlockSlabGrass()), ItemBlockSlab.class, "blockGrass");
		GameRegistry.registerBlock(slabDirt = (new BlockSlab(Material.ground, Blocks.dirt).setBlockTextureName("dirtSlab").setBlockName("SlabDirt")), ItemBlockSlab.class, "blockSlabDirt");
		GameRegistry.registerBlock(fence = (new BlockFence1(modid + "palisade1", Material.wood).setBlockName("Fence1")), "blockFence");
		GameRegistry.registerBlock(invisiblewall = (new BlockInvisibleWall()), "blockInvisibleWall");
		GameRegistry.registerBlock(havenbag = (new BlockHavenBag()), "blockHavenBag");
		GameRegistry.registerBlock(hbChest = (new BlockHavenBagChest()), "blockHavenBagChest");
		GameRegistry.registerBlock(hbLock = (new BlockHavenBagLock()), "blockHavenBagLock");
		GameRegistry.registerBlock(hbBridge = (new BlockTransparent(Material.iron).setBlockName("HBBridge").setBlockTextureName("hbbridge")), "blockHBBridge");
		GameRegistry.registerBlock(hbGarden = (new BlockGeneric(Material.iron).setBlockTextureName(ForgeDirection.UP, "hbgarden_top").setBlockTextureName("hbgarden_side").setBlockName("HBGarden")), "blockHBGarden");
		GameRegistry.registerBlock(hbDeco = (new BlockGeneric(Material.iron).setBlockTextureName(ForgeDirection.UP, "hbdeco_top").setBlockTextureName("hbdeco_side").setBlockName("HBDeco")), "blockHBDeco");
		GameRegistry.registerBlock(hbDeco2 = (new BlockGeneric(Material.iron).setBlockTextureName(ForgeDirection.UP, "hbdeco2_top").setBlockTextureName("hbdeco_side").setBlockName("HBDeco2")), "blockHBDeco2");
		GameRegistry.registerBlock(hbCraft = (new BlockGeneric(Material.iron).setBlockTextureName(ForgeDirection.UP, "hbcraft_top").setBlockTextureName("hbcraft_side").setBlockName("HBCraft")), "blockHBCraft");
		GameRegistry.registerBlock(hbCraft2 = (new BlockGeneric(Material.iron).setBlockTextureName(ForgeDirection.UP, "hbcraft2_top").setBlockTextureName("hbcraft_side").setBlockName("HBCraft2")), "blockHBCraft2");
		GameRegistry.registerBlock(hbMerchant = (new BlockGeneric(Material.iron).setBlockTextureName(ForgeDirection.UP, "hbmerchant_top").setBlockTextureName("hbmerchant_side").setBlockName("HBMerchant")), "blockHBMerchant");
		GameRegistry.registerBlock(hbVisitors = (new BlockHavenBagVisitors()), "blockHavenBagVisitors");
		GameRegistry.registerBlock(hbBarrier = (new BlockHavenBagBarrier()), "blockHavenBagBarrier");
		GameRegistry.registerBlock(debug = (new BlockGeneric(Material.wood).setBlockTextureName("wakfuGreen").setBlockName("Debug")), "blockWakfuFull");
		GameRegistry.registerBlock(debugSlab = (new BlockDebugSlab()), ItemBlockSlab.class, "blockWakfuSlab");
		GameRegistry.registerBlock(fightMovement = (new BlockGeneric(Material.ground).setBlockTextureName("movement").setBlockName("FightMovement")), "blockFightMovement");
		GameRegistry.registerBlock(fightDirection = (new BlockGeneric(Material.ground).setBlockTextureName("direction").setBlockName("FightDirection")), "blockFightDirection");
		GameRegistry.registerBlock(fightWall = (new BlockFightWall()), "blockFightWall");
		GameRegistry.registerBlock(fightInsideWall = (new BlockFightInsideWall()), "blockFightInsideWall");
		GameRegistry.registerBlock(fightStart1 = (new BlockFightStart(1)), "blockFightStart1");
		GameRegistry.registerBlock(fightStart2 = (new BlockFightStart(2)), "blockFightStart2");
		GameRegistry.registerBlock(classConsole = (new BlockClassConsole()), "blockClassConsole");
		GameRegistry.registerBlock(ground1 = (new BlockGeneric(Material.ground).setBlockTextureName("ground1").setBlockName("Ground1")), "blockGround1");
		GameRegistry.registerBlock(ground1Slab = (new BlockYRotationSlab(Material.ground, WBlocks.ground1).setBlockTextureName("ground1").setBlockName("Ground1Slab")), ItemBlockSlab.class, "blockGround1Slab");
		GameRegistry.registerBlock(ground2 = (new BlockGeneric(Material.ground).setBlockTextureName("ground2").setBlockName("Ground2")), "blockGround2");
		GameRegistry.registerBlock(ground2Slab = (new BlockYRotationSlab(Material.ground, WBlocks.ground2).setBlockTextureName("ground2").setBlockName("Ground2Slab")), ItemBlockSlab.class, "blockGround2Slab");
		GameRegistry.registerBlock(ground3 = (new BlockGeneric(Material.ground).setBlockTextureName("ground3").setBlockName("Ground3")), "blockGround3");
		GameRegistry.registerBlock(ground3Slab = (new BlockSlab(Material.ground, WBlocks.ground3).setBlockTextureName("ground3").setBlockName("Ground3Slab")), ItemBlockSlab.class, "blockGround3Slab");
		GameRegistry.registerBlock(ground4 = (new BlockGeneric(Material.ground).setBlockTextureName("ground4").setBlockName("Ground4")), "blockGround4");
		GameRegistry.registerBlock(ground4Slab = (new BlockSlab(Material.ground, WBlocks.ground4).setBlockTextureName("ground4").setBlockName("Ground4Slab")), ItemBlockSlab.class, "blockGround4Slab");
		GameRegistry.registerBlock(ground5 = (new BlockGeneric(Material.ground).setBlockTextureName("ground5").setBlockName("Ground5")), "blockGround5");
		GameRegistry.registerBlock(ground5Slab = (new BlockYRotationSlab(Material.ground, WBlocks.ground5).setBlockTextureName("ground5").setBlockName("Ground5Slab")), ItemBlockSlab.class, "blockGround5Slab");
		GameRegistry.registerBlock(ground6 = (new BlockGeneric(Material.ground).setBlockTextureName("ground6").setBlockName("Ground6")), "blockGround6");
		GameRegistry.registerBlock(ground6Slab = (new BlockYRotationSlab(Material.ground, WBlocks.ground6).setBlockTextureName("ground6").setBlockName("Ground6Slab")), ItemBlockSlab.class, "blockGround6Slab");
		GameRegistry.registerBlock(ground7 = (new BlockGeneric(Material.ground).setBlockTextureName("ground7").setBlockName("Ground7")), "blockGround7");
		GameRegistry.registerBlock(ground7Slab = (new BlockYRotationSlab(Material.ground, WBlocks.ground7).setBlockTextureName("ground7").setBlockName("Ground7Slab")), ItemBlockSlab.class, "blockGround7Slab");
		GameRegistry.registerBlock(ground8 = (new BlockGeneric(Material.ground).setBlockTextureName("ground8").setBlockName("Ground8")), "blockGround8");
		GameRegistry.registerBlock(ground8Slab = (new BlockYRotationSlab(Material.ground, WBlocks.ground8).setBlockTextureName("ground8").setBlockName("Ground8Slab")), ItemBlockSlab.class, "blockGround8Slab");
		GameRegistry.registerBlock(ground9 = (new BlockGeneric(Material.ground).setBlockTextureName("ground9").setBlockName("Ground9")), "blockGround9");
		GameRegistry.registerBlock(ground9Slab = (new BlockYRotationSlab(Material.ground, WBlocks.ground9).setBlockTextureName("ground9").setBlockName("Ground9Slab")), ItemBlockSlab.class, "blockGround9Slab");
		GameRegistry.registerBlock(ground10 = (new BlockGeneric(Material.ground).setBlockTextureName("ground10").setBlockName("Ground10")), "blockGround10");
		GameRegistry.registerBlock(ground10Slab = (new BlockYRotationSlab(Material.ground, WBlocks.ground10).setBlockTextureName("ground10").setBlockName("Ground10Slab")), ItemBlockSlab.class, "blockGround10Slab");
		GameRegistry.registerBlock(ground11 = (new BlockGeneric(Material.ground).setBlockTextureName("ground11").setBlockName("Ground11")), "blockGround11");
		GameRegistry.registerBlock(ground11Slab = (new BlockSlab(Material.ground, WBlocks.ground11).setBlockTextureName("ground11").setBlockName("Ground11Slab")), ItemBlockSlab.class, "blockGround11Slab");
		GameRegistry.registerBlock(ground12 = (new BlockGeneric(Material.ground).setBlockTextureName("ground12").setBlockName("Ground12")), "blockGround12");
		GameRegistry.registerBlock(ground12Slab = (new BlockSlab(Material.ground, WBlocks.ground12).setBlockTextureName("ground12").setBlockName("Ground12Slab")), ItemBlockSlab.class, "blockGround12Slab");
		GameRegistry.registerBlock(ground13 = (new BlockGeneric(Material.ground).setBlockTextureName("ground13").setBlockName("Ground13")), "blockGround13");
		GameRegistry.registerBlock(ground13Slab = (new BlockSlab(Material.ground, WBlocks.ground13).setBlockTextureName("ground13").setBlockName("Ground13Slab")), ItemBlockSlab.class, "blockGround13Slab");
		GameRegistry.registerBlock(ground14 = (new BlockGeneric(Material.ground).setBlockTextureName("ground14").setBlockName("Ground14")), "blockGround14");
		GameRegistry.registerBlock(ground14Slab = (new BlockSlab(Material.ground, WBlocks.ground14).setBlockTextureName("ground14").setBlockName("Ground14Slab")), ItemBlockSlab.class, "blockGround14Slab");
		GameRegistry.registerBlock(box = (new BlockGeneric(Material.wood).setBlockTextureName("box").setBlockName("Box").setCreativeTab(WakcraftCreativeTabs.tabBlock)), "blockBox");
		GameRegistry.registerBlock(plank = (new BlockGeneric(Material.wood).setBlockTextureName("plank").setBlockName("Plank").setCreativeTab(WakcraftCreativeTabs.tabBlock)), "blockPlank");
		GameRegistry.registerBlock(ground15Slab = (new BlockSlab(Material.ground, Blocks.sandstone).setBlockTextureName("ground15").setBlockName("Ground15Slab")), ItemBlockSlab.class, "blockGround15Slab");
		GameRegistry.registerBlock(ground16Slab = (new BlockSlab(Material.ground, Blocks.quartz_block).setBlockTextureName("ground16").setBlockName("Ground16Slab")), ItemBlockSlab.class, "blockGround16Slab");
		GameRegistry.registerBlock(ground17Slab = (new BlockSlab(Material.ground, WBlocks.sufokiaGround).setBlockTextureName("sufokiaGround0").setBlockName("Ground17Slab")), ItemBlockSlab.class, "blockGround17Slab");
		GameRegistry.registerBlock(ground18Slab = (new BlockSlab(Material.ground, WBlocks.sufokiaGround, 1).setBlockTextureName("sufokiaGround1").setBlockName("Ground18Slab")), ItemBlockSlab.class, "blockGround18Slab");
		GameRegistry.registerBlock(plant1 = (new BlockPlant().setBlockTextureName("plant1").setBlockName("Plant1")), "blockPlant1");
		GameRegistry.registerBlock(plant2 = (new BlockPlant().setBlockTextureName("plant2").setBlockName("Plant2")), "blockPlant2");
		GameRegistry.registerBlock(sufokiaWave1NorthSlab = (new BlockYRotationSlab(Material.ground, WBlocks.sufokiaWave, 2, ForgeDirection.NORTH).setBlockTextureName("sufokiaWave1").setBlockName("sufokiaWave1NorthSlab")), ItemBlockSlab.class, "sufokiaWave1NorthSlab");
		GameRegistry.registerBlock(sufokiaWave1SouthSlab = (new BlockYRotationSlab(Material.ground, WBlocks.sufokiaWave, 0, ForgeDirection.SOUTH).setBlockTextureName("sufokiaWave1").setBlockName("sufokiaWave1SouthSlab")), ItemBlockSlab.class, "sufokiaWave1SouthSlab");
		GameRegistry.registerBlock(sufokiaWave1EastSlab = (new BlockYRotationSlab(Material.ground, WBlocks.sufokiaWave, 3, ForgeDirection.EAST).setBlockTextureName("sufokiaWave1").setBlockName("sufokiaWave1EastSlab")), ItemBlockSlab.class, "sufokiaWave1EastSlab");
		GameRegistry.registerBlock(sufokiaWave1WestSlab = (new BlockYRotationSlab(Material.ground, WBlocks.sufokiaWave, 1, ForgeDirection.WEST).setBlockTextureName("sufokiaWave1").setBlockName("sufokiaWave1WestSlab")), ItemBlockSlab.class, "sufokiaWave1WestSlab");
		GameRegistry.registerBlock(sufokiaWave2NorthSlab = (new BlockYRotationSlab(Material.ground, WBlocks.sufokiaWave, 6, ForgeDirection.NORTH).setBlockTextureName("sufokiaWave2").setBlockName("sufokiaWave2NorthSlab")), ItemBlockSlab.class, "sufokiaWave2NorthSlab");
		GameRegistry.registerBlock(sufokiaWave2SouthSlab = (new BlockYRotationSlab(Material.ground, WBlocks.sufokiaWave, 4, ForgeDirection.SOUTH).setBlockTextureName("sufokiaWave2").setBlockName("sufokiaWave2SouthSlab")), ItemBlockSlab.class, "sufokiaWave2SouthSlab");
		GameRegistry.registerBlock(sufokiaWave2EastSlab = (new BlockYRotationSlab(Material.ground, WBlocks.sufokiaWave, 7, ForgeDirection.EAST).setBlockTextureName("sufokiaWave2").setBlockName("sufokiaWave2EastSlab")), ItemBlockSlab.class, "sufokiaWave2EastSlab");
		GameRegistry.registerBlock(sufokiaWave2WestSlab = (new BlockYRotationSlab(Material.ground, WBlocks.sufokiaWave, 5, ForgeDirection.WEST).setBlockTextureName("sufokiaWave2").setBlockName("sufokiaWave2WestSlab")), ItemBlockSlab.class, "sufokiaWave2WestSlab");
		GameRegistry.registerBlock(sufokiaWave3NorthSlab = (new BlockYRotationSlab(Material.ground, WBlocks.sufokiaWave, 10, ForgeDirection.NORTH).setBlockTextureName("sufokiaWave3").setBlockName("sufokiaWave3NorthSlab")), ItemBlockSlab.class, "sufokiaWave3NorthSlab");
		GameRegistry.registerBlock(sufokiaWave3SouthSlab = (new BlockYRotationSlab(Material.ground, WBlocks.sufokiaWave, 8, ForgeDirection.SOUTH).setBlockTextureName("sufokiaWave3").setBlockName("sufokiaWave3SouthSlab")), ItemBlockSlab.class, "sufokiaWave3SouthSlab");
		GameRegistry.registerBlock(sufokiaWave3EastSlab = (new BlockYRotationSlab(Material.ground, WBlocks.sufokiaWave, 11, ForgeDirection.EAST).setBlockTextureName("sufokiaWave3").setBlockName("sufokiaWave3EastSlab")), ItemBlockSlab.class, "sufokiaWave3EastSlab");
		GameRegistry.registerBlock(sufokiaWave3WestSlab = (new BlockYRotationSlab(Material.ground, WBlocks.sufokiaWave, 9, ForgeDirection.WEST).setBlockTextureName("sufokiaWave3").setBlockName("sufokiaWave3WestSlab")), ItemBlockSlab.class, "sufokiaWave3WestSlab");

		// Special blocks
		GameRegistry.registerBlock(polisher = (new BlockPolisher()), "BlockPolisher");
		GameRegistry.registerBlock(dragoexpress = (new BlockDragoexpress()), "dragoexpressBlock");
		GameRegistry.registerBlock(phoenix = (new BlockPhoenix()), "phoenixBlock");
		GameRegistry.registerBlock(havenGemWorkbench = (new BlockHavenGemWorkbench()), "blockHavenGemWorkbench");
	}
}
