package heero.wakcraft.network;

import heero.wakcraft.client.gui.inventory.GuiTannerWorkbench;
import heero.wakcraft.inventory.ContainerTannerWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {

		return new ContainerTannerWorkbench(player.inventory, world);
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {

			return new GuiTannerWorkbench(player.inventory, world);
	}
}