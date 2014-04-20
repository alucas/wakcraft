package heero.wakcraft.eventhandler;

import heero.wakcraft.client.gui.GUIWakcraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GUIEventHandler {
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
		if (event.gui != null && event.gui instanceof GuiInventory) {
			GuiInventory gui = (GuiInventory) event.gui;
			Minecraft mc = Minecraft.getMinecraft();

			if (mc.playerController.isInCreativeMode()) {
				return;
			}

			mc.displayGuiScreen(new GUIWakcraft(mc.thePlayer));

			event.setCanceled(true);
		}
	}
}
