package heero.wakcraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockGrass extends ItemBlockSlab {
	public ItemBlockGrass(Block block) {
		super(block);

		names = new String[] { "Grass", "Dirt" };

		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName()	+ names[itemstack.getItemDamage() % names.length];
	}

	/**
	 * Returns the metadata of the block which this Item (ItemBlock) can place
	 */
	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}
}
