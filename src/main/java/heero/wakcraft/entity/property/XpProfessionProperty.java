package heero.wakcraft.entity.property;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class XpProfessionProperty implements IExtendedEntityProperties {
	public static final String IDENTIFIER = "XpCrafting";

	private static final String TAG_XP_HERBALIST = "XpHerbalist";
	private static final String TAG_XP_LUMBERJACK = "XpLumberjack";
	private static final String TAG_XP_MINER = "XpMiner";
	private static final String TAG_XP_FARMER = "XpFarmer";
	private static final String TAG_XP_FISHERMAN = "XpFisherman";
	private static final String TAG_XP_TRAPPER = "XpTrapper";
	private static final String TAG_XP_CHEF = "XpChef";
	private static final String TAG_XP_BAKER = "XpBaker";
	private static final String TAG_XP_LEATHER_DEALER = "XpLeatherDealer";
	private static final String TAG_XP_HANDYMAN= "XpHandyman";
	private static final String TAG_XP_CLOSE_COMBAT = "XpCloseCombat";
	private static final String TAG_XP_LONG_DISTANCE = "XpLongDistance";
	private static final String TAG_XP_AREA_OF_EFFECT = "XpAreaOfEffect";
	private static final String TAG_XP_TAILOR = "XpTailor";
	private static final String TAG_XP_ARMORER = "XpArmorer";
	private static final String TAG_XP_JEWELER = "XpJeweler";

	public int xpHerbalist;
	public int xpLumberjack;
	public int xpMiner;
	public int xpFarmer;
	public int xpFisherman;
	public int xpTrapper;
	public int xpChef;
	public int xpBaker;
	public int xpLeatherDealer;
	public int xpHandyman;
	public int xpCloseCombat;
	public int xpLongDistance;
	public int xpAreaOfEffect;
	public int xpTailor;
	public int xpArmorer;
	public int xpJeweler;

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
}
