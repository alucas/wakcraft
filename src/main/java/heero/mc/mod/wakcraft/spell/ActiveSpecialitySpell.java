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

public class ActiveSpecialitySpell extends Item implements IActiveSpell {
    private final ISpell spell;

    private final List<IEffect> effects;
    private final List<IEffect> effectsCritical;
    private final List<ICondition> conditions;

    private final int actionCost;
    private final int movementCost;
    private final int wakfuCost;

    private int rangeMinBase;
    private float rangeMinFactor;
    private int rangeMaxBase;
    private float rangeMaxFactor;
    private boolean isRangeViewRequired;
    private boolean isRangeModifiable;
    private IRangeMode rangeMode;

    private final IEffectArea displayArea;

    /**
     * Main constructor.
     *
     * @param name        The name of the spell.
     * @param displayArea The IEffectArea to display.
     */
    public ActiveSpecialitySpell(final String name, final IEffectArea displayArea) {
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
        this.isRangeModifiable = false;
        this.isRangeViewRequired = true;
        this.rangeMode = RangeMode.DEFAULT;

        this.displayArea = displayArea;

        setCreativeTab(WCreativeTabs.tabSpells);
        setUnlocalizedName(Reference.MODID + "_" + name);
    }

    /**
     * Constructor with default IEffectArea : POINT
     *
     * @param name The name of the spell.
     */
    public ActiveSpecialitySpell(final String name) {
        this(name, EffectArea.POINT);
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
     * Set the range of the spell.
     *
     * @param rangeMinMin         The minimal range at level 0.
     * @param rangeMinMax         The minimal range at level 9.
     * @param rangeMaxMin         The maximal range at level 0.
     * @param rangeMaxMax         The maximal range at level 9.
     * @param isRangeViewRequired True if the spell can be launch behind obstacle.
     * @param isRangeModifiable   True if the range can be modified.
     * @param rangeMode           The range mode. LINE, DIAGONALE, DEFAULT, ...
     * @return This instance.
     */
    public ActiveSpecialitySpell setRange(final int rangeMinMin, final int rangeMinMax, final int rangeMaxMin, final int rangeMaxMax, final boolean isRangeViewRequired, final boolean isRangeModifiable, final IRangeMode rangeMode) {
        this.rangeMinBase = rangeMinMin;
        this.rangeMinFactor = (rangeMinMax - rangeMinMin) / 9.0F;
        this.rangeMaxBase = rangeMaxMin;
        this.rangeMaxFactor = (rangeMaxMax - rangeMaxMax) / 9.0F;

        this.isRangeViewRequired = isRangeViewRequired;
        this.isRangeModifiable = isRangeModifiable;
        this.rangeMode = rangeMode;

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