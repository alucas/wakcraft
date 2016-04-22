package heero.mc.mod.wakcraft.client.model.baked;

import heero.mc.mod.wakcraft.block.BlockOverSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.ISmartBlockModel;
import net.minecraftforge.common.property.IExtendedBlockState;

public class BakedModelOverSlabWrapper extends IFlexibleBakedModel.Wrapper implements ISmartBlockModel {
    protected final IFlexibleBakedModel bakedModel1;
    protected final IFlexibleBakedModel bakedModel2;
    protected final IFlexibleBakedModel bakedModel3;
    protected final IFlexibleBakedModel bakedModel4;

    public BakedModelOverSlabWrapper(IFlexibleBakedModel bakedModel1, IFlexibleBakedModel bakedModel2, IFlexibleBakedModel bakedModel3, IFlexibleBakedModel bakedModel4, final VertexFormat format) {
        super(bakedModel1, format);

        this.bakedModel1 = bakedModel1;
        this.bakedModel2 = bakedModel2;
        this.bakedModel3 = bakedModel3;
        this.bakedModel4 = bakedModel4;
    }

    @Override
    public IBakedModel handleBlockState(IBlockState state) {
        if (!(state instanceof IExtendedBlockState)) {
            return bakedModel1;
        }

        final IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;
        switch (extendedBlockState.getValue(BlockOverSlab.PROP_BOTTOM_POSITION)) {
            case 1:
                return bakedModel2;
            case 2:
                return bakedModel3;
            case 3:
                return bakedModel4;
        }

        return bakedModel1;
    }
}
