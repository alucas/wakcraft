package heero.mc.mod.wakcraft.client.gui;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.profession.ProfessionManager;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIProfession extends GuiScreen {
    private static final ResourceLocation professionBackground = new ResourceLocation(
            Reference.MODID.toLowerCase(), "textures/gui/profession.png");

    /**
     * The X size of the window in pixels.
     */
    protected int xSize = 176;
    /**
     * The Y size of the window in pixels.
     */
    protected int ySize = 166;
    /**
     * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiLeft;
    /**
     * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiTop;
    protected EntityPlayer player;
    protected PROFESSION currentProfession;

    public GUIProfession(EntityPlayer player, PROFESSION defaultProfession) {
        super();

        this.player = player;
        this.currentProfession = defaultProfession;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        guiLeft = (width - xSize) / 2;
        guiTop = (height - ySize) / 2;

        buttonList.clear();

        int i = 0;
        for (PROFESSION profession : PROFESSION.craftings) {
            buttonList.add(new ProfessionButton(profession, guiLeft + 15 + (i % 6) * 25, guiTop + 15 + (i / 6) * 20));

            i++;
        }

        i = 0;
        for (PROFESSION profession : PROFESSION.harvestings) {
            buttonList.add(new ProfessionButton(profession, guiLeft + 15 + (i % 6) * 25, guiTop + 65));

            i++;
        }

        super.initGui();
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        mc.getTextureManager().bindTexture(professionBackground);

        // Background
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int xp = ProfessionManager.getXp(player, currentProfession);
        int level = ProfessionManager.getLevelFromXp(xp);
        int xpLevel = ProfessionManager.getXpFromLevel(level);
        int xpNextLevel = ProfessionManager.getXpFromLevel(level + 1);

        // Experience bar
        drawTexturedModalRect(guiLeft, guiTop + 95, 0, ySize + 1, (int) (xSize - (170 * (1 - (xp - xpLevel) / (float) (xpNextLevel - xpLevel)))), 6);

        // Profession levels
        int i = 0;
        for (PROFESSION profession : PROFESSION.craftings) {
            if (profession == PROFESSION.KAMA_MINTER) {
                continue;
            }

            drawCenteredString(fontRendererObj, Integer.toString(ProfessionManager.getLevel(player, profession)), guiLeft + 25 + (i % 6) * 25, guiTop + 25 + (i / 6) * 20, 0xFFFFFF);

            i++;
        }

        i = 0;
        for (PROFESSION profession : PROFESSION.harvestings) {
            drawCenteredString(fontRendererObj, Integer.toString(ProfessionManager.getLevel(player, profession)), guiLeft + 25 + i * 25, guiTop + 75, 0xFFFFFF);

            i++;
        }

        // Titles
        drawString(fontRendererObj, I18n.format("title.crafting", new Object[0]), guiLeft + 5, guiTop + 5, 0xFFFFFF);
        drawString(fontRendererObj, I18n.format("title.harvesting", new Object[0]), guiLeft + 5, guiTop + 55, 0xFFFFFF);
        drawString(fontRendererObj, I18n.format("profession." + currentProfession.toString().toLowerCase(), new Object[0]), guiLeft + 5, guiTop + 85, 0xFFFFFF);

        drawString(fontRendererObj, "Lvl 5, Xp : " + Integer.toString(xp - xpLevel) + "/" + Integer.toString(xpNextLevel - xpLevel), guiLeft + 5, guiTop + 105, 0xFFFFFF);

        super.drawScreen(mouseX, mouseY, renderPartialTicks);
    }

    protected void actionPerformed(GuiButton button) {
        if (button instanceof ProfessionButton) {
            currentProfession = ((ProfessionButton) button).profession;
        }
    }

    private class ProfessionButton extends GuiButton {
        public final PROFESSION profession;

        public ProfessionButton(PROFESSION profession, int x, int y) {
            super(profession.getValue(), x, y, 16, 16, "");

            this.profession = profession;
        }

        /**
         * Draws this button to the screen.
         */
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        }
    }
}