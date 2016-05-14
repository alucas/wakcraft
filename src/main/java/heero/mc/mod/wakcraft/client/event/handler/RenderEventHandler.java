package heero.mc.mod.wakcraft.client.event.handler;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.item.ItemSeed;
import heero.mc.mod.wakcraft.item.ItemWCreatureSeeds;
import net.minecraft.client.Minecraft;
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

        final Minecraft mc = Minecraft.getMinecraft();
        final Entity entity = mc.getRenderViewEntity();
        if (!(entity instanceof EntityPlayer)) {
            return;
        }

        final EntityPlayer player = (EntityPlayer) entity;

        if (mc.playerController.getIsHittingBlock() && mc.playerController.curBlockDamageMP > 0) {
            renderItemInUses(mc, mc.playerController.curBlockDamageMP);
            return;
        }

        if (player.isUsingItem()) {
            final Item itemInUse = player.getItemInUse().getItem();
            if (!(itemInUse instanceof ItemSeed) && !(itemInUse instanceof ItemWCreatureSeeds)) {
                return;
            }

            renderItemInUses(mc, getProgression(player, itemInUse));
            return;
        }
    }

    public float getProgression(final EntityPlayer player, final Item itemInUse) {
        final int usageMaxDuration = itemInUse.getMaxItemUseDuration(player.getItemInUse());
        final int usageDuration = player.getItemInUseDuration();

        return (float) usageDuration / (float) usageMaxDuration;
    }

    public void renderItemInUses(final Minecraft mc, final float progression) {
        mc.mcProfiler.startSection("itemInUse");

        mc.getTextureManager().bindTexture(ITEM_IN_USE_BACKGROUND);

        GlStateManager.enableBlend();

        mc.ingameGUI.drawTexturedModalRect(10, 10, 0, 0, 106, 12);
        mc.ingameGUI.drawTexturedModalRect(13, 13, 0, 12, (int) (progression * 100), 6);

        GlStateManager.disableBlend();

        mc.mcProfiler.endSection();
    }
}
