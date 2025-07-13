package zeru.iin.dragonFight;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import zeru.iin.IIN;

public class CustomDragonSpawn implements Listener {
    private final IIN plugin;
    public CustomDragonSpawn(IIN plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDragonSpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.ENDER_DRAGON) {
            EnderDragon dragon = (EnderDragon) event.getEntity();
            dragon.setCustomName("§5Ascended Ender Dragon");
            dragon.getAttribute(Attribute.MAX_HEALTH).setBaseValue(1500.0);

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (dragon.isValid()) {
                    double maxHealth = dragon.getAttribute(Attribute.MAX_HEALTH).getValue();
                    dragon.heal(maxHealth);
                }
            }, 1L);
        }
    }
}
