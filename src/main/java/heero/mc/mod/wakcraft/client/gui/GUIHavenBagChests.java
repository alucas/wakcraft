package heero.mc.mod.wakcraft.client.gui;

import heero.mc.mod.wakcraft.client.gui.inventory.GUIHavenBagChest;
import heero.mc.mod.wakcraft.havenbag.ChestType;
import heero.mc.mod.wakcraft.inventory.ContainerHavenBagChest;
import heero.mc.mod.wakcraft.network.GuiId;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIHavenBagChests extends GUITabs {
	protected static List<GuiId> havenBagChestGuiIds = new ArrayList<GuiId>();
	static {
		havenBagChestGuiIds.add(GuiId.HAVEN_BAG_CHEST_NORMAL);
		havenBagChestGuiIds.add(GuiId.HAVEN_BAG_CHEST_SMALL);
		havenBagChestGuiIds.add(GuiId.HAVEN_BAG_CHEST_ADVENTURER);
		havenBagChestGuiIds.add(GuiId.HAVEN_BAG_CHEST_KIT);
		havenBagChestGuiIds.add(GuiId.HAVEN_BAG_CHEST_COLLECTOR);
		havenBagChestGuiIds.add(GuiId.HAVEN_BAG_CHEST_GOLDEN);
		havenBagChestGuiIds.add(GuiId.HAVEN_BAG_CHEST_EMERALD);
	}

	protected ContainerHavenBagChest container;

	public GUIHavenBagChests(GuiId guiId, ContainerHavenBagChest container, EntityPlayer player, World world, int x, int y, int z) {
		super(new GUIHavenBagChest(container, ChestType.CHEST_NORMAL),
				player,
				world,
				x,
				y,
				z,
				havenBagChestGuiIds.indexOf(GuiId.HAVEN_BAG_CHEST_NORMAL),
				havenBagChestGuiIds);

		this.container = container;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		super.initGui();

		tabButtonTop = height / 2 - 105;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed() {
		if (this.mc.thePlayer != null) {
			container.onContainerClosed(this.mc.thePlayer);
		}
	}

	@Override
	protected void onSelectTab(int tabId) {
		ChestType chestTypes[] = ChestType.values();
		if (tabId < 0 || tabId >= chestTypes.length) {
			FMLLog.warning("Cannot convert tabId %d to chestTypeId");
			return;
		}

		((GUIHavenBagChest) currentScreen).chestId = chestTypes[tabId];
		container.updateHBSlots(chestTypes[tabId]);
	}
}