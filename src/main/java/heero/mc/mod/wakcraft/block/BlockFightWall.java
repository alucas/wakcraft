package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.helper.FightHelper;

import java.util.List;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFightWall extends BlockGeneric {

	public BlockFightWall() {
		super(new Material(MapColor.emeraldColor) {
			@Override
			public boolean isOpaque() {
				return false;
			}
		});

		setBlockTextureName("fightWall");
		setBlockName("FightWall");
	}

	/**
	 * Returns true if the given side of this block type should be rendered, if
	 * the adjacent block is at the given coordinates. Args: blockAccess, x, y,
	 * z, side
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world,
			int x, int y, int z, int side) {
		return FightHelper.isFighter(Minecraft.getMinecraft().thePlayer) && FightHelper.isFighting(Minecraft.getMinecraft().thePlayer);
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

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int posX, int posY, int posZ, Vec3 vec1, Vec3 vec2) {
		if (world.isRemote) {
			if (!FightHelper.isFighter(Wakcraft.proxy.getClientPlayer()) || !FightHelper.isFighting(Wakcraft.proxy.getClientPlayer())) {
				return null;
			}
		}

		return super.collisionRayTrace(world, posX, posY, posZ, vec1, vec2);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int posX, int posY, int posZ, AxisAlignedBB mask, List list, Entity entity) {
		if (!FightHelper.isFighter(entity) || !FightHelper.isFighting(entity)) {
			return;
		}

		super.addCollisionBoxesToList(world, posX, posY, posZ, mask, list, entity);
	}
}
