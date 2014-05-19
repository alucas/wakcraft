package heero.wakcraft.item;

import heero.wakcraft.block.BlockSlab;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockSlab extends ItemBlock {
	// Opaque version of the slab block
	protected Block blockOpaque;

	public ItemBlockSlab(Block block) {
		super(block);

		if (block instanceof BlockSlab) {
			this.blockOpaque = ((BlockSlab) block).blockOpaque;
		}
	}

	/**
	 * Callback for item usage. If the item does something special on right
	 * clicking, he will have one of those. Return True if something happen and
	 * false if it don't. Args : stack, player, world, x, y, z, side, hitX,
	 * hitY, hitZ
	 */
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		switch (side) {
		case 0: // Bottom
			if (world.getBlock(x, y, z) == field_150939_a) {
				int metadata = world.getBlockMetadata(x, y, z);
				int size = metadata >> 2;
				int pos = metadata & 0b11;

				if (pos == 1 && size == 2) {
					world.setBlock(x, y, z, blockOpaque);
					return true;
				} else if (pos > 0) {
					world.setBlockMetadataWithNotify(x, y, z, ((size + 1) << 2) + pos - 1, 2);
					return true;
				}
			}

			Block block = world.getBlock(x, y - 1, z);
			if (block == field_150939_a) {
				int metadata = world.getBlockMetadata(x, y - 1, z);
				int size = metadata >> 2;
				int pos = metadata & 0b11;

				if (pos == 0 && size == 2) {
					world.setBlock(x, y - 1, z, blockOpaque);
					return true;
				} else if (size + pos == 2) {
					world.setBlockMetadataWithNotify(x, y - 1, z, ((size + 1) << 2) + pos, 2);
					return true;
				}
			} else if (block.isReplaceable(world, x, y - 1, z)) {
				world.setBlock(x, y - 1, z, field_150939_a, 3, 2);
			}

			break;
		case 1: // Top
			if (world.getBlock(x, y, z) == field_150939_a) {
				int metadata = world.getBlockMetadata(x, y, z);
				int size = metadata >> 2;
				int pos = metadata & 0b11;

				if (pos == 0 && size == 2) {
					world.setBlock(x, y, z, blockOpaque);
					return true;
				} else if (size + pos < 3) {
					world.setBlockMetadataWithNotify(x, y, z, ((size + 1) << 2) + pos, 2);
					return true;
				}
			}

			if (world.getBlock(x, y + 1, z) == field_150939_a) {
				int metadata = world.getBlockMetadata(x, y, z);
				int size = metadata >> 2;
				int pos = metadata & 0b11;

				if (size == 2 && pos == 1) {
					world.setBlock(x, y + 1, z, blockOpaque);
					return true;
				} else if (pos == 1) {
					world.setBlockMetadataWithNotify(x, y + 1, z, ((size + 1) << 2) + pos, 2);
					return true;
				}
			}

			break;
		default:
			int posX = x + ((side == 5) ? 1 : (side == 4) ? -1 : 0);
			int posZ = z + ((side == 3) ? 1 : (side == 2) ? -1 : 0);

			if (world.getBlock(posX, y, posZ) == field_150939_a) {
				int metadata = world.getBlockMetadata(posX, y, posZ);
				int size = metadata >> 2;
				int pos = metadata & 0b11;

				int sectionY = (int) (hitY * 4);
				int sum = size + pos;

				if (size == 2 && ((sectionY == 0 && pos == 1) || (sectionY == 3 && pos == 0))) {
					world.setBlock(posX, y, posZ,blockOpaque);
				}

				if ((sectionY == 0 && pos == 1) || (sectionY == 1 && pos == 2) || (sectionY == 2 && pos == 3)) {
					world.setBlockMetadataWithNotify(posX, y, posZ, ((size + 1) << 2) + pos - 1, 2);
					return true;
				}

				if ((sectionY == 3 && sum == 2) || (sectionY == 2 && sum == 1) || (sectionY == 1 && sum == 0)) {
					world.setBlockMetadataWithNotify(posX, y, posZ, ((size + 1) << 2) + pos, 2);
					return true;
				}
			} else if (world.getBlock(posX, y, posZ).isReplaceable(world, posX, y, posZ)) {
				world.setBlock(posX, y, posZ, field_150939_a, (int) (hitY * 4), 2);

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
		int size = metadata >> 2;
		int pos = metadata & 0b11;

		int posX = x + ((side == 5) ? 1 : (side == 4) ? -1 : 0);
		int posZ = z + ((side == 3) ? 1 : (side == 2) ? -1 : 0);

		Block block = world.getBlock(posX, y, posZ);

		int posY = y
				+ ((side == 1 && (size + pos == 3 || block != this.field_150939_a)) ? 1
						: (side == 0 && (pos == 0 || block != this.field_150939_a)) ? -1
								: 0);

		block = world.getBlock(posX, posY, posZ);
		if (block == this.field_150939_a) {
			metadata = world.getBlockMetadata(posX, posY, posZ);
			size = metadata >> 2;
			pos = metadata & 0b11;

			int sum = pos + size;

			if ((side == 0 && pos > 0) || (side == 1 && sum < 3) || side > 1) {
				return true;
			}
		}

		return super.func_150936_a(world, x, y, z, side, player, itemStack);
	}
}
