package heero.wakcraft.client.gui;

import heero.wakcraft.inventory.ContainerHavenBagChest;
import heero.wakcraft.tileentity.TileEntityHavenBagChest;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIHavenBagChests extends GUITabs {
	private ContainerHavenBagChest container;

	public GUIHavenBagChests(ContainerHavenBagChest container) {
		super(new GuiScreen[] { new GUIHavenBagChest(container, TileEntityHavenBagChest.CHEST_NORMAL),
				new GUIHavenBagChest(container, TileEntityHavenBagChest.CHEST_SMALL),
				new GUIHavenBagChest(container, TileEntityHavenBagChest.CHEST_ADVENTURER),
				new GUIHavenBagChest(container, TileEntityHavenBagChest.CHEST_KIT),
				new GUIHavenBagChest(container, TileEntityHavenBagChest.CHEST_ADVENTURER),
				new GUIHavenBagChest(container, TileEntityHavenBagChest.CHEST_GOLDEN),
				new GUIHavenBagChest(container, TileEntityHavenBagChest.CHEST_EMERALD) });

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
}