package zeru.iin.DragonFight;

import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CustomDragonSpawn implements Listener {
    @EventHandler
    public void onDragonSpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() == EntityType.ENDER_DRAGON) {
            EnderDragon dragon = (EnderDragon) event.getEntity();
            dragon.setMaxHealth(1000.0); // Default = 200.0
            dragon.setHealth(600.0);
            dragon.setCustomName("§5Ascended Ender Dragon");
        }
    }
}
