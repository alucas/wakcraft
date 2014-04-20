package heero.wakcraft.block;

public interface ILevelBlock {
	/**
	 * Get the level of the block.
	 * 
	 * @param metadata Metadata of the block.
	 * @return The level of the block.
	 */
	public int getLevel(int metadata);
}
