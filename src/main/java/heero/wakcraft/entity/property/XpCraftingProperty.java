package heero.wakcraft.entity.property;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class XpCraftingProperty implements IExtendedEntityProperties {
	public static final String IDENTIFIER = "XpCrafting";

	private static final String TAG_XP_MINER = "XpMiner";

	private int xpMiner;

	@Override
	public void init(Entity entity, World world) {
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		compound.setInteger(TAG_XP_MINER, xpMiner);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		xpMiner = compound.getInteger(TAG_XP_MINER);
	}

	public int getXpMiner() {
		return xpMiner;
	}

	public void addXpMiner(int xpValue) {
		if (xpMiner + xpValue > xpMiner) {
			xpMiner += xpValue;
		}
	}
}
