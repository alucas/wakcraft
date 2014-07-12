package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockYRotation extends Item implements IBlockProvider {
	protected final Block block;

	protected final ItemBlock itemBlockNorth;
	protected final ItemBlock itemBlockEast;
	protected final ItemBlock itemBlockSouth;
	protected final ItemBlock itemBlockWest;

	public ItemBlockYRotation(Block blockNorth, Block blockEast, Block blockSouth, Block blockWest) {
		super();

		this.block = blockNorth;

		this.itemBlockNorth = (ItemBlock) Item.getItemFromBlock(blockNorth);
		this.itemBlockEast = (ItemBlock) Item.getItemFromBlock(blockEast);
		this.itemBlockSouth = (ItemBlock) Item.getItemFromBlock(blockSouth);
		this.itemBlockWest = (ItemBlock) Item.getItemFromBlock(blockWest);

		if (itemBlockNorth == null || itemBlockEast == null || itemBlockSouth == null || itemBlockWest == null) {
			throw new RuntimeException("Failed to initialize " + this.getClass().getCanonicalName());
		}
	}

	/**
	 * Returns 0 for /terrain.png, 1 for /gui/items.png
	 */
	@SideOnly(Side.CLIENT)
	public int getSpriteNumber() {
		return 0;
	}

	/**
	 * Callback for item usage. If the item does something special on right
	 * clicking, he will have one of those. Return True if something happen and
	 * false if it don't. Args : stack, player, world, x, y, z, side, hitX,
	 * hitY, hitZ
	 */
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		ForgeDirection direction = RotationUtil.getYRotationFromYaw(player.rotationYaw);

		switch (direction) {
		case NORTH:
			return itemBlockNorth.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
		case EAST:
			return itemBlockEast.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
		case SOUTH:
			return itemBlockSouth.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
		case WEST:
			return itemBlockWest.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
		default:
			break;
		}

		return false;
	}

	@Override
	public Block getBlock() {
		return block;
	}
}
