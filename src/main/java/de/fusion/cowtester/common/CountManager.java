package de.fusion.cowtester.common;

import de.fusion.cowtester.CowTester;
import java.util.concurrent.ConcurrentHashMap;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

public class CountManager {

  private long totalCows = 0;
  private ConcurrentHashMap<Integer, Long> average = new ConcurrentHashMap<>();
  private int threads;


  public CountManager() {
    threads = CowTester.getConfiguration().getPath("General.CountThreads").getInt();
  }

  public void init() {
    long diff = 1000 / threads;
    CowTester.getMainExecutorService().submit(() -> {
      for (int i = 1; i <= threads; i++) {
        CounterThread counterThread = new CounterThread(i);
        counterThread.setName("CounterThread#" + i);
        counterThread.start();
        try {
          Thread.sleep(diff);
        } catch (InterruptedException ignored) {
        }
      }
      CowTester.getInstance().log("Started " + threads + " counter threads.");
    });

    new Thread(() -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }

      while (CowTester.getInstance().isRunning()) {
        Bukkit.getOnlinePlayers().forEach(player -> {
          if (player.hasPermission("cowtester.view")) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(
                    CowTester.getConfiguration().getPath("General.ActionBar").getString()
                        .replace("%prefix%", CowTester.getPrefix())
                        .replace("%amount%", getBotsPerSecond() + "")));
          }
        });
        try {
          Thread.sleep(20);
        } catch (InterruptedException ignored) {
        }
      }
    }, "DeluxeCounter#Notificator").start();
  }

  public void addCow() {
    totalCows++;
  }

  public void submit(int number, long amount) {
    average.put(number, amount);
  }

  public long getTotalCows() {
    return totalCows;
  }

  private long getBotsPerSecond() {
    long total = 0;
    for (int i = 1; i <= threads; i++) {
      total += average.get(i);
    }

    return total / threads;
  }
}
