package heero.wakcraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHavenBag extends TileEntity {
	private static final String TAG_UID = "UID";
	private static final String TAG_LOCKED = "Locked";

	protected int uid;
	protected boolean isLocked;

	public void readFromNBT(NBTTagCompound tagRoot) {
		super.readFromNBT(tagRoot);

		uid = tagRoot.getInteger(TAG_UID);
		isLocked = tagRoot.getBoolean(TAG_LOCKED);
	}

	public void writeToNBT(NBTTagCompound tagRoot) {
		super.writeToNBT(tagRoot);

		tagRoot.setInteger(TAG_UID, uid);
		tagRoot.setBoolean(TAG_LOCKED, isLocked);
	}
}
