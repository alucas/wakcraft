package heero.wakcraft.client.gui;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.inventory.ContainerHavenBagChest.HavenBagChestSlot;

import java.util.List;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIHavenBagChest extends GUIContainer {
	private static final ResourceLocation textureBackground = new ResourceLocation(WakcraftInfo.MODID.toLowerCase(), "textures/gui/havenbagchest.png");

	public GUIHavenBagChest(Container container) {
		super(container);

		ySize = 222;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick,
			int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(textureBackground);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	@Override
	protected Slot getSlotAtPosition(int x, int y) {
		for (int k = 0; k < this.inventorySlots.inventorySlots.size(); ++k) {
			Slot slot = (Slot) this.inventorySlots.inventorySlots.get(k);

			if (isMouseOverSlot(slot, x, y) && (!(slot instanceof HavenBagChestSlot) || !((HavenBagChestSlot) slot).conceal)) {
				return slot;
			}
		}

		return null;
	}

	@Override
	protected Slot getHoveredSlot(int mouseX, int mouseY) {
		for (int i = 0; i < inventorySlots.inventorySlots.size(); ++i) {
			Slot slot = (Slot) inventorySlots.inventorySlots.get(i);

			if (isMouseOverSlot(slot, mouseX, mouseY) && slot.func_111238_b() && (!(slot instanceof HavenBagChestSlot) || !((HavenBagChestSlot) slot).conceal)) {
				return slot;
			}
		}

		return null;
	}

	@Override
	protected void drawSlots(List slots, int hoveredSlotId) {
		for (int i = 0; i < inventorySlots.inventorySlots.size(); ++i) {
			Slot slot = (Slot) inventorySlots.inventorySlots.get(i);

			if (slot instanceof HavenBagChestSlot && ((HavenBagChestSlot) slot).conceal) {
				continue;
			}

			drawSlot(slot);

			if (slot.slotNumber == hoveredSlotId) {
				drawSlotOverlay(slot);
			}
		}
	}
}