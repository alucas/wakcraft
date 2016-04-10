package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockContainerGeneric extends BlockContainer {
    protected boolean canBePlacedManually = true;
    protected int renderType = 3;
    protected boolean fullCube = true;
    protected EnumWorldBlockLayer layer = EnumWorldBlockLayer.SOLID;

    public BlockContainerGeneric(Material material, CreativeTabs tab) {
        this(material);

        setCreativeTab(tab);
    }

    public BlockContainerGeneric(Material material) {
        super(material);

        setBlockUnbreakable();
        setResistance(600000);
        setCreativeTab(WCreativeTabs.tabOther);
    }

    public BlockContainerGeneric setRenderType(int renderType) {
        this.renderType = renderType;

        return this;
    }

    @Override
    public int getRenderType() {
        super.getRenderType();
        return renderType;
    }

    public BlockContainerGeneric setFullBlock(final boolean fullBlock) {
        this.fullBlock = fullBlock;

        return this;
    }

    /**
     * If entity get damages when inside the block
     */
    public BlockContainerGeneric setFullCube(final boolean fullCube) {
        this.fullCube = fullCube;

        return this;
    }

    @Override
    public boolean isFullCube() {
        return fullCube;
    }

    @SideOnly(Side.CLIENT)
    public BlockContainerGeneric setLayer(final EnumWorldBlockLayer layer) {
        this.layer = layer;

        return this;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return layer;
    }

    public BlockContainerGeneric setCanBePlacedManually(final boolean canBePlacedManually) {
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
