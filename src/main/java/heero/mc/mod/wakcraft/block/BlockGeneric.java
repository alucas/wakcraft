package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGeneric extends Block {
    private boolean opaque;
    private boolean full;
    private EnumWorldBlockLayer layer;

    public BlockGeneric(Material material) {
        super(material);

        this.opaque = true;
        this.full = true;
        this.layer = EnumWorldBlockLayer.SOLID;

        setBlockUnbreakable();
        setCreativeTab(WakcraftCreativeTabs.tabBlock);
    }

    public BlockGeneric setOpaque(final boolean opaque) {
        this.opaque = opaque;

        return this;
    }

    @Override
    public boolean isOpaqueCube() {
        return opaque;
    }

    public BlockGeneric setFull(final boolean full) {
        this.full = full;

        return this;
    }

    @Override
    public boolean isFullBlock() {
        return full;
    }

    public BlockGeneric setLayer(final EnumWorldBlockLayer layer) {
        this.layer = layer;

        return this;
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return layer;
    }
}
