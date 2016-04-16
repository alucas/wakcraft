package heero.mc.mod.wakcraft.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;

public class WMaterial {
    public static final Material invisible = new MaterialTransparent(MapColor.airColor) {
        @Override
        public boolean isReplaceable() {
            return false;
        }
    };
}
