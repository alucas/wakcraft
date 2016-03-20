package heero.mc.mod.wakcraft.client.gui;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.network.GuiId;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class GUITabs extends GuiScreen {
    private static final ResourceLocation tabButtonsTexture = new ResourceLocation(
            Reference.MODID.toLowerCase(), "textures/gui/tabs.png");

    protected GuiScreen currentScreen;
    protected EntityPlayer player;
    protected World world;
    protected BlockPos pos;
    protected List<GuiId> tabs;
    protected int selectedTab;

    protected int tabButtonLeft;
    protected int tabButtonTop;

    public GUITabs(GuiScreen currentScreen, EntityPlayer player, World world, BlockPos pos, int selectedTab, List<GuiId> tabs) {
        super();

        this.currentScreen = currentScreen;
        this.player = player;
        this.world = world;
        this.pos = pos;
        this.allowUserInput = true;
        this.tabs = tabs;
        this.selectedTab = selectedTab;
    }

    /**
     * Causes the screen to lay out its subcomponents again. This is the
     * equivalent of the Java call Container.validate()
     */
    @Override
    public void setWorldAndResolution(Minecraft minecraft, int width, int height) {
        currentScreen.setWorldAndResolution(minecraft, width, height);

        super.setWorldAndResolution(minecraft, width, height);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui() {
        tabButtonLeft = width / 2 + 85;
        tabButtonTop = height / 2 - 70;

        super.initGui();

        currentScreen.initGui();
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        drawDefaultBackground();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);

        mc.getTextureManager().bindTexture(tabButtonsTexture);

        for (int i = 0; i < tabs.size(); i++) {
            drawTexturedModalRect(tabButtonLeft, tabButtonTop + 30 * i,
                    (selectedTab == i) ? 0 : 33, i * 28, 33, 28);
        }

        currentScreen.drawScreen(mouseX, mouseY, renderPartialTicks);
    }

    /**
     * Handles mouse input.
     */
    @Override
    public void handleMouseInput() throws IOException {
        currentScreen.handleMouseInput();

        super.handleMouseInput();
    }

    /**
     * Handles keyboard input.
     */
    @Override
    public void handleKeyboardInput() throws IOException {
        currentScreen.handleKeyboardInput();

        super.handleKeyboardInput();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            int relativeMouseX = mouseX - tabButtonLeft;
            int relativeMouseY = mouseY - tabButtonTop;

            for (int i = 0; i < tabs.size(); ++i) {
                if (relativeMouseX >= 0 && relativeMouseX < 29
                        && relativeMouseY > i * 30
                        && relativeMouseY < 30 + i * 30) {
                    selectTab(i);
                }
            }
        }
    }

    protected void selectTab(int tabId) {
        if (tabId < 0 || tabId >= tabs.size()) {
            return;
        }

        selectedTab = tabId;

        onSelectTab(tabId);
    }

    protected abstract void onSelectTab(int tabId);
}