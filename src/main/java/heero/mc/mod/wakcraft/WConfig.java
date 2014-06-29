package heero.mc.mod.wakcraft;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

public class WConfig {
	protected static String KEY_HAVENBAG_DIMENSION = "havenBagDimentionId";

	public static int HAVENBAG_DIMENSION_ID;

	public static void loadConfig(File configFilet) {
		Configuration config = new Configuration(configFilet);

		try {
			config.load();
			WConfig.HAVENBAG_DIMENSION_ID = config.get(Configuration.CATEGORY_GENERAL, KEY_HAVENBAG_DIMENSION, 2).getInt();
		} catch (Exception e) {
			WLog.log(Level.ERROR, e, "Wakcraft has a problem loading it's configuration");
		} finally {
			if (config.hasChanged()) {
				config.save();
			}
		}
	}
}
