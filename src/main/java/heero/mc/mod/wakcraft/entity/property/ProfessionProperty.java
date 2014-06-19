package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.manager.ProfessionManager.PROFESSION;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ProfessionProperty implements IExtendedEntityProperties {
	public static final String IDENTIFIER = WInfo.MODID + "Profession";

	private static final String TAG_ID = "name";
	private static final String TAG_LEVEL = "level";
	private static final String TAG_PROFESSIONS = "professions";

	private Map<PROFESSION, Integer> xps = new HashMap<PROFESSION, Integer>();

	@Override
	public void init(Entity entity, World world) {
	}

	@Override
	public void saveNBTData(NBTTagCompound tagRoot) {
		NBTTagList tagProfessions = new NBTTagList();

		for (PROFESSION professionId : PROFESSION.values()) {
			NBTTagInt tagId = new NBTTagInt(professionId.getValue());
			
			Integer xp = xps.get(professionId);
			NBTTagInt tagXp = new NBTTagInt(xp == null ? 0 : xp);

			NBTTagCompound tagProfession = new NBTTagCompound();
			tagProfession.setTag(TAG_ID, tagId);
			tagProfession.setTag(TAG_LEVEL, tagXp);

			tagProfessions.appendTag(tagProfession);
		}

		tagRoot.setTag(TAG_PROFESSIONS, tagProfessions);
	}

	@Override
	public void loadNBTData(NBTTagCompound tagRoot) {
		NBTTagList tagProfessions = tagRoot.getTagList(TAG_PROFESSIONS, 10);
		for (int i = 0; i < tagProfessions.tagCount(); i++) {
			NBTTagCompound tagProfession = tagProfessions.getCompoundTagAt(i);

			PROFESSION professionId = PROFESSION.getProfession(tagProfession.getInteger(TAG_ID));

			xps.put(professionId, tagProfession.getInteger(TAG_LEVEL));
		}
	}

	public void setXp(PROFESSION professionId, int xp) {
		xps.put(professionId, xp);
	}

	public int getXp(PROFESSION professionId) {
		Integer xp = xps.get(professionId);

		return xp == null ? 0 : xp;
	}
}
