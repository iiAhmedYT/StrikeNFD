package dev.iiahmed.nfd.listener;

import dev.iiahmed.nfd.NFD;
import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    private final StrikePracticeAPI api = StrikePractice.getAPI();

    @EventHandler
    public void playerDamageEvent(EntityDamageEvent event) {
        if(event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (api.isInFight(player) && NFD.getInstance().isContained(api.getFight(player).getKit().getName())) {
                event.setCancelled(true);
            }
        }

    }


}
