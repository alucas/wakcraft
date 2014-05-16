package heero.wakcraft.item;

import heero.wakcraft.WInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;

public class ItemWoollyKey extends ItemWithLevel {
	public ItemWoollyKey() {
		super(1);

		setCreativeTab(WakcraftCreativeTabs.tabResource);
		setUnlocalizedName("WoollyKey");
		setTextureName(WInfo.MODID.toLowerCase() + ":woollykey");
	}
}