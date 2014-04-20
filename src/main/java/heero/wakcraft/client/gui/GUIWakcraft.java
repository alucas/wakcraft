package heero.wakcraft.client.gui;

import heero.wakcraft.client.gui.inventory.GUIInventory;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIWakcraft extends GUITabs {

	public GUIWakcraft(EntityPlayer player) {
		super(new GuiScreen[] { new GUIInventory(player),
				new GUIProfession(player) });
	}
}
