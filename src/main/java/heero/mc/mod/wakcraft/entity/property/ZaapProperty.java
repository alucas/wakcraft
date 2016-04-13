package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.*;

public class ZaapProperty implements IExtendedEntityProperties, ISynchProperties {
    public static final String IDENTIFIER = Reference.MODID + "Zaaps";

    private static final String TAG_ZAAPS_BY_DIMENSION_ID = "ZaapsByDimensionId";
    private static final String TAG_DIMENSION_ID = "DimensionId";
    private static final String TAG_ZAAPS = "Zaaps";
    private static final String TAG_POS_X = "PosX";
    private static final String TAG_POS_Y = "PosY";
    private static final String TAG_POS_Z = "PosZ";

    private Map<Integer, Set<BlockPos>> zaapsByDimensionId;

    @Override
    public void init(Entity entity, World world) {
        zaapsByDimensionId = new HashMap<>();
    }

    @Override
    public void saveNBTData(NBTTagCompound tagRoot) {
        final NBTTagList tagZaapsByDimensionId = new NBTTagList();

        for (Map.Entry<Integer, Set<BlockPos>> zaaps : zaapsByDimensionId.entrySet()) {
            final NBTTagCompound tagZaapsForDimensionId = new NBTTagCompound();

            final NBTTagList tagZaaps = new NBTTagList();
            for (BlockPos zaap : zaaps.getValue()) {
                final NBTTagCompound tagZaap = new NBTTagCompound();
                tagZaap.setInteger(TAG_POS_X, zaap.getX());
                tagZaap.setInteger(TAG_POS_Y, zaap.getY());
                tagZaap.setInteger(TAG_POS_Z, zaap.getZ());

                tagZaaps.appendTag(tagZaap);
            }

            tagZaapsForDimensionId.setInteger(TAG_DIMENSION_ID, zaaps.getKey());
            tagZaapsForDimensionId.setTag(TAG_ZAAPS, tagZaaps);
            tagZaapsByDimensionId.appendTag(tagZaapsForDimensionId);
        }

        tagRoot.setTag(TAG_ZAAPS_BY_DIMENSION_ID, tagZaapsByDimensionId);
    }

    @Override
    public void loadNBTData(NBTTagCompound tagRoot) {
        final NBTTagList tagZaapsByDimensionId = tagRoot.getTagList(TAG_ZAAPS_BY_DIMENSION_ID, 10);

        for (int i = 0; i < tagZaapsByDimensionId.tagCount(); i++) {
            final NBTTagCompound tagZaapsForDimensionId = tagZaapsByDimensionId.getCompoundTagAt(i);

            final int dimensionId = tagZaapsForDimensionId.getInteger(TAG_DIMENSION_ID);
            final NBTTagList tagZaaps = tagZaapsForDimensionId.getTagList(TAG_ZAAPS, 10);

            final Set<BlockPos> zaaps = Collections.synchronizedSet(new TreeSet<BlockPos>());
            for (int j = 0; j < tagZaaps.tagCount(); j++) {
                final NBTTagCompound tagZaapPos = tagZaaps.getCompoundTagAt(j);
                final int posX = tagZaapPos.getInteger(TAG_POS_X);
                final int posY = tagZaapPos.getInteger(TAG_POS_Y);
                final int posZ = tagZaapPos.getInteger(TAG_POS_Z);

                zaaps.add(new BlockPos(posX, posY, posZ));
            }

            zaapsByDimensionId.put(dimensionId, zaaps);
        }
    }

    @Override
    public NBTTagCompound getClientPacket() {
        NBTTagCompound tagRoot = new NBTTagCompound();

        saveNBTData(tagRoot);

        return tagRoot;
    }

    @Override
    public void onClientPacket(NBTTagCompound tagRoot) {
        loadNBTData(tagRoot);
    }

    public void addZaap(final int dimensionId, final BlockPos pos) {
        Set<BlockPos> zaaps = zaapsByDimensionId.get(dimensionId);
        if (zaaps == null) {
            zaaps = Collections.synchronizedSet(new TreeSet<BlockPos>());
        }

        zaaps.add(pos);
        zaapsByDimensionId.put(dimensionId, zaaps);
    }

    public Set<BlockPos> getZaaps(final int dimensionId) {
        return zaapsByDimensionId.get(dimensionId);
    }

    public void setZaaps(final int dimensionId, final Set<BlockPos> zaaps) {
        zaapsByDimensionId.put(dimensionId, zaaps);
    }

    public void clear() {
        zaapsByDimensionId.clear();
    }
}
