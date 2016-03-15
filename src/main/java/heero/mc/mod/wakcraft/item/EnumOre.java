package heero.mc.mod.wakcraft.item;

import com.google.common.base.Predicate;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;

public enum EnumOre implements IStringSerializable {
    // Do not reorganize!
    // Name, Level, Red, Green, Blue
    PRIMITIVE_IRON("primitive_iron", 0, 0xA0, 0xA8, 0xB2),
    FLAT_TIN("flat_tin", 0, 0xA0, 0xB2, 0xB2),
    FINEST_SEA_SALT("finest_sea_salt", 10, 0xEA, 0xF2, 0xEF),
    CLASSIC_COAL("classic_coal", 15, 0x33, 0x33, 0x33),
    BRIGHT_COPPER("bright_copper", 20, 0xED, 0xC8, 0x44),
    SHADOWY_COBALT("shadowy_cobalt", 25, 0x8C, 0xA5, 0xA5),
    BRONZE_NUGGET("bronze_nugget", 30, 0xE0, 0xCC, 0x8E),
    SHARD_OF_FLINT("shard_of_flint", 35, 0xE0, 0xCC, 0x8E),
    GRAINY_MANGANESE("grainy_manganese", 40, 0xA0, 0xB2, 0xB2),
    DARK_CARBON("dark_carbon", 45, 0xA0, 0xB2, 0xB2),
    WHOLESOME_ZINC("wholesome_zinc", 50, 0xEA, 0xF2, 0xEF),
    RUGGED_QUARTZ("rugged_quartz", 55, 0x33, 0x33, 0x33),
    HAZY_LEAD("hazy_lead", 60, 0xED, 0xC8, 0x44),
    SILVER("silver", 65, 0x8C, 0xA5, 0xA5),
    SILVER2("silver_2", 70, 0xE0, 0xCC, 0x8E),
    ROYAL_BAUXITE("royal_bauxite", 70, 0xE0, 0xCC, 0x8E),
    ROYAL_BAUXITE2("royal_bauxite_2", 75, 0xA0, 0xB2, 0xB2),
    SOVEREIGN_TITANIUM("sovereign_titanium", 75, 0xA0, 0xB2, 0xB2),
    SOVEREIGN_TITANIUM2("sovereign_titanium_2", 80, 0xEA, 0xF2, 0xEF),
    GRIEVOUS_KROOMIUM("grievous_kroomium", 80, 0x33, 0x33, 0x33),
    GRIEVOUS_KROOMIUM2("grievous_kroomium_2", 85, 0xED, 0xC8, 0x44),
    BLOOD_RED_AMETHYST("blood_red_amethyst", 85, 0x8C, 0xA5, 0xA5),
    BLOOD_RED_AMETHYST2("blood_red_amethyst_2", 90, 0xE0, 0xCC, 0x8E),
    DOUBLE_CARAT_SAPPHIRE("double_carat_sapphire", 90, 0xE0, 0xCC, 0x8E),
    DOUBLE_CARAT_SAPPHIRE2("double_carat_sapphire_2", 95, 0xA0, 0xB2, 0xB2),
    TAROUDIUM("taroudium", 90, 0xA0, 0xB2, 0xB2),
    BLACK_GOLD_ORE("black_gold_ore", 100, 0xEA, 0xF2, 0xEF),
    MYTHWIL("mythwil", 100, 0x33, 0x33, 0x33),
    SANDY_ORE("sandy_ore", 100, 0xED, 0xC8, 0x44);

    private final String name;
    private final int level;
    private final int color;

    private EnumOre(final String name, final int level, final int... rgb) {
        this.name = name;
        this.level = level;
        this.color = (rgb[0] << 16) + (rgb[1] << 8) + rgb[2];
    }

    @Override
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getColor() {
        return color;
    }

    public static class EnumOrePredicate implements Predicate<EnumOre> {
        protected final int index;

        public EnumOrePredicate(final int index) {
            this.index = index;
        }

        @Override
        public boolean apply(@Nullable EnumOre input) {
            return input != null && (input.ordinal() / 8) == index;
        }
    }
}
