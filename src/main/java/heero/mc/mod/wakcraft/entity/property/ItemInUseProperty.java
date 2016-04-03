package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ItemInUseProperty implements IExtendedEntityProperties {
    public static final String IDENTIFIER = Reference.MODID + "ItemInUse";

    public BlockPos pos;

    @Override
    public void init(Entity entity, World world) {
    }

    @Override
    public void saveNBTData(NBTTagCompound tagRoot) {
    }

    @Override
    public void loadNBTData(NBTTagCompound tagRoot) {
    }
}
