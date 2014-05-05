package heero.wakcraft.block;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.havenbag.HavenBagManager;
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
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import cpw.mods.fml.common.FMLLog;
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
		this.setBlockName("HavenBagChest");
		this.setCreativeTab(WakcraftCreativeTabs.tabSpecialBlock);
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
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.canPlaceBlockManualy")));
		}

		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity != null && tileEntity instanceof TileEntityHavenBagChest) {
			TileEntityHavenBagChest tileEntityChest = (TileEntityHavenBagChest) tileEntity;
			for (int i1 = 0; i1 < tileEntityChest.getSizeInventory(); ++i1) {
				ItemStack itemstack = tileEntityChest.getStackInSlot(i1);

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

		IExtendedEntityProperties properties = player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
		if (properties == null || !(properties instanceof HavenBagProperty)) {
			FMLLog.warning("Error while loading the extended properties of %s", player.getDisplayName());

			return true;
		}

		int havenBagUID = HavenBagManager.getUIDFromCoord(x, y, z);
		if (((HavenBagProperty)properties).uid != havenBagUID) {
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.notYourBag")));
			return true;
		}

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null && !(tileEntity instanceof TileEntityHavenBagChest)) {
			FMLLog.warning("Error while loading the haven bag chest tile entity (%d, %d, %d)", x, y, z);
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