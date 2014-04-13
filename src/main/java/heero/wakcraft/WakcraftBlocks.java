package heero.wakcraft;

import heero.wakcraft.block.BlockCarpet;
import heero.wakcraft.block.BlockDragoexpress;
import heero.wakcraft.block.BlockOreLvl1;
import heero.wakcraft.block.BlockPhoenix;
import heero.wakcraft.block.BlockStairs2;
import heero.wakcraft.block.BlockSufokiaColor;
import heero.wakcraft.block.BlockSufokiaGround;
import heero.wakcraft.block.BlockSufokiaSun;
import heero.wakcraft.block.BlockSufokiaWave;
import heero.wakcraft.block.BlockTannerWorkbench;
import heero.wakcraft.entity.item.ItemBlockOreLvl1;
import heero.wakcraft.entity.item.ItemBlockSufokiaGround;
import heero.wakcraft.entity.item.ItemBlockSufokiaWave;
import heero.wakcraft.reference.References;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.registry.GameRegistry;

public class WakcraftBlocks extends Blocks {

	public static Block tannerWorkbench, dragoexpress, phoenix, sufokiaColor, sufokiaSun, sufokiaWave, sufokiaStair, oreLvl1, carpet1, sufokiaGround;

	public static void registerBlocks() {
		// Basic blocks
		GameRegistry.registerBlock(sufokiaColor = (new BlockSufokiaColor()), "blockSufokiaColor");
		GameRegistry.registerBlock(sufokiaSun = (new BlockSufokiaSun()), "blockSufokiaSunBlock");
		GameRegistry.registerBlock(sufokiaWave = (new BlockSufokiaWave()), ItemBlockSufokiaWave.class, "blockSufokiaWaveBlock");
		GameRegistry.registerBlock(sufokiaStair = ((new BlockStairs2(sufokiaColor, 0)).setBlockName("SufokiaStair")), "blockSufokiaStair");
		GameRegistry.registerBlock(sufokiaGround = (new BlockSufokiaGround()), ItemBlockSufokiaGround.class, "sufokiaGroundBlock");
		GameRegistry.registerBlock(oreLvl1 = (new BlockOreLvl1()), ItemBlockOreLvl1.class, "oreLvl1Block");
		GameRegistry.registerBlock(carpet1 = (new BlockCarpet()), "carpet1Block");
		
		// Special blocks
		GameRegistry.registerBlock(tannerWorkbench = (new BlockTannerWorkbench().setHardness(2.5F).setBlockName("tannerWorkbench").setBlockTextureName(References.MODID.toLowerCase() + ":tannerWorkbench")),	"tannerWorkbench");
		GameRegistry.registerBlock(dragoexpress = (new BlockDragoexpress()), "dragoexpressBlock");
		GameRegistry.registerBlock(phoenix = (new BlockPhoenix()), "phoenixBlock");
	}
}
