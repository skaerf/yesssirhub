package xyz.skaerf.yesssirhub;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompassGUI {

    private static final HashMap<Player, Inventory> playerInvList = new HashMap<>();

    public static void showGUI(Player player) {
        String invName = "&a&lMain Menu";
        Inventory inventory = Bukkit.createInventory(null, 27, Component.text(ChatColor.translateAlternateColorCodes('&', invName)));

        // selector item
        ItemStack selector = new ItemStack(Material.COMPASS);
        ItemMeta selectorMeta = selector.getItemMeta();
        selectorMeta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', "&c&lServers")));
        NamespacedKey key = new NamespacedKey(Yesssirhub.getPlugin(Yesssirhub.class), "selectorIcon");
        selectorMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
        selector.setItemMeta(selectorMeta);
        inventory.setItem(10, selector);

        // 'what's new' item
        ItemStack whatsNew = new ItemStack(Material.BOOK);
        ItemMeta whatsNewMeta = whatsNew.getItemMeta();
        whatsNewMeta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', "&a&lWhat's New?")));
        key = new NamespacedKey(Yesssirhub.getPlugin(Yesssirhub.class), "whatsNewIcon");
        whatsNewMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
        whatsNew.setItemMeta(whatsNewMeta);
        inventory.setItem(12, whatsNew);

        // tebex item
        ItemStack tebex = new ItemStack(Material.EMERALD);
        ItemMeta tebexMeta = tebex.getItemMeta();
        tebexMeta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', "&d&lTebex")));
        key = new NamespacedKey(Yesssirhub.getPlugin(Yesssirhub.class), "tebexIcon");
        tebexMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
        tebex.setItemMeta(tebexMeta);
        inventory.setItem(14, tebex);

        // discord item
        ItemStack discord = new ItemStack(Material.GRAY_GLAZED_TERRACOTTA);
        ItemMeta discordMeta = discord.getItemMeta();
        discordMeta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', "&6&lDiscord")));
        key = new NamespacedKey(Yesssirhub.getPlugin(Yesssirhub.class), "discordIcon");
        discordMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
        discord.setItemMeta(discordMeta);
        inventory.setItem(16, discord);

        inventory = fillRemainder(inventory);

        playerInvList.put(player, inventory);
        player.openInventory(inventory);
    }

    public static Inventory getPlayerInv(Player player) {
        return playerInvList.get(player);
    }

    public static void openSelectorGUI(Player player) {
        String selectorName = "&c&lServers";
        Inventory inventory = Bukkit.createInventory(null, 27, Component.text(ChatColor.translateAlternateColorCodes('&', selectorName)));

        // lifesteal item
        ItemStack lifesteal = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta lifestealMeta = lifesteal.getItemMeta();
        lifestealMeta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', "&c&lDupe Lifesteal")));
        NamespacedKey key = new NamespacedKey(Yesssirhub.getPlugin(Yesssirhub.class), "lifestealIcon");
        lifestealMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(ChatColor.translateAlternateColorCodes('&', "&4&lWORK IN PROGRESS")));
        lifestealMeta.lore(lore);
        lifesteal.setItemMeta(lifestealMeta);
        inventory.setItem(10, lifesteal);

        // box item
        ItemStack box = new ItemStack(Material.BOOK);
        ItemMeta boxMeta = box.getItemMeta();
        boxMeta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', "&e&lBox")));
        key = new NamespacedKey(Yesssirhub.getPlugin(Yesssirhub.class), "boxIcon");
        boxMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
        lore = new ArrayList<>();
        lore.add(Component.text(ChatColor.translateAlternateColorCodes('&', "&b&lSEASON ONE")));
        lifestealMeta.lore(lore);
        box.setItemMeta(boxMeta);
        inventory.setItem(13, box);

        // gens item
        ItemStack gens = new ItemStack(Material.EMERALD);
        ItemMeta gensMeta = gens.getItemMeta();
        gensMeta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', "&b&lGens")));
        key = new NamespacedKey(Yesssirhub.getPlugin(Yesssirhub.class), "gensIcon");
        gensMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
        lore = new ArrayList<>();
        lore.add(Component.text(ChatColor.translateAlternateColorCodes('&', "&4&lWORK IN PROGRESS")));
        lifestealMeta.lore(lore);
        gens.setItemMeta(gensMeta);
        inventory.setItem(16, gens);

        inventory = fillRemainder(inventory);

        playerInvList.put(player, inventory);
        player.openInventory(inventory);
    }

    private static Inventory fillRemainder(Inventory inventory) {
        // placeholder item
        ItemStack placeholder = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.displayName(Component.text(""));
        placeholder.setItemMeta(placeholderMeta);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, placeholder);
            }
        }
        return inventory;
    }
}
