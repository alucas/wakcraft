package heero.wakcraft.profession;

import heero.wakcraft.entity.property.XpProfessionProperty;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

public class ProfessionManager {
	public enum PROFESSION {
		HERBALIST, LUMBERJACK, MINER, FARMER, FISHERMAN, TRAPPER, CHEF, BAKER, LEATHER_DEALER, HANDYMAN, CLOSE_COMBAT, LONG_DISTANCE, AREA_OF_EFFECT, TAILOR, ARMORER, JEWELER,
	}

	public static void addXpFromBlock(EntityPlayer player,
			PROFESSION profession, Block block) {
		addXp(player, profession, 100);
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

		return -1;
	}
}
