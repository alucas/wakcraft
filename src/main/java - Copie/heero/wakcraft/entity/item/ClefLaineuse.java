package heero.wakcraft.entity.item;

import heero.wakcraft.reference.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ClefLaineuse extends Item {
	public ClefLaineuse()
	{
		super();
		
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("ClefLaineuse");
		setTextureName(References.MODID.toLowerCase() + ":cleflaineuse");
	}
}