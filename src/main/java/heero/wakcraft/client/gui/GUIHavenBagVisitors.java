package heero.wakcraft.client.gui;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.havenbag.HavenBagProperties;
import heero.wakcraft.havenbag.HavenBagsManager;
import heero.wakcraft.network.packet.PacketCloseWindow;
import heero.wakcraft.network.packet.PacketHavenBagVisitors;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;

public class GUIHavenBagVisitors extends GuiScreen {
	protected HavenBagProperties properties;

	public GUIHavenBagVisitors(int uid) {
		super();

		this.properties = HavenBagsManager.getProperties(uid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();

		int aclIndex = 2;
		for (String name : properties.getRightKeys()) {
			int right = properties.getRight(name);
			if (name == HavenBagProperties.ACL_KEY_ALL) {
				buttonList.add(new GUICheck(0, name, (right & 0x1) != 0, 100, 30));
				buttonList.add(new GUICheck(1, name, (right & 0x2) != 0, 130, 30));
				buttonList.add(new GUICheck(2, name, (right & 0x4) != 0, 160, 30));
				buttonList.add(new GUICheck(3, name, (right & 0x8) != 0, 190, 30));

				continue;
			}

			if (name == HavenBagProperties.ACL_KEY_GUILD) {
				buttonList.add(new GUICheck(4, name, (right & 0x1) != 0, 100, 60));
				buttonList.add(new GUICheck(5, name, (right & 0x2) != 0, 130, 60));
				buttonList.add(new GUICheck(6, name, (right & 0x4) != 0, 160, 60));
				buttonList.add(new GUICheck(7, name, (right & 0x8) != 0, 190, 60));

				continue;
			}

			buttonList.add(new GUICheck(aclIndex * 4 + 0, name, (right & 0x1) != 0, 100, 30 + aclIndex * 30));
			buttonList.add(new GUICheck(aclIndex * 4 + 1, name, (right & 0x2) != 0, 130, 30 + aclIndex * 30));
			buttonList.add(new GUICheck(aclIndex * 4 + 2, name, (right & 0x4) != 0, 160, 30 + aclIndex * 30));
			buttonList.add(new GUICheck(aclIndex * 4 + 3, name, (right & 0x8) != 0, 190, 30 + aclIndex * 30));

			++aclIndex;
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		drawBackground(0);

		int lineIndex = 0;
		for (String name : properties.getRightKeys()) {
			if (name == HavenBagProperties.ACL_KEY_ALL) {
				drawString(fontRendererObj, StatCollector.translateToLocal("key.all"), 20, 30, 0xFFFFFF);
				continue;
			}

			if (name == HavenBagProperties.ACL_KEY_GUILD) {
				drawString(fontRendererObj, StatCollector.translateToLocal("key.guild"), 20, 60, 0xFFFFFF);
				continue;
			}

			drawString(fontRendererObj, name, 20, 30 + lineIndex * 30, 0xFFFFFF);

			++lineIndex;
		}

		super.drawScreen(mouseX, mouseY, partialTick);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button instanceof GUICheck) {
			GUICheck buttonCheck = (GUICheck) button;

			buttonCheck.state = !buttonCheck.state;

			Wakcraft.packetPipeline.sendToServer(new PacketHavenBagVisitors(buttonCheck.state ? PacketHavenBagVisitors.ACTION_ADD : PacketHavenBagVisitors.ACTION_REMOVE, buttonCheck.name, 1 << (buttonCheck.id % 4)));
		}
	}

	@Override
	public void onGuiClosed() {
		Wakcraft.packetPipeline.sendToServer(new PacketCloseWindow(PacketCloseWindow.WINDOW_HB_VISITORS));
	}
}
