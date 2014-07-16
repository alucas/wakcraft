package heero.mc.mod.wakcraft.eventhandler;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.network.packet.PacketOpenWindow;
import heero.mc.mod.wakcraft.spell.ISpell;
import heero.mc.mod.wakcraft.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
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

	@SubscribeEvent
	public void onItemTooltipEvent(ItemTooltipEvent event) {
		if (event.itemStack.getItem() instanceof ISpell) {
			String description  = StatCollector.translateToLocal(((ISpell) event.itemStack.getItem()).getDescription());

			for (String string : StringUtil.warpString(description, 26)) {
				event.toolTip.add(string);
			}
		}
	}
}
