package heero.wakcraft.entity.item;

import heero.wakcraft.WakcraftBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockGrass extends ItemBlock {
	public ItemBlockGrass(Block block) {
		super(block);
	}

	/**
	 * Callback for item usage. If the item does something special on right
	 * clicking, he will have one of those. Return True if something happen and
	 * false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		if (Block.getIdFromBlock(world.getBlock(x, y, z)) == Block.getIdFromBlock(WakcraftBlocks.grass)) {
			if (world.getBlockMetadata(x, y, z) == 0) {
				world.setBlockMetadataWithNotify(x, y, z, 1, 2);

				return true;
			}
		}

		super.onItemUse(itemStack, entityPlayer, world, x, y, z, side, hitX,
				hitY, hitZ);

		return false;
	}
}
