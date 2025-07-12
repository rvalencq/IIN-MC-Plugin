package zeru.iin.DragonFight.mobsLogic;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CustomMobs {
    public static CustomMobSpawner createEndGhast(int maxTotal, int perPlayer) {

        return new CustomMobSpawner(
            EntityType.GHAST,
            MobSpawnType.AIR,
            "endGhast",
            maxTotal,
            perPlayer,
            35,
            25,
            mob -> {
                mob.setCustomName("§lEnder Ghast");
                mob.setCustomNameVisible(true);
                mob.setGlowing(true);
                mob.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 3*60*20, 1, false, false));
                mob.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30*20, 1, false, false));
            }
        );
    }

    public static CustomMobSpawner createEndBlaze(int maxTotal, int perPlayer) {
        return new CustomMobSpawner(
            EntityType.BLAZE,
            MobSpawnType.AIR,
            "endBlaze",
                maxTotal,
                perPlayer,
            35,
            25,
            mob -> {
                mob.setCustomName("§e§lEnder Blaze");
                mob.setCustomNameVisible(true);
                mob.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60*20, 0, false, false));
            }
        );
    }

    public static CustomMobSpawner createEndCreeper(int maxTotal, int perPlayer) {

        return new CustomMobSpawner(
                EntityType.CREEPER,
                MobSpawnType.GROUND,
                "endCreeper",
                maxTotal,
                perPlayer,
                35,
                25,
                mob -> {
                    mob.setCustomName("§b§lEnder Creeper");
                    mob.setCustomNameVisible(true);
                    mob.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15*20, 0, false, false));
                    mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 60*20, 0, false, false));
                    mob.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60*20, 2, false, false));
                    Creeper creeper = (Creeper) mob;
                    creeper.setPowered(true);
                }
        );
    }

    public static CustomMobSpawner createEndSkeleton(int maxTotal, int perPlayer) {
        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 4, true);
        helmet.setItemMeta(helmetMeta);

        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.addEnchant(Enchantment.POWER, 3, true);
        bowMeta.addEnchant(Enchantment.PUNCH, 1, true);
        bow.setItemMeta(bowMeta);


        return new CustomMobSpawner(
                EntityType.SKELETON,
                MobSpawnType.GROUND,
                "endSkeleton",
                maxTotal,
                perPlayer,
                35,
                25,
                mob -> {
                    mob.setCustomName("§7§lEnder Skeleton");
                    mob.setCustomNameVisible(true);
                    mob.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120*20, 2, false, false));
                    mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 60*20, 0, false, false));
                    mob.getEquipment().setHelmet(helmet);
                    mob.getEquipment().setHelmetDropChance(0f);
                    mob.getEquipment().setItemInMainHand(bow);
                    mob.getEquipment().setItemInMainHandDropChance(0f);
                    mob.getEquipment().setItemInOffHand(new ItemStack(Material.EXPERIENCE_BOTTLE));
                    mob.getEquipment().setItemInOffHandDropChance(0f);
                }
        );
    }

    public static CustomMobSpawner createEndVex(int maxTotal, int perPlayer) {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(Enchantment.KNOCKBACK, 3, true);
        item.setItemMeta(itemMeta);

        return new CustomMobSpawner(
                EntityType.VEX,
                MobSpawnType.GROUND,
                "endSkeleton",
                maxTotal,
                perPlayer,
                35,
                25,
                mob -> {
                    mob.setCustomName("§7§lEnder Vex");
                    mob.setCustomNameVisible(true);
                    mob.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 120*20, 1, false, false));
                    mob.getEquipment().setItemInMainHand(item);
                    mob.getEquipment().setItemInMainHandDropChance(0f);
                }
        );
    }
    public static CustomMobSpawner createEndBrute(int maxTotal, int perPlayer) {
        return new CustomMobSpawner(
                EntityType.PIGLIN_BRUTE,
                MobSpawnType.GROUND,
                "endBrute",
                maxTotal,
                perPlayer,
                35,
                25,
                mob -> {
                    mob.setCustomName("§0§lEnder Brute");
                    mob.setCustomNameVisible(true);
                    mob.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 120*20, 1, false, false));
                    mob.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
                    mob.getEquipment().setHelmetDropChance(0f);
                    mob.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                    mob.getEquipment().setChestplateDropChance(0f);
                    mob.getEquipment().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                    mob.getEquipment().setLeggingsDropChance(0f);
                    mob.getEquipment().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                    mob.getEquipment().setBootsDropChance(0f);
                    mob.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_AXE));
                    mob.getEquipment().setItemInMainHandDropChance(0f);
                    if (mob instanceof PiglinBrute brute) {
                        brute.setImmuneToZombification(true);
                    }
                }
        );
    }
}
