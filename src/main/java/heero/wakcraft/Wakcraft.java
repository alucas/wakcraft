package heero.wakcraft;

import heero.wakcraft.network.GuiHandler;
import heero.wakcraft.proxy.CommonProxy;
import heero.wakcraft.reference.References;
import heero.wakcraft.tileentity.TileEntityDragoexpress;
import heero.wakcraft.tileentity.TileEntityPhoenix;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = References.MODID, name = References.READABLE_NAME, version = References.VERSION)
public class Wakcraft {
	// The instance of your mod that Forge uses.
	@Instance(value = References.MODID)
	public static Wakcraft instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = References.PROXY_CLIENT_PATH, serverSide = References.PROXY_SERVER_PATH)
	public static CommonProxy proxy;

	private GuiHandler guiHandler = new GuiHandler();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);

		WakcraftItems.registerItems();
		WakcraftBlocks.registerBlocks();
		WakcraftEntities.registerEntities();

		GameRegistry.registerTileEntity(TileEntityDragoexpress.class, "tile_entity_dragoexpress");
		GameRegistry.registerTileEntity(TileEntityPhoenix.class, "tile_entity_phoenix");
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
}