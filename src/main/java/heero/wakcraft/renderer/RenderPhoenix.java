package heero.wakcraft.renderer;

import heero.wakcraft.block.BlockPhoenix;
import heero.wakcraft.client.model.ModelPhoenix;
import heero.wakcraft.reference.References;
import heero.wakcraft.tileentity.TileEntityPhoenix;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class RenderPhoenix extends TileEntitySpecialRenderer {
	private static final ResourceLocation texture = new ResourceLocation(References.MODID.toLowerCase(), "textures/entity/phoenix.png");
    private ModelPhoenix model = new ModelPhoenix();
	
    public void renderTileEntityAt(TileEntityPhoenix entity, double tileX, double tileY, double tileZ, float p_147539_8_)
    {
    	this.bindTexture(texture);
    	
        GL11.glPushMatrix();
        
        GL11.glTranslatef((float)tileX, (float)tileY, (float)tileZ);
        
        // Flip the entity upside down
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        
        // Fix Tecne model position
        GL11.glTranslatef(0.5F, -1.5F, -0.5F);
        
        // Set orientation
        ForgeDirection direction = BlockPhoenix.getOrientationFromMetadata(entity.getBlockMetadata());
//        System.out.println(direction);
        GL11.glRotatef(direction == ForgeDirection.SOUTH ? 0.0F : direction == ForgeDirection.WEST ? 90.0F : direction == ForgeDirection.NORTH ? 180.0F : 270.0F ,0.0F, 1.0F, 0.0F);
        
        GL11.glEnable(GL11.GL_CULL_FACE);
        this.model.render((Entity)null, 0, 0, 0, 0, 0, 0.0625F);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity entity, double tileX, double tileY, double tileZ, float p_147500_8_)
    {

        this.renderTileEntityAt((TileEntityPhoenix)entity, tileX, tileY, tileZ, p_147500_8_);
    }
}
