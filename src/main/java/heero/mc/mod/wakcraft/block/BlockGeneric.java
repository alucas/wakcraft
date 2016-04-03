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
    protected boolean canBePlacedManually = true;
    protected int renderType = 3;
    protected boolean fullCube = true;
    protected EnumWorldBlockLayer layer = EnumWorldBlockLayer.SOLID;

    public BlockGeneric(Material material, CreativeTabs tab) {
        this(material);

        setCreativeTab(tab);
    }

    public BlockGeneric(Material material) {
        super(material);

        setBlockUnbreakable();
        setCreativeTab(WCreativeTabs.tabOther);
    }

    public BlockGeneric setRenderType(int renderType) {
        this.renderType = renderType;

        return this;
    }

    @Override
    public int getRenderType() {
        super.getRenderType();
        return renderType;
    }

    public BlockGeneric setFullBlock(final boolean fullBlock) {
        this.fullBlock = fullBlock;

        return this;
    }

    /**
     * If entity get damages when inside the block
     */
    public BlockGeneric setFullCube(final boolean fullCube) {
        this.fullCube = fullCube;

        return this;
    }

    @Override
    public boolean isFullCube() {
        return fullCube;
    }

    @SideOnly(Side.CLIENT)
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
