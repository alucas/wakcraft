package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.characteristic.CharacteristicsManager.CHARACTERISTIC;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class CharacteristicsProperty implements IExtendedEntityProperties, ISynchProperties {
	public static final String IDENTIFIER = "characteristics";

	protected static String TAG_CHARACTERISTICS = "characteristics";
	protected static String TAG_NAME = "Name";
	protected static String TAG_VALUE = "Value";

	protected static EnumSet<CHARACTERISTIC> persistableCharacteristics = EnumSet.of(
			CHARACTERISTIC.STRENGTH, CHARACTERISTIC.INTELLIGENCE, CHARACTERISTIC.AGILITY,
			CHARACTERISTIC.CHANCE, CHARACTERISTIC.BLOCK, CHARACTERISTIC.RANGE, CHARACTERISTIC.ACTION,
			CHARACTERISTIC.MOVEMENT, CHARACTERISTIC.CRITICAL, CHARACTERISTIC.KIT, CHARACTERISTIC.LOCK,
			CHARACTERISTIC.DODGE, CHARACTERISTIC.INITIATIVE, CHARACTERISTIC.HEALTH);

	/** Enable persistence */
	protected boolean enablePersistence = false;
	/** All characteristics */
	protected Map<CHARACTERISTIC, Integer> characteristics = new HashMap<CHARACTERISTIC, Integer>();

	@Override
	public void init(Entity entity, World world) {
	}

	@Override
	public void saveNBTData(NBTTagCompound tagRoot) {
		if (!enablePersistence) {
			return;
		}

		NBTTagList tagCharacteristics = new NBTTagList();

		for (CHARACTERISTIC key : persistableCharacteristics) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(TAG_NAME, key.name());
			tag.setInteger(TAG_VALUE, characteristics.get(key));
			tagCharacteristics.appendTag(tag);
		}

		tagRoot.setTag(TAG_CHARACTERISTICS, tagCharacteristics);
	}

	@Override
	public void loadNBTData(NBTTagCompound tagRoot) {
		if (!enablePersistence) {
			return;
		}

		NBTTagList tagCharacteristics = tagRoot.getTagList(TAG_CHARACTERISTICS, 10);

		for (int i = 0; i < tagCharacteristics.tagCount(); i++) {
			NBTTagCompound tag = tagCharacteristics.getCompoundTagAt(i);
			CHARACTERISTIC key = CHARACTERISTIC.valueOf(tag.getString(TAG_NAME));
			Integer value = tag.getInteger(TAG_VALUE);

			characteristics.put(key, value);
		}
	}

	public int get(CHARACTERISTIC key) {
		Integer value = characteristics.get(key);
		if (value != null) {
			return value;
		}

		return 0;
	}

	public void set(CHARACTERISTIC key, int value) {
		characteristics.put(key, value);
	}

	public void enablePersistence(boolean enablePersistence) {
		this.enablePersistence = enablePersistence;
	}

	@Override
	public NBTTagCompound getClientPacket() {
		NBTTagCompound tagRoot = new NBTTagCompound();
		NBTTagList tagCharacteristics = new NBTTagList();

		for (CHARACTERISTIC key : characteristics.keySet()) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(TAG_NAME, key.toString());
			tag.setInteger(TAG_VALUE, characteristics.get(key));

			tagCharacteristics.appendTag(tag);
		}

		tagRoot.setTag(TAG_CHARACTERISTICS, tagCharacteristics);

		return tagRoot;
	}

	@Override
	public void onClientPacket(NBTTagCompound tagRoot) {
		NBTTagList tagCharacteristics = tagRoot.getTagList(TAG_CHARACTERISTICS, 10);

		for (int i = 0; i < tagCharacteristics.tagCount(); i++) {
			NBTTagCompound tag = tagCharacteristics.getCompoundTagAt(i);
			CHARACTERISTIC key = CHARACTERISTIC.valueOf(tag.getString(TAG_NAME));
			Integer value = tag.getInteger(TAG_VALUE);

			characteristics.put(key, value);
		}
	}
}
