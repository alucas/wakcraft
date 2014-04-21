package heero.wakcraft.eventhandler;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;

public class WakcraftEventHandler {
	public static void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
		MinecraftForge.EVENT_BUS.register(new GUIEventHandler());

		FMLCommonHandler.instance().bus().register(new PlayerEventHandler());
	}
}
