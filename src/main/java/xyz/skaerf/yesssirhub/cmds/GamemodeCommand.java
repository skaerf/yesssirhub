package xyz.skaerf.yesssirhub.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage("Console cannot switch gamemode - please add a player to the end of the command.");
            return true;
        }
        if (label.contains("c")) {
            doGamemode(sender, "creative", args);
        }
        else if (label.contains("a")) {
            doGamemode(sender, "adventure", args);
        }
        else if (label.contains("sp")) {
            doGamemode(sender, "spectator", args);
        }
        else if (label.contains("s")) {
            doGamemode(sender, "survival", args);
        }
        else {
            sender.sendMessage(ChatColor.RED+"Please specify a gamemode! e.g. /gms");
        }
        return true;
    }

    private void doGamemode(CommandSender sender, String gamemode, String[] args) {
        if (!sender.hasPermission("essentials.gamemode."+gamemode)) { // doing this so that double permissions don't have to be added
            sender.sendMessage(ChatColor.RED+"You don't have permission!");
            return;
        }
        if (args.length != 0) {
            Player player = Bukkit.getPlayer(args[args.length-1]);
            if (player == null) {
                sender.sendMessage(ChatColor.RED+args[0]+" is not a valid player!");
                return;
            }
            player.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
            player.sendMessage(ChatColor.GOLD+"Your gamemode was changed to "+gamemode+".");
            sender.sendMessage(ChatColor.GREEN+"Player's gamemode has been set to "+gamemode+".");
            return;
        }
        ((Player) sender).setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
        sender.sendMessage(ChatColor.GOLD+"Your gamemode was changed to "+gamemode+".");
    }
}
