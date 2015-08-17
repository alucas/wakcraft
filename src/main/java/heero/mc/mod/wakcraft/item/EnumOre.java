package heero.mc.mod.wakcraft.item;

import net.minecraft.util.IStringSerializable;

public enum EnumOre implements IStringSerializable {
    PRIMITIVE_IRON("PrimitiveIron", 0, 0.63F, 0.66F, 0.70F),
    FLAT_TIN("FlatTin", 0, 0.63F, 0.66F, 0.70F),
    FINEST_SEA_SALT("FinestSeaSalt", 10, 0.92F, 0.95F, 0.94F),
    CLASSIC_COAL("ClassicCoal", 15, 0.2F, 0.2F, 0.2F),
    BRIGHT_COPPER("BrightCopper", 20, 0.93F, 0.78F, 0.27F),
    SHADOWY_COBALT("ShadowyCobal", 25, 0.55F, 0.65F, 0.65F),
    BRONZE_NUGGET("BronzeNugget", 30, 0.88F, 0.8F, 0.56F),
    SHARD_OF_LIGHT("ShardOfLight", 35, 0.88F, 0.8F, 0.56F),
    GRAINY_MANGANESE("GrainyManganese", 40, 0.63F, 0.66F, 0.70F),
    DARK_CARBON("DarkCarbon", 45, 0.63F, 0.66F, 0.70F),
    WHOLESOME_ZINC("WholesomeZinc", 50, 0.92F, 0.95F, 0.94F),
    RUGGED_QUARTZ("RuggedQuartz", 55, 0.2F, 0.2F, 0.2F),
    HAZY_LEAD("HazyLead", 60, 0.93F, 0.78F, 0.27F),
    SILVER("Silver", 65, 0.55F, 0.65F, 0.65F),
    SILVER2("Silver2", 70, 0.88F, 0.8F, 0.56F),
    ROYAL_BAUXITE("RoyalBauxite", 70, 0.88F, 0.8F, 0.56F),
    ROYAL_BAUXITE2("RoyalBauxite2", 75, 0.63F, 0.66F, 0.70F),
    SOVEREIGN_TITANIUM("SovereignTitanium", 75, 0.63F, 0.66F, 0.70F),
    SOVEREIGN_TITANIUM2("SovereignTitanium2", 80, 0.92F, 0.95F, 0.94F),
    GRIEVOUS_KROOMIUM("GrievousKroomium", 80, 0.2F, 0.2F, 0.2F),
    GRIEVOUS_KROOMIUM2("GrievousKroomium2", 85, 0.93F, 0.78F, 0.27F),
    BLOOD_RED_AMETHYST("BloodRedAmethyst", 85, 0.55F, 0.65F, 0.65F),
    BLOOD_RED_AMETHYST2("BloodRedAmethyst2", 90, 0.88F, 0.8F, 0.56F),
    DOUBLE_CARAT_SAPPHIRE("DoubleCaratSapphire", 90, 0.88F, 0.8F, 0.56F),
    DOUBLE_CARAT_SAPPHIRE2("DoubleCaratSapphire2", 95, 0.63F, 0.66F, 0.70F),
    TAROUDIUM("Taroudium", 90, 0.63F, 0.66F, 0.70F),
    BLACK_GOLD_ORE("BlackGoldOre", 100, 0.92F, 0.95F, 0.94F),
    MYTHWIL("Mythwil", 100, 0.2F, 0.2F, 0.2F),
    SANDY_ORE("SandyOre", 100, 0.93F, 0.78F, 0.27F);

    private final String name;
    private final int level;
    private final float[] color;

    private EnumOre(final String name, final int level, final float... color) {
        this.name = name;
        this.level = level;
        this.color = color;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public float[] getColor() {
        return color;
    }
}
