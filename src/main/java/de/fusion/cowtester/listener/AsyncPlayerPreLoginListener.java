package de.fusion.cowtester.listener;

import de.fusion.cowtester.CowTester;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

public class AsyncPlayerPreLoginListener implements Listener {

  @EventHandler
  public void on(AsyncPlayerPreLoginEvent e) {

    if (!(CowTester.getInstance().isAllowingConnections())) {
      e.setKickMessage(
          CowTester.getConfiguration().getPath("KickMessages.NotAllowing").getStringList());
      e.setLoginResult(Result.KICK_OTHER);
    }
  }
}
