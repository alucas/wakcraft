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
	}

	@Override
	public void saveNBTData(NBTTagCompound tagRoot) {
	}

	@Override
	public void loadNBTData(NBTTagCompound tagRoot) {
	}
}
