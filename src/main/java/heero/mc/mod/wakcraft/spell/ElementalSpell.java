package heero.mc.mod.wakcraft.spell;

import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.mc.mod.wakcraft.spell.effect.IEffect;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Elemental spell implementation.
 */
public class ElementalSpell extends Item implements IActiveSpell {
	private final ISpell spell;

	private final List<IEffect> effects;
	private final List<IEffect> effectsCritical;
	private final List<ICondition> conditions;

	private final int actionCost;
	private final int movementCost;
	private final int wakfuCost;

	/**
	 * Main constructor.
	 * @param name	The name of the spell.
	 * @param actionCost	The action point cost of the spell.
	 * @param movementCost	The movement point cost of the spell.
	 * @param wakfuCost		The wakfu point cost of the spell.
	 */
	public ElementalSpell(final String name, final int actionCost, final int movementCost, final int wakfuCost) {
		this.spell = new Spell(name);

		this.effects = new ArrayList<>();
		this.effectsCritical = new ArrayList<>();
		this.conditions = new ArrayList<>();

		this.actionCost = actionCost;
		this.movementCost = movementCost;
		this.wakfuCost = wakfuCost;

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
		// metadata = xp
		return SpellUtil.getLevelFromXp(metadata);
	}

	@Override
	public ElementalSpell setEffect(final IEffect effect) {
		effects.add(effect);

		return this;
	}

	@Override
	public List<IEffect> getEffects() {
		return effects;
	}

	@Override
	public ElementalSpell setEffectCritical(final IEffect effect) {
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
	public String getUnlocalizedName() {
		return "spell." + super.getUnlocalizedName().substring(5);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "spell." + super.getUnlocalizedName(stack).substring(5);
	}
}