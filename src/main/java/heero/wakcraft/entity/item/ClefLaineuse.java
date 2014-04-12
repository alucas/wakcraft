package heero.wakcraft.entity.item;

import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.reference.References;
import net.minecraft.item.Item;

public class ClefLaineuse extends Item {
	public ClefLaineuse() {
		super();

		setCreativeTab(WakcraftCreativeTabs.tabResource);
		setUnlocalizedName("WoollyKey");
		setTextureName(References.MODID.toLowerCase() + ":woollykey");
	}
}