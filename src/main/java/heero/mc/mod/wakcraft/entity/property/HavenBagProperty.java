package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class HavenBagProperty implements IExtendedEntityProperties, ISynchProperties {
    public static final String IDENTIFIER = Reference.MODID + "HavenBag";

    private static final String TAG_HAVENBAG = "HavenBag";
    private static final String TAG_PLAYER_HAVENBAG_ID = "PlayerHavenBagId";
    private static final String TAG_CURRENT_HAVENBAG_ID = "CurrentHavenBagId";
    private static final String TAG_CURRENT_HAVENBAG_POS_X = "CurrentHavenBagPosX";
    private static final String TAG_CURRENT_HAVENBAG_POS_Y = "CurrentHavenBagPosY";
    private static final String TAG_CURRENT_HAVENBAG_POS_Z = "CurrentHavenBagPosZ";
    private static final String TAG_OLD_PLAYER_POS_X = "OldPlayerPosX";
    private static final String TAG_OLD_PLAYER_POS_Y = "OldPlayerPosY";
    private static final String TAG_OLD_PLAYER_POS_Z = "OldPlayerPosZ";

    // Unique Identifier used to know which haven bag belong to the player (0 = no haven bag attributed yet)
    private int playerHavenBagId;
    // The uid of the haven bag the player is currently in (0 = not in an haven bag)
    private int currentHavenBagId;
    // The current havenbag position
    private int currentHavenBagPosX;
    private int currentHavenBagPosY;
    private int currentHavenBagPosZ;
    // Coordinates of the player before he was teleport to the haven bag
    private double oldPlayerPosX;
    private double oldPlayerPosY;
    private double oldPlayerPosZ;

    @Override
    public void init(Entity entity, World world) {
        playerHavenBagId = -1;
        currentHavenBagId = -1;
        oldPlayerPosX = 0;
        oldPlayerPosY = 0;
        oldPlayerPosZ = 0;
    }

    @Override
    public void saveNBTData(NBTTagCompound tagRoot) {
        NBTTagCompound tagHavenBag = new NBTTagCompound();

        tagHavenBag.setInteger(TAG_PLAYER_HAVENBAG_ID, playerHavenBagId);
        tagHavenBag.setInteger(TAG_CURRENT_HAVENBAG_ID, currentHavenBagId);
        tagHavenBag.setInteger(TAG_CURRENT_HAVENBAG_POS_X, currentHavenBagPosX);
        tagHavenBag.setInteger(TAG_CURRENT_HAVENBAG_POS_Y, currentHavenBagPosY);
        tagHavenBag.setInteger(TAG_CURRENT_HAVENBAG_POS_Z, currentHavenBagPosZ);
        tagHavenBag.setDouble(TAG_OLD_PLAYER_POS_X, oldPlayerPosX);
        tagHavenBag.setDouble(TAG_OLD_PLAYER_POS_Y, oldPlayerPosY);
        tagHavenBag.setDouble(TAG_OLD_PLAYER_POS_Z, oldPlayerPosZ);

        tagRoot.setTag(TAG_HAVENBAG, tagHavenBag);
    }

    @Override
    public void loadNBTData(NBTTagCompound tagRoot) {
        NBTTagCompound tagHavenBag = tagRoot.getCompoundTag(TAG_HAVENBAG);

        playerHavenBagId = tagHavenBag.getInteger(TAG_PLAYER_HAVENBAG_ID);
        currentHavenBagId = tagHavenBag.getInteger(TAG_CURRENT_HAVENBAG_ID);
        currentHavenBagPosX = tagHavenBag.getInteger(TAG_CURRENT_HAVENBAG_POS_X);
        currentHavenBagPosY = tagHavenBag.getInteger(TAG_CURRENT_HAVENBAG_POS_Y);
        currentHavenBagPosZ = tagHavenBag.getInteger(TAG_CURRENT_HAVENBAG_POS_Z);
        oldPlayerPosX = tagHavenBag.getDouble(TAG_OLD_PLAYER_POS_X);
        oldPlayerPosY = tagHavenBag.getDouble(TAG_OLD_PLAYER_POS_Y);
        oldPlayerPosZ = tagHavenBag.getDouble(TAG_OLD_PLAYER_POS_Z);
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

    public void setEnterHavenBag(final double oldPlayerPosX, final double oldPlayerPosY, final double oldPlayerPosZ, final int currentHavenBagId, final BlockPos havenbagPos) {
        this.oldPlayerPosX = oldPlayerPosX;
        this.oldPlayerPosY = oldPlayerPosY;
        this.oldPlayerPosZ = oldPlayerPosZ;
        this.currentHavenBagId = currentHavenBagId;
        this.currentHavenBagPosX = havenbagPos.getX();
        this.currentHavenBagPosY = havenbagPos.getY();
        this.currentHavenBagPosZ = havenbagPos.getZ();
    }

    public void setLeaveHavenBag() {
        currentHavenBagId = -1;
        currentHavenBagPosX = 0;
        currentHavenBagPosY = 100;
        currentHavenBagPosZ = 0;
        oldPlayerPosX = 0;
        oldPlayerPosY = 100;
        oldPlayerPosZ = 0;
    }

    public BlockPos getCurrentHavenBagPos() {
        return new BlockPos(currentHavenBagPosX, currentHavenBagPosY, currentHavenBagPosZ);
    }

    public double[] getOldPlayerPos() {
        return new double[]{oldPlayerPosX, oldPlayerPosY, oldPlayerPosZ};
    }

    public boolean isInHavenBag() {
        return currentHavenBagId != -1;
    }

    public int getCurrentHavenBagId() {
        return currentHavenBagId;
    }

    public int getPlayerHavenBagId() {
        return playerHavenBagId;
    }

    public void setPlayerHavenBagId(int uid) {
        this.playerHavenBagId = uid;
    }

    public boolean isInitialized() {
        return playerHavenBagId != -1;
    }
}
