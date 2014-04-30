package heero.wakcraft.entity.property;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class HavenBagProperty implements IExtendedEntityProperties {
	public static final String IDENTIFIER = "HavenBag";

	private static final String TAG_HAVENBAG = "HavenBag";
	private static final String TAG_UID = "UID";
	private static final String TAG_IN_HAVENBAG = "InHavenBag";
	private static final String TAG_POS_X = "PosX";
	private static final String TAG_POS_Y = "PosY";
	private static final String TAG_POS_Z = "PosZ";

	public int uid;
	public boolean inHavenBag;
	public double posX;
	public double posY;
	public double posZ;

	@Override
	public void init(Entity entity, World world) {
		uid = 0;
		inHavenBag = false;
		posX = 0;
		posY = 0;
		posZ = 0;
	}

	@Override
	public void saveNBTData(NBTTagCompound tagRoot) {
		NBTTagCompound tagHavenBag = new NBTTagCompound();

		tagHavenBag.setInteger(TAG_UID, uid);
		tagHavenBag.setBoolean(TAG_IN_HAVENBAG, inHavenBag);
		tagHavenBag.setDouble(TAG_POS_X, posX);
		tagHavenBag.setDouble(TAG_POS_Y, posY);
		tagHavenBag.setDouble(TAG_POS_Z, posZ);

		tagRoot.setTag(TAG_HAVENBAG, tagHavenBag);
	}

	@Override
	public void loadNBTData(NBTTagCompound tagRoot) {
		NBTTagCompound tagHavenBag = tagRoot.getCompoundTag(TAG_HAVENBAG);

		uid = tagHavenBag.getInteger(TAG_UID);
		inHavenBag = tagHavenBag.getBoolean(TAG_IN_HAVENBAG);
		posX = tagHavenBag.getDouble(TAG_POS_X);
		posY = tagHavenBag.getDouble(TAG_POS_Y);
		posZ = tagHavenBag.getDouble(TAG_POS_Z);
	}

	public void setEnterHavenBag(double posX, double posY, double posZ) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		inHavenBag = true;
	}

	public void setLeaveHavenBag() {
		posX = 0;
		posY = 0;
		posZ = 0;
		inHavenBag = false;
	}
}
