package heero.mc.mod.wakcraft.network;

import heero.mc.mod.wakcraft.client.gui.GUIAbilities;
import heero.mc.mod.wakcraft.client.gui.GUIHavenBagChests;
import heero.mc.mod.wakcraft.client.gui.GUIProfession;
import heero.mc.mod.wakcraft.client.gui.GUIWakcraft;
import heero.mc.mod.wakcraft.client.gui.inventory.GUIHavenGemWorkbench;
import heero.mc.mod.wakcraft.client.gui.inventory.GUIInventory;
import heero.mc.mod.wakcraft.client.gui.inventory.GUIWorkbench;
import heero.mc.mod.wakcraft.inventory.ContainerHavenBagChest;
import heero.mc.mod.wakcraft.inventory.ContainerHavenGemWorkbench;
import heero.mc.mod.wakcraft.inventory.ContainerPlayerInventory;
import heero.mc.mod.wakcraft.inventory.ContainerWorkbench;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBagChest;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	public static enum GuiId {
		POLISHER,
		HAVEN_GEM_WORKBENCH,
		HAVEN_BAG_CHEST_NORMAL,
		HAVEN_BAG_CHEST_SMALL,
		HAVEN_BAG_CHEST_ADVENTURER,
		HAVEN_BAG_CHEST_KIT,
		HAVEN_BAG_CHEST_COLLECTOR,
		HAVEN_BAG_CHEST_GOLDEN,
		HAVEN_BAG_CHEST_EMERALD,
		INVENTORY,
		ABILITIES,
		PROFESSION
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		GuiId guiId = (id >= 0 && id < GuiId.values().length) ? GuiId.values()[id] : null;
		if (guiId == null) {
			FMLLog.warning("Invalid Gui identifier : " + id);
			return null;
		}

		TileEntity tileEntity;

		switch (guiId) {
		case POLISHER:
			return new ContainerWorkbench(player.inventory, world, PROFESSION.MINER);
		case INVENTORY:
			return new ContainerPlayerInventory(player);
		case HAVEN_GEM_WORKBENCH:
			tileEntity = (TileEntityHavenGemWorkbench)world.getTileEntity(x, y, z);
			if (tileEntity == null || !(tileEntity instanceof TileEntityHavenGemWorkbench)) {
				return null;
			}

			return new ContainerHavenGemWorkbench(player.inventory, (TileEntityHavenGemWorkbench) tileEntity);
		case HAVEN_BAG_CHEST_NORMAL:
			tileEntity = world.getTileEntity(x, y, z);
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

		TileEntity tileEntity;

		switch (guiId) {
		case POLISHER:
			return new GUIWorkbench(new ContainerWorkbench(player.inventory, world, PROFESSION.MINER), PROFESSION.MINER);
		case INVENTORY:
			return new GUIWakcraft(new GUIInventory(new ContainerPlayerInventory(player)), player, world, x, y, z);
		case HAVEN_GEM_WORKBENCH:
			tileEntity = (TileEntityHavenGemWorkbench)world.getTileEntity(x, y, z);
			if (tileEntity == null || !(tileEntity instanceof TileEntityHavenGemWorkbench)) {
				return null;
			}

			return new GUIHavenGemWorkbench(new ContainerHavenGemWorkbench(player.inventory, (IInventory) tileEntity));
		case HAVEN_BAG_CHEST_NORMAL:
		case HAVEN_BAG_CHEST_SMALL:
		case HAVEN_BAG_CHEST_ADVENTURER:
		case HAVEN_BAG_CHEST_KIT:
		case HAVEN_BAG_CHEST_COLLECTOR:
		case HAVEN_BAG_CHEST_GOLDEN:
		case HAVEN_BAG_CHEST_EMERALD:
			tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBagChest)) {
				return null;
			}

			return new GUIHavenBagChests(guiId, new ContainerHavenBagChest(player.inventory, (TileEntityHavenBagChest) tileEntity), player, world, x, y, z);
		case ABILITIES:
			return new GUIWakcraft(new GUIAbilities(player), player, world, x, y, z);
		case PROFESSION:
			return new GUIWakcraft(new GUIProfession(player, PROFESSION.CHEF), player, world, x, y, z);
		default:
			break;
		}

		return null;
	}
}