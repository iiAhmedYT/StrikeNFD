package dev.iiahmed.nfd;

import dev.iiahmed.nfd.command.StrikeNFD;
import dev.iiahmed.nfd.listener.DamageListener;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Getter
public final class NFD extends JavaPlugin {

    @Getter private static NFD instance;
    private List<String> kits;
    private FileConfiguration config;
    private final String prefix = ChatColor.AQUA + "[StikeNFD] ";

    @Override
    public void onEnable() {
        instance = this;
        File configFile = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!(configFile.exists())){
            this.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        kits = config.getStringList("Kits");
        getCommand("StrikeNFD").setExecutor(new StrikeNFD());
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
    }

    public boolean isContained(String kitname){
        return kits.contains(kitname);
    }

    public boolean toggleKit(String kitname){
        if(kits.contains(kitname)){
            kits.remove(kitname);
            updateFile();
            return false;
        } else {
            kits.add(kitname);
            updateFile();
            return true;
        }
    }

    private void updateFile(){
        try {
            config.save(new File(this.getDataFolder() + File.separator + "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
