package heero.wakcraft.proxy;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.WakcraftBlocks;
import heero.wakcraft.WakcraftItems;
import heero.wakcraft.entity.misc.EntityTextPopup;
import heero.wakcraft.entity.monster.BlackGobbly;
import heero.wakcraft.entity.monster.Gobball;
import heero.wakcraft.entity.monster.GobballWC;
import heero.wakcraft.entity.monster.Gobbette;
import heero.wakcraft.entity.monster.WhiteGobbly;
import heero.wakcraft.eventhandler.PlayerEventHandler;
import heero.wakcraft.network.GuiHandler;
import heero.wakcraft.tileentity.TileEntityDragoexpress;
import heero.wakcraft.tileentity.TileEntityHavenBag;
import heero.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import heero.wakcraft.tileentity.TileEntityPhoenix;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
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

		FMLCommonHandler.instance().bus().register(new PlayerEventHandler());
	}

	public void registerItems() {
		WakcraftItems.registerItems();
	}

	public void registerBlocks() {
		WakcraftBlocks.registerBlocks();
	}

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityDragoexpress.class, "tile_entity_dragoexpress");
		GameRegistry.registerTileEntity(TileEntityPhoenix.class, "tile_entity_phoenix");
		GameRegistry.registerTileEntity(TileEntityHavenGemWorkbench.class, "tileEntityHavenGemWorkbench");
		GameRegistry.registerTileEntity(TileEntityHavenBag.class, "tileEntityHavenBag");
	}

	public void registerEntities() {
		registerEntity(Gobball.class, "Gobball", 0xeaeaea, 0xc99a03);
		registerEntity(Gobbette.class, "Gobbette", 0xeaeaea, 0xc99ab3);
		registerEntity(WhiteGobbly.class, "WhiteGobbly", 0xeaeaea, 0xc29ab3);
		registerEntity(BlackGobbly.class, "BlackGobbly", 0xeaeaea, 0xc22ab3);
		registerEntity(GobballWC.class, "GobballWarChief", 0xeaeaea, 0xc22a23);

		EntityRegistry.registerGlobalEntityID(EntityTextPopup.class, "TextPopup", EntityRegistry.findGlobalUniqueEntityId());
	}

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
}
