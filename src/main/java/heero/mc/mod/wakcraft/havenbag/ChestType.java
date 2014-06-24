package heero.mc.mod.wakcraft.havenbag;

import heero.mc.mod.wakcraft.WItems;
import heero.mc.mod.wakcraft.item.ItemIkiakit;

public enum ChestType {
	CHEST_NORMAL(14, null),
	CHEST_SMALL(14, WItems.ikiakitSmall),
	CHEST_ADVENTURER(21, WItems.ikiakitAdventurer),
	CHEST_KIT(21, WItems.ikiakitKit),
	CHEST_COLLECTOR(21, WItems.ikiakitCollector),
	CHEST_GOLDEN(28, WItems.ikiakitGolden),
	CHEST_EMERALD(28, WItems.ikiakitEmerald);

	public final int chestSize;
	public final ItemIkiakit ikiakit;

	private ChestType(int chestSize, ItemIkiakit ikiakit) {
		this.chestSize = chestSize;
		this.ikiakit = ikiakit;
	}

	public static ChestType getChestIdFromIkiakit(ItemIkiakit ikiakit) {
		for (ChestType chestType : ChestType.values()) {
			if (chestType.ikiakit != ikiakit) {
				continue;
			}

			return chestType;
		}

		return null;
	}
}
