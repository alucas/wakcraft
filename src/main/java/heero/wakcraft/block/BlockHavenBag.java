package heero.wakcraft.block;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.havenbag.HavenBagManager;
import heero.wakcraft.tileentity.TileEntityHavenBag;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockHavenBag extends BlockContainer {
	public BlockHavenBag() {
		super(Material.wood);

		setCreativeTab(WakcraftCreativeTabs.tabSpecialBlock);
		setBlockName("HavenBag");
		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":havengemworkbench");
		setBlockUnbreakable();
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile != null && (tile instanceof TileEntityHavenBag)) {
				TileEntityHavenBag tileHavenBag = (TileEntityHavenBag) tile;

				if (tileHavenBag.isLocked) {
					System.out.println("That haven bag is actualy locaked");
					return true;
				}

				HavenBagManager.teleportPlayerToHavenBag(player, tileHavenBag.uid);
			}
		}

		return true;
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntityHavenBag();
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if (world.isRemote) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Can't place this block manualy"));
		}

		return false;
	}
}
