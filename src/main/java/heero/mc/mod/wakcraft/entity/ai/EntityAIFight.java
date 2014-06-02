package heero.mc.mod.wakcraft.entity.ai;

import heero.mc.mod.wakcraft.helper.FightHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.ChunkCoordinates;

public class EntityAIFight extends EntityAIBase {
	protected EntityLiving entity;
	protected double speed;

	public EntityAIFight(EntityLiving entity, double speed) {
		this.entity = entity;
		this.speed = speed;

		setMutexBits(1);

		if (!FightHelper.isFighter(entity)) {
			throw new RuntimeException("This AI can't be assigned to a non-fighter");
		}
	}

	@Override
	public boolean shouldExecute() {
		return FightHelper.isFighting(entity);
	}

	@Override
	public void startExecuting() {
		ChunkCoordinates position = FightHelper.getStartPosition(entity);
		if (position == null) {
			return;
		}

		if (!entity.getNavigator().tryMoveToXYZ(position.posX + 0.5, position.posY, position.posZ + 0.5, this.speed)) {
			entity.setPositionAndUpdate(position.posX + 0.5, position.posY, position.posZ + 0.5);
		}
	}
}
