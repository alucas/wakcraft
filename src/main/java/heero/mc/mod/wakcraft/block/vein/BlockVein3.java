package heero.mc.mod.wakcraft.block.vein;

import heero.mc.mod.wakcraft.WItems;
import heero.mc.mod.wakcraft.item.EnumOre;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockVein3 extends BlockVein {
    protected static final PropertyEnum<EnumExtractable> PROP_EXTRACTABLE = PropertyEnum.create("extractable", EnumExtractable.class);
    protected static final PropertyEnum<EnumOre> PROP_ORE = PropertyEnum.create("ore", EnumOre.class, new EnumOre.EnumOrePredicate(2));

    @Override
    public PropertyEnum<EnumExtractable> getPropExtractable() {
        return PROP_EXTRACTABLE;
    }

    @Override
    public PropertyEnum<EnumOre> getPropOre() {
        return PROP_ORE;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return WItems.ore2;
    }
}
