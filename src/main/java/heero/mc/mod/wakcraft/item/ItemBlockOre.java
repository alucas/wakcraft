package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.block.ore.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockOre extends ItemBlock {

    protected BlockOre blockOre;

    public ItemBlockOre(Block block) {
        super(block);

        this.blockOre = (BlockOre) block;

        setHasSubtypes(true);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        int meta = stack.getMetadata();

        return blockOre.getStateFromMeta(meta).getValue(blockOre.getPropOre()).getColor();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getMetadata();

        return super.getUnlocalizedName() + blockOre.getStateFromMeta(meta).getValue(blockOre.getPropOre()).getName();
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    @Override
    public int getMetadata(int damageValue) {
        return damageValue & 0b1110;
    }
}
