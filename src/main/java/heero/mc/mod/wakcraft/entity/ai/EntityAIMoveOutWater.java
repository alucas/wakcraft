package heero.mc.mod.wakcraft.entity.ai;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.fluids.IFluidBlock;

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

		Vec3 blockCoords = getNearestRandomCoast(entity, 10);

		if (blockCoords == null) {
			return false;
		} else {
			this.destinationX = blockCoords.xCoord;
			this.destinationY = blockCoords.yCoord;
			this.destinationZ = blockCoords.zCoord;

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

	protected Vec3 getNearestRandomCoast(EntityCreature entity, int distanceMax) {
		Random random = entity.getRNG();
		boolean blockFound = false;
		int selectedPosX = 0;
		int selectedPosY = 0;
		int selectedPosZ = 0;
		double pathWeightMax = Double.MAX_VALUE;
		boolean isInHomeRange;

		if (entity.hasHome()) {
			double distanceToHomeSquared = entity.getHomePosition().getDistanceSquared(
					MathHelper.floor_double(entity.posX),
					MathHelper.floor_double(entity.posY),
					MathHelper.floor_double(entity.posZ)) + 4.0;
			double distanceToHomeMax = entity.func_110174_bM() + distanceMax;

			isInHomeRange = distanceToHomeSquared < distanceToHomeMax * distanceToHomeMax;
		} else {
			isInHomeRange = true;
		}

		for (int l1 = 0; l1 < 10; ++l1) {
			int posX = MathHelper.floor_double(entity.posX) + random.nextInt(2 * distanceMax) - distanceMax;
			int posY = MathHelper.floor_double(entity.posY);
			int posZ = MathHelper.floor_double(entity.posZ) + random.nextInt(2 * distanceMax) - distanceMax;

			if (!isInHomeRange && !entity.isWithinHomeDistance(posX, posY, posZ)) {
				continue;
			}

			double pathWeight = (posX - entity.posX) * (posX - entity.posX) + (posZ - entity.posZ) * (posZ - entity.posZ);
			if (pathWeight >= pathWeightMax) {
				continue;
			}

			Block block = entity.worldObj.getBlock(posX, posY, posZ);
			if (block instanceof BlockLiquid
					|| block instanceof IFluidBlock
					|| !entity.worldObj.getBlock(posX, posY + 1, posZ).equals(Blocks.air)) {
				continue;
			}

			pathWeightMax = pathWeight;
			selectedPosX = posX;
			selectedPosY = posY + 1;
			selectedPosZ = posZ;
			blockFound = true;
		}

		if (!blockFound) {
			return null;
		}

		return Vec3.createVectorHelper((double) selectedPosX, (double) selectedPosY, (double) selectedPosZ);
	}
}
