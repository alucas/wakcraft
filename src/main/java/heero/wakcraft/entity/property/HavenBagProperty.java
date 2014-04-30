package heero.wakcraft.entity.property;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class HavenBagProperty implements IExtendedEntityProperties {
	public static final String IDENTIFIER = "HavenBag";

	private static final String TAG_HAVENBAG = "HavenBag";
	private static final String TAG_UID = "UID";
	private static final String TAG_POS_X = "PosX";
	private static final String TAG_POS_Y = "PosY";
	private static final String TAG_POS_Z = "PosZ";

	public int uid;
	public int posX;
	public int posY;
	public int posZ;

	@Override
	public void init(Entity entity, World world) {
	}

	@Override
	public void saveNBTData(NBTTagCompound tagRoot) {
		NBTTagCompound tagHavenBag = new NBTTagCompound();

		tagHavenBag.setInteger(TAG_UID, uid);
		tagHavenBag.setInteger(TAG_POS_X, posX);
		tagHavenBag.setInteger(TAG_POS_Y, posY);
		tagHavenBag.setInteger(TAG_POS_Z, posZ);

		tagRoot.setTag(TAG_HAVENBAG, tagHavenBag);
	}

	@Override
	public void loadNBTData(NBTTagCompound tagRoot) {
		NBTTagCompound tagHavenBag = tagRoot.getCompoundTag(TAG_HAVENBAG);

		uid = tagHavenBag.getInteger(TAG_UID);
		posX = tagHavenBag.getInteger(TAG_POS_X);
		posY = tagHavenBag.getInteger(TAG_POS_Y);
		posZ = tagHavenBag.getInteger(TAG_POS_Z);
	}
}
