package heero.mc.mod.wakcraft.proxy;

import heero.mc.mod.wakcraft.*;
import heero.mc.mod.wakcraft.characteristic.CharacteristicsEventsHandler;
import heero.mc.mod.wakcraft.command.CommandFightRule;
import heero.mc.mod.wakcraft.command.CommandJobLevel;
import heero.mc.mod.wakcraft.entity.creature.gobball.*;
import heero.mc.mod.wakcraft.entity.creature.meow.BowMeow;
import heero.mc.mod.wakcraft.entity.creature.tofu.BabyTofu;
import heero.mc.mod.wakcraft.entity.creature.tofu.Tofurby;
import heero.mc.mod.wakcraft.entity.misc.EntitySeedsPile;
import heero.mc.mod.wakcraft.entity.misc.EntityTextPopup;
import heero.mc.mod.wakcraft.entity.npc.NPCHugoTydal;
import heero.mc.mod.wakcraft.event.handler.EntityEventHandler;
import heero.mc.mod.wakcraft.event.handler.PlayerEventHandler;
import heero.mc.mod.wakcraft.event.handler.WorldEventHandler;
import heero.mc.mod.wakcraft.fight.FightEventsHandler;
import heero.mc.mod.wakcraft.network.GuiHandler;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.network.handler.*;
import heero.mc.mod.wakcraft.network.handler.fight.*;
import heero.mc.mod.wakcraft.network.packet.*;
import heero.mc.mod.wakcraft.network.packet.fight.*;
import heero.mc.mod.wakcraft.quest.QuestManager;
import heero.mc.mod.wakcraft.tileentity.*;
import heero.mc.mod.wakcraft.world.WorldProviderHavenBag;
import heero.mc.mod.wakcraft.world.gen.WorldGenHavenBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

    public void registerRenderers() {
    }

    public void registerPreInitEvents() {
        MinecraftForge.EVENT_BUS.register(new CharacteristicsEventsHandler());
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
        MinecraftForge.EVENT_BUS.register(new FightEventsHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
        MinecraftForge.EVENT_BUS.register(new WorldEventHandler());
    }

    public void registerInitEvents() {
    }

    public void registerItems() {
        WItems.registerItems();
    }

    public void registerBlocks() {
        WBlocks.registerBlocks();
    }

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityDragoExpress.class, "tile_entity_dragoexpress");
        GameRegistry.registerTileEntity(TileEntityPhoenix.class, "tile_entity_phoenix");
        GameRegistry.registerTileEntity(TileEntityHavenGemWorkbench.class, "tileEntityHavenGemWorkbench");
        GameRegistry.registerTileEntity(TileEntityHavenBag.class, "tileEntityHavenBag");
        GameRegistry.registerTileEntity(TileEntityHavenBagChest.class, "tileEntityHavenBagChest");
    }

    public void registerEntities() {
        EntityRegistry.registerModEntity(EntityTextPopup.class, "TextPopup", 1, Wakcraft.instance, 10, 1, false);
        EntityRegistry.registerModEntity(EntitySeedsPile.class, "SeedPile", 2, Wakcraft.instance, 24, 3, false);

        // Monsters
        EntityRegistry.registerModEntity(Gobball.class, "Gobball", 1000, Wakcraft.instance, 64, 3, true, 0xeaeaea, 0xc99a03);
        EntityRegistry.registerModEntity(Gobbette.class, "Gobbette", 1001, Wakcraft.instance, 64, 3, true, 0xeaeaea, 0xc99ab3);
        EntityRegistry.registerModEntity(WhiteGobbly.class, "WhiteGobbly", 1002, Wakcraft.instance, 64, 3, true, 0xeaeaea, 0xc29ab3);
        EntityRegistry.registerModEntity(BlackGobbly.class, "BlackGobbly", 1003, Wakcraft.instance, 64, 3, true, 0xeaeaea, 0xc22ab3);
        EntityRegistry.registerModEntity(GobballWC.class, "GobballWarChief", 1004, Wakcraft.instance, 64, 3, true, 0xeaeaea, 0xc22a23);
        EntityRegistry.registerModEntity(BowMeow.class, "BowMeow", 1005, Wakcraft.instance, 64, 3, true, 0xeaeaea, 0x000000);
        EntityRegistry.registerModEntity(BabyTofu.class, "BabyTofu", 1006, Wakcraft.instance, 64, 3, true, 0xeaeaea, 0xffa500);
        EntityRegistry.registerModEntity(Tofurby.class, "Tofurby", 1007, Wakcraft.instance, 64, 3, true, 0xeaeaea, 0xffa500);

        // npcS
        EntityRegistry.registerModEntity(NPCHugoTydal.class, "hugo_tydal", 2000, Wakcraft.instance, 64, 20, true, 0xeaeaea, 0xffa500);
    }

    public void registerGui(Wakcraft wc) {
        NetworkRegistry.INSTANCE.registerGuiHandler(wc, new GuiHandler());
    }

    public void registerKeyBindings() {
    }

    public void openHBVisitorsGui(EntityPlayer player) {
    }

    public void openClassSelectionGui(EntityPlayer player) {
    }

    public void registerDimensions() {
        GameRegistry.registerWorldGenerator(new WorldGenHavenBag(), 0);

        DimensionManager.registerProviderType(WConfig.getHavenBagDimensionId(), WorldProviderHavenBag.class, false);
        DimensionManager.registerDimension(WConfig.getHavenBagDimensionId(), WConfig.getHavenBagDimensionId());
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }

    public World getClientWorld() {
        return null;
    }

    public void registerPackets(SimpleNetworkWrapper packetPipeline) {
        packetPipeline.registerMessage(HandlerServerHavenBagTeleportation.class, PacketHavenBagTeleportation.class, 0, Side.SERVER);
        packetPipeline.registerMessage(HandlerClientOpenWindow.class, PacketOpenWindow.class, 1, Side.CLIENT);
        packetPipeline.registerMessage(HandlerServerOpenWindow.class, PacketOpenWindow.class, 2, Side.SERVER);
        packetPipeline.registerMessage(HandlerClientProfession.class, PacketProfession.class, 4, Side.CLIENT);
        packetPipeline.registerMessage(HandlerServerCloseWindow.class, PacketCloseWindow.class, 3, Side.SERVER);
        packetPipeline.registerMessage(HandlerServerHavenBagVisitors.class, PacketHavenBagVisitors.class, 5, Side.SERVER);
        packetPipeline.registerMessage(HandlerClientExtendedEntityProperty.class, PacketExtendedEntityProperty.class, 6, Side.CLIENT);
        packetPipeline.registerMessage(HandlerClientHavenBagProperties.class, PacketHavenBagProperties.class, 7, Side.CLIENT);
        packetPipeline.registerMessage(HandlerClientFightStart.class, PacketFightStart.class, 8, Side.CLIENT);
        packetPipeline.registerMessage(HandlerClientFightStop.class, PacketFightStop.class, 9, Side.CLIENT);
        packetPipeline.registerMessage(HandlerClientFightChangeStage.class, PacketFightChangeStage.class, 10, Side.CLIENT);
        packetPipeline.registerMessage(HandlerClientFightSelectPosition.class, PacketFightSelectPosition.class, 11, Side.CLIENT);
        packetPipeline.registerMessage(HandlerServerFightSelectPosition.class, PacketFightSelectPosition.class, 12, Side.SERVER);
        packetPipeline.registerMessage(HandlerClientFightStartTurn.class, PacketFightStartTurn.class, 13, Side.CLIENT);
        packetPipeline.registerMessage(HandlerServerFightCastSpell.class, PacketFightCastSpell.class, 14, Side.SERVER);
        packetPipeline.registerMessage(HandlerServerZaapTeleportation.class, PacketZaapTeleportation.class, 15, Side.SERVER);
    }

    public Object getGui(GuiId guiId, EntityPlayer player, World world, BlockPos pos) {
        return null;
    }

    public void registerCommand(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandFightRule());
        event.registerServerCommand(new CommandJobLevel());
    }

    public void loadQuests() {
        QuestManager.INSTANCE.load("assets/" + Reference.MODID + "/quests/");
    }
}
