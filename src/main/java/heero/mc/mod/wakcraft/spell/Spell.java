package heero.mc.mod.wakcraft.spell;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.mc.mod.wakcraft.spell.effect.IEffect;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Spell extends Item implements ISpell {
	private final List<IEffect> effects;
	private final List<IEffect> effectsCritical;

	public Spell(final String name) {
		this.effects = new ArrayList<>();
		this.effectsCritical = new ArrayList<>();

		setCreativeTab(WakcraftCreativeTabs.tabSpells);
		setUnlocalizedName("Spell" + name);
		setTextureName(WInfo.MODID.toLowerCase() + ":spells/" + name.toLowerCase());
	}

	@Override
	public int getItemStackLimit(final ItemStack stack) {
		return 1;
	}

	@Override
	public Spell setEffect(final IEffect effect) {
		effects.add(effect);

		return this;
	}

	@Override
	public List<IEffect> getEffects() {
		return effects;
	}

	@Override
	public Spell setEffectCritical(final IEffect effect) {
		effectsCritical.add(effect);

		return this;
	}

	@Override
	public List<IEffect> getEffectsCritical() {
		return effectsCritical;
	}
}