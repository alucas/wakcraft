package heero.mc.mod.wakcraft.client.renderer.factory;

import heero.mc.mod.wakcraft.client.renderer.entity.RendererTextPopup;
import heero.mc.mod.wakcraft.entity.misc.EntityTextPopup;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactoryTextPopup implements IRenderFactory<EntityTextPopup> {
    @Override
    public Render<? super EntityTextPopup> createRenderFor(RenderManager manager) {
        return new RendererTextPopup(manager);
    }
}
