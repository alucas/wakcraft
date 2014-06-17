package heero.mc.mod.wakcraft.characteristic;

import heero.mc.mod.wakcraft.entity.property.CharacteristicsProperty;
import heero.mc.mod.wakcraft.helper.CharacteristicsHelper;
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
		if (!CharacteristicsHelper.haveCharacteristics(event.entity)) {
			return;
		}

		event.entity.registerExtendedProperties(CharacteristicsProperty.IDENTIFIER, new CharacteristicsProperty());

		CharacteristicsHelper.init(event.entity);
	}
}
