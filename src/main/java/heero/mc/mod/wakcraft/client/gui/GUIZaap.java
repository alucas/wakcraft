package heero.mc.mod.wakcraft.client.gui;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.network.GuiId;
import heero.mc.mod.wakcraft.network.packet.PacketZaapTeleportation;
import heero.mc.mod.wakcraft.util.ZaapUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIZaap extends GuiScreen {
    protected static final ResourceLocation ZAAP_BACKGROUND = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/background.png");

    protected static final int WINDOW_X = 0;
    protected static final int WINDOW_Y = 0;
    protected static final int WINDOW_WIDTH = 176;
    protected static final int WINDOW_HEIGHT = 166;

    protected final EntityPlayer player;
    protected final World world;
    protected final BlockPos pos;

    protected int guiLeft;
    protected int guiTop;

    public GUIZaap(final GuiId id, final EntityPlayer player, final World world, final BlockPos pos) {
        super();

        this.player = player;
        this.world = world;
        this.pos = pos;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui() {
        super.initGui();

        guiLeft = (width - WINDOW_WIDTH) / 2;
        guiTop = (height - WINDOW_HEIGHT) / 2;

        buttonList.clear();

        int i = 0;
        for (BlockPos zaapPos : ZaapUtil.getZaaps(player, world)) {
            buttonList.add(new ZaapButton(i, guiLeft + 15, guiTop + 15 + i * 25, WINDOW_WIDTH - 30, 20, zaapPos));

            i++;
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        mc.getTextureManager().bindTexture(ZAAP_BACKGROUND);

        // Background
        drawTexturedModalRect(guiLeft, guiTop, WINDOW_X, WINDOW_Y, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Titles
        drawString(fontRendererObj, I18n.format("title.zaaps"), guiLeft + 5, guiTop + 5, 0xFFFFFF);

        super.drawScreen(mouseX, mouseY, renderPartialTicks);
    }

    protected void actionPerformed(GuiButton button) {
        if (!(button instanceof ZaapButton)) {
            return;
        }

        final ZaapButton zaapButton = (ZaapButton) button;

        Minecraft.getMinecraft().displayGuiScreen(null);

        Wakcraft.packetPipeline.sendToServer(new PacketZaapTeleportation(zaapButton.pos));
    }

    private class ZaapButton extends GuiButton {
        public final BlockPos pos;

        public ZaapButton(final int id, final int x, final int y, final int width, final int height, final BlockPos pos) {
            super(id, x, y, width, height, "(" + pos.getX() + " / " + pos.getZ() + ")");

            this.pos = pos;
        }
    }
}