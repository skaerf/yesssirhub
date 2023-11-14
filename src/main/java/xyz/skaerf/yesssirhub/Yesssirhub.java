package xyz.skaerf.yesssirhub;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.skaerf.yesssirhub.cmds.CompassCommand;
import xyz.skaerf.yesssirhub.cmds.GamemodeCommand;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public final class Yesssirhub extends JavaPlugin {

    private static LuckPerms luckPerms;

    public static ItemStack compass;

    @Override
    public void onEnable() {
        getCommand("gm").setExecutor(new GamemodeCommand());
        getCommand("compass").setExecutor(new CompassCommand());
        getServer().getPluginManager().registerEvents(new Events(), this);
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
        this.saveConfig();
        if (this.getConfig().getItemStack("compass") == null) {
            this.getLogger().warning("[yesssirhub] The server compass item is null. Please add one by creating the item you want to use and running /compass create.");
        }
        else {
            compass = this.getConfig().getItemStack("compass");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public static boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

    public static void switchServer(Player player, String server) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(Yesssirhub.getPlugin(Yesssirhub.class), "BungeeCord", byteArrayOutputStream.toByteArray());
    }
}
