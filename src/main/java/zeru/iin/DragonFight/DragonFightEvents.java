package zeru.iin.DragonFight;

import org.bukkit.*;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import zeru.iin.DragonFight.mobsLogic.CustomMobSpawner;
import zeru.iin.DragonFight.mobsLogic.CustomMobs;

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

        // Punishment
        DragonFightUtils.crystalBreakPunishment();
    }

    @EventHandler
    public void onPhaseChange(EnderDragonChangePhaseEvent event) {
        EnderDragon.Phase newPhase = event.getNewPhase();
        switch (newPhase) {
            case LAND_ON_PORTAL -> {
                // Bukkit.broadcastMessage("LAND_ON_PORTAL");
            }
            case LEAVE_PORTAL -> {
                CustomMobSpawner endGhast = CustomMobs.createEndGhast(25, 5);
                // DragonFightUtils.blindnessAllPlayers();
                CustomMobSpawner endBrute = CustomMobs.createEndBrute(8, 2);
                endBrute.spawn();
                endGhast.spawn();

                // Bukkit.broadcastMessage("LEAVE_PORTAL");
            }
            case HOVER -> {
                // DragonFightUtils.blindnessAllPlayers();
            }
            case FLY_TO_PORTAL -> {
                CustomMobSpawner endVex = CustomMobs.createEndVex(15, 3);
                endVex.spawn();
            }

        }
    }
}
