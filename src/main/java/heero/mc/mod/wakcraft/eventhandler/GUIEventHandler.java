package heero.mc.mod.wakcraft.eventhandler;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.network.packet.PacketOpenWindow;
import heero.mc.mod.wakcraft.spell.IActiveSpell;
import heero.mc.mod.wakcraft.spell.ICondition;
import heero.mc.mod.wakcraft.spell.IPassiveSpell;
import heero.mc.mod.wakcraft.spell.ISpell;
import heero.mc.mod.wakcraft.spell.effect.IEffect;
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
			final ISpell spell = (ISpell) event.itemStack.getItem();
			final int spellLevel = spell.getLevel(event.itemStack.getItemDamage());

			if (spell instanceof IPassiveSpell) {
				event.toolTip.add("Effets :");

				for (IEffect effect : ((IPassiveSpell) spell).getEffects()) {
					event.toolTip.add(effect.getDescription(spellLevel));
				}
			} else if (spell instanceof IActiveSpell) {
				event.toolTip.add("Effets :");

				for (IEffect effect : ((IActiveSpell) spell).getEffects()) {
					event.toolTip.add(effect.getDescription(spellLevel));
				}

				event.toolTip.add("Critical effets :");

				for (IEffect effect : ((IActiveSpell) spell).getEffectsCritical()) {
					event.toolTip.add(effect.getDescription(spellLevel));
				}

				event.toolTip.add("Usage conditions :");

				for (ICondition condition : ((IActiveSpell) spell).getConditions()) {
					event.toolTip.add(condition.getDescription(spellLevel));
				}
			}

			String description  = StatCollector.translateToLocal(spell.getDescription());

			for (String string : StringUtil.warpString(description, 26)) {
				event.toolTip.add(string);
			}
		}
	}
}
