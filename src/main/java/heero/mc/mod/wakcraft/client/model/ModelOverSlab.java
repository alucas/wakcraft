package heero.mc.mod.wakcraft.client.model;

import com.google.common.base.Function;
import heero.mc.mod.wakcraft.client.model.baked.BakedModelOverSlabWrapper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IModelState;
import net.minecraftforge.client.model.TRSRTransformation;

import javax.vecmath.Vector3f;
import java.util.Collection;

public class ModelOverSlab implements IModel {
    protected final IModel model;

    public ModelOverSlab(final IModel model) {
        this.model = model;
    }

    public Collection<ResourceLocation> getDependencies() {
        return model.getDependencies();
    }

    public Collection<ResourceLocation> getTextures() {
        return model.getTextures();
    }

    public IFlexibleBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new BakedModelOverSlabWrapper(
                model.bake(state, format, bakedTextureGetter),
                model.bake(new TRSRTransformation(new Vector3f(0, -0.25F, 0), null, null, null), format, bakedTextureGetter),
                model.bake(new TRSRTransformation(new Vector3f(0, -0.5F, 0), null, null, null), format, bakedTextureGetter),
                model.bake(new TRSRTransformation(new Vector3f(0, -0.75F, 0), null, null, null), format, bakedTextureGetter),
                format);
    }

    public IModelState getDefaultState() {
        return model.getDefaultState();
    }
}
