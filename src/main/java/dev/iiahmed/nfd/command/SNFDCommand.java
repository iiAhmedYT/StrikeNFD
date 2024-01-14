package dev.iiahmed.nfd.command;

import dev.iiahmed.nfd.StrikeNFD;
import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class SNFDCommand implements CommandExecutor {

    private final StrikeNFD plugin;
    private final ComponentBuilder helpMessage;

    public SNFDCommand(final StrikeNFD plugin) {
        this.plugin = plugin;
        helpMessage = new ComponentBuilder("\n").color(ChatColor.YELLOW);
        helpMessage.append("  • /StrikeNFD list");
        helpMessage.append("  • /StrikeNFD toggle <kit>");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("strikepractice.admin")) {
            return true;
        }

        if (args.length == 0) {
            player.spigot().sendMessage(helpMessage.create());
            return true;
        }

        String subcommand = args[0];
        StrikePracticeAPI api = StrikePractice.getAPI();
        switch (subcommand) {
            case "list":
                if (plugin.getKits().isEmpty()){
                    player.sendMessage(ChatColor.RED + "You don't have any toggled kits");
                    return true;
                }
                ComponentBuilder builder = new ComponentBuilder("      ").color(ChatColor.YELLOW)
                        .append("Toggled Kits");

                for (String kit : plugin.getKits()) {
                    boolean exist = api.getKit(kit) != null;
                    builder.append("\n ")
                            .color(exist ? ChatColor.GREEN : ChatColor.RED)
                            .append("• ").append(kit);
                }

                player.spigot().sendMessage(builder.create());
                return true;
            case "toggle":
                if (args.length == 1) {
                    player.sendMessage(plugin.getPrefix() + ChatColor.RED + "USAGE: /StrikeNFD toggle <kit>");
                    return true;
                }

                boolean exist = api.getKit(args[1]) != null;
                if (!exist) {
                    player.sendMessage(ChatColor.RED + "THIS KIT DOES NOT EXIST");
                    return true;
                }
                final boolean toggle = plugin.toggleKit(args[1]);
                final String type = toggle ? ChatColor.GREEN + "ON" : ChatColor.RED + "OFF";
                player.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "Kit " + args[1] + " has been toggled successfully " + type);
                return true;
        }
        return true;
    }
}
