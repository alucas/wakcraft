package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockClassConsole extends BlockGeneric {

	public BlockClassConsole() {
		super(Material.wood);

		setCreativeTab(WakcraftCreativeTabs.tabSpecialBlock);
		setBlockName("ClassConsole");
		setBlockTextureName("classConsole");
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
