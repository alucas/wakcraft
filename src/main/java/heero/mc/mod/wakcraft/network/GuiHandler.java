package heero.mc.mod.wakcraft.network;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.block.BlockWorkbench;
import heero.mc.mod.wakcraft.inventory.*;
import heero.mc.mod.wakcraft.quest.Quest;
import heero.mc.mod.wakcraft.quest.QuestTask;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBagChest;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import heero.mc.mod.wakcraft.util.QuestUtil;
import net.minecraft.block.state.IBlockState;
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
            case HAVEN_BAG_CHEST_ADVENTURER:
            case HAVEN_BAG_CHEST_COLLECTOR:
            case HAVEN_BAG_CHEST_EMERALD:
            case HAVEN_BAG_CHEST_GOLDEN:
            case HAVEN_BAG_CHEST_KIT:
            case HAVEN_BAG_CHEST_NORMAL:
            case HAVEN_BAG_CHEST_SMALL:
                final TileEntity tileEntityChest = world.getTileEntity(new BlockPos(x, y, z));
                if (tileEntityChest == null || !(tileEntityChest instanceof TileEntityHavenBagChest)) {
                    return null;
                }

                return new ContainerHavenBagChest(player.inventory, (TileEntityHavenBagChest) tileEntityChest, player);
            case HAVEN_GEM_WORKBENCH:
                final TileEntity tileEntityWorkbench = world.getTileEntity(new BlockPos(x, y, z));
                if (tileEntityWorkbench == null || !(tileEntityWorkbench instanceof TileEntityHavenGemWorkbench)) {
                    return null;
                }

                return new ContainerHavenGemWorkbench(player.inventory, (TileEntityHavenGemWorkbench) tileEntityWorkbench);
            case INVENTORY:
                return new ContainerPlayerInventory(player);
            case NPC_GIVE:
                final Quest quest = QuestUtil.getQuest(player, x);
                if (quest == null) {
                    return null;
                }

                final QuestTask task = QuestUtil.getCurrentTask(player, quest);
                if (task == null) {
                    return null;
                }

                if (!"give".equals(task.action)) {
                    return null;
                }

                return new ContainerNPCGive(player, quest, task);
            case SPELLS:
                return new ContainerSpells(player);
            case WORKBENCH:
                final IBlockState state = world.getBlockState(new BlockPos(x, y, z));
                if (!(state.getBlock() instanceof BlockWorkbench)) {
                    return null;
                }

                final BlockWorkbench block = (BlockWorkbench) state.getBlock();
                return new ContainerWorkbench(player.inventory, world, block.getProfession());
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