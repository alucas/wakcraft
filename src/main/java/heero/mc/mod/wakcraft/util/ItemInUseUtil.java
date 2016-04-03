package heero.mc.mod.wakcraft.util;

import heero.mc.mod.wakcraft.entity.property.ItemInUseProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class ItemInUseUtil {
    public static void saveCoords(final EntityPlayer player, final BlockPos pos) {
        ItemInUseProperty properties = (ItemInUseProperty) player.getExtendedProperties(ItemInUseProperty.IDENTIFIER);
        properties.pos = pos;
    }

    public static BlockPos getCoords(final EntityPlayer player) {
        ItemInUseProperty properties = (ItemInUseProperty) player.getExtendedProperties(ItemInUseProperty.IDENTIFIER);
        return properties.pos;
    }
}
