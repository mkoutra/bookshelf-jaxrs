package personal.bookshelf.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * A utility class used to read the "config.properties" file in src/main/resources directory.
 */
public class ConfigFileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigFileUtil.class);

    private static final String CONFIGURATION_FILENAME = "config.properties";

    private ConfigFileUtil() {};

    /**
     * Returns a {@link Properties} object that corresponds to the configuration file.
     */
    public static Properties getPropertiesInstance() {
        try {
            URL configURL = Thread.currentThread().getContextClassLoader().getResource(CONFIGURATION_FILENAME);

            if (configURL == null) {
                throw new FileNotFoundException();
            }

            String configPath = configURL.getPath();
            Properties props = new Properties();
            props.load(new FileInputStream(configPath));
            return props;
        } catch (FileNotFoundException e1) {
            LOGGER.error("Config file not found.");
            return null;
        } catch (IOException e2) {
            LOGGER.error("IOException: {}", e2.getMessage());
            return null;
        }
    }
}
