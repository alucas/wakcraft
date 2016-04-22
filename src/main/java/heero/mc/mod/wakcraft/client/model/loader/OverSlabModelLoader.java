package heero.mc.mod.wakcraft.client.model.loader;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.client.model.ModelOverSlab;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;

import java.io.IOException;

public class OverSlabModelLoader implements ICustomModelLoader {
    protected static final String PREFIX = "models/block/overslab/";

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return Reference.MODID.equals(modelLocation.getResourceDomain())
                && modelLocation.getResourcePath().startsWith(PREFIX);
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws IOException {
        final String vanillaPath = "block/" + modelLocation.getResourcePath().substring(PREFIX.length());
        final ResourceLocation vanillaLocation = new ResourceLocation(modelLocation.getResourceDomain(), vanillaPath);
        final IModel model = ModelLoaderRegistry.getModel(vanillaLocation);

        return new ModelOverSlab(model);
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
    }
}
