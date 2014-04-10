package heero.wakcraft.proxy;

import heero.wakcraft.client.model.ModelBouffette;
import heero.wakcraft.client.model.ModelBoufton;
import heero.wakcraft.client.model.ModelBouftou;
import heero.wakcraft.client.model.ModelBouftouCG;
import heero.wakcraft.entity.monster.Bouffette;
import heero.wakcraft.entity.monster.BouftonBlanc;
import heero.wakcraft.entity.monster.BouftonNoir;
import heero.wakcraft.entity.monster.Bouftou;
import heero.wakcraft.entity.monster.BouftouCG;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class CombinedClientProxy extends CommonProxy{

	@Override
    public void registerRenderers() {
    	RenderingRegistry.registerEntityRenderingHandler(Bouftou.class, new Bouftou.RenderBouftou(new ModelBouftou(), 0.5f));
    	RenderingRegistry.registerEntityRenderingHandler(Bouffette.class, new Bouffette.RenderBouffette(new ModelBouffette(), 0.5f));
    	RenderingRegistry.registerEntityRenderingHandler(BouftonBlanc.class, new BouftonBlanc.RenderBouftonBlanc(new ModelBoufton(), 0.5f));
    	RenderingRegistry.registerEntityRenderingHandler(BouftonNoir.class, new BouftonNoir.RenderBouftonNoir(new ModelBoufton(), 0.5f));
    	RenderingRegistry.registerEntityRenderingHandler(BouftouCG.class, new BouftouCG.RenderBouftouCG(new ModelBouftouCG(), 0.5f));
    }

}
