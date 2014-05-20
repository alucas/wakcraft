package heero.wakcraft.entity.property;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class FightProperty implements IExtendedEntityProperties {
	public static final String IDENTIFIER = "fight";

	protected int fightId;

	@Override
	public void init(Entity entity, World world) {
		fightId = -1;
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

	public void resetFightId() {
		this.fightId = -1;
	}

	public boolean isFighting() {
		return fightId != -1;
	}
}
