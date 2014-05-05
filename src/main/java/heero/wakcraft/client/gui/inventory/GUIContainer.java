package heero.wakcraft.client.gui.inventory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GUIContainer extends GuiScreen {
	/** The location of the inventory background texture */
	protected static final ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
	/** The X size of the inventory window in pixels. */
	protected int xSize = 176;
	/** The Y size of the inventory window in pixels. */
	protected int ySize = 166;

	/** A list of the players inventory slots */
	public Container inventorySlots;

	/** Starting X position for the Gui. Inconsistent use for Gui backgrounds. */
	protected int guiLeft;
	/** Starting Y position for the Gui. Inconsistent use for Gui backgrounds. */
	protected int guiTop;

	/** holds the slot currently hovered */
	private Slot hoveredSlot;
	/** Used when touchscreen is enabled. */
	private Slot clickedSlot;
	/** Used when touchscreen is enabled. */
	private boolean isRightMouseClick;
	/** Used when touchscreen is enabled */
	private ItemStack draggedStack;
	private int touchUpX;
	private int touchUpY;
	private Slot returningStackDestSlot;
	private long returningStackTime;
	/** Used when touchscreen is enabled */
	private ItemStack returningStack;
	private Slot field_146985_D;
	private long field_146986_E;

	protected final Set dragSplittingSlots = new HashSet();
	protected boolean dragSpitting;

	private int dragSplittingLimit;
	private int dragSplittingButton;
	private boolean ignoreMouseUp;
	private int dragSplittingRemnant;
	private long lastClickTime;
	private Slot lastClickSlot;
	private int lastClickButton;
	private boolean doubleClick;
	private ItemStack shiftClickedSlot;

	public GUIContainer(Container container) {
		this.inventorySlots = container;
		this.ignoreMouseUp = true;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		super.initGui();

		this.mc.thePlayer.openContainer = this.inventorySlots;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
		drawDefaultBackground();
		drawGuiContainerBackgroundLayer(renderPartialTicks, mouseX, mouseY);

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		super.drawScreen(mouseX, mouseY, renderPartialTicks);

		RenderHelper.enableGUIStandardItemLighting();
		GL11.glPushMatrix();
		GL11.glTranslatef((float) guiLeft, (float) guiTop, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) 240 / 1.0F, (float) 240 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		hoveredSlot = getHoveredSlot(mouseX, mouseY);
		
		drawSlots(inventorySlots.inventorySlots, (hoveredSlot == null) ? -1 : hoveredSlot.slotNumber);

		// Forge: Force lighting to be disabled as there are some issue where
		// lighting would
		// incorrectly be applied based on items that are in the inventory.
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);

		InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
		ItemStack itemstack = this.draggedStack == null ? inventoryplayer.getItemStack() : this.draggedStack;

		// Draw hold itemStack
		if (itemstack != null) {
			byte b0 = 8;
			int k1 = this.draggedStack == null ? 8 : 16;
			String format = null;

			if (this.draggedStack != null && this.isRightMouseClick) {
				itemstack = itemstack.copy();
				itemstack.stackSize = MathHelper.ceiling_float_int((float) itemstack.stackSize / 2.0F);
			} else if (this.dragSpitting && this.dragSplittingSlots.size() > 1) {
				itemstack = itemstack.copy();
				itemstack.stackSize = this.dragSplittingRemnant;

				if (itemstack.stackSize == 0) {
					format = "" + EnumChatFormatting.YELLOW + "0";
				}
			}

			this.drawItemStack(itemstack, mouseX - guiLeft - b0, mouseY - guiTop - k1, format);
		}

		if (this.returningStack != null) {
			float f1 = (float) (Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;

			if (f1 >= 1.0F) {
				f1 = 1.0F;
				this.returningStack = null;
			}

			int k1 = this.returningStackDestSlot.xDisplayPosition - this.touchUpX;
			int j2 = this.returningStackDestSlot.yDisplayPosition - this.touchUpY;
			int l1 = this.touchUpX + (int) ((float) k1 * f1);
			int i2 = this.touchUpY + (int) ((float) j2 * f1);
			this.drawItemStack(this.returningStack, l1, i2, (String) null);
		}

		GL11.glPopMatrix();

		// Render tooltips
		if (inventoryplayer.getItemStack() == null && this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
			ItemStack itemstack1 = this.hoveredSlot.getStack();
			this.renderToolTip(itemstack1, mouseX, mouseY);
		}

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	}

	/**
	 * drawItemStack Render an ItemStack. Args : stack, x, y, format
	 */
	protected void drawItemStack(ItemStack stack, int x, int y, String format) {
		GL11.glTranslatef(0.0F, 0.0F, 32.0F);

		this.zLevel = 200.0F;
		itemRender.zLevel = 200.0F;

		FontRenderer font = null;
		if (stack != null) {
			font = stack.getItem().getFontRenderer(stack);
		}
		if (font == null) {
			font = fontRendererObj;
		}

		itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), stack, x, y);
		itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), stack, x, y - (this.draggedStack == null ? 0 : 8), format);

		this.zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	}

	/**
	 * Args : renderPartialTicks, mouseX, mouseY
	 */
	protected abstract void drawGuiContainerBackgroundLayer(
			float renderPartialTicks, int mouseX, int mouseY);

	protected void drawSlots(List slots, int hoveredSlotId) {
		for (int i = 0; i < inventorySlots.inventorySlots.size(); ++i) {
			Slot slot = (Slot) inventorySlots.inventorySlots.get(i);

			drawSlot(slot);

			if (slot.slotNumber == hoveredSlotId) {
				drawSlotOverlay(slot);
			}
		}
	}

	/**
	 * Render a slot
	 */
	protected void drawSlot(Slot slot) {
		int slotX = slot.xDisplayPosition;
		int slotY = slot.yDisplayPosition;

		ItemStack itemstack = slot.getStack();

		boolean flag = false;
		boolean flag1 = slot == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick;
		ItemStack itemstack1 = this.mc.thePlayer.inventory.getItemStack();
		String format = null;

		if (slot == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && itemstack != null) {
			itemstack = itemstack.copy();
			itemstack.stackSize /= 2;
		} else if (this.dragSpitting && this.dragSplittingSlots.contains(slot) && itemstack1 != null) {
			if (this.dragSplittingSlots.size() == 1) {
				return;
			}

			if (Container.func_94527_a(slot, itemstack1, true) && this.inventorySlots.canDragIntoSlot(slot)) {
				itemstack = itemstack1.copy();
				flag = true;
				Container.func_94525_a(this.dragSplittingSlots, this.dragSplittingLimit, itemstack, slot.getStack() == null ? 0 : slot.getStack().stackSize);

				if (itemstack.stackSize > itemstack.getMaxStackSize()) {
					format = EnumChatFormatting.YELLOW + "" + itemstack.getMaxStackSize();
					itemstack.stackSize = itemstack.getMaxStackSize();
				}

				if (itemstack.stackSize > slot.getSlotStackLimit()) {
					format = EnumChatFormatting.YELLOW + "" + slot.getSlotStackLimit();
					itemstack.stackSize = slot.getSlotStackLimit();
				}
			} else {
				this.dragSplittingSlots.remove(slot);
				this.updateDragSplitting();
			}
		}

		this.zLevel = 100.0F;
		itemRender.zLevel = 100.0F;

		// Placeholder
		if (itemstack == null) {
			IIcon icon = slot.getBackgroundIconIndex();

			if (icon != null) {
				GL11.glDisable(GL11.GL_LIGHTING);
				this.mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
				this.drawTexturedModelRectFromIcon(slotX, slotY, icon, 16, 16);
				GL11.glEnable(GL11.GL_LIGHTING);
				flag1 = true;
			}
		}

		// Render ItemStack
		if (!flag1) {
			if (flag) {
				drawRect(slotX, slotY, slotX + 16, slotY + 16, -2130706433);
			}

			GL11.glEnable(GL11.GL_DEPTH_TEST);

			itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemstack, slotX, slotY);
			itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemstack, slotX, slotY, format);
		}

		itemRender.zLevel = 0.0F;
		this.zLevel = 0.0F;
	}

	protected void drawSlotOverlay(Slot slot) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		int slotX = slot.xDisplayPosition;
		int slotY = slot.yDisplayPosition;

		GL11.glColorMask(true, true, true, false);

		drawRect(slotX, slotY, slotX + 16, slotY + 16, 0x80FFFFFF);

		GL11.glColorMask(true, true, true, true);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	protected void updateDragSplitting() {
		ItemStack itemstack = this.mc.thePlayer.inventory.getItemStack();

		if (itemstack != null && this.dragSpitting) {
			this.dragSplittingRemnant = itemstack.stackSize;
			ItemStack itemstack1;
			int i;

			for (Iterator iterator = this.dragSplittingSlots.iterator(); iterator.hasNext(); this.dragSplittingRemnant -= itemstack1.stackSize - i) {
				Slot slot = (Slot) iterator.next();
				itemstack1 = itemstack.copy();
				i = slot.getStack() == null ? 0 : slot.getStack().stackSize;
				Container.func_94525_a(this.dragSplittingSlots, this.dragSplittingLimit, itemstack1, i);

				if (itemstack1.stackSize > itemstack1.getMaxStackSize()) {
					itemstack1.stackSize = itemstack1.getMaxStackSize();
				}

				if (itemstack1.stackSize > slot.getSlotStackLimit()) {
					itemstack1.stackSize = slot.getSlotStackLimit();
				}
			}
		}
	}

	/**
	 * Returns the slot at the given coordinates or null if there is none.
	 */
	protected Slot getSlotAtPosition(int x, int y) {
		for (int k = 0; k < this.inventorySlots.inventorySlots.size(); ++k) {
			Slot slot = (Slot) this.inventorySlots.inventorySlots.get(k);

			if (this.isMouseOverSlot(slot, x, y)) {
				return slot;
			}
		}

		return null;
	}
	
	protected Slot getHoveredSlot(int mouseX, int mouseY) {
		for (int i = 0; i < inventorySlots.inventorySlots.size(); ++i) {
			Slot slot = (Slot) inventorySlots.inventorySlots.get(i);

			if (isMouseOverSlot(slot, mouseX, mouseY) && slot.func_111238_b()) {
				return slot;
			}
		}

		return null;
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int clickedButton) {
		super.mouseClicked(mouseX, mouseY, clickedButton);

		boolean pickBlockButton = clickedButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;

		Slot slot = this.getSlotAtPosition(mouseX, mouseY);

		long time = Minecraft.getSystemTime();

		this.doubleClick = this.lastClickSlot == slot && time - this.lastClickTime < 250L && this.lastClickButton == clickedButton;
		this.ignoreMouseUp = false;

		if (clickedButton == 0 || clickedButton == 1 || pickBlockButton) {
			boolean clickOutside = mouseX < guiLeft || mouseY < guiTop || mouseX >= guiLeft + this.xSize || mouseY >= guiTop + this.ySize;
			int slotId = -1;

			if (slot != null) {
				slotId = slot.slotNumber;
			}

			if (clickOutside) {
				slotId = -999;
			}

			if (this.mc.gameSettings.touchscreen && clickOutside && this.mc.thePlayer.inventory.getItemStack() == null) {
				this.mc.displayGuiScreen((GuiScreen) null);
				return;
			}

			// Click on slot or outside
			if (slotId != -1) {
				if (this.mc.gameSettings.touchscreen) {
					if (slot != null && slot.getHasStack()) {
						this.clickedSlot = slot;
						this.draggedStack = null;
						this.isRightMouseClick = clickedButton == 1;
					} else {
						this.clickedSlot = null;
					}
				} else if (!this.dragSpitting) {
					if (this.mc.thePlayer.inventory.getItemStack() == null) {
						if (pickBlockButton) {
							this.handleMouseClick(slot, slotId, clickedButton, 3);
						} else {
							boolean shift = slotId != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
							byte b0 = 0;

							if (shift) {
								this.shiftClickedSlot = slot != null && slot.getHasStack() ? slot.getStack() : null;
								b0 = 1;
							} else if (slotId == -999) {
								b0 = 4;
							}

							this.handleMouseClick(slot, slotId, clickedButton, b0);
						}

						this.ignoreMouseUp = true;
					} else {
						this.dragSpitting = true;
						this.dragSplittingButton = clickedButton;
						this.dragSplittingSlots.clear();

						if (clickedButton == 0) {
							this.dragSplittingLimit = 0;
						} else if (clickedButton == 1) {
							this.dragSplittingLimit = 1;
						}
					}
				}
			}
		}

		this.lastClickSlot = slot;
		this.lastClickTime = time;
		this.lastClickButton = clickedButton;
	}

	/**
	 * Called when a mouse button is pressed and the mouse is moved around.
	 * Parameters are : mouseX, mouseY, lastButtonClicked & timeSinceMouseClick.
	 */
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceMouseClick) {
		Slot slot = this.getSlotAtPosition(mouseX, mouseY);
		ItemStack itemstack = this.mc.thePlayer.inventory.getItemStack();

		if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
			if (lastButtonClicked == 0 || lastButtonClicked == 1) {
				if (this.draggedStack == null) {
					if (slot != this.clickedSlot) {
						this.draggedStack = this.clickedSlot.getStack().copy();
					}
				} else if (this.draggedStack.stackSize > 1 && slot != null && Container.func_94527_a(slot, this.draggedStack, false)) {
					long i1 = Minecraft.getSystemTime();

					if (this.field_146985_D == slot) {
						if (i1 - this.field_146986_E > 500L) {
							this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
							this.handleMouseClick(slot, slot.slotNumber, 1, 0);
							this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
							this.field_146986_E = i1 + 750L;
							--this.draggedStack.stackSize;
						}
					} else {
						this.field_146985_D = slot;
						this.field_146986_E = i1;
					}
				}
			}
		} else if (this.dragSpitting && slot != null && itemstack != null && itemstack.stackSize > this.dragSplittingSlots.size() && Container.func_94527_a(slot, itemstack, true) && slot.isItemValid(itemstack) && this.inventorySlots.canDragIntoSlot(slot)) {
			this.dragSplittingSlots.add(slot);
			this.updateDragSplitting();
		}
	}

	/**
	 * Called when the mouse released. Args : mouseX, mouseY, releasedButton
	 */
	protected void mouseMovedOrUp(int mouseX, int mouseY, int releasedButton) {
		Slot slot = this.getSlotAtPosition(mouseX, mouseY);

		int x = this.guiLeft;
		int y = this.guiTop;

		boolean releaseOutside = mouseX < x || mouseY < y || mouseX >= x + this.xSize || mouseY >= y + this.ySize;
		int slotID = -1;

		if (slot != null) {
			slotID = slot.slotNumber;
		}

		if (releaseOutside) {
			slotID = -999;
		}

		Slot slot1;
		Iterator iterator;

		if (this.doubleClick && slot != null && releasedButton == 0 && this.inventorySlots.func_94530_a((ItemStack) null, slot)) {
			// shift + double clic while holding item = transfert all item
			if (isShiftKeyDown()) {
				if (slot != null && slot.inventory != null && this.shiftClickedSlot != null) {
					iterator = this.inventorySlots.inventorySlots.iterator();

					while (iterator.hasNext()) {
						slot1 = (Slot) iterator.next();

						if (slot1 != null && slot1.canTakeStack(this.mc.thePlayer) && slot1.getHasStack() && slot1.inventory == slot.inventory && Container.func_94527_a(slot1, this.shiftClickedSlot, true)) {
							this.handleMouseClick(slot1, slot1.slotNumber, releasedButton, 1);
						}
					}
				}
			} else {
				this.handleMouseClick(slot, slotID, releasedButton, 6);
			}

			this.doubleClick = false;
			this.lastClickTime = 0L;
		} else {
			if (this.dragSpitting && this.dragSplittingButton != releasedButton) {
				this.dragSpitting = false;
				this.dragSplittingSlots.clear();
				this.ignoreMouseUp = true;
				return;
			}

			if (this.ignoreMouseUp) {
				this.ignoreMouseUp = false;
				return;
			}

			boolean flag1;

			if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
				if (releasedButton == 0 || releasedButton == 1) {
					if (this.draggedStack == null && slot != this.clickedSlot) {
						this.draggedStack = this.clickedSlot.getStack();
					}

					flag1 = Container.func_94527_a(slot, this.draggedStack, false);

					if (slotID != -1 && this.draggedStack != null && flag1) {
						this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, releasedButton, 0);
						this.handleMouseClick(slot, slotID, 0, 0);

						if (this.mc.thePlayer.inventory.getItemStack() != null) {
							this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, releasedButton, 0);
							this.touchUpX = mouseX - x;
							this.touchUpY = mouseY - y;
							this.returningStackDestSlot = this.clickedSlot;
							this.returningStack = this.draggedStack;
							this.returningStackTime = Minecraft.getSystemTime();
						} else {
							this.returningStack = null;
						}
					} else if (this.draggedStack != null) {
						this.touchUpX = mouseX - x;
						this.touchUpY = mouseY - y;
						this.returningStackDestSlot = this.clickedSlot;
						this.returningStack = this.draggedStack;
						this.returningStackTime = Minecraft.getSystemTime();
					}

					this.draggedStack = null;
					this.clickedSlot = null;
				}
			} else if (this.dragSpitting && !this.dragSplittingSlots.isEmpty()) {
				this.handleMouseClick((Slot) null, -999, Container.func_94534_d(0, this.dragSplittingLimit), 5);
				iterator = this.dragSplittingSlots.iterator();

				while (iterator.hasNext()) {
					slot1 = (Slot) iterator.next();
					this.handleMouseClick(slot1, slot1.slotNumber, Container.func_94534_d(1, this.dragSplittingLimit), 5);
				}

				this.handleMouseClick((Slot) null, -999, Container.func_94534_d(2, this.dragSplittingLimit), 5);
			} else if (this.mc.thePlayer.inventory.getItemStack() != null) {
				if (releasedButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
					this.handleMouseClick(slot, slotID, releasedButton, 3);
				} else {
					flag1 = slotID != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));

					if (flag1) {
						this.shiftClickedSlot = slot != null && slot.getHasStack() ? slot.getStack() : null;
					}

					this.handleMouseClick(slot, slotID, releasedButton, flag1 ? 1 : 0);
				}
			}
		}

		if (this.mc.thePlayer.inventory.getItemStack() == null) {
			this.lastClickTime = 0L;
		}

		this.dragSpitting = false;
	}

	/**
	 * Returns if the passed mouse position is over the specified slot.
	 */
	protected boolean isMouseOverSlot(Slot slot, int mouseX, int mouseY) {
		return this.isPointInRegion(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, mouseX, mouseY);
	}

	/**
	 * Test if the 2D point is in a rectangle (relative to the GUI). Args :
	 * rectX, rectY, rectWidth, rectHeight, pointX, pointY
	 */
	protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
		int guiX = this.guiLeft;
		int guiY = this.guiTop;
		pointX -= guiX;
		pointY -= guiY;
		return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
	}

	/**
	 * Called when the mouse is clicked over a slot or outside the gui. Args :
	 * slot, slotId, clickedButton, mode (0 = basic click, 1 = shift click, 2 =
	 * Hotbar, 3 = pickBlock, 4 = Drop, 5 = ?, 6 = Double click)
	 */
	protected void handleMouseClick(Slot slot, int slotId, int clickedButton, int mode) {
		if (slot != null) {
			slotId = slot.slotNumber;
		}

		this.mc.playerController.windowClick(this.inventorySlots.windowId, slotId, clickedButton, mode, this.mc.thePlayer);
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(char character, int keyCode) {
		if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
		}

		this.checkHotbarKeys(keyCode);

		if (this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
			if (keyCode == this.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
				this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, 0, 3);
			} else if (keyCode == this.mc.gameSettings.keyBindDrop.getKeyCode()) {
				this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, isCtrlKeyDown() ? 1 : 0, 4);
			}
		}
	}

	/**
	 * This function is what controls the hotbar shortcut check when you press a
	 * number key when hovering a stack. Args : keyCode, Returns true if a
	 * Hotbar key is pressed, else false
	 */
	protected boolean checkHotbarKeys(int keyCode) {
		if (this.mc.thePlayer.inventory.getItemStack() == null && this.hoveredSlot != null) {
			for (int j = 0; j < 9; ++j) {
				if (keyCode == this.mc.gameSettings.keyBindsHotbar[j].getKeyCode()) {
					this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, j, 2);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed() {
		if (this.mc.thePlayer != null) {
			this.inventorySlots.onContainerClosed(this.mc.thePlayer);
		}
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		super.updateScreen();

		if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead) {
			this.mc.thePlayer.closeScreen();
		}
	}
}