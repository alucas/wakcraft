package heero.wakcraft.block;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.WakcraftBlocks;
import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.network.GuiHandler;
import heero.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockHavenGemWorkbench extends BlockContainer {
	public BlockHavenGemWorkbench() {
		super(Material.wood);
		setCreativeTab(WakcraftCreativeTabs.tabSpecialBlock);
		setBlockName("HavenGemWorkbench");
		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":havengemworkbench");
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if (world.isRemote) {
			return true;
		}

		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null && !(tile instanceof TileEntityHavenGemWorkbench)) {
			return true;
		}

		player.openGui(Wakcraft.instance, GuiHandler.GUI_HAVEN_GEM_WORKBENCH, world, x, y, z);

		return true;
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntityHavenGemWorkbench();
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if (world.isRemote) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Can't place this block manualy"));
		}

		return false;
	}
}
