package zeru.iin.dragonFight;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
            int playersEnd = Bukkit.getWorld("world_the_end").getPlayers().size();
            float baseHealth = (200*playersEnd) + 500;
            dragon.customName(Component.text("Ascended Ender Dragon").color(NamedTextColor.LIGHT_PURPLE));
            dragon.getAttribute(Attribute.MAX_HEALTH).setBaseValue(baseHealth);

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (dragon.isValid()) {
                    dragon.heal(baseHealth);
                }
            }, 1L);
        }
    }
}
