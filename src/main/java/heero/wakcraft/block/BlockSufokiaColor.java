package heero.wakcraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.reference.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockSufokiaColor extends Block {
	private IIcon blockIconColor1, blockIconColor2;

	public BlockSufokiaColor() {
		super(Material.sand);

		setBlockTextureName(References.MODID.toLowerCase() + ":sufokiaColor1");
		setBlockName("SufokiaColor");

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister registerer)
    {
        this.blockIconColor1 = registerer.registerIcon(References.MODID.toLowerCase() + ":sufokiaColor1");
        this.blockIconColor2 = registerer.registerIcon(References.MODID.toLowerCase() + ":sufokiaColor2");
    }
    
    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata)
    {
    	if (metadata == 1) {
    		return blockIconColor2;
    	}
    	
        return blockIconColor1;
    }
	
	/**
     * Called when the block is placed in the world.
     */
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemBlock)
    {
		super.onBlockPlacedBy(world, x, y, z, player, itemBlock);
		
		if (Math.random() > 0.5) {
			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
		}
	}

}
