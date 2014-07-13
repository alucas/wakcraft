package heero.mc.mod.wakcraft.proxy;

import heero.mc.mod.wakcraft.WItems;
import heero.mc.mod.wakcraft.client.gui.GUIAbilities;
import heero.mc.mod.wakcraft.client.gui.GUIClassSelection;
import heero.mc.mod.wakcraft.client.gui.GUIHavenBagChests;
import heero.mc.mod.wakcraft.client.gui.GUIHavenBagVisitors;
import heero.mc.mod.wakcraft.client.gui.GUIProfession;
import heero.mc.mod.wakcraft.client.gui.GUIWakcraft;
import heero.mc.mod.wakcraft.client.gui.fight.GuiFightOverlay;
import heero.mc.mod.wakcraft.client.gui.inventory.GUIHavenGemWorkbench;
import heero.mc.mod.wakcraft.client.gui.inventory.GUIInventory;
import heero.mc.mod.wakcraft.client.gui.inventory.GUISpells;
import heero.mc.mod.wakcraft.client.gui.inventory.GUIWorkbench;
import heero.mc.mod.wakcraft.client.model.ModelBabyTofu;
import heero.mc.mod.wakcraft.client.model.ModelBowMeow;
import heero.mc.mod.wakcraft.client.model.ModelGobball;
import heero.mc.mod.wakcraft.client.model.ModelGobballWC;
import heero.mc.mod.wakcraft.client.model.ModelGobbette;
import heero.mc.mod.wakcraft.client.model.ModelGobbly;
import heero.mc.mod.wakcraft.client.model.ModelTofurby;
import heero.mc.mod.wakcraft.client.renderer.block.RendererBlockOre;
import heero.mc.mod.wakcraft.client.renderer.block.RendererBlockPalisade;
import heero.mc.mod.wakcraft.client.renderer.block.RendererBlockPlant;
import heero.mc.mod.wakcraft.client.renderer.block.RendererBlockRotation;
import heero.mc.mod.wakcraft.client.renderer.entity.RendererSeedsPile;
import heero.mc.mod.wakcraft.client.renderer.entity.RendererTextPopup;
import heero.mc.mod.wakcraft.client.renderer.fight.FightRenderer;
import heero.mc.mod.wakcraft.client.renderer.fight.FighterRenderer;
import heero.mc.mod.wakcraft.client.renderer.fight.SpellRenderer;
import heero.mc.mod.wakcraft.client.renderer.item.RendererItemBlock;
import heero.mc.mod.wakcraft.client.renderer.tileentity.RendererDragoexpress;
import heero.mc.mod.wakcraft.client.renderer.tileentity.RendererHavenBagChest;
import heero.mc.mod.wakcraft.client.renderer.tileentity.RendererPhoenix;
import heero.mc.mod.wakcraft.client.setting.KeyBindings;
import heero.mc.mod.wakcraft.entity.creature.gobball.BlackGobbly;
import heero.mc.mod.wakcraft.entity.creature.gobball.Gobball;
import heero.mc.mod.wakcraft.entity.creature.gobball.GobballWC;
import heero.mc.mod.wakcraft.entity.creature.gobball.Gobbette;
import heero.mc.mod.wakcraft.entity.creature.gobball.WhiteGobbly;
import heero.mc.mod.wakcraft.entity.creature.meow.BowMeow;
import heero.mc.mod.wakcraft.entity.creature.tofu.BabyTofu;
import heero.mc.mod.wakcraft.entity.creature.tofu.Tofurby;
import heero.mc.mod.wakcraft.entity.misc.EntitySeedsPile;
import heero.mc.mod.wakcraft.entity.misc.EntityTextPopup;
import heero.mc.mod.wakcraft.eventhandler.GUIEventHandler;
import heero.mc.mod.wakcraft.eventhandler.KeyInputHandler;
import heero.mc.mod.wakcraft.eventhandler.TextureEventHandler;
import heero.mc.mod.wakcraft.fight.FightClientEventsHandler;
import heero.mc.mod.wakcraft.helper.HavenBagHelper;
import heero.mc.mod.wakcraft.inventory.ContainerHavenBagChest;
import heero.mc.mod.wakcraft.inventory.ContainerHavenGemWorkbench;
import heero.mc.mod.wakcraft.inventory.ContainerPlayerInventory;
import heero.mc.mod.wakcraft.inventory.ContainerSpells;
import heero.mc.mod.wakcraft.inventory.ContainerWorkbench;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import heero.mc.mod.wakcraft.tileentity.TileEntityDragoexpress;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBagChest;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenGemWorkbench;
import heero.mc.mod.wakcraft.tileentity.TileEntityPhoenix;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
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
		RenderingRegistry.registerEntityRenderingHandler(BowMeow.class, new BowMeow.RenderBowMeow(new ModelBowMeow(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(BabyTofu.class, new BabyTofu.RenderBabyTofu(new ModelBabyTofu(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(Tofurby.class, new Tofurby.RenderTofurby(new ModelTofurby(), 0.5f));
		
		RenderingRegistry.registerEntityRenderingHandler(EntityTextPopup.class, new RendererTextPopup());
		RenderingRegistry.registerEntityRenderingHandler(EntitySeedsPile.class, new RendererSeedsPile());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDragoexpress.class, new RendererDragoexpress());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPhoenix.class, new RendererPhoenix());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHavenBagChest.class, new RendererHavenBagChest());

		RenderingRegistry.registerBlockHandler(new RendererBlockRotation(RenderingRegistry.getNextAvailableRenderId()));
		RenderingRegistry.registerBlockHandler(new RendererBlockOre(RenderingRegistry.getNextAvailableRenderId()));
		RenderingRegistry.registerBlockHandler(new RendererBlockPalisade(RenderingRegistry.getNextAvailableRenderId()));
		RenderingRegistry.registerBlockHandler(new RendererBlockPlant(RenderingRegistry.getNextAvailableRenderId()));

		RendererItemBlock rendererItemBlock = new RendererItemBlock();
		MinecraftForgeClient.registerItemRenderer(WItems.sufokiaWave1, rendererItemBlock);
		MinecraftForgeClient.registerItemRenderer(WItems.sufokiaWave2, rendererItemBlock);
		MinecraftForgeClient.registerItemRenderer(WItems.sufokiaWave3, rendererItemBlock);
		MinecraftForgeClient.registerItemRenderer(WItems.ground1, rendererItemBlock);
		MinecraftForgeClient.registerItemRenderer(WItems.ground2, rendererItemBlock);
		MinecraftForgeClient.registerItemRenderer(WItems.carpet1, rendererItemBlock);
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
		int uid = HavenBagHelper.getUIDFromCoord((int) player.posX, (int) player.posY, (int) player.posZ);
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
	public Object getGui(GuiId guiId, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity;

		switch (guiId) {
		case POLISHER:
			return new GUIWorkbench(new ContainerWorkbench(player.inventory, world, PROFESSION.MINER), PROFESSION.MINER);
		case INVENTORY:
			return new GUIWakcraft(guiId, new GUIInventory(new ContainerPlayerInventory(player)), player, world, x, y, z);
		case HAVEN_GEM_WORKBENCH:
			tileEntity = (TileEntityHavenGemWorkbench)world.getTileEntity(x, y, z);
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
			tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBagChest)) {
				return null;
			}

			return new GUIHavenBagChests(guiId, new ContainerHavenBagChest(player.inventory, (TileEntityHavenBagChest) tileEntity), player, world, x, y, z);
		case ABILITIES:
			return new GUIWakcraft(guiId, new GUIAbilities(player), player, world, x, y, z);
		case PROFESSION:
			return new GUIWakcraft(guiId, new GUIProfession(player, PROFESSION.CHEF), player, world, x, y, z);
		case SPELLS:
			return new GUIWakcraft(guiId, new GUISpells(new ContainerSpells(player)), player, world, x, y, z);
		default:
			break;
		}

		return null;
	}
}
