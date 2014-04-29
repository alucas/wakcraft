package heero.wakcraft.entity.property;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class HavenBagProperty implements IExtendedEntityProperties {
	public static final String IDENTIFIER = "HavenBag";

	private static final String TAG_ID = "Id";

	public int id;

	@Override
	public void init(Entity entity, World world) {
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		compound.setInteger(TAG_ID, id);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		id = compound.getInteger(TAG_ID);
	}
}
