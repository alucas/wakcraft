package heero.wakcraft.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockSufokiaGround extends ItemBlock {
	public ItemBlockSufokiaGround(Block block) {
		super(block);
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + itemstack.getItemDamage();
	}

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}
}
