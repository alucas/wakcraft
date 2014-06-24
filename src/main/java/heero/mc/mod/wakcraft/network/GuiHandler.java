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
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	public static enum GuiId {
		POLISHER, HAVEN_GEM_WORKBENCH, HAVEN_BAG_CHEST, INVENTORY, ABILITIES, PROFESSION
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		GuiId guiId = (id >= 0 && id < GuiId.values().length) ? GuiId.values()[id] : null;
		if (guiId == null) {
			FMLLog.warning("Invalid Gui identifier : " + id);
			return null;
		}

		switch (guiId) {
		case POLISHER:
			return new ContainerWorkbench(player.inventory, world, PROFESSION.MINER);
		case INVENTORY:
			return new ContainerPlayerInventory(player);
		case HAVEN_GEM_WORKBENCH:
			TileEntityHavenGemWorkbench tile = (TileEntityHavenGemWorkbench)world.getTileEntity(x, y, z);
			return new ContainerHavenGemWorkbench(player.inventory, tile);
		case HAVEN_BAG_CHEST:
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBagChest)) {
				return null;
			}

			return new ContainerHavenBagChest(player.inventory, (TileEntityHavenBagChest) tileEntity);
		case ABILITIES:
			break;
		case PROFESSION:
			break;
		default:
			break;
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		GuiId guiId = (id >= 0 && id < GuiId.values().length) ? GuiId.values()[id] : null;
		if (guiId == null) {
			FMLLog.warning("Invalid Gui identifier : " + id);
			return null;
		}

		switch (guiId) {
		case POLISHER:
			return new GUIWorkbench(new ContainerWorkbench(player.inventory, world, PROFESSION.MINER), PROFESSION.MINER);
		case INVENTORY:
			return new GUIWakcraft(new ContainerPlayerInventory(player), player);
		case HAVEN_GEM_WORKBENCH:
			TileEntityHavenGemWorkbench tile = (TileEntityHavenGemWorkbench)world.getTileEntity(x, y, z);
			return new GUIHavenGemWorkbench(new ContainerHavenGemWorkbench(player.inventory, tile));
		case HAVEN_BAG_CHEST:
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBagChest)) {
				return null;
			}

			return new GUIHavenBagChests(new ContainerHavenBagChest(player.inventory, (TileEntityHavenBagChest) tileEntity));
		case ABILITIES:
			break;
		case PROFESSION:
			break;
		default:
			break;
		}

		return null;
	}
}