package heero.mc.mod.wakcraft.client.renderer.tileentity;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.block.BlockDragoExpress;
import heero.mc.mod.wakcraft.client.model.ModelDragoExpress;
import heero.mc.mod.wakcraft.tileentity.TileEntityDragoExpress;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class RendererDragoExpress extends TileEntitySpecialRenderer<TileEntityDragoExpress> {
    private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/entity/drago_express.png");
    private ModelDragoExpress model = new ModelDragoExpress();

    @Override
    public void renderTileEntityAt(TileEntityDragoExpress entity, double tileX, double tileY, double tileZ, float partialTicks, int destroyStage) {
        this.bindTexture(texture);

        BlockDragoExpress block = (BlockDragoExpress) entity.getBlockType();
        EnumFacing direction = RotationUtil.getYRotationFromState(block.getStateFromMeta(entity.getBlockMetadata()));

        GlStateManager.pushMatrix();
        GlStateManager.translate(tileX + 0.5, tileY + 2.5, tileZ - 0.5);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(direction == EnumFacing.NORTH ? 0.0F : direction == EnumFacing.EAST ? 90.0F : direction == EnumFacing.SOUTH ? 180.0F : 270.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.enableCull();

        this.model.render(null, 0, 0, 0, 0, 0, 0.0625F);

        GlStateManager.popMatrix();
    }
}
