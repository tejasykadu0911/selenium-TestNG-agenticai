package framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties props = new Properties();
    public static final String ENV = System.getProperty("env", "qa");

    static {
        try (InputStream in = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (in != null) props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config.properties", e);
        }
    }

    private ConfigReader() {}

    /** System property overrides config.properties value. */
    public static String get(String key) {
        return System.getProperty(key, props.getProperty(key, ""));
    }

    public static String get(String key, String defaultValue) {
        return System.getProperty(key, props.getProperty(key, defaultValue));
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String val = get(key);
        return val.isEmpty() ? defaultValue : Boolean.parseBoolean(val);
    }

    public static int getInt(String key, int defaultValue) {
        String val = get(key);
        try { return val.isEmpty() ? defaultValue : Integer.parseInt(val); }
        catch (NumberFormatException e) { return defaultValue; }
    }
}