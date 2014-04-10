package heero.wakcraft;

import heero.wakcraft.block.BlockTannerWorkbench;
import heero.wakcraft.entity.item.ClefLaineuse;
import heero.wakcraft.entity.item.CorneDeBouftou;
import heero.wakcraft.entity.item.CuirDeBouftou;
import heero.wakcraft.entity.item.LaineDeBouftou;
import heero.wakcraft.entity.monster.Bouffette;
import heero.wakcraft.entity.monster.BouftonBlanc;
import heero.wakcraft.entity.monster.BouftonNoir;
import heero.wakcraft.entity.monster.Bouftou;
import heero.wakcraft.entity.monster.BouftouCG;
import heero.wakcraft.network.GuiHandler;
import heero.wakcraft.proxy.CommonProxy;
import heero.wakcraft.reference.References;
import heero.wakcraft.tileentity.TileEntityTannerWorkbench;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
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
		
		// Items
		GameRegistry.registerItem(new LaineDeBouftou(), "LaineDeBouftou");
		GameRegistry.registerItem(new CuirDeBouftou(), "CuirDeBouftou");
		GameRegistry.registerItem(new CorneDeBouftou(), "CorneDeBouftou");
		GameRegistry.registerItem(new ClefLaineuse(), "ClefLaineuse");
		
		// Blocks
		GameRegistry.registerBlock(new BlockTannerWorkbench().setHardness(2.5F).setBlockName("tanner_workbench").setBlockTextureName("crafting_table"), "tanner_workbench");
		
		// Mobs
		registerEntity(Bouftou.class, "Bouftou", 0xeaeaea, 0xc99a03);
		registerEntity(Bouffette.class, "Bouffette", 0xeaeaea, 0xc99ab3);
		registerEntity(BouftonBlanc.class, "BouftonBlanc", 0xeaeaea, 0xc29ab3);
		registerEntity(BouftonNoir.class, "BouftonNoir", 0xeaeaea, 0xc22ab3);
		registerEntity(BouftouCG.class, "BouftouChefDeGuerre", 0xeaeaea, 0xc22a23);
		
		// TileEntity
		GameRegistry.registerTileEntity(TileEntityTannerWorkbench.class, "tile_entity_tanner_workbench");
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}

	public void registerEntity(Class<? extends Entity> entityClass,
			String entityName, int bkEggColor, int fgEggColor) {
		int id = EntityRegistry.findGlobalUniqueEntityId();

		EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
		EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id,
				bkEggColor, fgEggColor));
	}
}