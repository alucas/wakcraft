package heero.wakcraft.entity.item;

import heero.wakcraft.reference.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CuirDeBouftou extends Item {
	public CuirDeBouftou()
	{
		super();
		
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("CuirDeBouftou");
		setTextureName(References.MODID.toLowerCase() + ":cuirdebouftou");
	}
}