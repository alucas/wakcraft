package heero.mc.mod.wakcraft;

import heero.mc.mod.wakcraft.manager.FightManager;
import heero.mc.mod.wakcraft.manager.HavenBagsManager;
import heero.mc.mod.wakcraft.network.PacketPipeline;
import heero.mc.mod.wakcraft.network.packet.PacketCloseWindow;
import heero.mc.mod.wakcraft.network.packet.PacketExtendedEntityProperty;
import heero.mc.mod.wakcraft.network.packet.PacketFight;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagProperties;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagTeleportation;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagVisitors;
import heero.mc.mod.wakcraft.network.packet.PacketOpenWindow;
import heero.mc.mod.wakcraft.network.packet.PacketProfession;
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

@Mod(modid = WInfo.MODID, name = WInfo.READABLE_NAME)
public class Wakcraft {
	// The instance of your mod that Forge uses.
	@Instance(value = WInfo.MODID)
	public static Wakcraft instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = WInfo.PROXY_CLIENT_PATH, serverSide = WInfo.PROXY_SERVER_PATH)
	public static CommonProxy proxy;

	public static final PacketPipeline packetPipeline = new PacketPipeline();

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

		packetPipeline.initialise();
		packetPipeline.registerPacket(PacketOpenWindow.class);
		packetPipeline.registerPacket(PacketCloseWindow.class);
		packetPipeline.registerPacket(PacketProfession.class);
		packetPipeline.registerPacket(PacketHavenBagTeleportation.class);
		packetPipeline.registerPacket(PacketHavenBagVisitors.class);
		packetPipeline.registerPacket(PacketExtendedEntityProperty.class);
		packetPipeline.registerPacket(PacketHavenBagProperties.class);
		packetPipeline.registerPacket(PacketFight.class);
		packetPipeline.postInitialise();
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