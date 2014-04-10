package heero.wakcraft.block;

import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.reference.References;
import heero.wakcraft.renderer.RenderPillarBlock;
import heero.wakcraft.tileentity.TileEntityPhoenix;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSufokia extends BlockPillar {
	protected IIcon blockIcon1;
	protected IIcon blockIcon2;
	protected IIcon blockIconSun;
	protected IIcon blockIconWave1;
	protected IIcon blockIconWave2;
	
	public BlockSufokia() {
		super(Material.rock);
		
		setBlockTextureName(References.MODID.toLowerCase() + ":sufokia2");
		
		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockName("sufokia");
	}
	
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List subItems)
    {
        subItems.add(new ItemStack(item, 1, 0));
        subItems.add(new ItemStack(item, 1, 2));
        subItems.add(new ItemStack(item, 1, 4));
        subItems.add(new ItemStack(item, 1, 8));
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister registerer)
    {
        this.blockIcon1 = registerer.registerIcon(References.MODID.toLowerCase() + ":sufokia1");
        this.blockIcon2 = registerer.registerIcon(References.MODID.toLowerCase() + ":sufokia2");
        this.blockIconWave1 = registerer.registerIcon(References.MODID.toLowerCase() + ":sufokiaWave1");
        this.blockIconWave2 = registerer.registerIcon(References.MODID.toLowerCase() + ":sufokiaWave2");
        this.blockIconSun = registerer.registerIcon(References.MODID.toLowerCase() + ":sufokiaSun");
    }
    
    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
    	if (meta == 0) return blockIcon1;
    	else if (meta == 1) return blockIcon2;
    	else if (meta == 2) return blockIconSun;
    	else if ((meta & 12) == 4) return blockIconWave1; // 4, 5, 6, 7
    	else if ((meta & 12) == 8) return blockIconWave2; // 8, 9, 10, 11
    	
        return blockIcon1;
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
    }
    
	/**
     * Called when the block is placed in the world.
     */
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemBlock)
    {
		int metadata = itemBlock.getItemDamage();
		
		if (metadata == 0) {
			world.setBlockMetadataWithNotify(x, y, z, (int) (Math.random() * 2), 2);
			return;
		} else if (metadata == 2) {
			world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
			return;
		} else if ((metadata & 12) == 4) { // 4, 5, 6, 7
			world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
		} else if ((metadata & 12) == 8) { // 8, 9, 10, 11
			world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
		}
		
		setOrientationFromYaw(world, x, y, z, player.rotationYaw);
	}
}
