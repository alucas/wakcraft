package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.characteristic.CharacteristicsManager.CHARACTERISTIC;
import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.helper.CharacteristicsHelper;
import heero.mc.mod.wakcraft.helper.FightHelper;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockFightInsideWall extends BlockGeneric {

	public BlockFightInsideWall() {
		super(Material.air);

		setBlockName("FightInsideWall");
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

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add
	 * boxes to the list if they intersect the mask.) Parameters: World, X, Y,
	 * Z, mask, list, colliding entity
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z,
			AxisAlignedBB mask, List list, Entity entity) {
		if (FightHelper.isAutonomousFighter(entity)) {
			return;
		}

		if (!FightHelper.isFighter(entity) || !FightHelper.isFighting(entity)) {
			return;
		}

		switch (FightManager.INSTANCE.getFightStage(world, FightHelper.getFightId(entity))) {
		case PREFIGHT:
			ChunkCoordinates pos = FightHelper.getStartPosition(entity);
			if (pos == null || (pos.posX == x && pos.posZ == z && (pos.posY == y || pos.posY == y - 1))) {
				return;
			}

			break;
		case FIGHT:
			int posX = MathHelper.floor_double(entity.posX);
			int posY = MathHelper.floor_double(entity.posY);
			int posZ = MathHelper.floor_double(entity.posZ);
			int movementPoint = CharacteristicsHelper.getCharacteristic(entity, CHARACTERISTIC.MOVEMENT);
			int distance = MathHelper.abs_int(posX - x) + MathHelper.abs_int(posZ - z);

			if (movementPoint >= distance) {
				return;
			}

			break;
		default:
			break;
		}

		super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
	}

	/**
	 * Returns whether this block is collideable based on the arguments passed
	 * in n@param par1 block metaData n@param par2 whether the player
	 * right-clicked while holding a boat
	 */
	@Override
	public boolean canCollideCheck(int metadata, boolean stopOnLiquid) {
		return false;
	}
}
