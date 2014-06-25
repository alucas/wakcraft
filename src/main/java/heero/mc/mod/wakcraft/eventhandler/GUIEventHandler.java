package heero.mc.mod.wakcraft.eventhandler;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.network.packet.PacketOpenWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GUIEventHandler {
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
		if (event.gui != null && event.gui instanceof GuiInventory) {
			Minecraft mc = Minecraft.getMinecraft();

			if (mc.playerController.isInCreativeMode()) {
				return;
			}

			Wakcraft.packetPipeline.sendToServer(new PacketOpenWindow(GuiId.INVENTORY));

			event.setCanceled(true);
		}
	}
}
