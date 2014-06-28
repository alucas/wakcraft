package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.mc.mod.wakcraft.network.GuiId;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockPolisher extends BlockGeneric {

	public BlockPolisher() {
		super(Material.wood);
		setCreativeTab(WakcraftCreativeTabs.tabSpecialBlock);
		setBlockName("Polisher");
		setBlockTextureName("polisher");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if (world.isRemote) {
			return true;
		}

		player.openGui(Wakcraft.instance, GuiId.POLISHER.ordinal(), world, x, y, z);

		return true;
	}
}
