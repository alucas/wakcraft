package heero.mc.mod.wakcraft.spell;

import net.minecraft.util.StatCollector;

public class Spell implements ISpell {
	private final String description;

	public Spell(final String name) {
		this.description = "spell." + name + ".description";
	}

	@Override
	public String getDescription() {
		return StatCollector.translateToLocal(description);
	}

	@Override
	public int getLevel(final int metadata) {
		return 0;
	}
}