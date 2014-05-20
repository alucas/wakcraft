package heero.wakcraft.proxy;

import heero.wakcraft.WBlocks;
import heero.wakcraft.WConfig;
import heero.wakcraft.WItems;
import heero.wakcraft.Wakcraft;
import heero.wakcraft.entity.misc.EntityTextPopup;
import heero.wakcraft.entity.monster.BlackGobbly;
import heero.wakcraft.entity.monster.Gobball;
import heero.wakcraft.entity.monster.GobballWC;
import heero.wakcraft.entity.monster.Gobbette;
import heero.wakcraft.entity.monster.WhiteGobbly;
import heero.wakcraft.eventhandler.EntityEventHandler;
import heero.wakcraft.eventhandler.PlayerEventHandler;
import heero.wakcraft.eventhandler.WorldEventHandler;
import heero.wakcraft.fight.FightManager;
import heero.wakcraft.network.GuiHandler;
import heero.wakcraft.tileentity.TileEntityDragoexpress;
import heero.wakcraft.tileentity.TileEntityHavenBag;
import heero.wakcraft.tileentity.TileEntityHavenBagChest;
import heero.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import heero.wakcraft.tileentity.TileEntityPhoenix;
import heero.wakcraft.world.WorldProviderHavenBag;
import heero.wakcraft.world.gen.WorldGenHavenBag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void registerRenderers() {
	}

	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
		MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
		MinecraftForge.EVENT_BUS.register(new WorldEventHandler());
		MinecraftForge.EVENT_BUS.register(new FightManager());

		FMLCommonHandler.instance().bus().register(new PlayerEventHandler());
	}

	public void registerItems() {
		WItems.registerItems();
	}

	public void registerBlocks() {
		WBlocks.registerBlocks();
	}

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityDragoexpress.class, "tile_entity_dragoexpress");
		GameRegistry.registerTileEntity(TileEntityPhoenix.class, "tile_entity_phoenix");
		GameRegistry.registerTileEntity(TileEntityHavenGemWorkbench.class, "tileEntityHavenGemWorkbench");
		GameRegistry.registerTileEntity(TileEntityHavenBag.class, "tileEntityHavenBag");
		GameRegistry.registerTileEntity(TileEntityHavenBagChest.class, "tileEntityHavenBagChest");
	}

	public void registerEntities() {
		registerEntity(Gobball.class, "Gobball", 0xeaeaea, 0xc99a03);
		registerEntity(Gobbette.class, "Gobbette", 0xeaeaea, 0xc99ab3);
		registerEntity(WhiteGobbly.class, "WhiteGobbly", 0xeaeaea, 0xc29ab3);
		registerEntity(BlackGobbly.class, "BlackGobbly", 0xeaeaea, 0xc22ab3);
		registerEntity(GobballWC.class, "GobballWarChief", 0xeaeaea, 0xc22a23);

		EntityRegistry.registerGlobalEntityID(EntityTextPopup.class, "TextPopup", EntityRegistry.findGlobalUniqueEntityId());
	}

	@SuppressWarnings("unchecked")
	private void registerEntity(Class<? extends Entity> entityClass,
			String entityName, int bkEggColor, int fgEggColor) {
		int id = EntityRegistry.findGlobalUniqueEntityId();

		EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
		EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id,
				bkEggColor, fgEggColor));
	}

	public void registerGui(Wakcraft wc) {
		NetworkRegistry.INSTANCE.registerGuiHandler(wc, new GuiHandler());
	}

	public void registerKeyBindings() {
	}

	public void openHBVisitorsGui(EntityPlayer player) {
	}

	public void registerDimensions() {
		GameRegistry.registerWorldGenerator(new WorldGenHavenBag(), 0);

		DimensionManager.registerProviderType(WConfig.havenBagDimensionId, WorldProviderHavenBag.class, false);
		DimensionManager.registerDimension(WConfig.havenBagDimensionId, WConfig.havenBagDimensionId);
	}
}
