package heero.mc.mod.wakcraft.block;


import net.minecraft.block.state.IBlockState;

public interface ILevelBlock {
    /**
     * Get the level of the block.
     *
     * @param state State of the block.
     * @return The level of the block.
     */
    public int getLevel(final IBlockState state);

    /**
     * Gathers how much experience this block drops when broken.
     *
     * @param state State of the block.
     * @return Amount of XP from breaking this block.
     */
    public int getProfessionExp(final IBlockState state);
}
