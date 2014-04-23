package heero.wakcraft.item;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemOre extends ItemWithLevel {
	protected String[] names;
	protected int[] levels;

	public ItemOre() {
		super(0);

		setCreativeTab(WakcraftCreativeTabs.tabResource);
		setUnlocalizedName("Ore");
		setTextureName(WakcraftInfo.MODID.toLowerCase() + ":ore");
		setHasSubtypes(true);
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (int i = 0; i < names.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an
	 * ItemStack so different stacks can have different names based on their
	 * damage or NBT.
	 */
	public String getUnlocalizedName(ItemStack itemStack) {
		return getUnlocalizedName()	+ names[itemStack.getItemDamage() % names.length];
	}
}