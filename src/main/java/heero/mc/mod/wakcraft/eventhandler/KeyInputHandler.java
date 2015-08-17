package heero.mc.mod.wakcraft.eventhandler;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.client.setting.KeyBindings;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagTeleportation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyInputHandler {
	private long havenBagTimer = 0;

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (KeyBindings.havenBag.isPressed()) {
			long time = System.currentTimeMillis();

			if (time < havenBagTimer + 1000) {
				return;
			}

			Wakcraft.packetPipeline.sendToServer(new PacketHavenBagTeleportation());
		}
	}
}