package heero.mc.mod.wakcraft.spell;

import net.minecraft.client.resources.I18n;

public class Spell implements ISpell {
	private final String description;

	public Spell(final String name) {
		this.description = "spell." + name + ".description";
	}

	private static final Object nullObject = new Object();
	@Override
	public String getDescription() {
		return I18n.format(description, nullObject);
	}

	@Override
	public int getLevel(final int metadata) {
		return 0;
	}
}