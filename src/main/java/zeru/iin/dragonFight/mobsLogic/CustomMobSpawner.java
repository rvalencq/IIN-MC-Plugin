package zeru.iin.dragonFight.mobsLogic;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;
import zeru.iin.IIN;

import java.util.function.Consumer;
import java.util.concurrent.ThreadLocalRandom;

public class CustomMobSpawner {
    private final IIN plugin = IIN.getPlugin();
    private final EntityType entityType;
    private final int maxTotal;
    private final int perPlayer;
    private final int radius;
    private final int attempts;
    private final Consumer<LivingEntity> config;
    private final MobSpawnType spawnType;
    private final String id;

    public CustomMobSpawner(EntityType entityType, MobSpawnType spawnType, String id, int maxTotal, int perPlayer, int radio, int attempts, Consumer<LivingEntity> config) {
        this.entityType = entityType;
        this.id = id;
        this.maxTotal = maxTotal;
        this.perPlayer = perPlayer;
        this.radius = radio;
        this.attempts = attempts;
        this.config = config;
        this.spawnType = spawnType;
    }

    public void spawn() {
        Location arenaCenter = new Location(Bukkit.getWorld("world_the_end"), 0, 70, 0);
        World world = arenaCenter.getWorld();
        int actualMobs = world.getEntitiesByClass(entityType.getEntityClass()).size();
        int availableMobs = maxTotal - actualMobs;

        if (availableMobs <= 0) return;

        int total = Math.min(availableMobs, world.getPlayers().size() * perPlayer);

        for (int i = 0; i < total; i++) {
            Location loc = switch (spawnType) {
                case AIR -> generateAirPos(arenaCenter, radius, attempts);
                case GROUND -> generateGroundPos(arenaCenter, radius, attempts);
            };
            LivingEntity entity = (LivingEntity) world.spawnEntity(loc, entityType);

            NamespacedKey key = new NamespacedKey(plugin, "mob_id");
            entity.getPersistentDataContainer().set(key, PersistentDataType.STRING, id);

            config.accept(entity);
        }
    }

    private Location generateAirPos(Location center, int radius, int attempts) {
        World world = center.getWorld();
        int height = 15;
        for (int i = 0; i < attempts; i++) {
            double angle = ThreadLocalRandom.current().nextDouble(0, 2 * Math.PI);
            double distance = ThreadLocalRandom.current().nextDouble(10, radius);
            double x = center.getX() + Math.cos(angle) * distance;
            double z = center.getZ() + Math.sin(angle) * distance;
            double y = center.getY() + height;

            Location pos = new Location(world, x, y, z);
            if (availableAirZone(pos)) return pos;
        }

        return center.clone().add(0, height, 0); // fallback
    }

    private Location generateGroundPos(Location center, int radius, int attempts) {
        World world = center.getWorld();

        for (int i = 0; i < attempts; i++) {
            double angle = ThreadLocalRandom.current().nextDouble(0, 2 * Math.PI);
            double distance = ThreadLocalRandom.current().nextDouble(5, radius);
            double x = center.getX() + Math.cos(angle) * distance;
            double z = center.getZ() + Math.sin(angle) * distance;
            int y = world.getHighestBlockYAt((int) x, (int) z);

            Location pos = new Location(world, x, y + 1, z);
            if (isGroundSpawnable(pos)) return pos;
        }

        return center.clone();
    }

    private boolean availableAirZone(Location loc) {
        World mundo = loc.getWorld();
        for (int y = 0; y <= 2; y++) {
            if (!mundo.getBlockAt(loc.clone().add(0, y, 0)).isPassable()) return false;
        }
        return true;
    }

    private boolean isGroundSpawnable(Location loc) {
        Block below = loc.clone().add(0, -1, 0).getBlock();
        Material type = below.getType();

        boolean solidBase = type.isSolid() && !type.isAir()
                && !type.toString().contains("WATER")
                && !type.toString().contains("LAVA");

        Block onTop = loc.getBlock();
        Block onTop2 = loc.clone().add(0, 1, 0).getBlock();
        boolean freeSpace = onTop.isPassable() && onTop2.isPassable();

        return solidBase && freeSpace;
    }
}

