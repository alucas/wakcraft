package heero.mc.mod.wakcraft.characteristic;

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
    public void onEntityConstructing(final EntityConstructing event) {
        if (!CharacteristicUtil.hasCharacteristics(event.entity)) {
            return;
        }

        CharacteristicUtil.registerCharacteristics(event.entity);
        CharacteristicUtil.initCharacteristics(event.entity);
    }
}
