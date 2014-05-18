package heero.wakcraft;

import heero.wakcraft.block.BlockCarpet;
import heero.wakcraft.block.BlockDragoexpress;
import heero.wakcraft.block.BlockFence1;
import heero.wakcraft.block.BlockGeneric;
import heero.wakcraft.block.BlockGrass;
import heero.wakcraft.block.BlockHavenBag;
import heero.wakcraft.block.BlockHavenBagBarrier;
import heero.wakcraft.block.BlockHavenBagChest;
import heero.wakcraft.block.BlockHavenBagLock;
import heero.wakcraft.block.BlockHavenBagVisitors;
import heero.wakcraft.block.BlockHavenGemWorkbench;
import heero.wakcraft.block.BlockInvisibleWall;
import heero.wakcraft.block.BlockOre1;
import heero.wakcraft.block.BlockOre2;
import heero.wakcraft.block.BlockOre3;
import heero.wakcraft.block.BlockOre4;
import heero.wakcraft.block.BlockPalisade;
import heero.wakcraft.block.BlockPhoenix;
import heero.wakcraft.block.BlockPolisher;
import heero.wakcraft.block.BlockStairs2;
import heero.wakcraft.block.BlockSufokiaColor;
import heero.wakcraft.block.BlockSufokiaGround;
import heero.wakcraft.block.BlockSufokiaWave;
import heero.wakcraft.block.BlockTransparent;
import heero.wakcraft.block.BlockWakfu;
import heero.wakcraft.block.BlockYRotation;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.item.ItemBlockGrass;
import heero.wakcraft.item.ItemBlockOre1;
import heero.wakcraft.item.ItemBlockOre2;
import heero.wakcraft.item.ItemBlockOre3;
import heero.wakcraft.item.ItemBlockOre4;
import heero.wakcraft.item.ItemBlockPalisade;
import heero.wakcraft.item.ItemBlockSufokiaGround;
import heero.wakcraft.item.ItemBlockSufokiaWave;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.registry.GameRegistry;

public class WBlocks {

	public static Block dragoexpress, phoenix, sufokiaColor, sufokiaSun,
			sufokiaWave, sufokiaStair, ore1, ore2, ore3, ore4, carpet1,
			sufokiaGround, palisade, pillar, wood, hbstand, grass, fence,
			polisher, havenGemWorkbench, invisiblewall, havenbag, hbChest,
			hbLock, hbBridge, hbVisitors, hbBarrier, hbCraft,
			hbCraft2, hbGarden, hbDeco, hbDeco2, hbMerchant;
	public static Block wakfu, wakfuFull;

	public static void registerBlocks() {
		// Basic blocks
		GameRegistry.registerBlock(sufokiaColor = (new BlockSufokiaColor()), "blockSufokiaColor");
		GameRegistry.registerBlock(sufokiaSun = (new BlockGeneric(Material.sand).setBlockTextureName("sufokiaSun").setBlockName("SufokiaSun").setCreativeTab(WakcraftCreativeTabs.tabBlock)), "blockSufokiaSunBlock");
		GameRegistry.registerBlock(sufokiaWave = (new BlockSufokiaWave()), ItemBlockSufokiaWave.class, "blockSufokiaWaveBlock");
		GameRegistry.registerBlock(sufokiaStair = ((new BlockStairs2(sufokiaColor, 0)).setBlockName("SufokiaStair")), "blockSufokiaStair");
		GameRegistry.registerBlock(sufokiaGround = (new BlockSufokiaGround()), ItemBlockSufokiaGround.class, "sufokiaGroundBlock");
		GameRegistry.registerBlock(ore1 = (new BlockOre1()), ItemBlockOre1.class, "oreLvl1Block");
		GameRegistry.registerBlock(ore2 = (new BlockOre2()), ItemBlockOre2.class, "blockOre2");
		GameRegistry.registerBlock(ore3 = (new BlockOre3()), ItemBlockOre3.class, "blockOre3");
		GameRegistry.registerBlock(ore4 = (new BlockOre4()), ItemBlockOre4.class, "blockOre4");
		GameRegistry.registerBlock(carpet1 = (new BlockCarpet()), "carpet1Block");
		GameRegistry.registerBlock(palisade = (new BlockPalisade()), ItemBlockPalisade.class, "blockPalisade");
		GameRegistry.registerBlock(pillar = (new BlockGeneric(Material.wood).setBlockTextureName(ForgeDirection.UP, "pillarTop").setBlockTextureName("pillarSide").setBlockName("Pillar").setCreativeTab(WakcraftCreativeTabs.tabBlock)), "blockPillar");
		GameRegistry.registerBlock(wood = (new BlockYRotation(Material.wood).setBlockTextureName("wood").setBlockName("Wood").setCreativeTab(WakcraftCreativeTabs.tabBlock)), "blockWood");
		GameRegistry.registerBlock(hbstand = (new BlockYRotation(Material.wood).setBlockTextureName("hbstand").setBlockName("HBStand").setCreativeTab(WakcraftCreativeTabs.tabBlock)), "blockHBStand");
		GameRegistry.registerBlock(grass = (new BlockGrass()), ItemBlockGrass.class, "blockGrass");
		GameRegistry.registerBlock(fence = (new BlockFence1(WInfo.MODID.toLowerCase() + ":palisade1", Material.wood).setBlockName("Fence1")), "blockFence");
		GameRegistry.registerBlock(invisiblewall = (new BlockInvisibleWall()), "blockInvisibleWall");
		GameRegistry.registerBlock(havenbag = (new BlockHavenBag()), "blockHavenBag");
		GameRegistry.registerBlock(hbChest = (new BlockHavenBagChest()), "blockHavenBagChest");
		GameRegistry.registerBlock(hbLock = (new BlockHavenBagLock()), "blockHavenBagLock");
		GameRegistry.registerBlock(hbBridge = (new BlockTransparent(Material.iron).setBlockName("HBBridge").setCreativeTab(WakcraftCreativeTabs.tabBlock).setBlockTextureName("hbbridge")), "blockHBBridge");
		GameRegistry.registerBlock(hbGarden = (new BlockGeneric(Material.iron).setBlockTextureName(ForgeDirection.UP, "hbgarden_top").setBlockTextureName("hbgarden_side").setBlockName("HBGarden").setCreativeTab(WakcraftCreativeTabs.tabBlock)), "blockHBGarden");
		GameRegistry.registerBlock(hbDeco = (new BlockGeneric(Material.iron).setBlockTextureName(ForgeDirection.UP, "hbdeco_top").setBlockTextureName("hbdeco_side").setBlockName("HBDeco").setCreativeTab(WakcraftCreativeTabs.tabBlock)), "blockHBDeco");
		GameRegistry.registerBlock(hbDeco2 = (new BlockGeneric(Material.iron).setBlockTextureName(ForgeDirection.UP, "hbdeco2_top").setBlockTextureName("hbdeco_side").setBlockName("HBDeco2").setCreativeTab(WakcraftCreativeTabs.tabBlock)), "blockHBDeco2");
		GameRegistry.registerBlock(hbCraft = (new BlockGeneric(Material.iron).setBlockTextureName(ForgeDirection.UP, "hbcraft_top").setBlockTextureName("hbcraft_side").setBlockName("HBCraft").setCreativeTab(WakcraftCreativeTabs.tabBlock)), "blockHBCraft");
		GameRegistry.registerBlock(hbCraft2 = (new BlockGeneric(Material.iron).setBlockTextureName(ForgeDirection.UP, "hbcraft2_top").setBlockTextureName("hbcraft_side").setBlockName("HBCraft2").setCreativeTab(WakcraftCreativeTabs.tabBlock)), "blockHBCraft2");
		GameRegistry.registerBlock(hbMerchant = (new BlockGeneric(Material.iron).setBlockTextureName(ForgeDirection.UP, "hbmerchant_top").setBlockTextureName("hbmerchant_side").setBlockName("HBMerchant").setCreativeTab(WakcraftCreativeTabs.tabBlock)), "blockHBMerchant");
		GameRegistry.registerBlock(hbVisitors = (new BlockHavenBagVisitors()), "blockHavenBagVisitors");
		GameRegistry.registerBlock(hbBarrier = (new BlockHavenBagBarrier()), "blockHavenBagBarrier");
		GameRegistry.registerBlock(wakfu = (new BlockWakfu()), "blockWakfu");
		GameRegistry.registerBlock(wakfuFull = (new BlockGeneric(Material.wood).setCreativeTab(WakcraftCreativeTabs.tabBlock).setBlockTextureName("wakfuGreen")), "blockWakfuFull");

		// Special blocks
		GameRegistry.registerBlock(polisher = (new BlockPolisher().setBlockName("Polisher").setBlockTextureName(WInfo.MODID.toLowerCase() + ":polisher")), "BlockPolisher");
		GameRegistry.registerBlock(dragoexpress = (new BlockDragoexpress()), "dragoexpressBlock");
		GameRegistry.registerBlock(phoenix = (new BlockPhoenix()), "phoenixBlock");
		GameRegistry.registerBlock(havenGemWorkbench = (new BlockHavenGemWorkbench()), "blockHavenGemWorkbench");
	}
}
