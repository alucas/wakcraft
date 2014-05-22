package heero.wakcraft.entity.property;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class AbilitiesProperty implements IExtendedEntityProperties {
	public static final String IDENTIFIER = "abilities";

	protected static String TAG_ABILITIES = "abilities";
	protected static String TAG_NAME = "Name";
	protected static String TAG_VALUE = "Value";

	// Principal
	public static String HEALTH = "Health";
	public static String ACTION = "Action";
	public static String MOVEMENT = "Movement";
	public static String WAKFU = "Wakfu";
	public static String STRENGTH = "Strength";
	public static String INTELLIGENCE = "Intelligence";
	public static String AGILITY = "Agility";
	public static String CHANCE = "Chance";

	// Batle
	public static String INITIATIVE = "Initiative";
	public static String HEAL = "Heal";
	public static String CRITICAL = "Critical";
	public static String critical_damage;
	public static String critical_failure;
	public static String BACKSTAB = "Backstab";
	public static String BACKSTAB_RESISTANCE = "Backstab_Resistance";
	public static String DODGE = "Dodge";
	public static String LOCK = "Lock";
	public static String BLOCK = "Block";
	public static String WILLPOWER = "Willpower";
	public static String RANGE = "Range";

	// Secondary
	public static String CONTROL = "Control";
	/** Increases the damages of the creatures and mechanisms you control */
	public static String CMC_DAMAGE = "CMC_Damage";
	public static String WISDOM = "Wisdom";
	public static String PROSPECTION = "Prospection";
	public static String PERCEPTION = "Perception";
	public static String KIT = "Kit";

	protected String[] abilitiesBaseKeys = new String[] { STRENGTH,
			INTELLIGENCE, AGILITY, CHANCE, BLOCK, RANGE, ACTION, MOVEMENT,
			CRITICAL, KIT, LOCK, DODGE, INITIATIVE, HEALTH };

	/** Persisted abilities */
	protected Map<String, Integer> abilitiesBase = new HashMap<String, Integer>();
	/** All abilities */
	protected Map<String, Integer> abilities = new HashMap<String, Integer>();

	@Override
	public void init(Entity entity, World world) {
		abilitiesBase.clear();

		abilitiesBase.put(ACTION, 6);
		abilitiesBase.put(MOVEMENT, 3);
		abilitiesBase.put(HEALTH, 49);
		abilitiesBase.put(CRITICAL, 3);

		abilities.put(WAKFU, 6);
		abilities.put(CONTROL, 1);
	}

	@Override
	public void saveNBTData(NBTTagCompound tagRoot) {
		NBTTagList tagAbilities = new NBTTagList();

		for (String key : abilitiesBaseKeys) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(TAG_NAME, key);
			tag.setInteger(TAG_VALUE, abilitiesBase.get(key));

			tagAbilities.appendTag(tag);
		}

		tagRoot.setTag(TAG_ABILITIES, tagAbilities);
	}

	@Override
	public void loadNBTData(NBTTagCompound tagRoot) {
		NBTTagList tagAbilities = tagRoot.getTagList(TAG_ABILITIES, 10);

		for (int i = 0; i < tagAbilities.tagCount(); i++) {
			NBTTagCompound tag = tagAbilities.getCompoundTagAt(i);
			String key = tag.getString(TAG_NAME);
			Integer value = tag.getInteger(TAG_VALUE);

			abilitiesBase.put(key, value);
		}
	}

	public int get(String key) {
		Integer value = abilities.get(key);
		if (value != null) {
			return value;
		}

		value = abilitiesBase.get(key);
		if (value != null) {
			return value;
		}

		return 0;
	}

	public void set(String key, int value) {
		abilities.put(key, value);
	}
}
