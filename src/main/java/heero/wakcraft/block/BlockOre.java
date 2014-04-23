package heero.wakcraft.block;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.block.material.AventureMaterial;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.entity.misc.EntityTextPopup;
import heero.wakcraft.network.packet.ProfessionPacket;
import heero.wakcraft.profession.ProfessionManager;
import heero.wakcraft.profession.ProfessionManager.PROFESSION;
import heero.wakcraft.renderer.block.RenderBlockOre;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockOre extends Block implements ILevelBlock {
	public static IIcon iconTop, iconBottom;
	protected float[][] colors;
	protected int[]levels;
	
	/**
	 * Wakfu Ore block.
	 * Metadata :
	 * - Bit 1 : isExtractable
	 * - Bit 2, 3, 4 : Mineral type
	 */
	public BlockOre() {
		super(new AventureMaterial(MapColor.brownColor));
		
		setCreativeTab(WakcraftCreativeTabs.tabOreBlock);
		
		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":ore");
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
		setBlockName("Ore");
		setHardness(6.66f); // 10s
	}
	
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		for (int i = 0; i < colors.length; i++) {
			subItems.add(new ItemStack(item, 1, i * 2));
		}
	}
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister registerer) {
    	iconBottom = registerer.registerIcon(WakcraftInfo.MODID.toLowerCase() + ":oreBottom");
        iconTop = registerer.registerIcon(WakcraftInfo.MODID.toLowerCase() + ":oreTop");
    }
    
    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return iconBottom;
    }
    
	@SideOnly(Side.CLIENT)
	public float[] getColor(int metadata) {
		return colors[(metadata / 2) % colors.length];
	}

	@Override
	public int getLevel(int metadata) {
		return levels[(metadata / 2) % levels.length];
	}

	/**
	 * Gathers how much experience this block drops when broken.
	 * 
	 * @param metadata
	 * @return Amount of XP from breaking this block.
	 */
	@Override
	public int getProfessionExp(int metadata) {
		return 100;
	}

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int metadata) {
        return (metadata % (colors.length * 2)) & 14;
    }
    
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
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
        return RenderBlockOre.renderId;
    }
    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
	@Override
    public boolean isOpaqueCube() {
        return false;
	}

	/**
	 * Gets the hardness of block at the given coordinates in the given world,
	 * relative to the ability of the given EntityPlayer.
	 */
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		if ((metadata & 1) == 1) {
			return 0;
		} else if (ProfessionManager.getLevel(player, PROFESSION.MINER) < getLevel(metadata)) {
			return 0;
		}

		return super.getPlayerRelativeBlockHardness(player, world, x, y, z);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random random) {
		return 3;
	}
    
	/**
     * Called when the block is placed in the world.
     */
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemBlock) {
		int metadata = itemBlock.getItemDamage();
		
		world.setBlockMetadataWithNotify(x, y, z, metadata & 14, 2);
	}

	/**
	 * Called when a player removes a block. This is responsible for actually
	 * destroying the block, and the block is intact at time of call. This is
	 * called regardless of whether the player can harvest the block or not.
	 * 
	 * Return true if the block is actually destroyed.
	 * 
	 * Note: When used in multiplayer, this is called on both client and server
	 * sides!
	 * 
	 * @param world		The current world
	 * @param player	The player damaging the block, may be null
	 * @param x			X Position
	 * @param y			Y position
	 * @param z			Z position
	 * @return True if the block is actually destroyed.
	 */
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x,
			int y, int z) {
		if (player.capabilities.isCreativeMode) {
			return world.setBlockToAir(x, y, z);
		}

		int metadata = world.getBlockMetadata(x, y, z);
		if ((metadata & 1) == 1) {
			return false;
		}

		world.setBlockMetadataWithNotify(x, y, z, (metadata & 14) + 1, 2);
		world.scheduleBlockUpdate(x, y, z, this, 1200); // 1 min

		dropBlockAsItemWithChance(world, x, y, z, metadata, 0.5f, 0);

		int currentLevel = ProfessionManager.getLevel(player, PROFESSION.MINER);
		int xp = ProfessionManager.addXpFromBlock(player, world, x, y, z, PROFESSION.MINER);
		if (xp > 0) {
			if (world.isRemote) {
				int levelDiff = ProfessionManager.getLevel(player, PROFESSION.MINER) - currentLevel;
				if (levelDiff > 0) {
					world.spawnEntityInWorld(new EntityTextPopup(world, "+" + levelDiff + " niveau", x, y + 1, z, 1.0F, 0.21F, 0.21F));
					world.playRecord("random.levelup", x, y, z);
				} else {
					world.spawnEntityInWorld(new EntityTextPopup(world, "+" + xp + "xp", x, y + 1, z, 0.21F, 0.21F, 1.0F));
					world.playRecord("random.orb", x, y, z);
				}
			} else if (player instanceof EntityPlayerMP) {
				Wakcraft.packetPipeline.sendTo(new ProfessionPacket(player, PROFESSION.MINER), (EntityPlayerMP) player);
			}
		}

		return false;
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		int metadata = world.getBlockMetadata(x, y, z);
		if ((metadata & 1) == 0) {
			return;
		}

		world.setBlockMetadataWithNotify(x, y, z, (metadata & 14) + 0, 2);
	}

	/**
	 * Called when a player hits the block. Args: world, x, y, z, player
	 */
	public void onBlockClicked(World world, int x,
			int y, int z, EntityPlayer player) {
		if (world.isRemote) {
			int blockLevel = getLevel(world.getBlockMetadata(x, y, z));
			if (ProfessionManager.getLevel(player, PROFESSION.MINER) < blockLevel) {
				int metadata = world.getBlockMetadata(x, y, z);
				Item item = getItemDropped(metadata, world.rand, 0);
				ItemStack itemStack = new ItemStack(item, 1, damageDropped(metadata));
				String itemName = item.getItemStackDisplayName(itemStack);
				player.addChatMessage(new ChatComponentText(I18n.format("message.blockOre.insufficientLevel", itemName, blockLevel)));
			}
		}
	}
}
