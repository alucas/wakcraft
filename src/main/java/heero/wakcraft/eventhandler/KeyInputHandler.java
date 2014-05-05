package heero.wakcraft.eventhandler;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.client.setting.KeyBindings;
import heero.wakcraft.network.packet.HavenBagPacket;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyInputHandler {
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (KeyBindings.havenBag.isPressed()) {
			EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
			WorldClient world = Minecraft.getMinecraft().theWorld;

			if (! player.onGround) {
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cantOpenHavenBagInAir")));
				return;
			}

			Block block = world.getBlock((int)Math.floor(player.posX), (int)Math.floor(player.posY - 1.63), (int)Math.floor(player.posZ));
			if (block == null || (block != null && !block.isOpaqueCube())) {
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cantOpenHavenBagHere")));
				return;
			}

			Wakcraft.packetPipeline.sendToServer(new HavenBagPacket());
		}
	}
}