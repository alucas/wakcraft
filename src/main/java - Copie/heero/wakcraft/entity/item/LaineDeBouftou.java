package heero.wakcraft.entity.item;

import heero.wakcraft.reference.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class LaineDeBouftou extends Item {
	public LaineDeBouftou()
	{
		super();
		
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("LaineDeBouftou");
		setTextureName(References.MODID.toLowerCase() + ":lainedebouftou");
	}
}
