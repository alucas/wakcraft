package heero.mc.mod.wakcraft.spell.state;

import heero.mc.mod.wakcraft.spell.effect.IEffect;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.StatCollector;

public class State implements IState {
	private String name;

	private List<IEffect> effects;

	public State(final String name) {
		this.name = name;

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
		// metadata = level
		return metadata;
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
