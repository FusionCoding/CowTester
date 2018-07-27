package de.fusion.cowtester.common.config;

import java.io.File;
import java.util.List;

/**
 * This class is the main ConfigManager for BungeeCord This class handles everything and sets the
 * actual methods
 */
public abstract class ConfigManagerAdapter extends ConfigAdapter implements ConfigAdapterInterface {

  /**
   * Initialize the ConfigManager
   *
   * @param f as configuration File
   */
  public ConfigManagerAdapter(File f) {
    super(f);
  }

  /**
   * Method builds the ConfigManager
   */
  public ConfigManagerAdapter build() {
    reload();
    return this;
  }

  /**
   * Method reloads the configuration
   *
   * @return ConfigManager
   */
  public abstract ConfigManagerAdapter reload();

  /**
   * Method to set the path which needs to be written to
   *
   * @return ConfigWriter
   */
  @Override
  public abstract ConfigWriter setPath(String path);

  /**
   * Method to set the path which needs to be read.
   *
   * @return ConfigReader
   */
  @Override
  public abstract ConfigReader getPath(String path);

  /**
   * Class defining methods to read the configuration
   */
  protected abstract class RealConfigReader implements ConfigReader {

    public final Object configuration;
    public final String path;

    /**
     * Initializes the config reader
     */
    public RealConfigReader(Object configuration, String path) {
      this.configuration = configuration;
      this.path = path;
    }

    @Override
    public abstract boolean contains();

    @Override
    public abstract int getInt();

    @Override
    public abstract boolean getBoolean();

    @Override
    public abstract String getString();

    @Override
    public abstract String getStringList();

    @Override
    public abstract List<?> getList();

    @Override
    public abstract Object getObject();
  }

  /**
   * Class defining methods to read the configuration
   */
  public abstract class RealConfigWriter implements ConfigWriter {

    public final Object configuration;
    public final String path;

    /**
     * Initializes the config reader
     */
    public RealConfigWriter(Object configuration, String path) {
      this.configuration = configuration;
      this.path = path;
    }

    @Override
    public abstract void setObject(Object o);

    @Override
    public abstract void setAsyncObject(Object o);
  }
}
