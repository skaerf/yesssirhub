package xyz.skaerf.yesssirhub;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Events implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("yesssirhub.modifyblocks")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to break blocks here."));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBuild(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("yesssirhub.modifyblocks")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to place blocks here."));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHit(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setFoodLevel(20);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        String prefix = "&7";
        String message = ((TextComponent) event.message()).content();
        User user = Yesssirhub.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
        if (user.getCachedData().getMetaData().getPrefix() != null)
            prefix = user.getCachedData().getMetaData().getPrefix();
        if (!event.isCancelled()) {
            event.setCancelled(true);
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + player.getName() + " &8&l>> &f" + message));
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', prefix + player.getName() + " &8&l>> &f" + message)));
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().equals(Yesssirhub.compass)) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String prefix = "&7";
        User user = Yesssirhub.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
        if (user.getCachedData().getMetaData().getPrefix() != null)
            prefix = user.getCachedData().getMetaData().getPrefix();
        if (!Yesssirhub.isVanished(player)) for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&a+&7] " + prefix + player.getName()));
        }
        player.teleport(player.getLocation().getWorld().getSpawnLocation().toCenterLocation());
        //player.showTitle(Title.title(Component.text(ChatColor.translateAlternateColorCodes('&', "&bWelcome to &lyesssirnet")), Component.text("Enjoy your stay!")));
        player.sendPlayerListHeaderAndFooter(Component.text(ChatColor.translateAlternateColorCodes('&', "&b&lyesssirnet - HUB")), Component.text());
        if (Yesssirhub.compass != null) player.getInventory().setItem(0, Yesssirhub.compass);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().equals(Yesssirhub.compass)) {
            event.setCancelled(true);
            CompassGUI.showGUI(player);
        }
    }

    @EventHandler
    public void onInv(InventoryClickEvent event) {
        NamespacedKey selector = new NamespacedKey(Yesssirhub.getPlugin(Yesssirhub.class), "selectorIcon");
        NamespacedKey whatsNew = new NamespacedKey(Yesssirhub.getPlugin(Yesssirhub.class), "whatsNewIcon");
        NamespacedKey tebex = new NamespacedKey(Yesssirhub.getPlugin(Yesssirhub.class), "tebexIcon");
        NamespacedKey discord = new NamespacedKey(Yesssirhub.getPlugin(Yesssirhub.class), "discordIcon");

        NamespacedKey lifesteal = new NamespacedKey(Yesssirhub.getPlugin(Yesssirhub.class), "lifestealIcon");
        NamespacedKey box = new NamespacedKey(Yesssirhub.getPlugin(Yesssirhub.class), "boxIcon");
        NamespacedKey gens = new NamespacedKey(Yesssirhub.getPlugin(Yesssirhub.class), "gensIcon");

        if (event.getInventory().equals(CompassGUI.getPlayerInv((Player) event.getWhoClicked()))) {
            if (event.isShiftClick()) {
                event.setCancelled(true);
                return;
            }
            if (event.getCurrentItem().getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
                event.setCancelled(true);
            }
            else if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(selector)) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                CompassGUI.openSelectorGUI((Player)event.getWhoClicked());
            }
            else if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(discord)) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&7Our Discord can be found at https://discord.com/invite/CSPH9PREKb.")));
            }
            else if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(tebex)) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&7Our store can be found at https://yesssirbox.tebex.io/.")));
            }
            else if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(whatsNew)) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&aWhat's New?")));
            }


            else if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(lifesteal)) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                if (!event.getWhoClicked().hasPermission("yesssirhub.lifesteal")) {
                    event.getWhoClicked().sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&cThanks for the enthusiasm, but Lifesteal isn't available yet!")));
                }
                else {
                    event.getWhoClicked().sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&aSending you to Lifesteal (you have permission)...")));
                    Yesssirhub.switchServer((Player) event.getWhoClicked(), "dupelifesteal");
                }
            }
            else if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(box)) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&aSending you to Box...")));
                Yesssirhub.switchServer((Player) event.getWhoClicked(), "box");
            }
            else if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(gens)) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                if (!event.getWhoClicked().hasPermission("yesssirhub.gens")) {
                    event.getWhoClicked().sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&cThanks for the enthusiasm, but Gens isn't available yet!")));
                }
                else {
                    event.getWhoClicked().sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&aSending you to Gens (you have permission)...")));
                    Yesssirhub.switchServer((Player) event.getWhoClicked(), "gens");
                }
            }
        }
    }
}
