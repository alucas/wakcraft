package heero.mc.mod.wakcraft.spell;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.mc.mod.wakcraft.spell.effect.IEffect;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ActiveSpecialitySpell extends Item implements IActiveSpell {
	private final ISpell spell;

	private final List<IEffect> effects;
	private final List<IEffect> effectsCritical;
	private final List<ICondition> conditions;

	private final int actionCost;
	private final int movementCost;
	private final int wakfuCost;

	public ActiveSpecialitySpell(final String name) {
		this.spell = new Spell(name);

		this.effects = new ArrayList<>();
		this.effectsCritical = new ArrayList<>();
		this.conditions = new ArrayList<>();

		this.actionCost = 0;
		this.movementCost = 0;
		this.wakfuCost = 0;

		setCreativeTab(WakcraftCreativeTabs.tabSpells);
		setUnlocalizedName("Spell" + name);
		setTextureName(WInfo.MODID.toLowerCase() + ":spells/" + name.toLowerCase());
	}

	@Override
	public int getItemStackLimit(final ItemStack stack) {
		return 1;
	}

	@Override
	public String getDescription() {
		return spell.getDescription();
	}

	@Override
	public int getLevel(int metadata) {
		if (metadata > 9) {
			throw new IllegalArgumentException("The metadata of a Passive Speciality Spell must be lower than 10 (lvl = metadata)");
		}

		return metadata;
	}

	@Override
	public ActiveSpecialitySpell setEffect(final IEffect effect) {
		effects.add(effect);

		return this;
	}

	@Override
	public List<IEffect> getEffects() {
		return effects;
	}

	@Override
	public ActiveSpecialitySpell setEffectCritical(final IEffect effect) {
		effectsCritical.add(effect);

		return this;
	}

	@Override
	public List<IEffect> getEffectsCritical() {
		return effectsCritical;
	}

	@Override
	public ISpell setCondition(ICondition condition) {
		conditions.add(condition);

		return this;
	}

	@Override
	public List<ICondition> getCondition() {
		return conditions;
	}

	@Override
	public int getActionCost() {
		return actionCost;
	}

	@Override
	public int getMovementCost() {
		return movementCost;
	}

	@Override
	public int getWakfuCost() {
		return wakfuCost;
	}
}