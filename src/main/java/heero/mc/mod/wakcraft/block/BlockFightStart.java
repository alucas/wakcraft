package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.helper.FightHelper;

import java.util.List;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFightStart extends BlockGeneric {

	public BlockFightStart(int team) {
		super(new Material(MapColor.redColor) {
			@Override
			public boolean isOpaque() {
				return false;
			}
		});

		setBlockTextureName("fightStart" + team);
		setBlockName("FightStart" + team);
		setBlockBounds(0, 0, 0, 1, 0.01F, 1);
	}

	/**
	 * Not opaque, render surrounding blocks
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
	 * This block is only here for the display : no collision
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z,
			AxisAlignedBB mask, List list, Entity entity) {
	}

	/**
	 * This block is only here for the display : don't block raytracing
	 */
	@Override
	public boolean canCollideCheck(int metadata, boolean stopOnLiquid) {
		return false;
	}

	/**
	 * Only display the top texture, for player in fight (if the player is from
	 * an other fight, that's not important)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world,
			int x, int y, int z, int side) {
		return side == 1 && FightHelper.isFighter(Minecraft.getMinecraft().thePlayer) && FightHelper.isFighting(Minecraft.getMinecraft().thePlayer);
	}

	@Override
	public boolean isAir(IBlockAccess world, int x, int y, int z) {
		return true;
	}
}
