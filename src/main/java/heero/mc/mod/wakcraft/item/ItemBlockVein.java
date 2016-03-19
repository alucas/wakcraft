package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.block.vein.BlockVein;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockVein extends ItemBlock {

    protected BlockVein blockVein;

    public ItemBlockVein(Block block) {
        super(block);

        this.blockVein = (BlockVein) block;

        setHasSubtypes(true);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        int meta = stack.getMetadata();

        return blockVein.getStateFromMeta(meta).getValue(blockVein.getPropOre()).getColor();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getMetadata();
        String superName = super.getUnlocalizedName();

        return superName.substring(0, superName.lastIndexOf('.')) + ".vein_" + blockVein.getStateFromMeta(meta).getValue(blockVein.getPropOre()).getName();
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    @Override
    public int getMetadata(int damageValue) {
        return damageValue & 0b1110;
    }
}
