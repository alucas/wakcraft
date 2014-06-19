package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.characteristic.Characteristic;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class FightCharacteristicsProperty implements IExtendedEntityProperties {
	public static final String IDENTIFIER = "fightCharacteristics";

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
}
