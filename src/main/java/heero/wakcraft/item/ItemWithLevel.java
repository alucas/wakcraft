package heero.wakcraft.item;

import net.minecraft.item.Item;

public class ItemWithLevel extends Item {
	protected int level;

	public ItemWithLevel(int level) {
		super();

		this.level = level;
	}

	public int getLevel(int metadata) {
		return level;
	}
}
