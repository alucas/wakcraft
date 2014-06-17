package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.ability.AbilityManager.ABILITY;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class AbilitiesProperty implements IExtendedEntityProperties, ISynchProperties {
	public static final String IDENTIFIER = "abilities";

	protected static String TAG_ABILITIES = "abilities";
	protected static String TAG_NAME = "Name";
	protected static String TAG_VALUE = "Value";

	protected EnumSet<ABILITY> abilitiesPersistKeys = EnumSet.of(
			ABILITY.STRENGTH, ABILITY.INTELLIGENCE, ABILITY.AGILITY,
			ABILITY.CHANCE, ABILITY.BLOCK, ABILITY.RANGE, ABILITY.ACTION,
			ABILITY.MOVEMENT, ABILITY.CRITICAL, ABILITY.KIT, ABILITY.LOCK,
			ABILITY.DODGE, ABILITY.INITIATIVE, ABILITY.HEALTH);

	/** Persisted abilities */
	protected Map<ABILITY, Integer> abilitiesPersist = new HashMap<ABILITY, Integer>();
	/** All abilities */
	protected Map<ABILITY, Integer> abilities = new HashMap<ABILITY, Integer>();

	@Override
	public void init(Entity entity, World world) {
		abilitiesPersist.clear();

		for (ABILITY key : abilitiesPersistKeys) {
			abilitiesPersist.put(key, 0);
		}

		abilitiesPersist.put(ABILITY.ACTION, 6);
		abilitiesPersist.put(ABILITY.MOVEMENT, 3);
		abilitiesPersist.put(ABILITY.HEALTH, 49);
		abilitiesPersist.put(ABILITY.CRITICAL, 3);

		abilities.put(ABILITY.WAKFU, 6);
		abilities.put(ABILITY.CONTROL, 1);
	}

	@Override
	public void saveNBTData(NBTTagCompound tagRoot) {
		NBTTagList tagAbilities = new NBTTagList();

		for (ABILITY key : abilitiesPersistKeys) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(TAG_NAME, key.name());
			tag.setInteger(TAG_VALUE, abilitiesPersist.get(key));
			tagAbilities.appendTag(tag);
		}

		tagRoot.setTag(TAG_ABILITIES, tagAbilities);
	}

	@Override
	public void loadNBTData(NBTTagCompound tagRoot) {
		NBTTagList tagAbilities = tagRoot.getTagList(TAG_ABILITIES, 10);

		for (int i = 0; i < tagAbilities.tagCount(); i++) {
			NBTTagCompound tag = tagAbilities.getCompoundTagAt(i);
			ABILITY key = ABILITY.valueOf(tag.getString(TAG_NAME));
			Integer value = tag.getInteger(TAG_VALUE);

			abilitiesPersist.put(key, value);
		}
	}

	public int get(ABILITY key) {
		Integer value = abilities.get(key);
		if (value != null) {
			return value;
		}

		value = abilitiesPersist.get(key);
		if (value != null) {
			return value;
		}

		return 0;
	}

	public void set(ABILITY key, int value) {
		abilities.put(key, value);
	}

	@Override
	public NBTTagCompound getClientPacket() {
		NBTTagCompound tagRoot = new NBTTagCompound();
		NBTTagList tagAbilities = new NBTTagList();

		for (ABILITY key : abilities.keySet()) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(TAG_NAME, key.toString());
			tag.setInteger(TAG_VALUE, abilities.get(key));

			tagAbilities.appendTag(tag);
		}

		tagRoot.setTag(TAG_ABILITIES, tagAbilities);

		return tagRoot;
	}

	@Override
	public void onClientPacket(NBTTagCompound tagRoot) {
		NBTTagList tagAbilities = tagRoot.getTagList(TAG_ABILITIES, 10);

		for (int i = 0; i < tagAbilities.tagCount(); i++) {
			NBTTagCompound tag = tagAbilities.getCompoundTagAt(i);
			ABILITY key = ABILITY.valueOf(tag.getString(TAG_NAME));
			Integer value = tag.getInteger(TAG_VALUE);

			abilities.put(key, value);
		}
	}
}
