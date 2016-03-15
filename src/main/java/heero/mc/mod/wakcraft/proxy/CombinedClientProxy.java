package heero.mc.mod.wakcraft.proxy;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.WItems;
import heero.mc.mod.wakcraft.client.gui.*;
import heero.mc.mod.wakcraft.client.gui.fight.GuiFightOverlay;
import heero.mc.mod.wakcraft.client.gui.inventory.GUIHavenGemWorkbench;
import heero.mc.mod.wakcraft.client.gui.inventory.GUIInventory;
import heero.mc.mod.wakcraft.client.gui.inventory.GUISpells;
import heero.mc.mod.wakcraft.client.gui.inventory.GUIWorkbench;
import heero.mc.mod.wakcraft.client.renderer.entity.RenderFactory;
import heero.mc.mod.wakcraft.client.renderer.fight.FightRenderer;
import heero.mc.mod.wakcraft.client.renderer.fight.FighterRenderer;
import heero.mc.mod.wakcraft.client.renderer.fight.SpellRenderer;
import heero.mc.mod.wakcraft.client.renderer.item.RendererItemBlock;
import heero.mc.mod.wakcraft.client.renderer.tileentity.RendererDragoexpress;
import heero.mc.mod.wakcraft.client.renderer.tileentity.RendererHavenBagChest;
import heero.mc.mod.wakcraft.client.renderer.tileentity.RendererPhoenix;
import heero.mc.mod.wakcraft.client.setting.KeyBindings;
import heero.mc.mod.wakcraft.entity.misc.EntityTextPopup;
import heero.mc.mod.wakcraft.eventhandler.GUIEventHandler;
import heero.mc.mod.wakcraft.eventhandler.KeyInputHandler;
import heero.mc.mod.wakcraft.eventhandler.TextureEventHandler;
import heero.mc.mod.wakcraft.fight.FightClientEventsHandler;
import heero.mc.mod.wakcraft.inventory.*;
import heero.mc.mod.wakcraft.item.EnumOre;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import heero.mc.mod.wakcraft.tileentity.TileEntityDragoexpress;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBagChest;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import heero.mc.mod.wakcraft.tileentity.TileEntityPhoenix;
import heero.mc.mod.wakcraft.util.HavenBagUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class CombinedClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        RenderFactory renderFactory = new RenderFactory();
//        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
//        RenderingRegistry.registerEntityRenderingHandler(Gobball.class, new Gobball.RenderGobball(renderManager, new ModelGobball(), 0.5f));
//        RenderingRegistry.registerEntityRenderingHandler(Gobbette.class, new Gobbette.RenderGobette(renderManager, new ModelGobbette(), 0.5f));
//        RenderingRegistry.registerEntityRenderingHandler(WhiteGobbly.class, new WhiteGobbly.RenderWhiteGobbly(renderManager, new ModelGobbly(), 0.5f));
//        RenderingRegistry.registerEntityRenderingHandler(BlackGobbly.class, new BlackGobbly.RenderBlackGobbly(renderManager, new ModelGobbly(), 0.5f));
//        RenderingRegistry.registerEntityRenderingHandler(GobballWC.class, new GobballWC.RenderGobballWC(renderManager, new ModelGobballWC(), 0.5f));
//        RenderingRegistry.registerEntityRenderingHandler(BowMeow.class, new BowMeow.RenderBowMeow(renderManager, new ModelBowMeow(), 0.5f));
//        RenderingRegistry.registerEntityRenderingHandler(BabyTofu.class, new BabyTofu.RenderBabyTofu(renderManager, new ModelBabyTofu(), 0.5f));
//        RenderingRegistry.registerEntityRenderingHandler(Tofurby.class, new Tofurby.RenderTofurby(renderManager, new ModelTofurby(), 0.5f));

        RenderingRegistry.registerEntityRenderingHandler(EntityTextPopup.class, renderFactory);
//        RenderingRegistry.registerEntityRenderingHandler(EntitySeedsPile.class, new RendererSeedsPile(renderManager));

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDragoexpress.class, new RendererDragoexpress());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPhoenix.class, new RendererPhoenix());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHavenBagChest.class, new RendererHavenBagChest());

//		RenderingRegistry.registerBlockHandler(new RendererBlockRotation(RenderingRegistry.getNextAvailableRenderId()));
//		RenderingRegistry.registerBlockHandler(new RendererBlockPalisade(RenderingRegistry.getNextAvailableRenderId()));
//		RenderingRegistry.registerBlockHandler(new RendererBlockPlant(RenderingRegistry.getNextAvailableRenderId()));
//		RenderingRegistry.registerBlockHandler(new RendererBlockScree(RenderingRegistry.getNextAvailableRenderId()));
//		RenderingRegistry.registerBlockHandler(new RendererBlockFence(RenderingRegistry.getNextAvailableRenderId()));

//		RendererItemBlock rendererItemBlock = new RendererItemBlock();
//		MinecraftForgeClient.registerItemRenderer(WItems.sufokiaWave, rendererItemBlock);
//		MinecraftForgeClient.registerItemRenderer(WItems.sufokiaWave2, rendererItemBlock);
//		MinecraftForgeClient.registerItemRenderer(WItems.sufokiaWave3, rendererItemBlock);
//		MinecraftForgeClient.registerItemRenderer(WItems.groundSlab, rendererItemBlock);
//		MinecraftForgeClient.registerItemRenderer(WItems.ground2Slab, rendererItemBlock);
//		MinecraftForgeClient.registerItemRenderer(WItems.carpet, rendererItemBlock);
//		MinecraftForgeClient.registerItemRenderer(WItems.wood, rendererItemBlock);

        Block[] blockOres = {WBlocks.ore1, WBlocks.ore2, WBlocks.ore3, WBlocks.ore4};
        for (int i = 0; i < blockOres.length; i++) {
            for (int j = 0; j < 8 && j < EnumOre.values().length - i * 8; j++) {
                registerItemBlockModel(blockOres[i], j << 1, Reference.MODID.toLowerCase() + ":block_ore_" + EnumOre.values()[i * 8 + j].getName());
            }
        }

        registerItemBlockModel(WBlocks.box);
        registerItemBlockModel(WBlocks.carpet);
        registerItemBlockModel(WBlocks.debug);
        registerItemBlockModel(WBlocks.dirt);
        registerItemBlockModel(WBlocks.fightDirection);
        registerItemBlockModel(WBlocks.fightMovement);
        registerItemBlockModel(WBlocks.fightStart);
        registerItemBlockModel(WBlocks.fightStart2);
        registerItemBlockModel(WBlocks.grass);
        registerItemBlockModel(WBlocks.ground);
        registerItemBlockModel(WBlocks.ground2);
        registerItemBlockModel(WBlocks.ground3);
        registerItemBlockModel(WBlocks.ground4);
        registerItemBlockModel(WBlocks.ground11);
        registerItemBlockModel(WBlocks.ground12);
        registerItemBlockModel(WBlocks.ground13);
        registerItemBlockModel(WBlocks.ground14);
        registerItemBlockModel(WBlocks.pillar);
        registerItemBlockModel(WBlocks.plank);
        registerItemBlockModel(WBlocks.plant);
        registerItemBlockModel(WBlocks.plant2);
        registerItemBlockModel(WBlocks.sufokiaColor);
        registerItemBlockModel(WBlocks.sufokiaGround);
        registerItemBlockModel(WBlocks.sufokiaGround2);
        registerItemBlockModel(WBlocks.sufokiaStair);
        registerItemBlockModel(WBlocks.sufokiaSun);
        registerItemBlockModel(WBlocks.sufokiaWave2);
        registerItemBlockModel(WBlocks.sufokiaWave3);
        registerItemBlockModel(WBlocks.sufokiaWave4);
        registerItemBlockModel(WBlocks.wood);

        // Special
        registerItemBlockModel(WBlocks.classConsole);

        // Haven Bag
        registerItemBlockModel(WBlocks.hbBridge);
        registerItemBlockModel(WBlocks.hbCraft);
        registerItemBlockModel(WBlocks.hbCraft2);
        registerItemBlockModel(WBlocks.hbDeco);
        registerItemBlockModel(WBlocks.hbDeco2);
        registerItemBlockModel(WBlocks.hbGarden);
        registerItemBlockModel(WBlocks.hbMerchant);
        registerItemBlockModel(WBlocks.hbStand);

        // Slabs
        registerItemBlockModel(WItems.carpet);
        registerItemBlockModel(WBlocks.debugSlab);
        registerItemBlockModel(WBlocks.dirtSlab);
        registerItemBlockModel(WBlocks.grassSlab);
        registerItemBlockModel(WItems.groundSlab);
        registerItemBlockModel(WItems.ground2Slab);
        registerItemBlockModel(WBlocks.ground3Slab);
        registerItemBlockModel(WBlocks.ground4Slab);
        registerItemBlockModel(WBlocks.ground11Slab);
        registerItemBlockModel(WBlocks.ground12Slab);
        registerItemBlockModel(WBlocks.ground13Slab);
        registerItemBlockModel(WBlocks.ground14Slab);
        registerItemBlockModel(WBlocks.hbStandSlab);
        registerItemBlockModel(WBlocks.pillarSlab);
        registerItemBlockModel(WBlocks.sufokiaGroundSlab);
        registerItemBlockModel(WBlocks.sufokiaGround2Slab);
        registerItemBlockModel(WBlocks.sufokiaSunSlab);
        registerItemBlockModel(WItems.sufokiaWave);
        registerItemBlockModel(WItems.sufokiaWave2);
        registerItemBlockModel(WItems.sufokiaWave3);
        registerItemBlockModel(WItems.wood);

        // Fences
        registerItemBlockModel(WBlocks.fence);

        // Palisade
        registerItemBlockModel(WBlocks.palisade);
        registerItemBlockModel(WBlocks.palisade2);

        // Items
        registerItemModel(WItems.woollyKey);
        registerItemModel(WItems.gobballWool);
        registerItemModel(WItems.gobballSkin);
        registerItemModel(WItems.gobballHorn);
        registerItemModel(WItems.tofuFeather);
        registerItemModel(WItems.tofuBlood);
        registerItemModel(WItems.canoonPowder);
        registerItemModel(WItems.clay);
        registerItemModel(WItems.waterBucket);
        registerItemModel(WItems.driedDung);
        registerItemModel(WItems.pearl);
        registerItemModel(WItems.moonstone);
        registerItemModel(WItems.bomb);
        registerItemModel(WItems.fossil);
        registerItemModel(WItems.shamPearl);
        registerItemModel(WItems.verbalasalt);
        registerItemModel(WItems.gumgum);
        registerItemModel(WItems.polishedmoonstone);
        registerItemModel(WItems.shadowyBlue);

        Item[] ores = {WItems.ore1, WItems.ore2};
        for (int i = 0; i < ores.length; i++) {
            for (int j = 0; j < 16 && j < EnumOre.values().length - i * 16; j++) {
                registerItemModel(ores[i], j, Reference.MODID.toLowerCase() + ":ore_" + EnumOre.values()[i * 16 + j].getName());
            }
        }

        registerItemModel(WItems.gobballSeed);

        registerItemModel(WItems.merchantHG);
        registerItemModel(WItems.decoHG);
        registerItemModel(WItems.craftHG);
        registerItemModel(WItems.gardenHG);

        registerItemModel(WItems.ikiakitSmall);
        registerItemModel(WItems.ikiakitGolden);
        registerItemModel(WItems.ikiakitKit);
        registerItemModel(WItems.ikiakitAdventurer);
        registerItemModel(WItems.ikiakitCollector);
        registerItemModel(WItems.ikiakitEmerald);

        registerItemModel(WItems.bouzeLiteYeahsRing);
        registerItemModel(WItems.gobballBreastplate);
        registerItemModel(WItems.gobboots);
        registerItemModel(WItems.gobballEpaulettes);
        registerItemModel(WItems.gobballCape);
        registerItemModel(WItems.gobballBelt);
        registerItemModel(WItems.gobballHeadgear);
        registerItemModel(WItems.gobballAmulet);

        registerItemModel(WItems.helmetofu);
        registerItemModel(WItems.tofuBreastplate);
        registerItemModel(WItems.tofuCloak);
        registerItemModel(WItems.tofuEpaulettes);
        registerItemModel(WItems.tofuBelt);
        registerItemModel(WItems.tofuAmulet);
        registerItemModel(WItems.tofuRing);
        registerItemModel(WItems.tofuBoots);

        // Spells
        // Iop
        registerSpellModel(WItems.spellShaker);
        registerSpellModel(WItems.spellRocknoceros);
        registerSpellModel(WItems.spellImpact);
        registerSpellModel(WItems.spellCharge);
        registerSpellModel(WItems.spellDevastate);
        registerSpellModel(WItems.spellThunderbolt);
        registerSpellModel(WItems.spellJudgment);
        registerSpellModel(WItems.spellSuperIopPunch);
        registerSpellModel(WItems.spellCelestialSword);
        registerSpellModel(WItems.spellIopsWrath);
        registerSpellModel(WItems.spellJabs);
        registerSpellModel(WItems.spellFlurry);
        registerSpellModel(WItems.spellIntimidation);
        registerSpellModel(WItems.spellGuttingGust);
        registerSpellModel(WItems.spellUppercut);
        registerSpellModel(WItems.spellJump);
        registerSpellModel(WItems.spellDefensiveStance);
        registerSpellModel(WItems.spellFlatten);
        registerSpellModel(WItems.spellBraveryStandard);
        registerSpellModel(WItems.spellIncrease);
        registerSpellModel(WItems.spellVirility);
        registerSpellModel(WItems.spellCompulsion);
        registerSpellModel(WItems.spellAuthority);
        registerSpellModel(WItems.spellShowOff);
        registerSpellModel(WItems.spellLockingPro);
    }

    protected void registerItemBlockModel(final Block block) {
        registerItemBlockModel(Item.getItemFromBlock(block));
    }

    protected void registerItemBlockModel(final Block block, final int metadata, final String name) {
        registerItemBlockModel(Item.getItemFromBlock(block), metadata, name);
    }

    protected void registerItemBlockModel(final Item itemBlock) {
        registerItemBlockModel(itemBlock, 0, itemBlock.getUnlocalizedName().substring(5).replaceFirst("\\.", ":block"));
    }

    protected void registerItemBlockModel(final Item itemBlock, final int metadata, final String name) {
        ModelLoader.setCustomModelResourceLocation(itemBlock, metadata, new ModelResourceLocation(name, "inventory"));
    }

    protected void registerItemModel(final Item item) {
        registerItemModel(item, 0, item.getUnlocalizedName().substring(5).replace('.', ':'));
    }

    protected void registerItemModel(final Item item, final int metadata, final String name) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(name, "inventory"));
    }

    protected void registerSpellModel(final Item spell) {
        ModelLoader.setCustomModelResourceLocation(spell, 0, new ModelResourceLocation(Reference.MODID + ":Spell" + spell.getUnlocalizedName().substring(6), "inventory"));
    }

    @Override
    public void registerPreInitEvents() {
        super.registerPreInitEvents();

        MinecraftForge.EVENT_BUS.register(new GUIEventHandler());
        MinecraftForge.EVENT_BUS.register(new TextureEventHandler());
    }

    @Override
    public void registerInitEvents() {
        super.registerInitEvents();

        Minecraft minecraft = Minecraft.getMinecraft();
        FighterRenderer fighterRenderer = new FighterRenderer(minecraft, new SpellRenderer(minecraft));
        FightRenderer fighteRenderer = new FightRenderer();
        GuiFightOverlay guiFightOverlay = new GuiFightOverlay(minecraft);
        FightClientEventsHandler fightEventHandler = new FightClientEventsHandler(fighteRenderer, guiFightOverlay, fighterRenderer);

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
        int uid = HavenBagUtil.getUIDFromCoord(player.getPosition());
        Minecraft.getMinecraft().displayGuiScreen(new GUIHavenBagVisitors(uid));
    }

    @Override
    public void openClassSelectionGui(EntityPlayer player) {
        Minecraft.getMinecraft().displayGuiScreen(new GUIClassSelection(player));
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getMinecraft().theWorld;
    }

    @Override
    public Object getGui(GuiId guiId, EntityPlayer player, World world, BlockPos pos) {
        TileEntity tileEntity;

        switch (guiId) {
            case POLISHER:
                return new GUIWorkbench(new ContainerWorkbench(player.inventory, world, PROFESSION.MINER), PROFESSION.MINER);
            case INVENTORY:
                return new GUIWakcraft(guiId, new GUIInventory(new ContainerPlayerInventory(player)), player, world, pos);
            case HAVEN_GEM_WORKBENCH:
                tileEntity = world.getTileEntity(pos);
                if (tileEntity == null || !(tileEntity instanceof TileEntityHavenGemWorkbench)) {
                    return null;
                }

                return new GUIHavenGemWorkbench(new ContainerHavenGemWorkbench(player.inventory, (IInventory) tileEntity));
            case HAVEN_BAG_CHEST_NORMAL:
            case HAVEN_BAG_CHEST_SMALL:
            case HAVEN_BAG_CHEST_ADVENTURER:
            case HAVEN_BAG_CHEST_KIT:
            case HAVEN_BAG_CHEST_COLLECTOR:
            case HAVEN_BAG_CHEST_GOLDEN:
            case HAVEN_BAG_CHEST_EMERALD:
                tileEntity = world.getTileEntity(pos);
                if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBagChest)) {
                    return null;
                }

                return new GUIHavenBagChests(guiId, new ContainerHavenBagChest(player.inventory, (TileEntityHavenBagChest) tileEntity, player), player, world, pos);
            case ABILITIES:
                return new GUIWakcraft(guiId, new GUIAbilities(player), player, world, pos);
            case PROFESSION:
                return new GUIWakcraft(guiId, new GUIProfession(player, PROFESSION.CHEF), player, world, pos);
            case SPELLS:
                return new GUIWakcraft(guiId, new GUISpells(new ContainerSpells(player)), player, world, pos);
            default:
                break;
        }

        return null;
    }
}
