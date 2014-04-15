package heero.wakcraft.proxy;

import heero.wakcraft.client.model.ModelGobball;
import heero.wakcraft.client.model.ModelGobballWC;
import heero.wakcraft.client.model.ModelGobbette;
import heero.wakcraft.client.model.ModelGobbly;
import heero.wakcraft.entity.monster.BlackGobbly;
import heero.wakcraft.entity.monster.Gobball;
import heero.wakcraft.entity.monster.GobballWC;
import heero.wakcraft.entity.monster.Gobbette;
import heero.wakcraft.entity.monster.WhiteGobbly;
import heero.wakcraft.renderer.RenderBlockPalisade;
import heero.wakcraft.renderer.RenderDragoexpress;
import heero.wakcraft.renderer.RenderBlockOre1;
import heero.wakcraft.renderer.RenderPhoenix;
import heero.wakcraft.renderer.RenderBlockYRotation;
import heero.wakcraft.tileentity.TileEntityDragoexpress;
import heero.wakcraft.tileentity.TileEntityPhoenix;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class CombinedClientProxy extends CommonProxy{

	@Override
    public void registerRenderers() {
    	RenderingRegistry.registerEntityRenderingHandler(Gobball.class, new Gobball.RenderBouftou(new ModelGobball(), 0.5f));
    	RenderingRegistry.registerEntityRenderingHandler(Gobbette.class, new Gobbette.RenderBouffette(new ModelGobbette(), 0.5f));
    	RenderingRegistry.registerEntityRenderingHandler(WhiteGobbly.class, new WhiteGobbly.RenderBouftonBlanc(new ModelGobbly(), 0.5f));
    	RenderingRegistry.registerEntityRenderingHandler(BlackGobbly.class, new BlackGobbly.RenderBouftonNoir(new ModelGobbly(), 0.5f));
    	RenderingRegistry.registerEntityRenderingHandler(GobballWC.class, new GobballWC.RenderBouftouCG(new ModelGobballWC(), 0.5f));
    	
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDragoexpress.class, new RenderDragoexpress());
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPhoenix.class, new RenderPhoenix());
    	
		RenderingRegistry.registerBlockHandler(new RenderBlockYRotation(RenderingRegistry.getNextAvailableRenderId()));
		RenderingRegistry.registerBlockHandler(new RenderBlockOre1(RenderingRegistry.getNextAvailableRenderId()));
		RenderingRegistry.registerBlockHandler(new RenderBlockPalisade(RenderingRegistry.getNextAvailableRenderId()));
    }
}
