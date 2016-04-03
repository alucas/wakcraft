package heero.mc.mod.wakcraft;

import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.havenbag.HavenBagsManager;
import heero.mc.mod.wakcraft.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = Reference.MODID, name = Reference.READABLE_NAME)
public class Wakcraft {
    @Instance(value = Reference.MODID)
    public static Wakcraft instance;

    @SidedProxy(clientSide = Reference.PROXY_CLIENT_PATH, serverSide = Reference.PROXY_SERVER_PATH)
    public static CommonProxy proxy;

    public static final SimpleNetworkWrapper packetPipeline = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Server mod version
        Version.init(event.getVersionProperties());
        event.getModMetadata().version = Version.fullVersionString();

        WConfig.loadConfig(event.getSuggestedConfigurationFile());

        proxy.registerPreInitEvents();
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
    public void init(FMLInitializationEvent event) {
        proxy.registerInitEvents();
        proxy.registerKeyBindings();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    public void onFMLServerStartedEvent(FMLServerStartingEvent event) {
        proxy.registerCommand(event);
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