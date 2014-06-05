package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;

public class ItemSeedGobball extends ItemWithLevel {
	protected String[] names;
	protected int[] levels;

	public ItemSeedGobball() {
		super(0);

		setCreativeTab(WakcraftCreativeTabs.tabResource);
		setUnlocalizedName("GobballSeed");
		setTextureName(WInfo.MODID.toLowerCase() + ":gobballseed");
	}
}