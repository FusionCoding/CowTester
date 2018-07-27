package de.fusion.cowtester;

import de.fusion.cowtester.common.CountManager;
import de.fusion.cowtester.config.ConfigManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CowTester extends JavaPlugin {


  private static ExecutorService executorService = Executors.newCachedThreadPool();
  private static CowTester instance;
  private static CountManager countManager;
  private static ConfigManager configuration;
  private boolean running;
  private List<String> toggledPlayers = new ArrayList<>();
  private boolean allowConnections = true;


  /**
   * Used to initialize the instance of the class
   */
  @Override
  public void onLoad() {
    super.onLoad();
    instance = this;
    running = true;
  }

  /**
   * Used to manage the configuration and everything else necessary.
   */
  public void onEnable() {
    super.onEnable();
    File config = new File(getDataFolder(), "config.yml");
    if (!getDataFolder().exists()) {
      getDataFolder().mkdir();
    }
    if (!config.exists()) {
      saveResource("config.yml", true);
    }

    configuration = new ConfigManager(config).build();
    log("Loaded DeluxeCounter by FusionCoding");

    countManager = new CountManager();
    countManager.init();

    PluginManager pm = Bukkit.getPluginManager();


  }

  /**
   * Used to disable all running tasks listening for running
   */
  @Override
  public void onDisable() {
    super.onDisable();
    running = false;
  }

  /**
   * This is used to log something to the console Automatically adds the prefix
   *
   * @param message to print
   */
  public void log(String message) {
    Bukkit.getConsoleSender().sendMessage(getPrefix() + message);
  }

  /**
   * Method is used to easily run asynchronous tasks
   *
   * @return ExecutorService
   */
  public static ExecutorService getMainExecutorService() {
    return executorService;
  }

  /**
   * This Method returns the instance of the plugin
   *
   * @return DeluxeCounter
   */
  public static CowTester getInstance() {
    return instance;
  }

  /**
   * Method will return a instance of 'ConfigManager' This allows easy access to the configuration
   * file
   *
   * @return ConfigManager
   */
  public static ConfigManager getConfiguration() {
    return configuration;
  }

  /**
   * Method will return the String of the plugin given in the configuration file
   *
   * @return String of DeluxeCounter
   */
  public static String getPrefix() {
    return getConfiguration().getPath("General.Prefix").getString();
  }

  /**
   * Method returns the current state of the active plugin
   *
   * @return boolean
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * Method returns the CountManager Can be used to receive the current bots per second
   *
   * @return CountManager
   */
  public static CountManager getCountManager() {
    return countManager;
  }
}
