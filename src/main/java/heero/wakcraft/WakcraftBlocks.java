package heero.wakcraft;

import heero.wakcraft.block.BlockDragoexpress;
import heero.wakcraft.block.BlockPhoenix;
import heero.wakcraft.block.BlockSufokia;
import heero.wakcraft.block.BlockTannerWorkbench;
import heero.wakcraft.entity.item.ItemBlockSufokia;
import heero.wakcraft.reference.References;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.registry.GameRegistry;

public class WakcraftBlocks extends Blocks {
	public static Block tannerWorkbench, dragoexpress, phoenix, sufokia;

	public static void registerBlocks() {
		// Basic blocks
		GameRegistry.registerBlock(sufokia = (new BlockSufokia()), ItemBlockSufokia.class, "sufokiaColorBlock");
		
		// Special blocks
		GameRegistry.registerBlock(tannerWorkbench = (new BlockTannerWorkbench().setHardness(2.5F).setBlockName("tannerWorkbench").setBlockTextureName(References.MODID.toLowerCase() + ":tannerWorkbench")),	"tannerWorkbench");
		GameRegistry.registerBlock(dragoexpress = (new BlockDragoexpress()), "dragoexpressBlock");
		GameRegistry.registerBlock(phoenix = (new BlockPhoenix()), "phoenixBlock");
	}
}
