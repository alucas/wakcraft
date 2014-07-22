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

	private int   rangeMinBase;
	private float rangeMinFactor;
	private int   rangeMaxBase;
	private float rangeMaxFactor;

	public ActiveSpecialitySpell(final String name) {
		this.spell = new Spell(name);

		this.effects = new ArrayList<>();
		this.effectsCritical = new ArrayList<>();
		this.conditions = new ArrayList<>();

		this.actionCost = 0;
		this.movementCost = 0;
		this.wakfuCost = 0;

		this.rangeMinBase = 0;
		this.rangeMinFactor = 0;
		this.rangeMaxBase = 0;
		this.rangeMaxFactor = 0;

		setCreativeTab(WakcraftCreativeTabs.tabSpells);
		setUnlocalizedName(name);
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
	public List<ICondition> getConditions() {
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

	@Override
	public int getRangeMax(int spellLevel) {
		return (int) (rangeMaxBase + rangeMaxFactor * spellLevel);
	}

	@Override
	public int getRangeMin(int spellLevel) {
		return (int) (rangeMinBase + rangeMinFactor * spellLevel);
	}

	/**
	 * Set the range of the spell.
	 * @param rangeMinMin	The minimal range at level 0.
	 * @param rangeMinMax	The minimal range at level 9.
	 * @param rangeMaxMin	The maximal range at level 0.
	 * @param rangeMaxMax	The maximal range at level 9.
	 * @return	This instance.
	 */
	public ActiveSpecialitySpell setRange(final int rangeMinMin, final int rangeMinMax, final int rangeMaxMin, final int rangeMaxMax) {
		this.rangeMinBase = rangeMinMin;
		this.rangeMinFactor = (rangeMinMax - rangeMinMin) / 9.0F;
		this.rangeMaxBase = rangeMaxMin;
		this.rangeMaxFactor = (rangeMaxMax - rangeMaxMax) / 9.0F;

		return this;
	}

	@Override
	public String getUnlocalizedName() {
		return "spell." + super.getUnlocalizedName().substring(5);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "spell." + super.getUnlocalizedName(stack).substring(5);
	}
}