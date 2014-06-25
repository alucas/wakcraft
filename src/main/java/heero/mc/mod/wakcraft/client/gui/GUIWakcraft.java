package heero.mc.mod.wakcraft.client.gui;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.network.packet.PacketOpenWindow;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIWakcraft extends GUITabs {
	protected static List<GuiId> guis = new ArrayList<GuiId>();
	static {
		guis.add(GuiId.INVENTORY);
		guis.add(GuiId.ABILITIES);
		guis.add(GuiId.PROFESSION);
		guis.add(GuiId.SPELLS);
	}

	public GUIWakcraft(GuiId guiId, GuiScreen guiScreen, EntityPlayer player, World world, int x, int y, int z) {
		super(guiScreen, player, world, x, y, z, guis.indexOf(guiId), guis);
	}

	@Override
	protected void onSelectTab(int tabId) {
		if (tabId < 0 || tabId >= tabs.size()) {
			FMLLog.warning("Cannot convert tabId %d to guiId");
			return;
		}

		GuiId guiId = tabs.get(tabId);
		switch (guiId) {
		case INVENTORY:
		case SPELLS:
			Wakcraft.packetPipeline.sendToServer(new PacketOpenWindow(tabs.get(tabId)));
			break;

		default:
			player.openGui(Wakcraft.instance, guiId.ordinal(), world, x, y, z);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
