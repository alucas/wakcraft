package heero.mc.mod.wakcraft.entity.ai;

import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.BlockPos;

public class EntityAIFight extends EntityAIBase {
	protected EntityLiving entity;
	protected double speed;

	public EntityAIFight(EntityLiving entity, double speed) {
		this.entity = entity;
		this.speed = speed;

		setMutexBits(1);

		if (!FightUtil.isFighter(entity)) {
			throw new RuntimeException("This AI can't be assigned to a non-fighter");
		}
	}

	@Override
	public boolean shouldExecute() {
		return FightUtil.isFighting(entity);
	}

	@Override
	public void startExecuting() {
        BlockPos position = FightUtil.getStartPosition(entity);
		if (position == null) {
			return;
		}

		if (!entity.getNavigator().tryMoveToXYZ(position.getX() + 0.5, position.getY(), position.getZ() + 0.5, this.speed)) {
			entity.setPositionAndUpdate(position.getX() + 0.5, position.getY(), position.getZ() + 0.5);
		}
	}
}
