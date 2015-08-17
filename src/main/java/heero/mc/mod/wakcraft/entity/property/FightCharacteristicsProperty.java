package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.characteristic.Characteristic;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class FightCharacteristicsProperty implements IExtendedEntityProperties, ISynchProperties {
	public static final String IDENTIFIER = Reference.MODID + "FightCharacteristics";

	protected static String TAG_CHARACTERISTICS = "characteristics";
	protected static String TAG_NAME = "Name";
	protected static String TAG_VALUE = "Value";

	/** All characteristics */
	protected Map<Characteristic, Integer> characteristics = new HashMap<Characteristic, Integer>();

	@Override
	public void init(Entity entity, World world) {
	}

	@Override
	public void saveNBTData(NBTTagCompound tagRoot) {
	}

	@Override
	public void loadNBTData(NBTTagCompound tagRoot) {
	}

	public int get(Characteristic key) {
		Integer value = characteristics.get(key);
		if (value != null) {
			return value;
		}

		return 0;
	}

	public void set(Characteristic key, int value) {
		characteristics.put(key, value);
	}

	@Override
	public NBTTagCompound getClientPacket() {
		NBTTagCompound tagRoot = new NBTTagCompound();
		NBTTagList tagCharacteristics = new NBTTagList();

		for (Characteristic key : characteristics.keySet()) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(TAG_NAME, key.name());
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
			Characteristic key = Characteristic.valueOf(tag.getString(TAG_NAME));
			Integer value = tag.getInteger(TAG_VALUE);

			characteristics.put(key, value);
		}
	}
}
