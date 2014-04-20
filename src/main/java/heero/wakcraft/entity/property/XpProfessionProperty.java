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
	private static final String TAG_XP_HANDYMAN = "XpHandyman";
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
		compound.setInteger(TAG_XP_HERBALIST, xpHerbalist);
		compound.setInteger(TAG_XP_LUMBERJACK, xpLumberjack);
		compound.setInteger(TAG_XP_MINER, xpMiner);
		compound.setInteger(TAG_XP_FARMER, xpFarmer);
		compound.setInteger(TAG_XP_FISHERMAN, xpFisherman);
		compound.setInteger(TAG_XP_TRAPPER, xpTrapper);
		compound.setInteger(TAG_XP_CHEF, xpChef);
		compound.setInteger(TAG_XP_BAKER, xpBaker);
		compound.setInteger(TAG_XP_LEATHER_DEALER, xpLeatherDealer);
		compound.setInteger(TAG_XP_HANDYMAN, xpHandyman);
		compound.setInteger(TAG_XP_CLOSE_COMBAT, xpCloseCombat);
		compound.setInteger(TAG_XP_LONG_DISTANCE, xpLongDistance);
		compound.setInteger(TAG_XP_AREA_OF_EFFECT, xpAreaOfEffect);
		compound.setInteger(TAG_XP_TAILOR, xpTailor);
		compound.setInteger(TAG_XP_ARMORER, xpArmorer);
		compound.setInteger(TAG_XP_JEWELER, xpJeweler);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		xpHerbalist = compound.getInteger(TAG_XP_HERBALIST);
		xpLumberjack = compound.getInteger(TAG_XP_LUMBERJACK);
		xpMiner = compound.getInteger(TAG_XP_MINER);
		xpFarmer = compound.getInteger(TAG_XP_FARMER);
		xpFisherman = compound.getInteger(TAG_XP_FISHERMAN);
		xpTrapper = compound.getInteger(TAG_XP_TRAPPER);
		xpChef = compound.getInteger(TAG_XP_CHEF);
		xpBaker = compound.getInteger(TAG_XP_BAKER);
		xpLeatherDealer = compound.getInteger(TAG_XP_LEATHER_DEALER);
		xpHandyman = compound.getInteger(TAG_XP_HANDYMAN);
		xpCloseCombat = compound.getInteger(TAG_XP_CLOSE_COMBAT);
		xpLongDistance = compound.getInteger(TAG_XP_LONG_DISTANCE);
		xpAreaOfEffect = compound.getInteger(TAG_XP_AREA_OF_EFFECT);
		xpTailor = compound.getInteger(TAG_XP_TAILOR);
		xpArmorer = compound.getInteger(TAG_XP_ARMORER);
		xpJeweler = compound.getInteger(TAG_XP_JEWELER);
	}
}
