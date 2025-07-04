package zeru.iin.DragonFight;

import org.bukkit.*;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import zeru.iin.IIN;

public class DragonFightEvents implements Listener {
    @EventHandler
    public void onCrystalBreak(EntityDamageByEntityEvent event) {
        // Verifications
        if (event.getEntityType() != EntityType.END_CRYSTAL) return;
        Player player = null;
        if (event.getDamager() instanceof Player p) {
            player = p;
        } else if (event.getDamager() instanceof Arrow arrow && arrow.getShooter() instanceof Player shooter) {
            player = shooter;
        }
        if (player == null) return;
        if (!player.getWorld().getEnvironment().equals(World.Environment.THE_END)) return;
        DragonBattle battle = player.getWorld().getEnderDragonBattle();
        if (battle == null || battle.getEnderDragon() == null || battle.getEnderDragon().isDead()) return;

        // Lightning Strike
        Location location = event.getEntity().getLocation();
        World world = location.getWorld();
        world.strikeLightning(location);

        // Player punishment
        DragonFightUtils.playerPunishment(player);
    }
}
