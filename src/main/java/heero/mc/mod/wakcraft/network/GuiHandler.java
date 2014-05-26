package heero.mc.mod.wakcraft.network;

import heero.mc.mod.wakcraft.client.gui.GUIHavenBagChests;
import heero.mc.mod.wakcraft.client.gui.GUIWakcraft;
import heero.mc.mod.wakcraft.client.gui.inventory.GUIHavenGemWorkbench;
import heero.mc.mod.wakcraft.client.gui.inventory.GUIWorkbench;
import heero.mc.mod.wakcraft.inventory.ContainerHavenBagChest;
import heero.mc.mod.wakcraft.inventory.ContainerHavenGemWorkbench;
import heero.mc.mod.wakcraft.inventory.ContainerPlayerInventory;
import heero.mc.mod.wakcraft.inventory.ContainerWorkbench;
import heero.mc.mod.wakcraft.manager.ProfessionManager.PROFESSION;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBagChest;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	public static final int GUI_POLISHER = 0;
	public static final int GUI_HAVEN_GEM_WORKBENCH = 1;
	public static final int GUI_HAVEN_BAG_CHEST = 2;
	public static final int GUI_INVENTORY = 3;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (id) {
		case GUI_POLISHER:
			return new ContainerWorkbench(player.inventory, world, PROFESSION.MINER);
		case GUI_INVENTORY:
			return new ContainerPlayerInventory(player);
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
		case GUI_INVENTORY:
			return new GUIWakcraft(new ContainerPlayerInventory(player), player);
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