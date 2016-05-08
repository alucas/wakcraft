package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class QuestProperty implements IExtendedEntityProperties {
    public static final String IDENTIFIER = Reference.MODID + "Quest";

    @Override
    public void saveNBTData(NBTTagCompound compound) {
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
    }

    @Override
    public void init(Entity entity, World world) {}
}
