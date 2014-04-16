package heero.wakcraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockSlab extends ItemBlock {
	protected String[] names;

	public ItemBlockSlab(Block block) {
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
		int itemType = itemStack.getItemDamage();

		switch (side) {
		case 0:
			if (world.getBlock(x, y, z) == field_150939_a) {
				int metadata = world.getBlockMetadata(x, y, z);
				int state = (metadata >> 2) % 3;
				int type = metadata % names.length;

				if (state == 1 && type == itemType) {
					world.setBlockMetadataWithNotify(x, y, z, itemType + 8, 2);

					return true;
				}
			}

			Block block = world.getBlock(x, y - 1, z);
			if (block == field_150939_a) {
				int metadata = world.getBlockMetadata(x, y - 1, z);
				int state = (metadata >> 2) % 3;
				int type = metadata % names.length;

				if (state == 0 && type == itemType) {
					world.setBlockMetadataWithNotify(x, y - 1, z, itemType + 8, 2);

					return true;
				}
			} else if (block.isReplaceable(world, x, y - 1, z)) {
				world.setBlock(x, y - 1, z, field_150939_a, itemType + 4, 2);
			}

			break;
		case 1:
			if (world.getBlock(x, y, z) == field_150939_a) {
				int metadata = world.getBlockMetadata(x, y, z);
				int state = (metadata >> 2) % 3;
				int type = metadata % names.length;

				if (state == 0 && type == itemType) {
					world.setBlockMetadataWithNotify(x, y, z, itemType + 8, 2);

					return true;
				}
			}

			if (world.getBlock(x, y + 1, z) == field_150939_a) {
				int metadata = world.getBlockMetadata(x, y + 1, z);
				int state = (metadata >> 2) % 3;
				int type = metadata % names.length;

				if (state == 1 && type == itemType) {
					world.setBlockMetadataWithNotify(x, y + 1, z, itemType + 8, 2);

					return true;
				}
			}

			break;
		default:
			int posX = x + ((side == 5) ? 1 : (side == 4) ? -1 : 0);
			int posZ = z + ((side == 3) ? 1 : (side == 2) ? -1 : 0);

			if (world.getBlock(posX, y, posZ) == field_150939_a) {
				int metadata = world.getBlockMetadata(posX, y, posZ);
				int state = (metadata >> 2) % 3;
				int type = metadata % names.length;

				if (type == itemType && (state == 1 && hitY < 0.5 || state == 0 && hitY > 0.5)) {
					world.setBlockMetadataWithNotify(posX, y, posZ, itemType + 8, 2);
				}
			} else if (hitY > 0.5 && world.getBlock(posX, y, posZ).isReplaceable(world, posX, y, posZ)) {
				world.setBlock(posX, y, posZ, field_150939_a, itemType + 4, 2);

				return true;
			}
		}

		super.onItemUse(itemStack, entityPlayer, world, x, y, z, side, hitX,
				hitY, hitZ);

		return false;
	}

	// canPlaceEntityOnSide ?
	@SideOnly(Side.CLIENT)
	@Override
	public boolean func_150936_a(World world, int x, int y, int z, int side,
			EntityPlayer player, ItemStack itemStack) {
		int metadata = world.getBlockMetadata(x, y, z);
		int state = (metadata >> 2) % 3;

		int posX = x + ((side == 5) ? 1 : (side == 4) ? -1 : 0);
		int posZ = z + ((side == 3) ? 1 : (side == 2) ? -1 : 0);

		Block block = world.getBlock(posX, y, posZ);

		int posY = y
				+ ((side == 1 && (state != 0 || block != this.field_150939_a)) ? 1
						: (side == 0 && (state != 1 || block != this.field_150939_a)) ? -1
								: 0);

		block = world.getBlock(posX, posY, posZ);
		if (block == this.field_150939_a) {
			metadata = world.getBlockMetadata(posX, posY, posZ);
			state = (metadata >> 2) % 3;
			int type = metadata % names.length;

			if (state != 2 && type == itemStack.getItemDamage()) {
				return true;
			}
		}

		return super.func_150936_a(world, x, y, z, side, player, itemStack);
	}
}
