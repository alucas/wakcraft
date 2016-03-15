package heero.mc.mod.wakcraft.characteristic;

import heero.mc.mod.wakcraft.entity.property.CharacteristicsProperty;
import heero.mc.mod.wakcraft.util.CharacteristicUtil;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CharacteristicsEventsHandler {
    /**
     * Handler called when an entity is created.
     *
     * @param event Event object.
     */
    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        if (!CharacteristicUtil.haveCharacteristics(event.entity)) {
            return;
        }

        event.entity.registerExtendedProperties(CharacteristicsProperty.IDENTIFIER, new CharacteristicsProperty());

        CharacteristicUtil.initCharacteristics(event.entity);
    }
}
