package heero.mc.mod.wakcraft.spell;

import com.sun.imageio.plugins.common.I18N;

public class Spell implements ISpell {
	private final String description;

	public Spell(final String name) {
		this.description = "spell." + name + ".description";
	}

	@Override
	public String getDescription() {
		return I18N.getString(description);
	}

	@Override
	public int getLevel(final int metadata) {
		return 0;
	}
}