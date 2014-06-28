package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSufokiaColor extends BlockGeneric {
	private IIcon blockIconColor1, blockIconColor2;

	public BlockSufokiaColor() {
		super(Material.sand);

		setBlockTextureName("sufokiaColor1");
		setBlockName("SufokiaColor");

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister registerer)
    {
        this.blockIconColor1 = registerer.registerIcon(WInfo.MODID.toLowerCase() + ":sufokiaColor1");
        this.blockIconColor2 = registerer.registerIcon(WInfo.MODID.toLowerCase() + ":sufokiaColor2");
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
