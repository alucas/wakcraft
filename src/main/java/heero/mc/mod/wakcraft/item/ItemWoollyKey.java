package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;

public class ItemWoollyKey extends ItemWithLevel {
	public ItemWoollyKey() {
		super(1);

		setCreativeTab(WakcraftCreativeTabs.tabResource);
		setUnlocalizedName("WoollyKey");
		setTextureName(WInfo.MODID.toLowerCase() + ":woollykey");
	}
}