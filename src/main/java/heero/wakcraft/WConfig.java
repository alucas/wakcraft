package heero.wakcraft;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class WConfig {
	protected static String KEY_HAVENBAG_DIMENSION = "havenBagDimentionId";

	public static int havenBagDimensionId;

	public static void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		config.load();
		WConfig.havenBagDimensionId = config.get(Configuration.CATEGORY_GENERAL, KEY_HAVENBAG_DIMENSION, 2).getInt();
		config.save();
	}
}
