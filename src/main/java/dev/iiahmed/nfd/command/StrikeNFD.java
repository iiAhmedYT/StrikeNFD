package dev.iiahmed.nfd.command;

import dev.iiahmed.nfd.NFD;
import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StrikeNFD implements CommandExecutor {

    private final String prefix = NFD.getInstance().getPrefix();
    private final ComponentBuilder helpMessage;

    public StrikeNFD(){
        helpMessage = new ComponentBuilder("\n").color(ChatColor.YELLOW);
        //TODO make it in a better way (for feature updates)
        helpMessage.append("  • /StrikeNFD list");
        helpMessage.append("  • /StrikeNFD toggle <kit>");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            return true;
        }
        Player player = (Player) sender;

        if (player.hasPermission("strikepractice.admin")) {
            if (args.length == 0) {
                player.spigot().sendMessage(helpMessage.create());
                return true;
            }
            String subcommand = args[0];
            switch (subcommand){
                case "list":
                    if(NFD.getInstance().getKits().isEmpty()){
                        player.sendMessage(ChatColor.RED + "You don't have any toggled kits");
                        return true;
                    }
                    ComponentBuilder builder = new ComponentBuilder("      ").color(ChatColor.YELLOW)
                            .append("Toggled Kits");
                    StrikePracticeAPI api = StrikePractice.getAPI();
                    for(String kit : NFD.getInstance().getKits()){
                        boolean exist = api.getKit(kit)!=null;
                        builder.append("\n ").color(exist?ChatColor.GREEN:ChatColor.RED).append("•")
                                .append(" ").append(kit);
                    }
                    player.spigot().sendMessage(builder.create());
                    return true;
                case "toggle":
                    if (args.length == 1) {
                        player.sendMessage(prefix + ChatColor.RED + "USAGE: /StrikeNFD toggle <kit>");
                        return true;
                    }
                    StrikePracticeAPI strikePracticeAPI = StrikePractice.getAPI();
                    boolean exist = strikePracticeAPI.getKit(args[1])!= null;
                    final boolean toggle = NFD.getInstance().toggleKit(args[1]);
                    final String type = toggle?ChatColor.GREEN+"ON":ChatColor.RED+"OFF";
                    player.sendMessage(prefix + ChatColor.YELLOW + "Kit " + args[1]
                            + "has been toggled successfully to " + type);
                    if(!exist) player.sendMessage(ChatColor.RED + "THIS KIT DOES NOT EXIST BUT" +
                            " IT'S ADDED ANYWAYS, if you wish to remove it just re-toggle it");
                    return true;
            }

        }
        player.spigot().sendMessage(helpMessage.create());
        return true;
    }
}
