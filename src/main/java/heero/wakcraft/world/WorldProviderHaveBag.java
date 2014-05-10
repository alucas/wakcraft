package heero.wakcraft.world;

import heero.wakcraft.WakcraftConfig;
import net.minecraft.world.WorldProvider;

public class WorldProviderHaveBag extends WorldProvider {
	public static final String NAME = "WakcraftHavenBag";

	/**
	 * creates a new world chunk manager for WorldProvider
	 */
	@Override
	public void registerWorldChunkManager() {
		super.registerWorldChunkManager();

		this.dimensionId = WakcraftConfig.havenBagDimensionId;
	}

	/**
	 * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
	 */
	@Override
	public String getDimensionName() {
		return NAME;
	}
}
