package heero.mc.mod.wakcraft.proxy;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.WItems;
import heero.mc.mod.wakcraft.block.vein.BlockVein;
import heero.mc.mod.wakcraft.client.gui.*;
import heero.mc.mod.wakcraft.client.gui.fight.GuiFightOverlay;
import heero.mc.mod.wakcraft.client.gui.inventory.GUIHavenGemWorkbench;
import heero.mc.mod.wakcraft.client.gui.inventory.GUIInventory;
import heero.mc.mod.wakcraft.client.gui.inventory.GUISpells;
import heero.mc.mod.wakcraft.client.gui.inventory.GUIWorkbench;
import heero.mc.mod.wakcraft.client.renderer.fight.FightRenderer;
import heero.mc.mod.wakcraft.client.renderer.fight.FighterRenderer;
import heero.mc.mod.wakcraft.client.renderer.fight.SpellRenderer;
import heero.mc.mod.wakcraft.client.renderer.tileentity.RendererDragoExpress;
import heero.mc.mod.wakcraft.client.renderer.tileentity.RendererPhoenix;
import heero.mc.mod.wakcraft.client.setting.KeyBindings;
import heero.mc.mod.wakcraft.entity.creature.gobball.*;
import heero.mc.mod.wakcraft.entity.creature.meow.BowMeow;
import heero.mc.mod.wakcraft.entity.creature.tofu.BabyTofu;
import heero.mc.mod.wakcraft.entity.creature.tofu.Tofurby;
import heero.mc.mod.wakcraft.entity.misc.EntitySeedsPile;
import heero.mc.mod.wakcraft.entity.misc.EntityTextPopup;
import heero.mc.mod.wakcraft.event.handler.GUIEventHandler;
import heero.mc.mod.wakcraft.event.handler.KeyInputHandler;
import heero.mc.mod.wakcraft.event.handler.TextureEventHandler;
import heero.mc.mod.wakcraft.fight.FightClientEventsHandler;
import heero.mc.mod.wakcraft.inventory.*;
import heero.mc.mod.wakcraft.item.EnumOre;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import heero.mc.mod.wakcraft.tileentity.TileEntityDragoExpress;
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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.ArrayList;
import java.util.List;

public class CombinedClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(Gobball.class, new Gobball.RenderFactoryGobball());
        RenderingRegistry.registerEntityRenderingHandler(Gobbette.class, new Gobbette.RenderFactoryGobbette());
        RenderingRegistry.registerEntityRenderingHandler(BlackGobbly.class, new BlackGobbly.RenderFactoryBlackGobbly());
        RenderingRegistry.registerEntityRenderingHandler(WhiteGobbly.class, new WhiteGobbly.RenderFactoryWhiteGobbly());
        RenderingRegistry.registerEntityRenderingHandler(GobballWC.class, new GobballWC.RenderFactoryGobballWC());
        RenderingRegistry.registerEntityRenderingHandler(BowMeow.class, new BowMeow.RenderFactoryBowMeow());
        RenderingRegistry.registerEntityRenderingHandler(BabyTofu.class, new BabyTofu.RenderFactoryBabyTofu());
        RenderingRegistry.registerEntityRenderingHandler(Tofurby.class, new Tofurby.RenderFactoryTofurby());

        RenderingRegistry.registerEntityRenderingHandler(EntityTextPopup.class, new EntityTextPopup.RenderFactoryTextPopup());
        RenderingRegistry.registerEntityRenderingHandler(EntitySeedsPile.class, new EntitySeedsPile.RenderFactorySeedPile());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDragoExpress.class, new RendererDragoExpress());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPhoenix.class, new RendererPhoenix());
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHavenBagChest.class, new RendererHavenBagChest());

//		RenderingRegistry.registerBlockHandler(new RendererBlockRotation(RenderingRegistry.getNextAvailableRenderId()));
//		RenderingRegistry.registerBlockHandler(new RendererBlockPalisade(RenderingRegistry.getNextAvailableRenderId()));
//		RenderingRegistry.registerBlockHandler(new RendererBlockPlant(RenderingRegistry.getNextAvailableRenderId()));
//		RenderingRegistry.registerBlockHandler(new RendererBlockScree(RenderingRegistry.getNextAvailableRenderId()));
//		RenderingRegistry.registerBlockHandler(new RendererBlockFence(RenderingRegistry.getNextAvailableRenderId()));

        BlockVein[] blockOres = {WBlocks.vein1, WBlocks.vein2, WBlocks.vein3, WBlocks.vein4};
        List<ItemStack> stacks = new ArrayList<>(8);
        for (BlockVein blockVein : blockOres) {
            Item itemBlock = Item.getItemFromBlock(blockVein);
            stacks.clear();
            blockVein.getSubBlocks(itemBlock, null, stacks);
            for (ItemStack stack : stacks) {
                registerItemBlockModel(blockVein, stack.getMetadata(), stack.getUnlocalizedName().substring(5).replaceFirst("\\.", ":block_"));
            }
        }

        registerItemBlockModel(WBlocks.box);
        registerItemBlockModel(WBlocks.carpet);
        registerItemBlockModel(WBlocks.carpetEastSlab);
        registerItemBlockModel(WBlocks.carpetNorthSlab);
        registerItemBlockModel(WBlocks.carpetSouthSlab);
        registerItemBlockModel(WBlocks.carpetWestSlab);
        registerItemBlockModel(WBlocks.debug);
        registerItemBlockModel(WBlocks.debugSlab);
        registerItemBlockModel(WBlocks.dirt);
        registerItemBlockModel(WBlocks.dirtSlab);
        registerItemBlockModel(WBlocks.fightDirection);
        registerItemBlockModel(WBlocks.fightMovement);
        registerItemBlockModel(WBlocks.fightStart);
        registerItemBlockModel(WBlocks.fightStart2);
        registerItemBlockModel(WBlocks.grass);
        registerItemBlockModel(WBlocks.grassSlab);
        registerItemBlockModel(WBlocks.ground);
        registerItemBlockModel(WBlocks.groundEastSlab);
        registerItemBlockModel(WBlocks.groundNorthSlab);
        registerItemBlockModel(WBlocks.groundSouthSlab);
        registerItemBlockModel(WBlocks.groundWestSlab);
        registerItemBlockModel(WBlocks.ground2);
        registerItemBlockModel(WBlocks.ground2EastSlab);
        registerItemBlockModel(WBlocks.ground2NorthSlab);
        registerItemBlockModel(WBlocks.ground2SouthSlab);
        registerItemBlockModel(WBlocks.ground2WestSlab);
        registerItemBlockModel(WBlocks.ground3);
        registerItemBlockModel(WBlocks.ground3Slab);
        registerItemBlockModel(WBlocks.ground4);
        registerItemBlockModel(WBlocks.ground4Slab);
        registerItemBlockModel(WBlocks.ground11);
        registerItemBlockModel(WBlocks.ground11Slab);
        registerItemBlockModel(WBlocks.ground12);
        registerItemBlockModel(WBlocks.ground12Slab);
        registerItemBlockModel(WBlocks.ground13);
        registerItemBlockModel(WBlocks.ground13Slab);
        registerItemBlockModel(WBlocks.ground14);
        registerItemBlockModel(WBlocks.ground14Slab);
        registerItemBlockModel(WBlocks.hbStand);
        registerItemBlockModel(WBlocks.hbStandSlab);
        registerItemBlockModel(WBlocks.invisibleWall);
        registerItemBlockModel(WBlocks.pillar);
        registerItemBlockModel(WBlocks.pillarSlab);
        registerItemBlockModel(WBlocks.plank);
        registerItemBlockModel(WBlocks.plant);
        registerItemBlockModel(WBlocks.plant2);
        registerItemBlockModel(WBlocks.sufokiaColor);
        registerItemBlockModel(WBlocks.sufokiaGround);
        registerItemBlockModel(WBlocks.sufokiaGroundSlab);
        registerItemBlockModel(WBlocks.sufokiaGround2);
        registerItemBlockModel(WBlocks.sufokiaGround2Slab);
        registerItemBlockModel(WBlocks.sufokiaSand);
        registerItemBlockModel(WBlocks.sufokiaSandSlab);
        registerItemBlockModel(WBlocks.sufokiaStair);
        registerItemBlockModel(WBlocks.sufokiaSun);
        registerItemBlockModel(WBlocks.sufokiaSunSlab);
        registerItemBlockModel(WBlocks.sufokiaWave);
        registerItemBlockModel(WBlocks.sufokiaWaveEastSlab);
        registerItemBlockModel(WBlocks.sufokiaWaveNorthSlab);
        registerItemBlockModel(WBlocks.sufokiaWaveSouthSlab);
        registerItemBlockModel(WBlocks.sufokiaWaveWestSlab);
        registerItemBlockModel(WBlocks.sufokiaWave2);
        registerItemBlockModel(WBlocks.sufokiaWave2EastSlab);
        registerItemBlockModel(WBlocks.sufokiaWave2NorthSlab);
        registerItemBlockModel(WBlocks.sufokiaWave2SouthSlab);
        registerItemBlockModel(WBlocks.sufokiaWave2WestSlab);
        registerItemBlockModel(WBlocks.sufokiaWave3);
        registerItemBlockModel(WBlocks.sufokiaWave3EastSlab);
        registerItemBlockModel(WBlocks.sufokiaWave3NorthSlab);
        registerItemBlockModel(WBlocks.sufokiaWave3SouthSlab);
        registerItemBlockModel(WBlocks.sufokiaWave3WestSlab);
        registerItemBlockModel(WBlocks.sufokiaWave4);
        registerItemBlockModel(WBlocks.wood);
        registerItemBlockModel(WBlocks.woodEastSlab);
        registerItemBlockModel(WBlocks.woodNorthSlab);
        registerItemBlockModel(WBlocks.woodSouthSlab);
        registerItemBlockModel(WBlocks.woodWestSlab);

        // Crop
        registerItemBlockModel(WBlocks.artichoke);
        registerItemBlockModel(WBlocks.tuberbulb);
        registerItemBlockModel(WBlocks.wheat);

        // Special
        registerItemBlockModel(WBlocks.classConsole);
        registerItemBlockModel(WBlocks.dragoExpress);
        registerItemBlockModel(WBlocks.jobPolisher);
        registerItemBlockModel(WBlocks.phoenix);

        // Haven Bag
        registerItemBlockModel(WBlocks.havenbag);
        registerItemBlockModel(WBlocks.hbBarrier);
        registerItemBlockModel(WBlocks.hbBridge);
        registerItemBlockModel(WBlocks.hbChest);
        registerItemBlockModel(WBlocks.hbCraft);
        registerItemBlockModel(WBlocks.hbCraft2);
        registerItemBlockModel(WBlocks.hbDeco);
        registerItemBlockModel(WBlocks.hbDeco2);
        registerItemBlockModel(WBlocks.hbGarden);
        registerItemBlockModel(WBlocks.hbGemWorkbench);
        registerItemBlockModel(WBlocks.hbLock);
        registerItemBlockModel(WBlocks.hbMerchant);
        registerItemBlockModel(WBlocks.hbVisitors);

        // Orientation item slab
        registerItemBlockModel(WItems.carpet);
        registerItemBlockModel(WItems.groundSlab);
        registerItemBlockModel(WItems.ground2Slab);
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
        registerItemModel(WItems.verbalaSalt);
        registerItemModel(WItems.gumgum);
        registerItemModel(WItems.polishedMoonstone);
        registerItemModel(WItems.shadowyBlue);

        Item[] ores = {WItems.ore1, WItems.ore2};
        for (int i = 0; i < ores.length; i++) {
            for (int j = 0; j < 16 && j < EnumOre.values().length - i * 16; j++) {
                registerItemModel(ores[i], j, Reference.MODID.toLowerCase() + ":ore_" + EnumOre.values()[i * 16 + j].getName());
            }
        }

        Item[] farmerSeeds = {
                WItems.artichokeSeed, WItems.babbageSeed, WItems.beanSeed, WItems.blackberrySeed, WItems.blackCawwotSeed,
                WItems.cawwotSeed, WItems.chiliSeed, WItems.cornSeed, WItems.curarareSeed, WItems.desertTruffleSeed,
                WItems.grainOfBarley, WItems.grainOfVanillaRice, WItems.jollyflowerSeed, WItems.juteySeed, WItems.makoffeeSeed,
                WItems.melonSeed, WItems.mushraySeed, WItems.oatGrain, WItems.palmCaneSeed, WItems.pumpkinSeed,
                WItems.ryeGrain, WItems.sunflowerSeed, WItems.tuberbulbSeed, WItems.watermelonSeed, WItems.wheatSeed};
        registerItemModel(farmerSeeds);

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
        registerItemBlockModel(itemBlock, 0, itemBlock.getUnlocalizedName().substring(5).replaceFirst("\\.", ":block_"));
    }

    protected void registerItemBlockModel(final Item itemBlock, final int metadata, final String name) {
        ModelLoader.setCustomModelResourceLocation(itemBlock, metadata, new ModelResourceLocation(name, "inventory"));
    }

    protected void registerItemModel(final Item[] items) {
        for (Item item : items) {
            registerItemModel(item);
        }
    }

    protected void registerItemModel(final Item item) {
        registerItemModel(item, 0, item.getUnlocalizedName().substring(5).replace('.', ':'));
    }

    protected void registerItemModel(final Item item, final int metadata, final String name) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(name, "inventory"));
    }

    protected void registerSpellModel(final Item spell) {
        ModelLoader.setCustomModelResourceLocation(spell, 0, new ModelResourceLocation(Reference.MODID + ":spell_" + spell.getUnlocalizedName().substring(6), "inventory"));
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
    }

    @Override
    public void registerKeyBindings() {
        super.registerKeyBindings();

        KeyBindings.init();

        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
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
