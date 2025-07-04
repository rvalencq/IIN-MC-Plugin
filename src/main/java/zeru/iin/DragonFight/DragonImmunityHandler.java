package zeru.iin.DragonFight;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DragonImmunityHandler implements Listener {
    @EventHandler
    public void onExplosionDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof EnderDragon)) return;

        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION ||
                event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onArrowDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof EnderDragon dragon)) return;
        if (!(event.getDamager() instanceof Arrow)) return;

        double percentageHealth = dragon.getHealth() / dragon.getMaxHealth();
        if (percentageHealth <= 0.10) {
            event.setCancelled(true);
            event.getDamager().sendMessage("El dragon es inmune a las flechas!");
        }
    }
}

