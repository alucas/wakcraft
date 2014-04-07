package heero.wakcraft;

import heero.wakcraft.entity.item.ClefLaineuse;
import heero.wakcraft.entity.item.CorneDeBouftou;
import heero.wakcraft.entity.item.CuirDeBouftou;
import heero.wakcraft.entity.item.LaineDeBouftou;
import heero.wakcraft.reference.References;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class WakcraftItems extends Items {
	public final static Item LaineDeBouftou = (Item) Item.itemRegistry.getObject(References.MODID.toLowerCase() + ":LaineDebouftou");
	public final static Item CuirDeBouftou = (Item) Item.itemRegistry.getObject(References.MODID.toLowerCase() + ":CuirDeBouftou");
	public final static Item CorneDeBouftou = (Item) Item.itemRegistry.getObject(References.MODID.toLowerCase() + ":CorneDeBouftou");
	public final static Item ClefLaineuse = (Item) Item.itemRegistry.getObject(References.MODID.toLowerCase() + ":ClefLaineuse");
}
