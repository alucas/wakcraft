package heero.wakcraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockOre1 extends ItemBlock {
	protected String[] names = new String[] {"PrimitiveIron", "FlatTin", "FinestSeaSalt", "ClassicCoal", "BrightCopper", "ShadowyCobalt", "BronzeNugget", "ShardOfFlint"};
	
	public ItemBlockOre1(Block block) {
		super(block);
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + names[(itemstack.getItemDamage() / 2) % names.length];
	}

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
	@Override
	public int getMetadata(int damageValue) {
		return damageValue & 14;
	}
}
