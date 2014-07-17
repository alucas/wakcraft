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
import net.minecraft.util.EnumChatFormatting;
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

	private static final String stringEffects = StatCollector.translateToLocal("effect.category.effects");
	private static final String stringCriticalEffects = StatCollector.translateToLocal("effect.category.effectsCritical");
	private static final String stringConditions = StatCollector.translateToLocal("effect.category.conditions");
	@SubscribeEvent
	public void onItemTooltipEvent(ItemTooltipEvent event) {
		if (event.itemStack.getItem() instanceof ISpell) {
			final ISpell spell = (ISpell) event.itemStack.getItem();
			final int spellLevel = spell.getLevel(event.itemStack.getItemDamage());

			if (spell instanceof IPassiveSpell) {
				event.toolTip.add(EnumChatFormatting.BLUE + stringEffects);

				for (IEffect effect : ((IPassiveSpell) spell).getEffects()) {
					event.toolTip.add(effect.getDescription(spellLevel));
				}

				event.toolTip.add("");
			} else if (spell instanceof IActiveSpell) {
				event.toolTip.add(EnumChatFormatting.BLUE + stringEffects);

				for (IEffect effect : ((IActiveSpell) spell).getEffects()) {
					event.toolTip.add(effect.getDescription(spellLevel));
				}

				event.toolTip.add("");
				event.toolTip.add(EnumChatFormatting.BLUE + stringCriticalEffects);

				for (IEffect effect : ((IActiveSpell) spell).getEffectsCritical()) {
					event.toolTip.add(effect.getDescription(spellLevel));
				}

				event.toolTip.add("");

				if (((IActiveSpell) spell).getConditions().size() > 0) {
					event.toolTip.add(EnumChatFormatting.BLUE + stringConditions);

					for (ICondition condition : ((IActiveSpell) spell).getConditions()) {
						event.toolTip.add(condition.getDescription(spellLevel));
					}

					event.toolTip.add("");
				}
			}

			String description  = StatCollector.translateToLocal(spell.getDescription());

			for (String string : StringUtil.warpString(description, 26)) {
				event.toolTip.add(string);
			}
		}
	}
}
