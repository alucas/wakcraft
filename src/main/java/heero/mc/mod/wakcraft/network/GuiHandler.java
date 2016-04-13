package heero.mc.mod.wakcraft.network;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.inventory.*;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBagChest;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        final GuiId guiId = (id >= 0 && id < GuiId.values().length) ? GuiId.values()[id] : null;
        if (guiId == null) {
            WLog.warning("Invalid Gui identifier : " + id);
            return null;
        }

        switch (guiId) {
            case POLISHER:
                return new ContainerWorkbench(player.inventory, world, PROFESSION.MINER);
            case INVENTORY:
                return new ContainerPlayerInventory(player);
            case HAVEN_GEM_WORKBENCH:
                final TileEntity tileEntityWorkbench = world.getTileEntity(new BlockPos(x, y, z));
                if (tileEntityWorkbench == null || !(tileEntityWorkbench instanceof TileEntityHavenGemWorkbench)) {
                    return null;
                }

                return new ContainerHavenGemWorkbench(player.inventory, (TileEntityHavenGemWorkbench) tileEntityWorkbench);
            case HAVEN_BAG_CHEST_NORMAL:
            case HAVEN_BAG_CHEST_SMALL:
            case HAVEN_BAG_CHEST_ADVENTURER:
            case HAVEN_BAG_CHEST_KIT:
            case HAVEN_BAG_CHEST_COLLECTOR:
            case HAVEN_BAG_CHEST_GOLDEN:
            case HAVEN_BAG_CHEST_EMERALD:
                final TileEntity tileEntityChest = world.getTileEntity(new BlockPos(x, y, z));
                if (tileEntityChest == null || !(tileEntityChest instanceof TileEntityHavenBagChest)) {
                    return null;
                }

                return new ContainerHavenBagChest(player.inventory, (TileEntityHavenBagChest) tileEntityChest, player);
            case SPELLS:
                return new ContainerSpells(player);
            default:
                break;
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        final GuiId guiId = (id >= 0 && id < GuiId.values().length) ? GuiId.values()[id] : null;
        if (guiId == null) {
            WLog.warning("Invalid Gui identifier : " + id);
            return null;
        }

        return Wakcraft.proxy.getGui(guiId, player, world, new BlockPos(x, y, z));
    }
}