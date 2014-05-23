package heero.wakcraft.proxy;

import heero.wakcraft.client.gui.GUIHavenBagVisitors;
import heero.wakcraft.client.model.ModelGobball;
import heero.wakcraft.client.model.ModelGobballWC;
import heero.wakcraft.client.model.ModelGobbette;
import heero.wakcraft.client.model.ModelGobbly;
import heero.wakcraft.client.renderer.block.RenderBlockOre;
import heero.wakcraft.client.renderer.block.RenderBlockPalisade;
import heero.wakcraft.client.renderer.block.RenderBlockYRotation;
import heero.wakcraft.client.renderer.entity.RenderTextPopup;
import heero.wakcraft.client.renderer.tileentity.RenderDragoexpress;
import heero.wakcraft.client.renderer.tileentity.RenderPhoenix;
import heero.wakcraft.client.renderer.tileentity.RendererHavenBagChest;
import heero.wakcraft.client.renderer.world.FightRenderer;
import heero.wakcraft.client.setting.KeyBindings;
import heero.wakcraft.entity.misc.EntityTextPopup;
import heero.wakcraft.entity.monster.BlackGobbly;
import heero.wakcraft.entity.monster.Gobball;
import heero.wakcraft.entity.monster.GobballWC;
import heero.wakcraft.entity.monster.Gobbette;
import heero.wakcraft.entity.monster.WhiteGobbly;
import heero.wakcraft.eventhandler.GUIEventHandler;
import heero.wakcraft.eventhandler.KeyInputHandler;
import heero.wakcraft.eventhandler.TextureEventHandler;
import heero.wakcraft.havenbag.HavenBagHelper;
import heero.wakcraft.tileentity.TileEntityDragoexpress;
import heero.wakcraft.tileentity.TileEntityHavenBagChest;
import heero.wakcraft.tileentity.TileEntityPhoenix;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class CombinedClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(Gobball.class, new Gobball.RenderBouftou(new ModelGobball(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(Gobbette.class, new Gobbette.RenderBouffette(new ModelGobbette(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(WhiteGobbly.class, new WhiteGobbly.RenderBouftonBlanc(new ModelGobbly(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(BlackGobbly.class, new BlackGobbly.RenderBouftonNoir(new ModelGobbly(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(GobballWC.class, new GobballWC.RenderBouftouCG(new ModelGobballWC(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(EntityTextPopup.class, new RenderTextPopup());

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
}
