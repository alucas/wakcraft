package heero.mc.mod.wakcraft;

import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.manager.HavenBagsManager;
import heero.mc.mod.wakcraft.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = WInfo.MODID, name = WInfo.READABLE_NAME)
public class Wakcraft {
	@Instance(value = WInfo.MODID)
	public static Wakcraft instance;

	@SidedProxy(clientSide = WInfo.PROXY_CLIENT_PATH, serverSide = WInfo.PROXY_SERVER_PATH)
	public static CommonProxy proxy;

	public static final SimpleNetworkWrapper packetPipeline = NetworkRegistry.INSTANCE.newSimpleChannel("Wakcraft");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// Server mod version
		Version.init(event.getVersionProperties());
		event.getModMetadata().version = Version.fullVersionString();

		WConfig.loadConfig(event.getSuggestedConfigurationFile());

		proxy.registerEvents();
		proxy.registerBlocks();
		proxy.registerItems();
		proxy.registerEntities();
		proxy.registerTileEntities();
		proxy.registerRenderers();
		proxy.registerGui(this);
		proxy.registerDimensions();
		proxy.registerPackets(packetPipeline);
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerKeyBindings();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}

	@EventHandler
	public void onFMLServerStartedEvent(FMLServerStartedEvent event) {
		HavenBagsManager.setup();
	}

	@EventHandler
	public void onFMLServerStoppingEvent(FMLServerStoppingEvent event) {
		FightManager.INSTANCE.teardown();
	}

	@EventHandler
	public void onFMLServerStoppedEvent(FMLServerStoppedEvent event) {
		HavenBagsManager.teardown();
	}
}