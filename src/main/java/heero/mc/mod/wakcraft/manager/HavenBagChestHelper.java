package heero.mc.mod.wakcraft.manager;

import heero.mc.mod.wakcraft.WItems;
import heero.mc.mod.wakcraft.item.ItemIkiakit;

import java.util.Arrays;

import net.minecraft.item.Item;

public class HavenBagChestHelper {
	public static enum ChestType {
		CHEST_NORMAL, CHEST_SMALL, CHEST_ADVENTURER, CHEST_KIT, CHEST_COLLECTOR, CHEST_GOLDEN, CHEST_EMERALD
	};

	private static final int[] CHESTS_SIZES = new int[] { 14, 14, 21, 21, 21,
			28, 28 };
	private static final Item[] CHESTS_IKIAKIT = new Item[] { null,
			WItems.smallikiakit, WItems.adventurerikiakit,
			WItems.kitikiakit, WItems.collectorikiakit,
			WItems.goldenikiakit, WItems.emeraldikiakit };

	public static int getChestSize(ChestType chestId) {
		return CHESTS_SIZES[chestId.ordinal()];
	}

	public static Item getChestIkiakit(ChestType chestId) {
		return CHESTS_IKIAKIT[chestId.ordinal()];
	}

	public static ChestType getChestIdFromIkiakit(Item ikiakit) {
		if (ikiakit instanceof ItemIkiakit) {
			int index = Arrays.asList(CHESTS_IKIAKIT).indexOf(ikiakit);
			if (index < 0 || index >= ChestType.values().length) {
				return null;
			}

			return ChestType.values()[index];
		}

		return null;
	}
}
