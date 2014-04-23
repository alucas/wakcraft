package heero.wakcraft.item;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.item.Item;

public class ClefLaineuse extends ItemWithLevel {
	public ClefLaineuse() {
		super(10);

		setCreativeTab(WakcraftCreativeTabs.tabResource);
		setUnlocalizedName("WoollyKey");
		setTextureName(WakcraftInfo.MODID.toLowerCase() + ":woollykey");
	}
}