package heero.wakcraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockSufokiaWave extends ItemBlock {

	public ItemBlockSufokiaWave(Block block) {
		super(block);
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		switch(itemstack.getItemDamage()){
		case 4:
			return getUnlocalizedName() + "2";
		default:
			return getUnlocalizedName() + "1";
		}
	}

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}
}
