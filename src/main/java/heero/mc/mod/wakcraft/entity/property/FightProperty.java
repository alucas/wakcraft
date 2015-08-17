package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import javax.annotation.Nullable;

public class FightProperty implements IExtendedEntityProperties {
	public static final String IDENTIFIER = Reference.MODID + "Fight";

	protected int fightId;
	protected int teamId;
	protected BlockPos startPosition;
	protected BlockPos currentPosition;

	@Override
	public void init(Entity entity, World world) {
		resetProperties();
	}

	@Override
	public void saveNBTData(NBTTagCompound tagRoot) {
	}

	@Override
	public void loadNBTData(NBTTagCompound tagRoot) {
	}

	public int getFightId() {
		return fightId;
	}

	public void setFightId(int fightId) {
		this.fightId = fightId;
	}

	public void resetProperties() {
		this.fightId = -1;
		this.teamId = -1;
	}

	public int getTeam() {
		return teamId;
	}

	public void setTeam(int teamId) {
		this.teamId = teamId;
	}

	public BlockPos getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(@Nullable BlockPos startPosition) {
		this.startPosition = startPosition;
	}

	public BlockPos getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(BlockPos position) {
		this.currentPosition = position;
	}
}
