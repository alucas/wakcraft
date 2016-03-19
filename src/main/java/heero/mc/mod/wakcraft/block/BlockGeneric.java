package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGeneric extends Block {
    protected boolean opaque;
    protected boolean canBePlacedManually;
    protected int renderType;
    protected EnumWorldBlockLayer layer;

    public BlockGeneric(Material material, CreativeTabs tab) {
        this(material);

        setCreativeTab(tab);
    }

    public BlockGeneric(Material material) {
        super(material);

        this.opaque = true;
        this.canBePlacedManually = true;
        this.renderType = 3;
        this.layer = EnumWorldBlockLayer.SOLID;

        setBlockUnbreakable();
        setCreativeTab(WCreativeTabs.tabOther);
    }

    public BlockGeneric setRenderType(int renderType) {
        this.renderType = renderType;

        return this;
    }

    @Override
    public int getRenderType() {
        return renderType;
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
        this.fullBlock = full;

        return this;
    }

    @Override
    public boolean isFullBlock() {
        return fullBlock;
    }

    public BlockGeneric setLayer(final EnumWorldBlockLayer layer) {
        this.layer = layer;

        return this;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return layer;
    }

    public BlockGeneric setCanBePlacedManually(final boolean canBePlacedManually) {
        this.canBePlacedManually = canBePlacedManually;

        return this;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        if (canBePlacedManually) {
            return super.canPlaceBlockAt(world, pos);
        }

        if (world.isRemote) {
            Wakcraft.proxy.getClientPlayer().addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.canPlaceBlockManualy")));
        }

        return false;
    }
}
