package heero.mc.mod.wakcraft.proxy;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.WItems;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.characteristic.CharacteristicsEventsHandler;
import heero.mc.mod.wakcraft.entity.creature.gobball.*;
import heero.mc.mod.wakcraft.entity.creature.meow.BowMeow;
import heero.mc.mod.wakcraft.entity.creature.tofu.BabyTofu;
import heero.mc.mod.wakcraft.entity.creature.tofu.Tofurby;
import heero.mc.mod.wakcraft.entity.misc.EntitySeedsPile;
import heero.mc.mod.wakcraft.entity.misc.EntityTextPopup;
import heero.mc.mod.wakcraft.eventhandler.EntityEventHandler;
import heero.mc.mod.wakcraft.eventhandler.PlayerEventHandler;
import heero.mc.mod.wakcraft.eventhandler.WorldEventHandler;
import heero.mc.mod.wakcraft.fight.FightEventsHandler;
import heero.mc.mod.wakcraft.network.GuiHandler;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.network.handler.*;
import heero.mc.mod.wakcraft.network.handler.fight.*;
import heero.mc.mod.wakcraft.network.packet.*;
import heero.mc.mod.wakcraft.network.packet.fight.*;
import heero.mc.mod.wakcraft.tileentity.*;
import heero.mc.mod.wakcraft.world.WorldProviderHavenBag;
import heero.mc.mod.wakcraft.world.gen.WorldGenHavenBag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

    public void registerRenderers() {
    }

    public void registerPreInitEvents() {
        FightEventsHandler fightsHandler = new FightEventsHandler();

        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
        MinecraftForge.EVENT_BUS.register(new WorldEventHandler());
        MinecraftForge.EVENT_BUS.register(new CharacteristicsEventsHandler());
        MinecraftForge.EVENT_BUS.register(fightsHandler);

        FMLCommonHandler.instance().bus().register(new PlayerEventHandler());
        FMLCommonHandler.instance().bus().register(fightsHandler);
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
        registerEntity(BowMeow.class, "BowMeow", 0xeaeaea, 0x000000);
        registerEntity(BabyTofu.class, "BabyTofu", 0xeaeaea, 0xffa500);
        registerEntity(Tofurby.class, "Tofurby", 0xeaeaea, 0xffa500);

        EntityRegistry.registerGlobalEntityID(EntityTextPopup.class, "TextPopup", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerGlobalEntityID(EntitySeedsPile.class, "SeedPile", EntityRegistry.findGlobalUniqueEntityId());

        EntityRegistry.registerModEntity(EntitySeedsPile.class, "SeedPile", EntityRegistry.findGlobalUniqueEntityId(), Wakcraft.instance, 20, 5, false);
    }

    @SuppressWarnings("unchecked")
    private void registerEntity(Class<? extends Entity> entityClass,
                                String entityName, int bkEggColor, int fgEggColor) {
        int id = EntityRegistry.findGlobalUniqueEntityId();

        EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
        EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id, bkEggColor, fgEggColor));
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
    }

    public Object getGui(GuiId guiId, EntityPlayer player, World world, BlockPos pos) {
        return null;
    }
}
