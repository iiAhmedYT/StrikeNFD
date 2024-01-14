package dev.iiahmed.nfd.listener;

import dev.iiahmed.nfd.StrikeNFD;
import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public final class DamageListener implements Listener {

    private final StrikeNFD plugin;
    private final StrikePracticeAPI api = StrikePractice.getAPI();

    public DamageListener(final StrikeNFD plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerDamageEvent(final EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player)event.getEntity();
        if (api.isInFight(player) && plugin.isContained(api.getFight(player).getKit().getName())) {
            event.setCancelled(true);
        }
    }


}
