package heero.wakcraft.eventhandler;

import heero.wakcraft.manager.HavenBagsManager;
import net.minecraftforge.event.world.WorldEvent.Load;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldEventHandler {
	@SubscribeEvent
	public void onWorldLoad(Load event) {
		HavenBagsManager.init(event.world);
	}
}