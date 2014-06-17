package heero.mc.mod.wakcraft.ability;

import heero.mc.mod.wakcraft.entity.property.AbilitiesProperty;
import heero.mc.mod.wakcraft.helper.AbilityHelper;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AbilityEventsHandler {
	/**
	 * Handler called when an entity is created.
	 * 
	 * @param event	Event object.
	 */
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (!AbilityHelper.haveAbilities(event.entity)) {
			return;
		}

		event.entity.registerExtendedProperties(AbilitiesProperty.IDENTIFIER, new AbilitiesProperty());
	}
}
