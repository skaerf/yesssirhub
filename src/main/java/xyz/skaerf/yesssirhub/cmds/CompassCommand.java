package xyz.skaerf.yesssirhub.cmds;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.skaerf.yesssirhub.CompassGUI;
import xyz.skaerf.yesssirhub.Yesssirhub;

public class CompassCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[yesssirhub] This command can only be performed by players.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            CompassGUI.showGUI(player);
            return true;
        }
        if (!player.hasPermission("yesssirhub.compass")) {
            player.sendMessage(Component.text(ChatColor.RED+"Only administrators can perform this command."));
            return true;
        }
        if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            player.sendMessage(Component.text(ChatColor.RED+"Please make sure you are holding the item that you want to set as the compass!"));
            return true;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        Yesssirhub.getPlugin(Yesssirhub.class).getConfig().set("compass", item);
        Yesssirhub.getPlugin(Yesssirhub.class).saveConfig();
        Yesssirhub.compass = item;
        player.sendMessage(Component.text(ChatColor.GREEN+"Item has been saved."));
        return true;
    }
}
