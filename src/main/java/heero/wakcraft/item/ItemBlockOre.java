package heero.wakcraft.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockOre extends ItemBlock {
	protected String[] names;
	
	public ItemBlockOre(Block block) {
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
