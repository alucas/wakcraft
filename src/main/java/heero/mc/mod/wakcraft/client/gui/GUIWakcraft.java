package heero.mc.mod.wakcraft.client.gui;

import heero.mc.mod.wakcraft.network.GuiHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIWakcraft extends GUITabs {
	public GUIWakcraft(GuiScreen guiScreen, EntityPlayer player, World world, int x, int y, int z) {
		super(guiScreen, player, world, x, y, z, new GuiHandler.GuiId[] { GuiHandler.GuiId.INVENTORY,
				GuiHandler.GuiId.ABILITIES, GuiHandler.GuiId.PROFESSION });
	}
}
