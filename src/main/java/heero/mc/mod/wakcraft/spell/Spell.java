package heero.mc.mod.wakcraft.spell;

import net.minecraft.util.StatCollector;

/**
 * Default implementation of ISpell. Implement a spell with no effects.
 */
public class Spell implements ISpell {
    private final String description;

    /**
     * Main constructor.
     *
     * @param name The name of the spell.
     */
    public Spell(final String name) {
        this.description = "spell." + name + ".description";
    }

    @Override
    public String getDescription() {
        return StatCollector.translateToLocal(description);
    }

    @Override
    public int getLevel(final int xp) {
        return 0;
    }
}