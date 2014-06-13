package heero.mc.mod.wakcraft;

import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.manager.HavenBagsManager;
import heero.mc.mod.wakcraft.network.handler.HandlerClientExtendedEntityProperty;
import heero.mc.mod.wakcraft.network.handler.HandlerClientHavenBagProperties;
import heero.mc.mod.wakcraft.network.handler.HandlerClientOpenWindow;
import heero.mc.mod.wakcraft.network.handler.HandlerClientProfession;
import heero.mc.mod.wakcraft.network.handler.HandlerServerCloseWindow;
import heero.mc.mod.wakcraft.network.handler.HandlerServerHavenBagTeleportation;
import heero.mc.mod.wakcraft.network.handler.HandlerServerHavenBagVisitors;
import heero.mc.mod.wakcraft.network.handler.HandlerServerOpenWindow;
import heero.mc.mod.wakcraft.network.handler.fight.HandlerClientFightChangeStage;
import heero.mc.mod.wakcraft.network.handler.fight.HandlerClientFightSelectPosition;
import heero.mc.mod.wakcraft.network.handler.fight.HandlerClientFightStart;
import heero.mc.mod.wakcraft.network.handler.fight.HandlerClientFightStop;
import heero.mc.mod.wakcraft.network.handler.fight.HandlerServerFightSelectPosition;
import heero.mc.mod.wakcraft.network.packet.PacketCloseWindow;
import heero.mc.mod.wakcraft.network.packet.PacketExtendedEntityProperty;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagProperties;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagTeleportation;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagVisitors;
import heero.mc.mod.wakcraft.network.packet.PacketOpenWindow;
import heero.mc.mod.wakcraft.network.packet.PacketProfession;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightChangeStage;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightSelectPosition;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightStart;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightStop;
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
import cpw.mods.fml.relauncher.Side;

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

		packetPipeline.registerMessage(HandlerServerHavenBagTeleportation.class, PacketHavenBagTeleportation.class, 0, Side.SERVER);
		packetPipeline.registerMessage(HandlerClientOpenWindow.class, PacketOpenWindow.class, 1, Side.CLIENT);
		packetPipeline.registerMessage(HandlerServerOpenWindow.class, PacketOpenWindow.class, 2, Side.SERVER);
		packetPipeline.registerMessage(HandlerServerCloseWindow.class, PacketCloseWindow.class, 3, Side.SERVER);
		packetPipeline.registerMessage(HandlerClientProfession.class, PacketProfession.class, 4, Side.CLIENT);
		packetPipeline.registerMessage(HandlerServerHavenBagVisitors.class, PacketHavenBagVisitors.class, 5, Side.SERVER);
		packetPipeline.registerMessage(HandlerClientExtendedEntityProperty.class, PacketExtendedEntityProperty.class, 6, Side.CLIENT);
		packetPipeline.registerMessage(HandlerClientHavenBagProperties.class, PacketHavenBagProperties.class, 7, Side.CLIENT);
		packetPipeline.registerMessage(HandlerClientFightStart.class, PacketFightStart.class, 8, Side.CLIENT);
		packetPipeline.registerMessage(HandlerClientFightStop.class, PacketFightStop.class, 9, Side.CLIENT);
		packetPipeline.registerMessage(HandlerClientFightChangeStage.class, PacketFightChangeStage.class, 10, Side.CLIENT);
		packetPipeline.registerMessage(HandlerClientFightSelectPosition.class, PacketFightSelectPosition.class, 11, Side.CLIENT);
		packetPipeline.registerMessage(HandlerServerFightSelectPosition.class, PacketFightSelectPosition.class, 12, Side.SERVER);
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