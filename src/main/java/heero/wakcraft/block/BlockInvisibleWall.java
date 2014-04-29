package heero.wakcraft.block;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockInvisibleWall extends Block {

	public BlockInvisibleWall() {
		super(Material.clay);

		setCreativeTab(WakcraftCreativeTabs.tabBlock);
		setBlockName("InvisibleWall");
		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":invisiblewall");
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
