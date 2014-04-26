package heero.wakcraft.block;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.network.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockPolisher extends Block {

	public BlockPolisher() {
		super(Material.wood);
		setCreativeTab(WakcraftCreativeTabs.tabSpecialBlock);
	}

	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if (world.isRemote) {
			return true;
		}

		player.openGui(Wakcraft.instance, GuiHandler.GUI_POLISHER, world, x, y, z);

		return true;
	}
}
