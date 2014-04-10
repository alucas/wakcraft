package heero.wakcraft.entity.item;

import heero.wakcraft.reference.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CorneDeBouftou extends Item {
	public CorneDeBouftou()
	{
		super();
		
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("CorneDeBouftou");
		setTextureName(References.MODID.toLowerCase() + ":cornedebouftou");
	}
}