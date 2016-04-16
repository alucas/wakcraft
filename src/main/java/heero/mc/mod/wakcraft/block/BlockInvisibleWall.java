package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.block.material.WMaterial;
import net.minecraft.block.state.IBlockState;

public class BlockInvisibleWall extends BlockGenericTransparent {

    public BlockInvisibleWall() {
        super(WMaterial.invisible);

        setCanBePlacedManually(false);
        setRenderType(-1);
        setLightLevel(1);
    }

    @Override
    public boolean canCollideCheck(IBlockState state, boolean stopOnLiquid) {
        return false;
    }
}
