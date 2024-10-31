package personal.bookshelf.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * A utility class that helps with reading the "config.properties" file in the root directory
 */
public class ConfigFileUtil {
    private static final String CONFIGURATION_FILENAME = "config.properties";

    private ConfigFileUtil() {};

    /**
     * Returns a {@link Properties} object that corresponds to the configuration file.
     * @return
     */
    public static Properties getPropertiesInstance() {
        try {
            URL configURL = Thread.currentThread().getContextClassLoader().getResource("config.properties");
            if (configURL == null) {
                throw new FileNotFoundException();
            }

            String configPath = configURL.getPath();
            Properties props = new Properties();

            props.load(new FileInputStream(configPath));
            return props;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
