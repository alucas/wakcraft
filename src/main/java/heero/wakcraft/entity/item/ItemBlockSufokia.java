package heero.wakcraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockSufokia extends ItemBlock {

	public ItemBlockSufokia(Block block) {
		super(block);
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		switch(itemstack.getItemDamage()){
		case 2:
			return getUnlocalizedName() + ".Sun";
		case 4:
			return getUnlocalizedName() + ".Wave1";
		case 8:
			return getUnlocalizedName() + ".Wave2";
		default:
			return getUnlocalizedName() + ".Base";
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
