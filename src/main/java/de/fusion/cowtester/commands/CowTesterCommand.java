package de.fusion.cowtester.commands;

import de.fusion.cowtester.CowTester;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CowTesterCommand implements CommandExecutor, TabCompleter {

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s,
      String[] args) {
    if (args.length == 0) {
      commandSender
          .sendMessage(CowTester.getConfiguration().getPath("Commands.Help").getStringList());
    } else if (args.length == 1) {
      if (args[0].equalsIgnoreCase("about")) {
        commandSender.sendMessage(
            CowTester.getPrefix() + "This server is running DeluxeCounter " + CowTester
                .getInstance().getDescription().getVersion() + " by FusionCoding");
      } else if (args[0].equalsIgnoreCase("reload")) {
        if (commandSender.hasPermission("CowTester.perform.reload")) {
          CowTester.getConfiguration().reload();
          commandSender.sendMessage(CowTester.getPrefix() + CowTester.getConfiguration()
              .getPath("Commands.Reloaded").getString());
        } else {
          commandSender.sendMessage(CowTester.getPrefix() + CowTester.getConfiguration()
              .getPath("Commands.NoPermissions").getString());
        }
      } else if (args[0].equalsIgnoreCase("toggle")) {
        if (commandSender instanceof Player) {
          if (commandSender.hasPermission("CowTester.perform.toggle")) {
            if (CowTester.getInstance().getToggledPlayers().contains(commandSender.getName())) {
              CowTester.getInstance().getToggledPlayers().remove(commandSender.getName());
              commandSender.sendMessage(CowTester.getPrefix() + CowTester.getConfiguration()
                  .getPath("Commands.Toggle.Other.Disabled").getString());
            } else {
              CowTester.getInstance().getToggledPlayers().add(commandSender.getName());
              commandSender.sendMessage(CowTester.getPrefix() + CowTester.getConfiguration()
                  .getPath("Commands.Toggle.Other.Enabled").getString());
            }
          } else {
            commandSender.sendMessage(CowTester.getPrefix() + CowTester.getConfiguration()
                .getPath("Commands.NoPermissions").getString());
          }
        } else {
          commandSender.sendMessage(CowTester.getPrefix() + CowTester.getConfiguration()
              .getPath("Commands.NoPlayer").getString());
        }
      } else if (args[0].equalsIgnoreCase("start")) {
        if (commandSender.hasPermission("CowTester.perform.start")) {
          commandSender.sendMessage(CowTester.getPrefix() + CowTester.getConfiguration()
              .getPath("Commands.StartBenchmark").getString());
          CowTester.getInstance().startBenchmark();

        } else {
          commandSender.sendMessage(CowTester.getPrefix() + CowTester.getConfiguration()
              .getPath("Commands.NoPermissions").getString());
        }
      } else if (args[0].equalsIgnoreCase("setlocation")) {
        if (commandSender.hasPermission("CowTester.perform.setlocation")) {
          commandSender.sendMessage(CowTester.getPrefix() + CowTester.getConfiguration()
              .getPath("Commands.SetLocation").getString());
            CowTester.getInstance().setCowLocation(((Player)commandSender).getLocation());

        } else {
          commandSender.sendMessage(CowTester.getPrefix() + CowTester.getConfiguration()
              .getPath("Commands.NoPermissions").getString());
        }
      } else {
        commandSender
            .sendMessage(CowTester.getConfiguration().getPath("Commands.Help").getStringList());
      }
    }
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender commandSender, Command command, String s,
      String[] strings) {
    List<String> l = new ArrayList<>();
    l.add("about");
    if (commandSender.hasPermission("cowtester.perform.toggle")) {
      l.add("toggle");
    }
    if (commandSender.hasPermission("cowtester.perform.start")) {
      l.add("start");
    }
    if (commandSender.hasPermission("cowtester.perform.reload")) {
      l.add("reload");
    }
    return null;
  }
}
