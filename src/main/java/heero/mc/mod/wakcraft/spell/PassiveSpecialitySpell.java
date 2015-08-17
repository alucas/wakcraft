package heero.mc.mod.wakcraft.spell;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.mc.mod.wakcraft.spell.effect.IEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Passive Specialty Spell implementation.
 */
public class PassiveSpecialitySpell extends Item implements IPassiveSpell {
	private final ISpell spell;

	private final List<IEffect> effects;

	/**
	 * Main constructor.
	 * @param name	The name of the spell.
	 */
	public PassiveSpecialitySpell(final String name) {
		this.spell = new Spell(name);

		this.effects = new ArrayList<>();

		setCreativeTab(WakcraftCreativeTabs.tabSpells);
		setUnlocalizedName(Reference.MODID + "_" + name);
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
	public int getLevel(final int metadata) {
		if (metadata > 20) {
			throw new IllegalArgumentException("The metadata of a Passive Speciality Spell must be lower than 21 (lvl = metadata)");
		}

		return metadata;
	}

	@Override
	public PassiveSpecialitySpell setEffect(final IEffect effect) {
		effects.add(effect);

		return this;
	}

	@Override
	public List<IEffect> getEffects() {
		return effects;
	}

	@Override
	public String getUnlocalizedName() {
		return "spell." + super.getUnlocalizedName().substring(Reference.MODID.length() + 6);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "spell." + super.getUnlocalizedName(stack).substring(Reference.MODID.length() + 6);
	}
}