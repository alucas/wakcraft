package heero.mc.mod.wakcraft;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

public class WConfig {
	private static final String KEY_HAVENBAG_DIMENSION = "havenBagDimentionId";
	private static final String KEY_WAKFU_FIGHT_ENABLE = "enableWakfuFight";

	private static int havenBagDimensionId;
	private static boolean wakfuFightEnable;

	public static void loadConfig(File configFilet) {
		Configuration config = new Configuration(configFilet);

		try {
			config.load();

			WConfig.havenBagDimensionId = config.get(Configuration.CATEGORY_GENERAL, KEY_HAVENBAG_DIMENSION, 2).getInt();
			WConfig.wakfuFightEnable = config.get(Configuration.CATEGORY_GENERAL, KEY_WAKFU_FIGHT_ENABLE, false).getBoolean();
		} catch (Exception e) {
			WLog.log(Level.ERROR, e, "Error while loading the configuration");
		} finally {
			if (config.hasChanged()) {
				config.save();
			}
		}
	}

	public static int getHavenBagDimensionId() {
		return havenBagDimensionId;
	}

	public static boolean isWakfuFightEnable() {
		return wakfuFightEnable;
	}
}
