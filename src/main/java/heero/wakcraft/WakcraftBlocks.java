package heero.wakcraft;

import heero.wakcraft.block.BlockCarpet;
import heero.wakcraft.block.BlockDragoexpress;
import heero.wakcraft.block.BlockHBStand;
import heero.wakcraft.block.BlockOre1;
import heero.wakcraft.block.BlockOre2;
import heero.wakcraft.block.BlockOre3;
import heero.wakcraft.block.BlockOre4;
import heero.wakcraft.block.BlockPalisade;
import heero.wakcraft.block.BlockPhoenix;
import heero.wakcraft.block.BlockPillar;
import heero.wakcraft.block.BlockStairs2;
import heero.wakcraft.block.BlockSufokiaColor;
import heero.wakcraft.block.BlockSufokiaGround;
import heero.wakcraft.block.BlockSufokiaSun;
import heero.wakcraft.block.BlockSufokiaWave;
import heero.wakcraft.block.BlockTannerWorkbench;
import heero.wakcraft.block.BlockWood;
import heero.wakcraft.entity.item.ItemBlockOre1;
import heero.wakcraft.entity.item.ItemBlockOre2;
import heero.wakcraft.entity.item.ItemBlockOre3;
import heero.wakcraft.entity.item.ItemBlockOre4;
import heero.wakcraft.entity.item.ItemBlockSufokiaGround;
import heero.wakcraft.entity.item.ItemBlockSufokiaWave;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.registry.GameRegistry;

public class WakcraftBlocks extends Blocks {

	public static Block tannerWorkbench, dragoexpress, phoenix, sufokiaColor,
			sufokiaSun, sufokiaWave, sufokiaStair, ore1, ore2, ore3, ore4,
			carpet1, sufokiaGround, palisade, pillar, wood, hbstand;

	public static void registerBlocks() {
		// Basic blocks
		GameRegistry.registerBlock(sufokiaColor = (new BlockSufokiaColor()), "blockSufokiaColor");
		GameRegistry.registerBlock(sufokiaSun = (new BlockSufokiaSun()), "blockSufokiaSunBlock");
		GameRegistry.registerBlock(sufokiaWave = (new BlockSufokiaWave()), ItemBlockSufokiaWave.class, "blockSufokiaWaveBlock");
		GameRegistry.registerBlock(sufokiaStair = ((new BlockStairs2(sufokiaColor, 0)).setBlockName("SufokiaStair")), "blockSufokiaStair");
		GameRegistry.registerBlock(sufokiaGround = (new BlockSufokiaGround()), ItemBlockSufokiaGround.class, "sufokiaGroundBlock");
		GameRegistry.registerBlock(ore1 = (new BlockOre1()), ItemBlockOre1.class, "oreLvl1Block");
		GameRegistry.registerBlock(ore2 = (new BlockOre2()), ItemBlockOre2.class, "blockOre2");
		GameRegistry.registerBlock(ore3 = (new BlockOre3()), ItemBlockOre3.class, "blockOre3");
		GameRegistry.registerBlock(ore4 = (new BlockOre4()), ItemBlockOre4.class, "blockOre4");
		GameRegistry.registerBlock(carpet1 = (new BlockCarpet()), "carpet1Block");
		GameRegistry.registerBlock(palisade = (new BlockPalisade()), "blockPalisade");
		GameRegistry.registerBlock(pillar = (new BlockPillar()), "blockPillar");
		GameRegistry.registerBlock(wood = (new BlockWood()), "blockWood");
		GameRegistry.registerBlock(hbstand = (new BlockHBStand()), "blockHBStand");
		
		// Special blocks
		GameRegistry.registerBlock(tannerWorkbench = (new BlockTannerWorkbench().setHardness(2.5F).setBlockName("tannerWorkbench").setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":tannerWorkbench")),	"tannerWorkbench");
		GameRegistry.registerBlock(dragoexpress = (new BlockDragoexpress()), "dragoexpressBlock");
		GameRegistry.registerBlock(phoenix = (new BlockPhoenix()), "phoenixBlock");
	}
}
