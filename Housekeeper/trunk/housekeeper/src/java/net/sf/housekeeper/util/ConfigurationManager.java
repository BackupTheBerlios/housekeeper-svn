package net.sf.housekeeper.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * This is a manager for getting and setting application properties. To read a
 * property first get a {@link org.apache.commons.configuration.Configuration}
 * object with {@link #getConfiguration()}. See this class' constants for
 * common property names.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ConfigurationManager
{

    /**
     * Singleton instance.
     */
    public static final ConfigurationManager INSTANCE            = new ConfigurationManager();

    /**
     * Property key returning a {@link String}with the application's version.
     */
    public static final String               HOUSEKEEPER_VERSION = "base.version";

    /**
     * Property key returning a {@link File}to the directory, where data and
     * configuration should be located.
     */
    public static final String               DATA_DIRECTORY      = "base.datadir";

    /**
     * The configuration of this application.
     */
    private CompositeConfiguration           configuration;

    /**
     * Do not make instances on your own. Use the Singleton instance instead.
     *  
     */
    private ConfigurationManager()
    {

    }

    /**
     * Initializes this manager. Must be called before any configurations can be
     * retrieved or saved.
     * 
     * @param dataDir The directory where data and configurations should be
     *            saved.
     * @throws ConfigurationException if a configuration cannot be created.
     * @throws IOException if there's a problem creating the user's
     *             configuration file.
     * @throws IllegalStateException if this manager has already been
     *             initialized.
     */
    public void init(final File dataDir) throws ConfigurationException,
            IOException, IllegalStateException
    {
        if (configuration != null)
        {
            throw new IllegalStateException(
                    "Manager has already been initialized!");
        }

        final URL defaultPropURL = getClass()
                .getResource("/net/sf/housekeeper/config/housekeeper.properties");
        final Configuration defaultConfiguration = new PropertiesConfiguration(
                defaultPropURL);

        defaultConfiguration.addProperty(DATA_DIRECTORY, dataDir);

        final File configFile = new File(dataDir, "housekeeper.properties");
        configFile.createNewFile();

        final Configuration userConfiguration = new PropertiesConfiguration(
                configFile);
        CompositeConfiguration cc = new CompositeConfiguration(
                userConfiguration);
        cc.addConfiguration(userConfiguration);
        cc.addConfiguration(defaultConfiguration);

        configuration = cc;
    }

    /**
     * Returns the configuration object for getting properties.
     * 
     * @return Returns the configuration object for getting properties.
     *         Guaranteed to be not null.
     * @throws IllegalStateException if this manager has not been initalized
     *             yet.
     */
    public Configuration getConfiguration() throws IllegalStateException
    {
        if (configuration == null)
        {
            throw new IllegalStateException(
                    "Manager has not yet been initialized. Call init() first.");
        }
        return configuration;
    }

    /**
     * Saves the current configuration to the user's configuration file.
     * 
     * @throws ConfigurationException if the configuration could not be saved.
     * @throws IllegalStateException if this manager has not been initalized
     *             yet.
     */
    public void saveConfiguration() throws ConfigurationException,
            IllegalStateException
    {
        if (configuration == null)
        {
            throw new IllegalStateException(
                    "Manager has not yet been initialized. Call init() first.");
        }
        final AbstractFileConfiguration userConfig = (AbstractFileConfiguration) configuration
                .getInMemoryConfiguration();

        userConfig.save();
    }

}