package heero.wakcraft.world;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderHaveBag extends WorldProvider {
	public static final String NAME = "WakcraftHavenBag";
	/**
	 * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
	 */
	@Override
	public String getDimensionName() {
		return NAME;
	}

	@Override
	public void registerWorldChunkManager() {
		super.registerWorldChunkManager();
//		this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.desertHills, 0);
//		this.dimensionId = WakcraftConfig.havenBagDimensionId;
	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return super.createChunkGenerator();
		//return new ChunkProviderTutorial(worldObj, worldObj.getSeed(), true);
	}
}
