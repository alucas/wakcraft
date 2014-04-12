package heero.wakcraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockOreLvl1 extends ItemBlock {
	private static final String[] names = new String[] {"PrimitiveIron", "FlatTin", "FinestSeaSalt", "ClassicCoal", "BrightCopper", "ShadowyCobalt", "BronzeNugget", "ShardOfFlint"};
	
	public ItemBlockOreLvl1(Block block) {
		super(block);
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + names[itemstack.getItemDamage() / 2];
	}

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
	@Override
	public int getMetadata(int damageValue) {
		return damageValue & 14;
	}
}
