package heero.mc.mod.wakcraft.client.gui;

import heero.mc.mod.wakcraft.client.gui.inventory.GUIHavenBagChest;
import heero.mc.mod.wakcraft.inventory.ContainerHavenBagChest;
import heero.mc.mod.wakcraft.manager.HavenBagChestHelper;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIHavenBagChests extends GUITabs {
	private ContainerHavenBagChest container;

	public GUIHavenBagChests(ContainerHavenBagChest container) {
		super(new GuiScreen[] { new GUIHavenBagChest(container, HavenBagChestHelper.CHEST_NORMAL),
				new GUIHavenBagChest(container, HavenBagChestHelper.CHEST_SMALL),
				new GUIHavenBagChest(container, HavenBagChestHelper.CHEST_ADVENTURER),
				new GUIHavenBagChest(container, HavenBagChestHelper.CHEST_KIT),
				new GUIHavenBagChest(container, HavenBagChestHelper.CHEST_COLLECTOR),
				new GUIHavenBagChest(container, HavenBagChestHelper.CHEST_GOLDEN),
				new GUIHavenBagChest(container, HavenBagChestHelper.CHEST_EMERALD) });

		this.container = container;

		setSelectedTab(0);
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
	protected void setSelectedTab(int tabId) {
		super.setSelectedTab(tabId);

		container.updateHBSlots(((GUIHavenBagChest)tabs[selectedTab]).chestId);
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
}