package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class CharacterProperty implements IExtendedEntityProperties, ISynchProperties {
    public static final String IDENTIFIER = Reference.MODID + "Character";

    protected static String TAG_CLASS = "WClass";
    protected static String TAG_LEVEL = "WLevel";

    public static enum CLASS {
        DISEMBODIED, FECA, OSAMODAS, ENUTROF, SRAM, XELOR, ECAFLIP, ENIRIPSA, IOP, CRA, SADIDA, SACRIER, PANDAWA, ROGUE, MASQUERAIDER, FOGGERNAUT;
    }

    protected CLASS characterClass;
    protected int characterLevel;

    @Override
    public void init(Entity entity, World world) {
        characterClass = CLASS.DISEMBODIED;
        characterLevel = 0;
    }

    @Override
    public void saveNBTData(NBTTagCompound tagRoot) {
        tagRoot.setInteger(TAG_CLASS, characterClass.ordinal());
        tagRoot.setInteger(TAG_LEVEL, characterLevel);
    }

    @Override
    public void loadNBTData(NBTTagCompound tagRoot) {
        characterClass = CLASS.values()[tagRoot.getInteger(TAG_CLASS)];
        characterLevel = tagRoot.getInteger(TAG_LEVEL);
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

    public void setCharacterClass(CLASS characterClass) {
        this.characterClass = characterClass;
        this.characterLevel = 0;
    }

    public CLASS getCharacterClass() {
        return characterClass;
    }
}
