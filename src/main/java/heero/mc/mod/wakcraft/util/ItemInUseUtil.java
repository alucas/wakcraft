package heero.mc.mod.wakcraft.util;

import heero.mc.mod.wakcraft.entity.property.ItemInUseProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class ItemInUseUtil {
    public static void saveCoords(EntityPlayer player, int posX, int posY, int posZ) {
        ItemInUseProperty properties = (ItemInUseProperty) player.getExtendedProperties(ItemInUseProperty.IDENTIFIER);
        properties.posX = posX;
        properties.posY = posY;
        properties.posZ = posZ;
    }

    public static int[] getCoords(EntityPlayer player) {
        ItemInUseProperty properties = (ItemInUseProperty) player.getExtendedProperties(ItemInUseProperty.IDENTIFIER);
        return new int[]{properties.posX, properties.posY, properties.posZ};
    }

    public static void saveCoords(EntityPlayer player, BlockPos pos) {
        saveCoords(player, pos.getX(), pos.getY(), pos.getZ());
    }
}
