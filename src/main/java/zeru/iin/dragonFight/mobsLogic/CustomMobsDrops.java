package zeru.iin.dragonFight.mobsLogic;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import zeru.iin.IIN;

import java.util.concurrent.ThreadLocalRandom;

public class CustomMobsDrops implements Listener {
    private final IIN plugin;
    public CustomMobsDrops(IIN plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        NamespacedKey key = new NamespacedKey(plugin, "mob_id");

        String id = entity.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (id == null) return;

        event.getDrops().clear();
        // event.setDroppedExp(0);

        switch (id) {
            case "endBlaze" -> {
                if (Math.random() < 0.10) {
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.GOLDEN_APPLE));
                }
            }
            case "endSkeleton" -> {
                if (Math.random() < 0.50) {
                    int amount = ThreadLocalRandom.current().nextInt(1, 4);
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.EXPERIENCE_BOTTLE, amount));
                }
            }
        }
    }
}
