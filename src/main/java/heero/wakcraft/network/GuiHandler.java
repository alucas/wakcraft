package heero.wakcraft.network;

import heero.wakcraft.client.gui.GUIHavenBagChests;
import heero.wakcraft.client.gui.inventory.GUIHavenGemWorkbench;
import heero.wakcraft.client.gui.inventory.GUIWorkbench;
import heero.wakcraft.inventory.ContainerHavenBagChest;
import heero.wakcraft.inventory.ContainerHavenGemWorkbench;
import heero.wakcraft.inventory.ContainerWorkbench;
import heero.wakcraft.profession.ProfessionManager.PROFESSION;
import heero.wakcraft.tileentity.TileEntityHavenBagChest;
import heero.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	public static final int GUI_POLISHER = 0;
	public static final int GUI_HAVEN_GEM_WORKBENCH = 1;
	public static final int GUI_HAVEN_BAG_CHEST = 2;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (id) {
		case GUI_POLISHER:
			return new ContainerWorkbench(player.inventory, world, PROFESSION.MINER);
		case GUI_HAVEN_GEM_WORKBENCH:
			TileEntityHavenGemWorkbench tile = (TileEntityHavenGemWorkbench)world.getTileEntity(x, y, z);
			return new ContainerHavenGemWorkbench(player.inventory, tile);
		case GUI_HAVEN_BAG_CHEST:
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBagChest)) {
				return null;
			}

			return new ContainerHavenBagChest(player.inventory, (TileEntityHavenBagChest) tileEntity);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (id) {
		case GUI_POLISHER:
			return new GUIWorkbench(new ContainerWorkbench(player.inventory, world, PROFESSION.MINER), PROFESSION.MINER);
		case GUI_HAVEN_GEM_WORKBENCH:
			TileEntityHavenGemWorkbench tile = (TileEntityHavenGemWorkbench)world.getTileEntity(x, y, z);
			return new GUIHavenGemWorkbench(new ContainerHavenGemWorkbench(player.inventory, tile));
		case GUI_HAVEN_BAG_CHEST:
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBagChest)) {
				return null;
			}

			return new GUIHavenBagChests(new ContainerHavenBagChest(player.inventory, (TileEntityHavenBagChest) tileEntity));
		}

		return null;
	}
}