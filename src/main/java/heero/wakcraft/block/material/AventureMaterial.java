package heero.wakcraft.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class AventureMaterial extends Material {

	public AventureMaterial(MapColor color) {
		super(color);

		setAdventureModeExempt();
	}
}
