package heero.mc.mod.wakcraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.block.state.IBlockState;

public class BlockInvisibleWall extends BlockGeneric {

    public BlockInvisibleWall() {
        super(new MaterialTransparent(MapColor.airColor) {
            @Override
            public boolean isReplaceable() {
                return false;
            }
        });

        setCanBePlacedManually(false);
        setRenderType(-1);
        setLightLevel(1);
        setOpaque(true);
    }

    @Override
    public boolean canCollideCheck(IBlockState state, boolean stopOnLiquid) {
        return false;
    }
}
