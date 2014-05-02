package heero.wakcraft.block;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.network.GuiHandler;
import heero.wakcraft.tileentity.TileEntityHavenBagChest;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHavenBagChest extends BlockContainer {
	private final Random rand = new Random();
	public final int chestType;

	public BlockHavenBagChest() {
		this(0);
	}

	public BlockHavenBagChest(int chestType) {
		super(Material.wood);
		this.chestType = chestType;
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
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

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return 22;
	}

	/**
	 * Checks to see if its valid to put this block at the specified
	 * coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if (world.isRemote) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("This block can't be placed manualy"));
		}

		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
		TileEntityHavenBagChest tileentitychest = (TileEntityHavenBagChest) world.getTileEntity(x, y, z);

		if (tileentitychest != null) {
			for (int i1 = 0; i1 < tileentitychest.getSizeInventory(); ++i1) {
				ItemStack itemstack = tileentitychest.getStackInSlot(i1);

				if (itemstack != null) {
					float f = this.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;

					for (float f2 = this.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem)) {
						int j1 = this.rand.nextInt(21) + 10;

						if (j1 > itemstack.stackSize) {
							j1 = itemstack.stackSize;
						}

						itemstack.stackSize -= j1;
						entityitem = new EntityItem(world,
								(double) ((float) x + f),
								(double) ((float) y + f1),
								(double) ((float) z + f2),
								new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

						float f3 = 0.05F;
						entityitem.motionX = (double) ((float) this.rand.nextGaussian() * f3);
						entityitem.motionY = (double) ((float) this.rand.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = (double) ((float) this.rand.nextGaussian() * f3);

						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
					}
				}
			}

			world.func_147453_f(x, y, z, block);
		}

		super.breakBlock(world, x, y, z, block, metadata);
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int hitX, float hitY, float hitZ, float partialTick) {
		if (world.isRemote) {
			return true;
		}

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null && !(tileEntity instanceof TileEntityHavenBagChest)) {
			return true;
		}

		player.openGui(Wakcraft.instance, GuiHandler.GUI_HAVEN_BAG_CHEST, world, x, y, z);

		return true;
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		return new TileEntityHavenBagChest();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon("planks_oak");
	}
}