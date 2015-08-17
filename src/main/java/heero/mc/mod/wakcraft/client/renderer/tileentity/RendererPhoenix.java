package heero.mc.mod.wakcraft.client.renderer.tileentity;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.block.BlockPhoenix;
import heero.mc.mod.wakcraft.client.model.ModelPhoenix;
import heero.mc.mod.wakcraft.tileentity.TileEntityPhoenix;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RendererPhoenix extends TileEntitySpecialRenderer {
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/entity/phoenix.png");
    private ModelPhoenix model = new ModelPhoenix();
	
    public void renderTileEntityAt(TileEntityPhoenix entity, double tileX, double tileY, double tileZ, float partialTicks, int destroyStage)
    {
    	this.bindTexture(texture);
    	
        GL11.glPushMatrix();
        
        GL11.glTranslatef((float)tileX, (float)tileY, (float)tileZ);
        
        // Flip the entity upside down
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        
        // Fix Tecne model position
        GL11.glTranslatef(0.5F, -1.5F, -0.5F);
        
        // Set orientation
        // TODO
        Block block = entity.getBlockType();
        EnumFacing direction = (block instanceof BlockPhoenix) ? (EnumFacing) (block.getStateFromMeta(entity.getBlockMetadata())).getValue(RotationUtil.PROP_Y_ROTATION) : EnumFacing.SOUTH;

        GL11.glRotatef(direction == EnumFacing.SOUTH ? 0.0F : direction == EnumFacing.WEST ? 90.0F : direction == EnumFacing.NORTH ? 180.0F : 270.0F ,0.0F, 1.0F, 0.0F);
        GL11.glEnable(GL11.GL_CULL_FACE);
        this.model.render((Entity)null, 0, 0, 0, 0, 0, 0.0625F);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity entity, double tileX, double tileY, double tileZ, float partialTicks, int destroyStage)
    {
        this.renderTileEntityAt((TileEntityPhoenix)entity, tileX, tileY, tileZ, partialTicks, destroyStage);
    }
}
