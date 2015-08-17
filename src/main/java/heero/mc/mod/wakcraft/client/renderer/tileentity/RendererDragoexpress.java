package heero.mc.mod.wakcraft.client.renderer.tileentity;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.client.model.ModelDragoexpress;
import heero.mc.mod.wakcraft.tileentity.TileEntityDragoexpress;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RendererDragoexpress extends TileEntitySpecialRenderer {
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/entity/dragoexpress.png");
    private ModelDragoexpress model = new ModelDragoexpress();
	
    public void renderTileEntityAt(TileEntityDragoexpress entity, double tileX, double tileY, double tileZ, float partialTicks, int destroyStage)
    {
    	this.bindTexture(texture);
    	
        GL11.glPushMatrix();
        
        GL11.glTranslatef((float)tileX, (float)tileY, (float)tileZ);
        
        // Flip the entity upside down
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        
        // Fix position
        GL11.glTranslatef(0.5F, -2.5F, -0.5F);
        
        // Set orientation
        int meta = entity.getBlockMetadata();
        GL11.glRotatef(meta == 2 ? 180.0F : meta == 4 ? 90.0F : meta == 5 ? 270.0F : 0.0F ,0.0F, 1.0F, 0.0F);
        
        GL11.glEnable(GL11.GL_CULL_FACE);
        this.model.render((Entity)null, 0, 0, 0, 0, 0, 0.0625F);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity entity, double tileX, double tileY, double tileZ, float partialTicks, int destroyStage) {
        this.renderTileEntityAt((TileEntityDragoexpress)entity, tileX, tileY, tileZ, partialTicks, destroyStage);
    }
}
