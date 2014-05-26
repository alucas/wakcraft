package heero.mc.mod.wakcraft.block;


public interface ILevelBlock {
	/**
	 * Get the level of the block.
	 * 
	 * @param metadata Metadata of the block.
	 * @return The level of the block.
	 */
	public int getLevel(int metadata);

	/**
	 * Gathers how much experience this block drops when broken.
	 * 
	 * @param metadata
	 * @return Amount of XP from breaking this block.
	 */
	public int getProfessionExp(int metadata);
}
