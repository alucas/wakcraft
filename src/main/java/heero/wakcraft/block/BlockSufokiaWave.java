package heero.wakcraft.block;

import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.reference.References;

import java.util.List;

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

public class BlockSufokiaWave extends BlockYOrientation {
	protected IIcon blockIconWave1;
	protected IIcon blockIconWave2;
	
	public BlockSufokiaWave() {
		super(Material.sand);
		
		setBlockTextureName(References.MODID.toLowerCase() + ":sufokiaWave1");
		
		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockName("SufokiaWave");
	}
	
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List subItems)
    {
        subItems.add(new ItemStack(item, 1, 0));
        subItems.add(new ItemStack(item, 1, 4));
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister registerer)
    {
        this.blockIconWave1 = registerer.registerIcon(References.MODID.toLowerCase() + ":sufokiaWave1");
        this.blockIconWave2 = registerer.registerIcon(References.MODID.toLowerCase() + ":sufokiaWave2");
    }
    
    /**
     * Gets the block's texture. Args: side, meta
     */
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getTopIcon(int side, int metadata) {
		if ((metadata & 12) == 0) return blockIconWave1; // 0, 1, 2, 3
		else if ((metadata & 12) == 4) return blockIconWave2; // 4, 5, 6, 7

		return blockIconWave1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getSideIcon(int side, int metadata) {
		return getTopIcon(side, metadata);
	}
    
	/**
     * Called when the block is placed in the world.
     */
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemBlock)
    {
		int metadata = itemBlock.getItemDamage();
		
		if ((metadata & 12) == 4) { // 4, 5, 6, 7
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		
		super.onBlockPlacedBy(world, x, y, z, player, itemBlock);
	}
}
