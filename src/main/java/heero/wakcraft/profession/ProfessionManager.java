package heero.wakcraft.profession;

import heero.wakcraft.block.ILevelBlock;
import heero.wakcraft.entity.property.ProfessionProperty;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

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
		int blockProfessionXp = ((ILevelBlock) block)
				.getProfessionExp(metadata);

		int playerProfessionLevel = getLevel(player, profession);
		int ponderedXp = getPonderedXp(playerProfessionLevel, blockLevel,
				blockProfessionXp);

		addXp(player, profession, ponderedXp);

		return ponderedXp;
	}

	public static void addXp(EntityPlayer player, PROFESSION profession,
			int xpValue) {
		setXp(player, profession, getXp(player, profession) + xpValue);
	}

	public static void setXp(EntityPlayer player, PROFESSION profession,
			int xpValue) {
		ProfessionProperty properties = (ProfessionProperty) player
				.getExtendedProperties(ProfessionProperty.IDENTIFIER);

		if (properties != null) {
			properties.setXp(profession, xpValue);
		}
	}

	public static int getXp(EntityPlayer player, PROFESSION profession) {
		ProfessionProperty properties = (ProfessionProperty) player
				.getExtendedProperties(ProfessionProperty.IDENTIFIER);

		if (properties != null) {
			return properties.getXp(profession);
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
				: (professionLvl >= recipeLvl + 30) ? 0 : recipeXp
						* xpFactor[professionLvl - (recipeLvl + 11)]);
	}
}
