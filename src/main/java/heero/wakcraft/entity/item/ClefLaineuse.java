package heero.wakcraft.entity.item;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.item.Item;

public class ClefLaineuse extends Item {
	public ClefLaineuse() {
		super();

		setCreativeTab(WakcraftCreativeTabs.tabResource);
		setUnlocalizedName("WoollyKey");
		setTextureName(WakcraftInfo.MODID.toLowerCase() + ":woollykey");
	}
}