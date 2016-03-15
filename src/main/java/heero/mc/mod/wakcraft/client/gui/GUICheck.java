package heero.mc.mod.wakcraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GUICheck extends GuiButton {
    public boolean state;
    public String name;

    public GUICheck(int id, int x, int y) {
        this(id, x, y, 16, 16);
    }

    public GUICheck(int id, int x, int y, int width, int height) {
        super(id, x, y, 16, 16, "");

        this.name = "";
        this.state = false;
    }

    public GUICheck(int id, String name, boolean state, int x, int y) {
        super(id, x, y, 16, 16, "");

        this.name = name;
        this.state = state;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(buttonTextures);

            drawTexturedModalRect(xPosition, yPosition, 208 + (state ? 0 : 16), 0, width, height);
        }
    }
}
