package heero.mc.mod.wakcraft;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.io.File;

public class WConfig {
    private static final String KEY_HAVENBAG_DIMENSION = "havenBagDimensionId";
    private static final String KEY_WAKFU_FIGHT_ENABLE = "wakfuFightEnable";
    private static final String KEY_WAKFU_FIGHT_PRE_FIGHT_DURATION = "wakfuFightPreFightDuration";
    private static final String KEY_WAKFU_FIGHT_TURN_DURATION = "wakfuFightTurnDuration";

    private static int havenBagDimensionId;
    private static boolean wakfuFightEnable;
    private static int wakfuFightPreFightDuration;
    private static int wakfuFightTurnDuration;

    public static void loadConfig(File configFile) {
        Configuration config = new Configuration(configFile);

        try {
            config.load();

            WConfig.havenBagDimensionId = config.get(Configuration.CATEGORY_GENERAL, KEY_HAVENBAG_DIMENSION, 2).getInt();
            WConfig.wakfuFightEnable = config.get(Configuration.CATEGORY_GENERAL, KEY_WAKFU_FIGHT_ENABLE, false).getBoolean();
            WConfig.wakfuFightPreFightDuration = config.get(Configuration.CATEGORY_GENERAL, KEY_WAKFU_FIGHT_PRE_FIGHT_DURATION, 30).getInt() * 20;
            WConfig.wakfuFightTurnDuration = config.get(Configuration.CATEGORY_GENERAL, KEY_WAKFU_FIGHT_TURN_DURATION, 30).getInt() * 20;
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

    public static int getWakfuFightPreFightDuration() {
        return wakfuFightPreFightDuration;
    }

    public static int getWakfuFightTurnDuration() {
        return wakfuFightTurnDuration;
    }
}
