package heero.wakcraft.network;

import heero.wakcraft.client.gui.inventory.GuiTannerWorkbench;
import heero.wakcraft.inventory.ContainerTannerWorkbench;
import heero.wakcraft.tileentity.TileEntityTannerWorkbench;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z){

		TileEntity tile_entity = world.getTileEntity(x, y, z);
		if (tile_entity instanceof TileEntityTannerWorkbench){
			return new ContainerTannerWorkbench(
					(TileEntityTannerWorkbench) tile_entity, player.inventory,
					world);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z){

		TileEntity tile_entity = world.getTileEntity(x, y, z);
		if (tile_entity instanceof TileEntityTannerWorkbench){
			return new GuiTannerWorkbench(player.inventory,
					(TileEntityTannerWorkbench) tile_entity, world);
		}

		return null;
	}
}