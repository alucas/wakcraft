package heero.mc.mod.wakcraft.havenbag;


import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.HashMap;
import java.util.Map;

public class HavenBagsManager extends WorldSavedData {
	// Constants
	private static final String TAG_HAVENBAGS = "Havenbags";
	private static final String TAG_UID = "UID";
	private static final String TAG_PROPERTIES = "Properties";
	private static final String TAG_LOCKED = "Locked";
	private static final String TAG_ACL = "ACL";
	private static final String TAG_NAME = "Name";
	private static final String TAG_RIGHT = "Right";

	public static final String ACL_KEY_ALL = "@all";
	public static final String ACL_KEY_GUILD = "@guild";

	// Statics
	private static HavenBagsManager instance;

	// Parameters
	private Map<Integer, HavenBagProperties> havenbags;

	// Static methods
	public static void setup() {
	}

	public static void teardown() {
		instance = null;
	}

	public static void init(World world) {
		if (instance == null) {
			instance = (HavenBagsManager) world.loadItemData(HavenBagsManager.class, "havenbags");

			if (instance == null) {
				world.setItemData("havenbags", new HavenBagsManager("havenbags"));
			}
		}
	}

	public static HavenBagProperties getProperties(int uid) {
		HavenBagProperties properties = instance.havenbags.get(uid);
		if (properties == null) {
			properties = new HavenBagProperties();
			setProperties(uid, properties);
		}

		return properties;
	}

	public static void setProperties(int uid, HavenBagProperties properties) {
		instance.havenbags.put(uid, properties);
		instance.markDirty();
	}

	public static NBTTagCompound getHavenBagNBT(int uid) {
		NBTTagCompound tagHavenBag = new NBTTagCompound();

		instance.writeHavenBagToNBT(tagHavenBag, uid);

		return tagHavenBag;
	}

	public static void setHavenBagNBT(NBTTagCompound tagHavenBag) {
		instance.readHavenBagFromNBT(tagHavenBag);
	}

	// Instance methods
	public HavenBagsManager(String name) {
		super(name);

		havenbags = new HashMap<>();
	}

	@Override
	public void readFromNBT(NBTTagCompound tagRoot) {
		NBTTagList tagHavenbags = tagRoot.getTagList(TAG_HAVENBAGS, 10);
		for (int i = 0; i < tagHavenbags.tagCount(); i++) {
			NBTTagCompound tagHavenBag = tagHavenbags.getCompoundTagAt(i);

			readHavenBagFromNBT(tagHavenBag);
		}
	}

	protected void readHavenBagFromNBT(NBTTagCompound tagHavenBag) {
		int uid = tagHavenBag.getInteger(TAG_UID);
		NBTTagCompound tagProperties = tagHavenBag.getCompoundTag(TAG_PROPERTIES);

		HavenBagProperties properties = new HavenBagProperties(); 
		properties.setLock(tagProperties.getBoolean(TAG_LOCKED));

		NBTTagList tagACL = tagProperties.getTagList(TAG_ACL, 10);
		for (int j = 0; j < tagACL.tagCount(); j++) {
			NBTTagCompound tagEntry = tagACL.getCompoundTagAt(j);
			String name = tagEntry.getString(TAG_NAME);
			int right = tagEntry.getInteger(TAG_RIGHT);

			properties.setRight(name, right);
		}

		havenbags.put(uid, properties);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagRoot) {
		NBTTagList tagHavenBags = new NBTTagList();

		for (int havenBagId : havenbags.keySet()) {
			NBTTagCompound tagHavenBag = new NBTTagCompound();

			writeHavenBagToNBT(tagHavenBag, havenBagId);

			tagHavenBags.appendTag(tagHavenBag);
		}

		tagRoot.setTag(TAG_HAVENBAGS, tagHavenBags);
	}

	protected void writeHavenBagToNBT(NBTTagCompound tagHavenBag, int uid){
		HavenBagProperties properties = getProperties(uid);

		NBTTagCompound tagProperties = new NBTTagCompound();
		NBTTagList tagACL = new NBTTagList();
		for (String name : properties.getRightKeys()) {
			NBTTagCompound tagEntry = new NBTTagCompound();
			tagEntry.setString(TAG_NAME, name);
			tagEntry.setInteger(TAG_RIGHT, properties.getRight(name));

			tagACL.appendTag(tagEntry);
		}

		tagProperties.setBoolean(TAG_LOCKED, properties.isLocked());
		tagProperties.setTag(TAG_ACL, tagACL);

		tagHavenBag.setInteger(TAG_UID, uid);
		tagHavenBag.setTag(TAG_PROPERTIES, tagProperties);
	}
}
