package heero.wakcraft.tileentity;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHavenBagProperties extends TileEntity {
	private static final String TAG_LOCKED = "Locked";
	private static final String TAG_ACL = "ACL";
	private static final String TAG_NAME = "Name";
	private static final String TAG_RIGHT = "Right";

	/** Used to allow visitor to enter in your haven bag */
	public boolean locked;
	/** Used to keep the acces permition of players */
	public Map<String, Integer> acl = new HashMap<String, Integer>();

	@Override
	public void readFromNBT(NBTTagCompound tagRoot) {
		super.readFromNBT(tagRoot);

		locked = tagRoot.getBoolean(TAG_LOCKED);

		NBTTagList tagACL = tagRoot.getTagList(TAG_ACL, 10);
		for (int i = 0; i < tagACL.tagCount(); i++) {
			NBTTagCompound tagEntry = tagACL.getCompoundTagAt(i);
			String name = tagEntry.getString(TAG_NAME);
			int right = tagEntry.getInteger(TAG_RIGHT);

			acl.put(name, right);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagRoot) {
		super.writeToNBT(tagRoot);

		tagRoot.setBoolean(TAG_LOCKED, locked);

		NBTTagList tagACL = new NBTTagList();
		for (String name : acl.keySet()) {
			NBTTagCompound tagEntry = new NBTTagCompound();
			tagEntry.setString(TAG_NAME, name);
			tagEntry.setInteger(TAG_RIGHT, acl.get(name));

			tagACL.appendTag(tagEntry);
		}

		tagRoot.setTag(TAG_ACL, tagACL);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();

		writeToNBT(nbtTag);

		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}
}
