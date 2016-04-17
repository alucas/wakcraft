package heero.mc.mod.wakcraft;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class WConfig {
    public static final String CATEGORY_FIGHT = "fight";

    private static final String KEY_HAVENBAG_DIMENSION = "haven_bag_dimension_id";
    private static final String KEY_WAKFU_FIGHT_ENABLE = "enable";
    private static final String KEY_WAKFU_FIGHT_PRE_FIGHT_DURATION = "pre_fight_duration";
    private static final String KEY_WAKFU_FIGHT_TURN_DURATION = "fight_turn_duration";

    public static Configuration config;

    public static void saveConfig() {
        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);
        config.load();
    }

    public static int getHavenBagDimensionId() {
        return config.get(Configuration.CATEGORY_GENERAL, KEY_HAVENBAG_DIMENSION, 2).getInt();
    }

    public static boolean isWakfuFightEnable() {
        return config.get(CATEGORY_FIGHT, KEY_WAKFU_FIGHT_ENABLE, false).getBoolean();
    }

    public static int getWakfuFightPreFightDuration() {
        return config.get(CATEGORY_FIGHT, KEY_WAKFU_FIGHT_PRE_FIGHT_DURATION, 30).getInt() * 20;
    }

    public static int getWakfuFightTurnDuration() {
        return config.get(CATEGORY_FIGHT, KEY_WAKFU_FIGHT_TURN_DURATION, 30).getInt() * 20;
    }
}
