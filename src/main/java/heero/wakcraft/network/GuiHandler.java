package heero.wakcraft.network;

import heero.wakcraft.client.gui.GUIWorkbench;
import heero.wakcraft.inventory.ContainerWorkbench;
import heero.wakcraft.profession.ProfessionManager.PROFESSION;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	public static final int GUI_POLISHER = 0;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (id) {
		case GUI_POLISHER:
			return new ContainerWorkbench(player.inventory, world, PROFESSION.MINER);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (id) {
		case GUI_POLISHER:
			return new GUIWorkbench(new ContainerWorkbench(player.inventory, world, PROFESSION.MINER), PROFESSION.MINER);
		}

		return null;
	}
}