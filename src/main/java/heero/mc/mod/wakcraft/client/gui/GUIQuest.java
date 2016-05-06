package heero.mc.mod.wakcraft.client.gui;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.profession.ProfessionManager.PROFESSION;
import heero.mc.mod.wakcraft.quest.Quest;
import heero.mc.mod.wakcraft.util.QuestUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class GUIQuest extends GuiScreen {
    private static final ResourceLocation professionBackground = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/gui/background.png");

    protected static final int BUTTON_LEFT = 0;
    protected static final int BUTTON_RIGHT = 1;

    protected final int guiWidth = 176;
    protected final int guiHeight = 166;
    protected int guiLeft;
    protected int guiTop;
    protected final EntityPlayer player;
    protected final List<Quest> quests;
    protected int questIndex;

    public GUIQuest(EntityPlayer player) {
        super();

        this.player = player;
        this.quests = QuestUtil.getQuests(this.player);
        this.questIndex = 0;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        guiLeft = (width - guiWidth) / 2;
        guiTop = (height - guiHeight) / 2;

        buttonList.clear();
        buttonList.add(new GuiButton(BUTTON_LEFT, guiLeft + 5, guiTop + 5, 20, 20, "<"));
        buttonList.add(new GuiButton(BUTTON_RIGHT, guiLeft + guiWidth - 25, guiTop + 5, 20, 20, ">"));

        super.initGui();
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
        mc.getTextureManager().bindTexture(professionBackground);

        // Background
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, guiWidth, guiHeight);

        if (quests == null || quests.isEmpty()) {
            drawCenteredString(fontRendererObj, I18n.format("Empty"), guiLeft + (guiWidth / 2), guiTop + 10, 0xFFFFFF);
        } else {
            drawCenteredString(fontRendererObj, I18n.format(quests.get(0).name), guiLeft + (guiWidth / 2), guiTop + 10, 0xFFFFFF);
        }

        super.drawScreen(mouseX, mouseY, renderPartialTicks);
    }

    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case BUTTON_LEFT:
                questIndex = (questIndex <= 0) ? 0 : questIndex - 1;
                break;
            case BUTTON_RIGHT:
                questIndex = (questIndex >= quests.size() - 1) ? quests.size() - 1 : questIndex + 1;
                break;
            default:
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