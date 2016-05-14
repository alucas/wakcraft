package heero.mc.mod.wakcraft.spell;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.spell.effect.EffectArea;
import heero.mc.mod.wakcraft.spell.effect.IEffect;
import heero.mc.mod.wakcraft.spell.effect.IEffectArea;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

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

    private int rangeMin;
    private int rangeMax;
    private boolean isRangeViewRequired;
    private boolean isRangeModifiable;
    private IRangeMode rangeMode;

    private final IEffectArea displayArea;

    /**
     * Main constructor.
     *
     * @param name         The name of the spell.
     * @param actionCost   The action point cost of the spell.
     * @param movementCost The movement point cost of the spell.
     * @param wakfuCost    The wakfu point cost of the spell.
     * @param displayArea  The IEffectArea to display.
     */
    public ElementalSpell(final String name, final int actionCost, final int movementCost, final int wakfuCost, final IEffectArea displayArea) {
        this.spell = new Spell(name);

        this.effects = new ArrayList<>();
        this.effectsCritical = new ArrayList<>();
        this.conditions = new ArrayList<>();

        this.actionCost = actionCost;
        this.movementCost = movementCost;
        this.wakfuCost = wakfuCost;

        this.rangeMin = 0;
        this.rangeMax = 0;
        this.isRangeModifiable = false;
        this.isRangeViewRequired = true;
        this.rangeMode = RangeMode.DEFAULT;

        this.displayArea = displayArea;

        setCreativeTab(WCreativeTabs.tabSpells);
        setUnlocalizedName(Reference.MODID + "_" + name);
    }

    /**
     * Constructor with default IEffectArea : POINT.
     *
     * @param name         The name of the spell.
     * @param actionCost   The action point cost of the spell.
     * @param movementCost The movement point cost of the spell.
     * @param wakfuCost    The wakfu point cost of the spell.
     */
    public ElementalSpell(final String name, final int actionCost, final int movementCost, final int wakfuCost) {
        this(name, actionCost, movementCost, wakfuCost, EffectArea.POINT);
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
    public int getLevel(final int xp) {
        return SpellUtil.getLevelFromXp(xp);
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
    public int getRangeMax(final int spellLevel) {
        return rangeMax;
    }

    @Override
    public int getRangeMin(final int spellLevel) {
        return rangeMin;
    }

    @Override
    public boolean isRangeModifiable() {
        return isRangeModifiable;
    }

    @Override
    public boolean isRangeViewRequired() {
        return isRangeViewRequired;
    }

    @Override
    public IRangeMode getRangeMode() {
        return rangeMode;
    }

    /**
     * Initialize the range of the spell.
     *
     * @param rangeMin            The minimal range.
     * @param rangeMax            The maximal range.
     * @param isRangeViewRequired True if the spell can be launch behind obstacle.
     * @param isRangeModifiable   True if the range can be modified.
     * @param rangeMode           The range mode. LINE, DIAGONALE, DEFAULT, ...
     * @return This instance.
     */
    public ElementalSpell setRange(final int rangeMin, final int rangeMax, final boolean isRangeViewRequired, final boolean isRangeModifiable, final IRangeMode rangeMode) {
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;

        this.isRangeViewRequired = isRangeViewRequired;
        this.isRangeModifiable = isRangeModifiable;
        this.rangeMode = rangeMode;

        return this;
    }

    /**
     * Initialize the range of the spell.
     *
     * @param rangeMin The minimal range.
     * @param rangeMax The maximal range.
     * @return This instance.
     */
    public ElementalSpell setRange(final int rangeMin, final int rangeMax) {
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;

        return this;
    }

    @Override
    public String getUnlocalizedName() {
        return "spell." + super.getUnlocalizedName().substring(Reference.MODID.length() + 6);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "spell." + super.getUnlocalizedName(stack).substring(Reference.MODID.length() + 6);
    }

    @Override
    public IEffectArea getDisplayEffectArea() {
        return displayArea;
    }
}