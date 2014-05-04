package heero.wakcraft.client.gui;

import heero.wakcraft.inventory.ContainerHavenBagChest;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIHavenBagChests extends GUITabs {
	private ContainerHavenBagChest container;

	public GUIHavenBagChests(ContainerHavenBagChest container) {
		super(new GuiScreen[] { new GUIHavenBagChest(container),
				new GUIHavenBagChest(container),
				new GUIHavenBagChest(container) });

		this.container = container;

		setSelectedTab(0);
	}

	@Override
	protected void setSelectedTab(int tabId) {
		super.setSelectedTab(tabId);

		container.updateHBSlots(tabId);
	}
}