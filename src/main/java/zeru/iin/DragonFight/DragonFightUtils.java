package zeru.iin.DragonFight;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import zeru.iin.DragonFight.mobsLogic.CustomMobSpawner;
import zeru.iin.DragonFight.mobsLogic.CustomMobs;
import zeru.iin.DragonFight.mobsLogic.MobSpawnType;

public class DragonFightUtils {
    public static void throwFireball(Location objective) {
        Location origin = objective.clone().add(0, 60, 0);
        Vector direction = objective.toVector().subtract(origin.toVector()).normalize();

        LargeFireball fireball = (LargeFireball) objective.getWorld().spawnEntity(origin, EntityType.FIREBALL);
        fireball.setDirection(direction);
        fireball.setYield(5.0f);
        fireball.setIsIncendiary(true);

        objective.getWorld().spawnParticle(
                Particle.LARGE_SMOKE,
                objective.add(0, 0.3, 0),
                150,
                1.5, 0.2, 1.5,
                0.0
        );
    }

    public static void playerPunishment(Player player) {
        player.sendMessage("§8Has sido cegado por romper un cristal del End...");
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.BLINDNESS,
                60, // TICKS
                0,
                false,
                false
        ));
        throwFireball(player.getLocation());
        spawnMobs();
    }


    public static void spawnMobs() {
        CustomMobSpawner endBlaze = CustomMobs.createEndBlaze(30, 5);
        CustomMobSpawner endCreeper = CustomMobs.createEndCreeper(15, 5);
        CustomMobSpawner endGhast = CustomMobs.createEndGhast(30, 5);
        CustomMobSpawner endSkeleton = CustomMobs.createEndSkeleton(30, 10);
        CustomMobSpawner endVex = CustomMobs.createEndVex(30, 3);
        endBlaze.spawn();
        endCreeper.spawn();
        endGhast.spawn();
        endSkeleton.spawn();
        endVex.spawn();
    }
    public boolean allCrystalBreak(World end) {
        return end.getEntitiesByClass(EnderCrystal.class).isEmpty();
    }
}
