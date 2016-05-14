package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemWArmor extends ItemWithLevel {
    public static enum TYPE {
        HELMET,
        AMULET,
        EPAULET,
        CHESTPLATE,
        CAPE,
        RING,
        BELT,
        BOOTS,
        WEAPON,
        PET;

        public ResourceLocation placeHolderResource;

        TYPE() {
            placeHolderResource = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/items/empty_armor_slot_" + toString().toLowerCase() + ".png");
        }
    }

    protected final TYPE type;
    protected final Map<Characteristic, Integer> characteristics;

    public ItemWArmor(TYPE type, int level) {
        super(level);

        this.type = type;
        this.characteristics = new HashMap<>();

        setCreativeTab(WCreativeTabs.tabCombat);
        setMaxStackSize(1);
    }

    public TYPE getArmorType() {
        return type;
    }

    public ItemWArmor setCharacteristic(final Characteristic ability, final int value) {
        characteristics.put(ability, value);

        return this;
    }

    public Integer getCharacteristic(final Characteristic ability) {
        return characteristics.get(ability);
    }

    public Set<Characteristic> getCharacteristics() {
        return characteristics.keySet();
    }

    /**
     * allows items to add custom lines of information to the mouseover
     * description
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedItemTooltips) {
        for (Characteristic characteristic : characteristics.keySet()) {
            list.add(StatCollector.translateToLocalFormatted("characteristic." + characteristic.toString(), characteristics.get(characteristic)));
        }
    }
}
