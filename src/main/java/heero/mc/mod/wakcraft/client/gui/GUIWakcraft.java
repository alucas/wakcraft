package heero.mc.mod.wakcraft.client.gui;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.network.packet.PacketOpenWindow;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GUIWakcraft extends GUITabs {
    protected static List<GuiId> guis = new ArrayList<GuiId>();

    static {
        guis.add(GuiId.INVENTORY);
        guis.add(GuiId.ABILITIES);
        guis.add(GuiId.PROFESSION);
        guis.add(GuiId.SPELLS);
    }

    public GUIWakcraft(GuiId guiId, GuiScreen guiScreen, EntityPlayer player, World world, BlockPos pos) {
        super(guiScreen, player, world, pos, guis.indexOf(guiId), guis);
    }

    @Override
    protected void onSelectTab(int tabId) {
        if (tabId < 0 || tabId >= tabs.size()) {
            WLog.warning("Cannot convert tabId %d to guiId");
            return;
        }

        GuiId guiId = tabs.get(tabId);
        switch (guiId) {
            case INVENTORY:
            case SPELLS:
                Wakcraft.packetPipeline.sendToServer(new PacketOpenWindow(tabs.get(tabId)));
                break;

            default:
                player.openGui(Wakcraft.instance, guiId.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
