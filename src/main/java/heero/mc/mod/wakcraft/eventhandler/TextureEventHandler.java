package heero.mc.mod.wakcraft.eventhandler;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.item.ItemWArmor;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TextureEventHandler {
	@SubscribeEvent
	public void onTextureStitchEvent(TextureStitchEvent.Pre event) {
		TextureMap map = event.map;
		if (map.getTextureType() == 1) { // Items
			String modid = WInfo.MODID.toLowerCase() + ":";
			ItemWArmor.helmetIcon = map.registerIcon(modid + "empty_armor_slot_helmet");
			ItemWArmor.amuletIcon = map.registerIcon(modid + "empty_armor_slot_amulet");
			ItemWArmor.epauletIcon = map.registerIcon(modid + "empty_armor_slot_epaulet");
			ItemWArmor.chestplateIcon = map.registerIcon(modid + "empty_armor_slot_chestplate");
			ItemWArmor.capeIcon = map.registerIcon(modid + "empty_armor_slot_cape");
			ItemWArmor.ringIcon = map.registerIcon(modid + "empty_armor_slot_ring");
			ItemWArmor.beltIcon = map.registerIcon(modid + "empty_armor_slot_leggings");
			ItemWArmor.bootsIcon = map.registerIcon(modid + "empty_armor_slot_boots");
			ItemWArmor.weaponIcon = map.registerIcon(modid + "empty_armor_slot_weapon");
			ItemWArmor.petIcon = map.registerIcon(modid + "empty_armor_slot_pet");
		}
	}
}
