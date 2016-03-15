package heero.mc.mod.wakcraft.eventhandler;

import heero.mc.mod.wakcraft.havenbag.HavenBagsManager;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEventHandler {
    @SubscribeEvent
    public void onWorldLoad(Load event) {
        HavenBagsManager.init(event.world);
    }
}