package heero.wakcraft.block;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockTannerWorkbench extends Block {

	public BlockTannerWorkbench() {
		super(Material.wood);
        setCreativeTab(WakcraftCreativeTabs.tabMisc);
	}

	public boolean onBlockActivated(World worldObj, int blockXPos,
			int blockYPos, int blockZPos, EntityPlayer player, int p_149727_6_,
			float p_149727_7_, float p_149727_8_, float p_149727_9_) {

		if (worldObj.isRemote) { // Server
			return true;
		}

		// Client
		player.openGui(Wakcraft.instance, 0, worldObj, blockXPos, blockYPos,
				blockZPos);

		return true;
	}
}
