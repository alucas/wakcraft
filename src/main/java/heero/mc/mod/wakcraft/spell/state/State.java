package heero.mc.mod.wakcraft.spell.state;

import heero.mc.mod.wakcraft.spell.effect.IEffect;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;

public enum State implements IState {
    AERIAL("Aerial", 2),
    DISORIENTED("Disoriented", 4),
    EXPLOSION("Explosion", 300),
    FLAMING("Flaming", 1),
    SCALDED("Scalded", 300),
    STUNNED("Stunned", 1);

    private final String name;
    private final int maxLevel;

    private final List<IEffect> effects;

    private State(final String name, final int maxLevel) {
        this.name = name;
        this.maxLevel = maxLevel;

        this.effects = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return StatCollector.translateToLocal("state." + name + ".description");
    }

    @Override
    public int getLevel(int metadata) {
        if (metadata >= maxLevel) {
            throw new IllegalArgumentException("Invalid metadata value. Must be between 0 and " + (maxLevel - 1));
        }

        // level = metadata + 1
        return metadata + 1;
    }

    @Override
    public IState setEffect(IEffect effect) {
        this.effects.add(effect);

        return this;
    }

    @Override
    public List<IEffect> getEffects() {
        return effects;
    }
}
