package heero.mc.mod.wakcraft.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class AdventureMaterial extends Material {

    public AdventureMaterial(MapColor color) {
        super(color);

        setAdventureModeExempt();
    }
}
