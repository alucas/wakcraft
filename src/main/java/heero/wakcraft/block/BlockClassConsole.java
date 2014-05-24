package heero.wakcraft.block;

import heero.wakcraft.WInfo;
import heero.wakcraft.Wakcraft;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockClassConsole extends Block {

	public BlockClassConsole() {
		super(Material.wood);

		setCreativeTab(WakcraftCreativeTabs.tabSpecialBlock);
		setBlockName("ClassConsole");
		setBlockTextureName(WInfo.MODID.toLowerCase() + ":classConsole");
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		Wakcraft.proxy.openClassSelectionGui(player);

		return true;
	}
}
