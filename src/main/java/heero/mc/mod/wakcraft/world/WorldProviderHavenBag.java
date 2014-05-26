package heero.mc.mod.wakcraft.world;

import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.client.renderer.world.EndSkyRenderer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldProviderHavenBag extends WorldProvider {
	public static final String NAME = "WakcraftHavenBag";

	/**
	 * creates a new world chunk manager for WorldProvider
	 */
	@Override
	public void registerWorldChunkManager() {
		super.registerWorldChunkManager();

		this.hasNoSky = true;
		this.dimensionId = WConfig.HAVENBAG_DIMENSION_ID;

		if (worldObj.isRemote) {
			this.setSkyRenderer(new EndSkyRenderer());
		}
	}

	/**
	 * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
	 */
	@Override
	public String getDimensionName() {
		return NAME;
	}

	/**
	 * A message to display to the user when they transfer to this dimension.
	 *
	 * @return The message to be displayed
	 */
	public String getWelcomeMessage() {
		return "Entering your HavenBag";
	}

	/**
	 * A Message to display to the user when they transfer out of this
	 * dismension.
	 *
	 * @return The message to be displayed
	 */
	public String getDepartMessage() {
		return "Leaving your HavenBag";
	}

	/**
	 * Returns 'true' if in the "main surface world", but 'false' if in the
	 * Nether or End dimensions.
	 */
	public boolean isSurfaceWorld() {
		return false;
	}

	/**
	 * Calculates the angle of sun and moon in the sky relative to a specified
	 * time (usually worldTime)
	 */
	public float calculateCelestialAngle(long par1, float par3) {
		return 0.0F;
	}

	/**
	 * Returns array with sunrise/sunset colors
	 */
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float par1, float par2) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	public boolean isSkyColored() {
		return false;
	}

	/**
	 * True if the player can respawn in this dimension (true = overworld, false
	 * = nether).
	 */
	public boolean canRespawnHere() {
		return false;
	}

	public int getAverageGroundLevel() {
		return 10;
	}

	/**
	 * Returns true if the given X,Z coordinate should show environmental fog.
	 */
	@SideOnly(Side.CLIENT)
	public boolean doesXZShowFog(int x, int z) {
		return true;
	}

	/**
	 * Return Vec3D with biome specific fog color
	 */
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float par1, float par2) {
		int i = 10518688;
		float f2 = MathHelper.cos(par1 * (float) Math.PI * 2.0F) * 2.0F + 0.5F;

		if (f2 < 0.0F) {
			f2 = 0.0F;
		}

		if (f2 > 1.0F) {
			f2 = 1.0F;
		}

		float f3 = (float) (i >> 16 & 255) / 255.0F;
		float f4 = (float) (i >> 8 & 255) / 255.0F;
		float f5 = (float) (i & 255) / 255.0F;
		f3 *= f2 * 0.0F + 0.15F;
		f4 *= f2 * 0.0F + 0.15F;
		f5 *= f2 * 0.0F + 0.15F;
		return this.worldObj.getWorldVec3Pool().getVecFromPool((double) f3,
				(double) f4, (double) f5);
	}

	/**
	 * Returns a new chunk provider which generates chunks for this world
	 */
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderFlat(worldObj, worldObj.getSeed(), false, field_82913_c);
	}
}
