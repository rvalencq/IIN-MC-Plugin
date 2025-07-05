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
        if (newPhase == EnderDragon.Phase.LAND_ON_PORTAL) {
            CustomMobSpawner endBrute = CustomMobs.createEndBrute(15, 3);
            endBrute.spawn();
            // Bukkit.broadcastMessage("LAND_ON_PORTAL");
        } else if (newPhase == EnderDragon.Phase.LEAVE_PORTAL) {
            CustomMobSpawner endGhast = CustomMobs.createEndGhast(25, 5);
            DragonFightUtils.blindnessAllPlayers();
            // Bukkit.broadcastMessage("LEAVE_PORTAL");
            endGhast.spawn();
        } else if (newPhase == EnderDragon.Phase.HOVER) {
            DragonFightUtils.blindnessAllPlayers();
            CustomMobSpawner endVex = CustomMobs.createEndVex(15, 3);
            endVex.spawn();
        }
    }
}
