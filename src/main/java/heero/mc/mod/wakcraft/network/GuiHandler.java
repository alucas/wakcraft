package heero.mc.mod.wakcraft.network;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.inventory.ContainerHavenBagChest;
import heero.mc.mod.wakcraft.inventory.ContainerHavenGemWorkbench;
import heero.mc.mod.wakcraft.inventory.ContainerPlayerInventory;
import heero.mc.mod.wakcraft.inventory.ContainerSpells;
import heero.mc.mod.wakcraft.inventory.ContainerWorkbench;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBagChest;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		GuiId guiId = (id >= 0 && id < GuiId.values().length) ? GuiId.values()[id] : null;
		if (guiId == null) {
			WLog.warning("Invalid Gui identifier : " + id);
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

			return new ContainerHavenBagChest(player.inventory, (TileEntityHavenBagChest) tileEntity);
		case SPELLS:
			return new ContainerSpells(player);
		default:
			break;
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		GuiId guiId = (id >= 0 && id < GuiId.values().length) ? GuiId.values()[id] : null;
		if (guiId == null) {
			WLog.warning("Invalid Gui identifier : " + id);
			return null;
		}

		return Wakcraft.proxy.getGui(guiId, player, world, x, y, z);
	}
}