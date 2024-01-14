package dev.iiahmed.nfd;

import dev.iiahmed.nfd.command.SNFDCommand;
import dev.iiahmed.nfd.listener.DamageListener;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@Getter
public final class StrikeNFD extends JavaPlugin {

    private final String prefix = ChatColor.AQUA + "[StikeNFD] ";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("StrikeNFD").setExecutor(new SNFDCommand(this));
        Bukkit.getPluginManager().registerEvents(new DamageListener(this), this);
    }

    public boolean isContained(final String kitname){
        return getKits().contains(kitname);
    }

    public List<String> getKits() {
        return getConfig().getStringList("Kits");
    }

    public boolean toggleKit(final String kitname){
        if (getKits().contains(kitname)) {
            getKits().remove(kitname);
            saveConfig();
            return false;
        } else {
            getKits().add(kitname);
            saveConfig();
            return true;
        }
    }

}
