package heero.mc.mod.wakcraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fluids.IFluidBlock;

import java.util.Random;

public class EntityAIMoveOutWater extends EntityAIBase {
    protected EntityCreature entity;

    protected double speed;
    protected double destinationX;
    protected double destinationY;
    protected double destinationZ;

    public EntityAIMoveOutWater(EntityCreature entity, double speed) {
        this.entity = entity;
        this.speed = speed;

        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (!entity.isInWater() || !entity.isEntityAlive()) {
            return false;
        }

        if (entity.getRNG().nextInt(20) != 0) {
            return false;
        }

        BlockPos blockPos = getNearestRandomCoast(entity, 10);

        if (blockPos == null) {
            return false;
        } else {
            this.destinationX = blockPos.getX();
            this.destinationY = blockPos.getY();
            this.destinationZ = blockPos.getZ();

            return true;
        }
    }

    @Override
    public boolean continueExecuting() {
        return !entity.getNavigator().noPath() && entity.isEntityAlive();
    }

    @Override
    public void startExecuting() {
        entity.getNavigator().tryMoveToXYZ(this.destinationX, this.destinationY, this.destinationZ, this.speed);
    }

    protected BlockPos getNearestRandomCoast(EntityCreature entity, int distanceMax) {
        Random random = entity.getRNG();
        BlockPos blockPosFound = null;
        double pathWeightMax = Double.MAX_VALUE;
        boolean isInHomeRange;

        if (entity.hasHome()) {
            double distanceToHomeSquared = entity.getHomePosition().distanceSq(
                    MathHelper.floor_double(entity.posX),
                    MathHelper.floor_double(entity.posY),
                    MathHelper.floor_double(entity.posZ)) + 4.0;
            double distanceToHomeMax = entity.getMaximumHomeDistance() + distanceMax;

            isInHomeRange = distanceToHomeSquared < distanceToHomeMax * distanceToHomeMax;
        } else {
            isInHomeRange = true;
        }

        for (int l1 = 0; l1 < 10; ++l1) {
            int posX = MathHelper.floor_double(entity.posX) + random.nextInt(2 * distanceMax) - distanceMax;
            int posY = MathHelper.floor_double(entity.posY);
            int posZ = MathHelper.floor_double(entity.posZ) + random.nextInt(2 * distanceMax) - distanceMax;
            BlockPos blockPos = new BlockPos(posX, posY, posZ);

            if (!isInHomeRange && !entity.isWithinHomeDistanceFromPosition(blockPos)) {
                continue;
            }

            double pathWeight = (posX - entity.posX) * (posX - entity.posX) + (posZ - entity.posZ) * (posZ - entity.posZ);
            if (pathWeight >= pathWeightMax) {
                continue;
            }

            Block block = entity.worldObj.getBlockState(blockPos).getBlock();
            if (block instanceof BlockLiquid
                    || block instanceof IFluidBlock
                    || !entity.worldObj.getBlockState(blockPos.up()).getBlock().equals(Blocks.air)) {
                continue;
            }

            pathWeightMax = pathWeight;
            blockPosFound = blockPos;
        }

        return blockPosFound;
    }
}
