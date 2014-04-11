package heero.wakcraft;

import heero.wakcraft.block.BlockDragoexpress;
import heero.wakcraft.block.BlockPhoenix;
import heero.wakcraft.block.BlockSufokia;
import heero.wakcraft.block.BlockTannerWorkbench;
import heero.wakcraft.entity.item.ItemBlockSufokia;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.registry.GameRegistry;

public class WakcraftBlocks extends Blocks {
	public static Block tannerWorkbench, dragoexpress, phoenix, sufokia;

	public static void registerBlocks() {
		GameRegistry.registerBlock(tannerWorkbench = (new BlockTannerWorkbench().setHardness(2.5F).setBlockName("tanner_workbench").setBlockTextureName("crafting_table")),	"tanner_workbench");
		GameRegistry.registerBlock(dragoexpress = (new BlockDragoexpress()), "dragoexpress");
		GameRegistry.registerBlock(phoenix = (new BlockPhoenix()), "phoenix");
		GameRegistry.registerBlock(sufokia = (new BlockSufokia()), ItemBlockSufokia.class, "sufokia");
	}
}
