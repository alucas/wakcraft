package heero.mc.mod.wakcraft.client.renderer.tileentity;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.block.BlockPhoenix;
import heero.mc.mod.wakcraft.client.model.ModelPhoenix;
import heero.mc.mod.wakcraft.tileentity.TileEntityPhoenix;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class RendererPhoenix extends TileEntitySpecialRenderer<TileEntityPhoenix> {
    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/entity/phoenix.png");
    private ModelPhoenix model = new ModelPhoenix();

    @Override
    public void renderTileEntityAt(TileEntityPhoenix entity, double tileX, double tileY, double tileZ, float partialTicks, int destroyStage) {
        this.bindTexture(texture);

        BlockPhoenix block = (BlockPhoenix) entity.getBlockType();
        EnumFacing direction = RotationUtil.getYRotationFromState(block.getStateFromMeta(entity.getBlockMetadata()));

        GlStateManager.pushMatrix();
        GlStateManager.translate(tileX, tileY, tileZ);
        GlStateManager.rotate(180, 1, 0, 0);
        GlStateManager.translate(0.5, -1.5, -0.5);
        GlStateManager.rotate(direction == EnumFacing.NORTH ? 0.0F : direction == EnumFacing.EAST ? 90.0F : direction == EnumFacing.SOUTH ? 180.0F : 270.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.enableCull();

        this.model.render(null, 0, 0, 0, 0, 0, 0.0625F);

        GlStateManager.popMatrix();
    }
}
