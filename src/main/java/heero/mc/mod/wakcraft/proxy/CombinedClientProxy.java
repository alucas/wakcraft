package heero.mc.mod.wakcraft.proxy;

import heero.mc.mod.wakcraft.client.gui.GUIClassSelection;
import heero.mc.mod.wakcraft.client.gui.GUIHavenBagVisitors;
import heero.mc.mod.wakcraft.client.model.ModelGobball;
import heero.mc.mod.wakcraft.client.model.ModelGobballWC;
import heero.mc.mod.wakcraft.client.model.ModelGobbette;
import heero.mc.mod.wakcraft.client.model.ModelGobbly;
import heero.mc.mod.wakcraft.client.renderer.block.RenderBlockOre;
import heero.mc.mod.wakcraft.client.renderer.block.RenderBlockPalisade;
import heero.mc.mod.wakcraft.client.renderer.block.RenderBlockYRotation;
import heero.mc.mod.wakcraft.client.renderer.entity.RenderSeedsPile;
import heero.mc.mod.wakcraft.client.renderer.entity.RenderTextPopup;
import heero.mc.mod.wakcraft.client.renderer.tileentity.RenderDragoexpress;
import heero.mc.mod.wakcraft.client.renderer.tileentity.RenderPhoenix;
import heero.mc.mod.wakcraft.client.renderer.tileentity.RendererHavenBagChest;
import heero.mc.mod.wakcraft.client.renderer.world.FightRenderer;
import heero.mc.mod.wakcraft.client.setting.KeyBindings;
import heero.mc.mod.wakcraft.entity.creature.BlackGobbly;
import heero.mc.mod.wakcraft.entity.creature.Gobball;
import heero.mc.mod.wakcraft.entity.creature.GobballWC;
import heero.mc.mod.wakcraft.entity.creature.Gobbette;
import heero.mc.mod.wakcraft.entity.creature.WhiteGobbly;
import heero.mc.mod.wakcraft.entity.misc.EntitySeedsPile;
import heero.mc.mod.wakcraft.entity.misc.EntityTextPopup;
import heero.mc.mod.wakcraft.eventhandler.GUIEventHandler;
import heero.mc.mod.wakcraft.eventhandler.KeyInputHandler;
import heero.mc.mod.wakcraft.eventhandler.TextureEventHandler;
import heero.mc.mod.wakcraft.manager.HavenBagHelper;
import heero.mc.mod.wakcraft.tileentity.TileEntityDragoexpress;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBagChest;
import heero.mc.mod.wakcraft.tileentity.TileEntityPhoenix;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class CombinedClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(Gobball.class, new Gobball.RenderGobball(new ModelGobball(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(Gobbette.class, new Gobbette.RenderGobette(new ModelGobbette(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(WhiteGobbly.class, new WhiteGobbly.RenderWhiteGobbly(new ModelGobbly(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(BlackGobbly.class, new BlackGobbly.RenderBlackGobbly(new ModelGobbly(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(GobballWC.class, new GobballWC.RenderGobballWC(new ModelGobballWC(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(EntityTextPopup.class, new RenderTextPopup());
		RenderingRegistry.registerEntityRenderingHandler(EntitySeedsPile.class, new RenderSeedsPile());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDragoexpress.class, new RenderDragoexpress());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPhoenix.class, new RenderPhoenix());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHavenBagChest.class, new RendererHavenBagChest());

		RenderingRegistry.registerBlockHandler(new RenderBlockYRotation(RenderingRegistry.getNextAvailableRenderId()));
		RenderingRegistry.registerBlockHandler(new RenderBlockOre(RenderingRegistry.getNextAvailableRenderId()));
		RenderingRegistry.registerBlockHandler(new RenderBlockPalisade(RenderingRegistry.getNextAvailableRenderId()));
	}

	@Override
	public void registerEvents() {
		super.registerEvents();

		MinecraftForge.EVENT_BUS.register(new GUIEventHandler());
		MinecraftForge.EVENT_BUS.register(new FightRenderer());
		MinecraftForge.EVENT_BUS.register(new TextureEventHandler());
	}

	@Override
	public void registerKeyBindings() {
		super.registerKeyBindings();

		KeyBindings.init();

		FMLCommonHandler.instance().bus().register(new KeyInputHandler());
	}

	@Override
	public void openHBVisitorsGui(EntityPlayer player) {
		int uid = HavenBagHelper.getUIDFromCoord((int) player.posX, (int) player.posY, (int) player.posZ);
		Minecraft.getMinecraft().displayGuiScreen(new GUIHavenBagVisitors(uid));
	}

	@Override
	public void openClassSelectionGui(EntityPlayer player) {
		Minecraft.getMinecraft().displayGuiScreen(new GUIClassSelection(player));
	}
}
