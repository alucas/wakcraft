package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.Wakcraft;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockInvisibleWall extends BlockGeneric {

	public BlockInvisibleWall() {
		super(new MaterialTransparent(MapColor.airColor) {
			@Override
			public boolean isReplaceable() {
				return false;
			}
		});

		setBlockName("InvisibleWall");
		setBlockTextureName("no_icon");
	}

	/**
	 * Checks to see if its valid to put this block at the specified
	 * coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if (world.isRemote) {
			Wakcraft.proxy.getClientPlayer().addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.canPlaceBlockManualy")));
		}

		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_) {
		return false;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		if (world instanceof World && ((World) world).provider.dimensionId == WConfig.getHavenBagDimensionId()) {
			return 15;
		}

		return super.getLightValue(world, x, y, z);
	}
}
