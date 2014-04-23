package heero.wakcraft.item;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.item.Item;

public class ItemWoollyKey extends ItemWithLevel {
	public ItemWoollyKey() {
		super(1);

		setCreativeTab(WakcraftCreativeTabs.tabResource);
		setUnlocalizedName("WoollyKey");
		setTextureName(WakcraftInfo.MODID.toLowerCase() + ":woollykey");
	}
}