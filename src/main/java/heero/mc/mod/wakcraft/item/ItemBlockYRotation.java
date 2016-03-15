package heero.mc.mod.wakcraft.item;

import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.util.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemBlockYRotation extends Item implements IBlockProvider {
    protected final Block block;

    protected final ItemBlock itemBlockNorth;
    protected final ItemBlock itemBlockEast;
    protected final ItemBlock itemBlockSouth;
    protected final ItemBlock itemBlockWest;

    public ItemBlockYRotation(Block blockNorth, Block blockEast, Block blockSouth, Block blockWest) {
        super();

        this.block = blockNorth;

        this.itemBlockNorth = (ItemBlock) Item.getItemFromBlock(blockNorth);
        this.itemBlockEast = (ItemBlock) Item.getItemFromBlock(blockEast);
        this.itemBlockSouth = (ItemBlock) Item.getItemFromBlock(blockSouth);
        this.itemBlockWest = (ItemBlock) Item.getItemFromBlock(blockWest);

        setCreativeTab(WCreativeTabs.tabBlock);
        blockNorth.setCreativeTab(null);
        blockEast.setCreativeTab(null);
        blockSouth.setCreativeTab(null);
        blockWest.setCreativeTab(null);

        if (itemBlockNorth == null || itemBlockEast == null || itemBlockSouth == null || itemBlockWest == null) {
            throw new RuntimeException("Failed to initialize " + this.getClass().getCanonicalName());
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
                             EnumFacing side, float hitX, float hitY, float hitZ) {
        EnumFacing direction = RotationUtil.getYRotationFromYaw(player.rotationYaw);

        switch (direction) {
            case NORTH:
                return itemBlockNorth.onItemUse(stack, player, world, pos, side, hitX, hitY, hitZ);
            case EAST:
                return itemBlockEast.onItemUse(stack, player, world, pos, side, hitX, hitY, hitZ);
            case SOUTH:
                return itemBlockSouth.onItemUse(stack, player, world, pos, side, hitX, hitY, hitZ);
            case WEST:
                return itemBlockWest.onItemUse(stack, player, world, pos, side, hitX, hitY, hitZ);
            default:
                break;
        }

        return false;
    }

    @Override
    public Block getBlock() {
        return block;
    }
}
