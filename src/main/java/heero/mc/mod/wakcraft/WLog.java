package heero.mc.mod.wakcraft;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WLog {
	public static Logger wLog = LogManager.getLogger(WInfo.MODID);

	public static Logger getLogger() {
		return wLog;
	}

	public static void log(Level level, String format, Object... data) {
		wLog.log(level, format, data);
	}

	public static void log(Level level, Throwable throwable, String format, Object... data) {
		wLog.log(level, format, data, throwable);
	}

	public static void error(String format, Object... data) {
		log(Level.ERROR, format, data);
	}

	public static void warning(String format, Object... data) {
		log(Level.WARN, format, data);
	}

	public static void info(String format, Object... data) {
		log(Level.INFO, format, data);
	}

	public static void fine(String format, Object... data) {
		log(Level.DEBUG, format, data);
	}

	public static void finer(String format, Object... data) {
		log(Level.TRACE, format, data);
	}
}