package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class BlockFightInsideWall extends BlockGenericTransparent {

    public BlockFightInsideWall() {
        super(Material.air);

        setCanBePlacedManually(false);
    }

    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add
     * boxes to the list if they intersect the mask.) Parameters: World, X, Y,
     * Z, mask, list, colliding entity
     */
    @Override
    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity entity) {
        if (FightUtil.isAutonomousFighter(entity)) {
            return;
        }

        if (!FightUtil.isFighter(entity) || !FightUtil.isFighting(entity)) {
            return;
        }

        int fightId = FightUtil.getFightId(entity);
        switch (FightUtil.getFightStage(world, fightId)) {
            case PREFIGHT:
                BlockPos startPosition = FightUtil.getStartPosition(entity);
                if (startPosition == null || (startPosition.getX() == pos.getX() && startPosition.getZ() == pos.getZ() && (startPosition.getY() == pos.getY() || startPosition.getY() == pos.getY() - 1))) {
                    return;
                }

                break;
            case FIGHT:
                BlockPos currentPosition = FightUtil.getCurrentPosition(entity);
                EntityLivingBase currentFighter = FightUtil.getCurrentFighter(world, fightId);
                int movementPoint = (currentFighter == entity) ? FightUtil.getFightCharacteristic(entity, Characteristic.MOVEMENT) : 0;
                int distance = MathHelper.abs_int(currentPosition.getX() - pos.getX()) + MathHelper.abs_int(currentPosition.getZ() - pos.getZ());

                if (movementPoint < distance) {
                    break;
                }

                if (FightManager.INSTANCE.isThereFighterInAABB(world, fightId, AxisAlignedBB.fromBounds(pos.getX(), pos.getY() - 1, pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1))) {
                    break;
                }

                return;
            default:
                break;
        }

        super.addCollisionBoxesToList(world, pos, state, mask, list, entity);
    }

    /**
     * Returns whether this block is collideable based on the arguments passed
     * in n@param par1 block metaData n@param par2 whether the player
     * right-clicked while holding a boat
     */
    @Override
    public boolean canCollideCheck(IBlockState state, boolean stopOnLiquid) {
        return false;
    }
}
