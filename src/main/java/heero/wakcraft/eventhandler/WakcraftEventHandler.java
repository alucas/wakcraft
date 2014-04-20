package heero.wakcraft.eventhandler;

import net.minecraftforge.common.MinecraftForge;

public class WakcraftEventHandler {
	public static void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
	}
}
