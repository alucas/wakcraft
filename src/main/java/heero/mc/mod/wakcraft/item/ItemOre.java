package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemOre extends ItemWithLevel {
    protected final int index;

    public ItemOre(final int index) {
        super(0);

        this.index = index;

        setCreativeTab(WCreativeTabs.tabResource);
        setUnlocalizedName(Reference.MODID.toLowerCase() + ".ore_");
        setHasSubtypes(true);
    }

    @SuppressWarnings({"unchecked"})
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        for (int i = 0; i < 16 && i < EnumOre.values().length - index * 16; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        return EnumOre.values()[stack.getItemDamage() + index * 16].getColor();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName() + EnumOre.values()[stack.getItemDamage() + index * 16].getName();
    }
}