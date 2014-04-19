package heero.wakcraft.block;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.renderer.RenderBlockOre;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockOre extends Block {
	public static IIcon iconTop, iconBottom;
	protected float[][] colors;
	
	public BlockOre() {
		super(Material.rock);
		
		setCreativeTab(WakcraftCreativeTabs.tabOreBlock);
		
		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":ore");
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
		setBlockName("Ore");
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
     * Called when the block is placed in the world.
     */
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemBlock) {
		int metadata = itemBlock.getItemDamage();
		
		world.setBlockMetadataWithNotify(x, y, z, metadata & 14, 2);
	}
}
