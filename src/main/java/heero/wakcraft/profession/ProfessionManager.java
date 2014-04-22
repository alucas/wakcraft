package heero.wakcraft.profession;

import heero.wakcraft.block.ILevelBlock;
import heero.wakcraft.entity.property.XpProfessionProperty;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ProfessionManager {
	public static enum PROFESSION {
		HERBALIST(0), LUMBERJACK(1), MINER(2), FARMER(3), FISHERMAN(4), TRAPPER(
				5), CHEF(6), BAKER(7), LEATHER_DEALER(8), HANDYMAN(9), CLOSE_COMBAT(
				10), LONG_DISTANCE(11), AREA_OF_EFFECT(12), TAILOR(13), ARMORER(
				14), JEWELER(15), KAMA_MINTER(16);

		private final int value;

		public static final PROFESSION craftings[] = new PROFESSION[] { CHEF,
				BAKER, LEATHER_DEALER, HANDYMAN, CLOSE_COMBAT, LONG_DISTANCE,
				AREA_OF_EFFECT, TAILOR, ARMORER, JEWELER, KAMA_MINTER };

		public static final PROFESSION harvestings[] = new PROFESSION[] {
				HERBALIST, LUMBERJACK, MINER, FARMER, FISHERMAN, TRAPPER };

		private PROFESSION(final int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}

		public static PROFESSION getProfession(int value) {
			for (PROFESSION profession : PROFESSION.values()) {
				if (profession.getValue() == value) {
					return profession;
				}
			}

			return null;
		}
	}

	private static final float[] xpFactor = new float[] { 0.99f, 0.98f, 0.95f,
			0.90f, 0.85f, 0.79f, 0.73f, 0.65f, 0.58f, 0.50f, 0.42f, 0.35f,
			0.27f, 0.21f, 0.15f, 0.10f, 0.05f, 0.02f, 0.01f };

	public static int addXpFromBlock(EntityPlayer player, World world, int x,
			int y, int z, PROFESSION profession) {
		Block block = world.getBlock(x, y, z);
		if (!(block instanceof ILevelBlock)) {
			return 0;
		}

		int metadata = world.getBlockMetadata(x, y, z);
		int blockLevel = ((ILevelBlock) block).getLevel(metadata);
		int blockProfessionXp = ((ILevelBlock) block).getProfessionExp(metadata);

		int playerProfessionLevel = getLevel(player, profession);
		int ponderedXp = getPonderedXp(playerProfessionLevel, blockLevel, blockProfessionXp);

		addXp(player, profession, ponderedXp);

		return ponderedXp;
	}

	public static void addXp(EntityPlayer player, PROFESSION profession,
			int xpValue) {
		XpProfessionProperty properties = (XpProfessionProperty) player
				.getExtendedProperties(XpProfessionProperty.IDENTIFIER);
		if (properties != null) {
			switch (profession) {
			case HERBALIST:
				properties.xpHerbalist += xpValue;
				break;
			case LUMBERJACK:
				properties.xpLumberjack += xpValue;
				break;
			case MINER:
				properties.xpMiner += xpValue;
				break;
			case FARMER:
				properties.xpFarmer += xpValue;
				break;
			case FISHERMAN:
				properties.xpFisherman += xpValue;
				break;
			case TRAPPER:
				properties.xpTrapper += xpValue;
				break;
			case CHEF:
				properties.xpChef += xpValue;
				break;
			case BAKER:
				properties.xpBaker += xpValue;
				break;
			case LEATHER_DEALER:
				properties.xpLeatherDealer += xpValue;
				break;
			case HANDYMAN:
				properties.xpHandyman += xpValue;
				break;
			case CLOSE_COMBAT:
				properties.xpCloseCombat += xpValue;
				break;
			case LONG_DISTANCE:
				properties.xpLongDistance += xpValue;
				break;
			case AREA_OF_EFFECT:
				properties.xpAreaOfEffect += xpValue;
				break;
			case TAILOR:
				properties.xpTailor += xpValue;
				break;
			case ARMORER:
				properties.xpArmorer += xpValue;
				break;
			case JEWELER:
				properties.xpJeweler += xpValue;
				break;
			default:
				break;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static void setXp(EntityPlayer player, PROFESSION profession,
			int xpValue) {
		XpProfessionProperty properties = (XpProfessionProperty) player
				.getExtendedProperties(XpProfessionProperty.IDENTIFIER);
		if (properties != null) {
			switch (profession) {
			case HERBALIST:
				properties.xpHerbalist = xpValue;
				break;
			case LUMBERJACK:
				properties.xpLumberjack = xpValue;
				break;
			case MINER:
				properties.xpMiner = xpValue;
				break;
			case FARMER:
				properties.xpFarmer = xpValue;
				break;
			case FISHERMAN:
				properties.xpFisherman = xpValue;
				break;
			case TRAPPER:
				properties.xpTrapper = xpValue;
				break;
			case CHEF:
				properties.xpChef = xpValue;
				break;
			case BAKER:
				properties.xpBaker = xpValue;
				break;
			case LEATHER_DEALER:
				properties.xpLeatherDealer = xpValue;
				break;
			case HANDYMAN:
				properties.xpHandyman = xpValue;
				break;
			case CLOSE_COMBAT:
				properties.xpCloseCombat = xpValue;
				break;
			case LONG_DISTANCE:
				properties.xpLongDistance = xpValue;
				break;
			case AREA_OF_EFFECT:
				properties.xpAreaOfEffect = xpValue;
				break;
			case TAILOR:
				properties.xpTailor = xpValue;
				break;
			case ARMORER:
				properties.xpArmorer = xpValue;
				break;
			case JEWELER:
				properties.xpJeweler = xpValue;
				break;
			default:
				break;
			}
		}
	}

	public static int getXp(EntityPlayer player, PROFESSION profession) {
		XpProfessionProperty properties = (XpProfessionProperty) player
				.getExtendedProperties(XpProfessionProperty.IDENTIFIER);
		if (properties != null) {
			switch (profession) {
			case HERBALIST:
				return properties.xpHerbalist;
			case LUMBERJACK:
				return properties.xpLumberjack;
			case MINER:
				return properties.xpMiner;
			case FARMER:
				return properties.xpFarmer;
			case FISHERMAN:
				return properties.xpFisherman;
			case TRAPPER:
				return properties.xpTrapper;
			case CHEF:
				return properties.xpChef;
			case BAKER:
				return properties.xpBaker;
			case LEATHER_DEALER:
				return properties.xpLeatherDealer;
			case HANDYMAN:
				return properties.xpHandyman;
			case CLOSE_COMBAT:
				return properties.xpCloseCombat;
			case LONG_DISTANCE:
				return properties.xpLongDistance;
			case AREA_OF_EFFECT:
				return properties.xpAreaOfEffect;
			case TAILOR:
				return properties.xpTailor;
			case ARMORER:
				return properties.xpArmorer;
			case JEWELER:
				return properties.xpJeweler;
			default:
				break;
			}
		}

		return 0;
	}
	
	public static int getLevel(EntityPlayer player, PROFESSION profession) {
		return getLevelFromXp(getXp(player, profession));
	}

	public static int getXpFromLevel(int level) {
		return (level * level) * 100;
	}

	public static int getLevelFromXp(int xp) {
		return (int) Math.floor(Math.sqrt(xp / 100));
	}

	private static int getPonderedXp(int professionLvl, int recipeLvl,
			int recipeXp) {
		return (int) ((professionLvl <= recipeLvl + 10) ? recipeXp
				: (professionLvl >= recipeLvl + 30) ? 0
						: recipeXp * xpFactor[professionLvl - (recipeLvl + 11)]);
	}
}
