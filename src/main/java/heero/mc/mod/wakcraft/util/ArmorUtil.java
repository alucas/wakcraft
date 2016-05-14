package heero.mc.mod.wakcraft.util;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.entity.property.InventoryProperty;
import heero.mc.mod.wakcraft.item.ItemWArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ArmorUtil {
    public static boolean hasArmor(final Entity entity) {
        return (entity instanceof EntityPlayer);
    }

    protected static InventoryProperty getArmorProperty(final Entity entity) {
        final InventoryProperty properties = (InventoryProperty) entity.getExtendedProperties(InventoryProperty.IDENTIFIER);
        if (properties == null) {
            WLog.warning("Error while loading the player's armors properties (%s)", entity.getDisplayName());
            return null;
        }

        return properties;
    }

    public static Integer getArmorsCharacteristic(final Entity entity, final Characteristic characteristic) {
        final InventoryProperty properties = getArmorProperty(entity);
        if (properties == null) {
            return null;
        }

        Integer characteristicValue = 0;

        final IInventory inventory = properties.getArmorInventory();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            final ItemStack stack = inventory.getStackInSlot(i);
            if (stack == null || !(stack.getItem() instanceof ItemWArmor)) {
                continue;
            }

            final ItemWArmor armor = (ItemWArmor) stack.getItem();
            final Integer armorCharacteristicValue = armor.getCharacteristic(characteristic);
            if (armorCharacteristicValue == null) {
                continue;
            }

            characteristicValue += armorCharacteristicValue;
        }

        return characteristicValue;
    }
}
