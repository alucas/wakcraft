package heero.wakcraft.block;

import heero.wakcraft.creativetab.WakcraftCreativeTabs;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWakfu extends BlockGeneric {
	public BlockWakfu() {
		super(Material.wood);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);

		setBlockTextureName("wakfuGreen");
		setBlockTextureName(ForgeDirection.UP, 0, "wakfuRed");
		setBlockTextureName(ForgeDirection.UP, 1, "wakfuBlue");
		setBlockTextureName(ForgeDirection.UP, 2, "wakfuGreen");
		setBlockTextureName(ForgeDirection.UP, 3, "wakfuOrange");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 4; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase player, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, player, stack);

		world.setBlockMetadataWithNotify(x, y, z, stack.getItemDamage(), 2);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y,
			int z) {
		int state = world.getBlockMetadata(x, y, z) % 4 + 1;
		setBlockBounds(0, 0, 0, 1, 0.25F * state, 1); 
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add
	 * boxes to the list if they intersect the mask.) Parameters: World, X, Y,
	 * Z, mask, list, colliding entity
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z,
			AxisAlignedBB mask, List list, Entity entity) {
		setBlockBoundsBasedOnState(world, x, y, z);

		super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
