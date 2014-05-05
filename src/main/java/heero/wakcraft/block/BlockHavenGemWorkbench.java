package heero.wakcraft.block;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.network.GuiHandler;
import heero.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHavenGemWorkbench extends BlockContainer {
	private IIcon iconSide;
	private IIcon iconTop;

	public BlockHavenGemWorkbench() {
		super(Material.wood);
		setCreativeTab(WakcraftCreativeTabs.tabSpecialBlock);
		setBlockName("HavenGemWorkbench");
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

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer) {
		iconSide = registerer.registerIcon(WakcraftInfo.MODID.toLowerCase() + ":havengemworkbench");
		iconTop = registerer.registerIcon(WakcraftInfo.MODID.toLowerCase() + ":havengemworkbench_top");
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if (side == 1) {
			return iconTop;
		}

		return iconSide;
	}
}
