package heero.mc.mod.wakcraft.client.event.handler;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.item.ItemSeed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderEventHandler {
    public static final ResourceLocation ITEM_IN_USE_BACKGROUND = new ResourceLocation(Reference.MODID, "textures/gui/item_in_use.png");

    @SubscribeEvent
    public void onRenderGameOverlayEventPre(RenderGameOverlayEvent.Pre event) {
//        switch (event.type) {
//            case AIR:
//                event.setCanceled(true);
//        }
    }

    @SubscribeEvent
    public void onRenderGameOverlayEventPost(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }

        final Minecraft minecraft = Minecraft.getMinecraft();
        final Entity entity = minecraft.getRenderViewEntity();
        if (!(entity instanceof EntityPlayer)) {
            return;
        }

        final EntityPlayer player = (EntityPlayer) entity;
        if (!player.isUsingItem()) {
            return;
        }

        final Item itemInUse = player.getItemInUse().getItem();
        if (!(itemInUse instanceof ItemSeed)) {
            return;
        }

        renderItemInUses(minecraft, player, itemInUse, event.resolution);
    }

    public void renderItemInUses(final Minecraft mc, final EntityPlayer player, final Item itemInUse, final ScaledResolution resolution) {
        mc.mcProfiler.startSection("itemInUse");

        mc.getTextureManager().bindTexture(ITEM_IN_USE_BACKGROUND);

        GlStateManager.enableBlend();

        int usageMaxDuration = itemInUse.getMaxItemUseDuration(player.getItemInUse());
        int usageDuration = player.getItemInUseDuration();

        mc.ingameGUI.drawTexturedModalRect(10, 10, 0, 0, 106, 12);
        mc.ingameGUI.drawTexturedModalRect(13, 13, 0, 12, (int) (((float) usageDuration / (float) usageMaxDuration) * 100), 6);

        GlStateManager.disableBlend();

        mc.mcProfiler.endSection();
    }
}
