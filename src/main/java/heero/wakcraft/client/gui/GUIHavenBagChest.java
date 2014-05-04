package heero.wakcraft.client.gui;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.havenbag.HavenBagManager;
import heero.wakcraft.inventory.ContainerHavenBagChest;
import heero.wakcraft.inventory.ContainerHavenBagChest.HavenBagChestSlot;

import java.util.List;

import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIHavenBagChest extends GUIContainer {
	private static final ResourceLocation textureBackground = new ResourceLocation(WakcraftInfo.MODID.toLowerCase(), "textures/gui/havenbagchest.png");
	private static final ResourceLocation textureBackground_locked = new ResourceLocation(WakcraftInfo.MODID.toLowerCase(), "textures/gui/havenbagchest_locked.png");

	public int chestId;

	public GUIHavenBagChest(ContainerHavenBagChest container, int chestId) {
		super(container);

		this.chestId = chestId;
		this.ySize = 222;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick,
			int mouseX, int mouseY) {
		if (((ContainerHavenBagChest) inventorySlots).isChestLocked(chestId)) {
			mc.getTextureManager().bindTexture(textureBackground);
		} else {
			mc.getTextureManager().bindTexture(textureBackground_locked);
		}

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		GL11.glEnable(GL11.GL_BLEND);

		for (int i = HavenBagManager.getChestSize(chestId); i < 54; i++) {
			drawTexturedModalRect(guiLeft + 8 + (i % 9) * 18, guiTop + 18 + (i / 9) * 18, xSize, 0, 16, 16);
		}

		GL11.glDisable(GL11.GL_BLEND);
	}

	/**
	 * Draws either a gradient over the background screen (when it exists) or a
	 * flat gradient over background.png
	 */
	public void drawDefaultBackground() {
		// no background
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