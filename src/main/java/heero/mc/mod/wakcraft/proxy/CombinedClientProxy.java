package heero.mc.mod.wakcraft.proxy;

import heero.mc.mod.wakcraft.client.gui.GUIClassSelection;
import heero.mc.mod.wakcraft.client.gui.GUIHavenBagVisitors;
import heero.mc.mod.wakcraft.client.model.ModelGobball;
import heero.mc.mod.wakcraft.client.model.ModelGobballWC;
import heero.mc.mod.wakcraft.client.model.ModelGobbette;
import heero.mc.mod.wakcraft.client.model.ModelGobbly;
import heero.mc.mod.wakcraft.client.renderer.block.RendererBlockOre;
import heero.mc.mod.wakcraft.client.renderer.block.RendererBlockPalisade;
import heero.mc.mod.wakcraft.client.renderer.block.RendererBlockYRotation;
import heero.mc.mod.wakcraft.client.renderer.entity.RendererSeedsPile;
import heero.mc.mod.wakcraft.client.renderer.entity.RendererTextPopup;
import heero.mc.mod.wakcraft.client.renderer.fight.FightRenderer;
import heero.mc.mod.wakcraft.client.renderer.tileentity.RendererDragoexpress;
import heero.mc.mod.wakcraft.client.renderer.tileentity.RendererHavenBagChest;
import heero.mc.mod.wakcraft.client.renderer.tileentity.RendererPhoenix;
import heero.mc.mod.wakcraft.client.setting.KeyBindings;
import heero.mc.mod.wakcraft.entity.creature.gobball.BlackGobbly;
import heero.mc.mod.wakcraft.entity.creature.gobball.Gobball;
import heero.mc.mod.wakcraft.entity.creature.gobball.GobballWC;
import heero.mc.mod.wakcraft.entity.creature.gobball.Gobbette;
import heero.mc.mod.wakcraft.entity.creature.gobball.WhiteGobbly;
import heero.mc.mod.wakcraft.entity.misc.EntitySeedsPile;
import heero.mc.mod.wakcraft.entity.misc.EntityTextPopup;
import heero.mc.mod.wakcraft.eventhandler.GUIEventHandler;
import heero.mc.mod.wakcraft.eventhandler.KeyInputHandler;
import heero.mc.mod.wakcraft.eventhandler.TextureEventHandler;
import heero.mc.mod.wakcraft.fight.FightClientEventsHandler;
import heero.mc.mod.wakcraft.havenbag.HavenBagHelper;
import heero.mc.mod.wakcraft.network.handler.HandlerClientExtendedEntityProperty;
import heero.mc.mod.wakcraft.network.handler.HandlerClientHavenBagProperties;
import heero.mc.mod.wakcraft.network.handler.HandlerClientOpenWindow;
import heero.mc.mod.wakcraft.network.handler.HandlerClientProfession;
import heero.mc.mod.wakcraft.network.handler.fight.HandlerClientFightChangeStage;
import heero.mc.mod.wakcraft.network.handler.fight.HandlerClientFightSelectPosition;
import heero.mc.mod.wakcraft.network.handler.fight.HandlerClientFightStart;
import heero.mc.mod.wakcraft.network.handler.fight.HandlerClientFightStartTurn;
import heero.mc.mod.wakcraft.network.handler.fight.HandlerClientFightStop;
import heero.mc.mod.wakcraft.network.packet.PacketExtendedEntityProperty;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagProperties;
import heero.mc.mod.wakcraft.network.packet.PacketOpenWindow;
import heero.mc.mod.wakcraft.network.packet.PacketProfession;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightChangeStage;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightSelectPosition;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightStart;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightStartTurn;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightStop;
import heero.mc.mod.wakcraft.tileentity.TileEntityDragoexpress;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBagChest;
import heero.mc.mod.wakcraft.tileentity.TileEntityPhoenix;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class CombinedClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(Gobball.class, new Gobball.RenderGobball(new ModelGobball(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(Gobbette.class, new Gobbette.RenderGobette(new ModelGobbette(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(WhiteGobbly.class, new WhiteGobbly.RenderWhiteGobbly(new ModelGobbly(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(BlackGobbly.class, new BlackGobbly.RenderBlackGobbly(new ModelGobbly(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(GobballWC.class, new GobballWC.RenderGobballWC(new ModelGobballWC(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(EntityTextPopup.class, new RendererTextPopup());
		RenderingRegistry.registerEntityRenderingHandler(EntitySeedsPile.class, new RendererSeedsPile());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDragoexpress.class, new RendererDragoexpress());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPhoenix.class, new RendererPhoenix());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHavenBagChest.class, new RendererHavenBagChest());

		RenderingRegistry.registerBlockHandler(new RendererBlockYRotation(RenderingRegistry.getNextAvailableRenderId()));
		RenderingRegistry.registerBlockHandler(new RendererBlockOre(RenderingRegistry.getNextAvailableRenderId()));
		RenderingRegistry.registerBlockHandler(new RendererBlockPalisade(RenderingRegistry.getNextAvailableRenderId()));
	}

	@Override
	public void registerEvents() {
		super.registerEvents();

		FightClientEventsHandler fightEventHandler = new FightClientEventsHandler(new FightRenderer());

		MinecraftForge.EVENT_BUS.register(new GUIEventHandler());
		MinecraftForge.EVENT_BUS.register(new TextureEventHandler());
		MinecraftForge.EVENT_BUS.register(fightEventHandler);

		FMLCommonHandler.instance().bus().register(fightEventHandler);
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

	@Override
	public EntityPlayer getCurrentPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

	public void registerPackets(SimpleNetworkWrapper packetPipeline) {
		super.registerPackets(packetPipeline);

		packetPipeline.registerMessage(HandlerClientOpenWindow.class, PacketOpenWindow.class, 1, Side.CLIENT);
		packetPipeline.registerMessage(HandlerClientProfession.class, PacketProfession.class, 4, Side.CLIENT);
		packetPipeline.registerMessage(HandlerClientExtendedEntityProperty.class, PacketExtendedEntityProperty.class, 6, Side.CLIENT);
		packetPipeline.registerMessage(HandlerClientHavenBagProperties.class, PacketHavenBagProperties.class, 7, Side.CLIENT);
		packetPipeline.registerMessage(HandlerClientFightStart.class, PacketFightStart.class, 8, Side.CLIENT);
		packetPipeline.registerMessage(HandlerClientFightStop.class, PacketFightStop.class, 9, Side.CLIENT);
		packetPipeline.registerMessage(HandlerClientFightChangeStage.class, PacketFightChangeStage.class, 10, Side.CLIENT);
		packetPipeline.registerMessage(HandlerClientFightSelectPosition.class, PacketFightSelectPosition.class, 11, Side.CLIENT);
		packetPipeline.registerMessage(HandlerClientFightStartTurn.class, PacketFightStartTurn.class, 13, Side.CLIENT);
	}
}
