package zeru.iin.dragonFight;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import zeru.iin.dragonFight.mobsLogic.CustomMobSpawner;
import zeru.iin.dragonFight.mobsLogic.CustomMobs;

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

    public static void crystalBreakPunishment() {
        // Bukkit.broadcast(Component.text("Has sido cegado por romper un cristal del End...").color(NamedTextColor.DARK_GRAY).decorate(TextDecoration.BOLD));
        // blindnessAllPlayers();
        Bukkit.broadcast(Component.text("¡Han sido castigados por romper un Cristal del End!").color(NamedTextColor.DARK_GRAY).decorate(TextDecoration.BOLD));
        spawnMobsWhenCrystalBreak();
    }

    public static void blindnessAllPlayers() {
        World theEnd = Bukkit.getWorld("world_the_end");
        for (Player player : theEnd.getPlayers()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10 * 20, 0, false, false));
        }
    }

    public static void spawnMobsWhenCrystalBreak() {
        CustomMobSpawner endCreeper = CustomMobs.createEndCreeper(20, 5);
        CustomMobSpawner endBlaze = CustomMobs.createEndBlaze(24, 6);
        CustomMobSpawner endSkeleton = CustomMobs.createEndSkeleton(40, 8);

        endCreeper.spawn();
        endBlaze.spawn();
        endSkeleton.spawn();
    }

    public boolean allCrystalBreak(World end) {
        return end.getEntitiesByClass(EnderCrystal.class).isEmpty();
    }
}
