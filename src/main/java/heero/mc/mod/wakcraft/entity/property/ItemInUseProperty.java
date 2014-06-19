package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.WInfo;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ItemInUseProperty implements IExtendedEntityProperties {
	public static final String IDENTIFIER = WInfo.MODID + "ItemInUse";

	public int posX;
	public int posY;
	public int posZ;

	@Override
	public void init(Entity entity, World world) {
		posX = 0;
		posY = 0;
		posZ = 0;
	}

	@Override
	public void saveNBTData(NBTTagCompound tagRoot) {
	}

	@Override
	public void loadNBTData(NBTTagCompound tagRoot) {
	}
}
