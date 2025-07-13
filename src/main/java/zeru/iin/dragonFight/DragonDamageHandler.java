package zeru.iin.dragonFight;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DragonDamageHandler implements Listener {
    private final Map<UUID, Double> dragonDamageMap = new HashMap<>();
    @EventHandler
    public void onExplosionDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof EnderDragon)) return;

        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION ||
                event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onArrowDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof EnderDragon)) return;
        if (!(event.getDamager() instanceof Arrow arrow)) return;

        if (Math.random() < 0.5) {
            event.setCancelled(true);
            if (arrow.getShooter() instanceof Player player) {
                player.sendMessage("§d§lNo le hiciste daño al dragon!");
            }
        }
    }

    @EventHandler
    public void onDragonDamaged(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof EnderDragon)) return;
        if (!(event.getDamager() instanceof Player player)) return;

        double damage = event.getFinalDamage();
        dragonDamageMap.merge(player.getUniqueId(), damage, Double::sum);
    }

    @EventHandler
    public void onDragonDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof EnderDragon)) return;

        Player killer = event.getEntity().getKiller();
        if (killer != null) {
            giveReward(killer, new ItemStack(Material.ELYTRA));
        }
        
        List<Map.Entry<UUID, Double>> top3 = dragonDamageMap.entrySet().stream()
                .sorted(Map.Entry.<UUID, Double>comparingByValue().reversed())
                .limit(3)
                .toList();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(Component.text("-------------------------------------").color(NamedTextColor.YELLOW));
            if (killer != null) {
                player.sendMessage(Component.text("¡El Dragón ha Muerto a Manos de " + killer.getName() + "!"));
            } else {
                player.sendMessage(Component.text("¡El Dragón ha Muerto!"));
            }
            player.sendMessage(Component.text("-------------------------------------").color(NamedTextColor.YELLOW));
            player.sendMessage(Component.text()
                    .append(Component.text("🏆")
                            .color(NamedTextColor.YELLOW))
                    .append(Component.text(" Top Damage:")
                            .color(NamedTextColor.AQUA)));
            for (int i = 0; i < top3.size(); i++) {
                UUID uuid = top3.get(i).getKey();
                double dmg = top3.get(i).getValue();
                OfflinePlayer playerTop = Bukkit.getOfflinePlayer(uuid);
                player.sendMessage(Component.text()
                        .append(Component.text((i + 1) + ". ").color(NamedTextColor.WHITE))
                        .append(Component.text(playerTop.getName()).color(NamedTextColor.WHITE))
                        .append(Component.text(" -> ").color(NamedTextColor.LIGHT_PURPLE).decorate(TextDecoration.BOLD))
                        .append(Component.text(String.format("%.1f", dmg)))
                        .append(Component.text(" dmg")));

                if (!playerTop.isOnline()) continue;

                Player p = playerTop.getPlayer();

                switch (i) {
                    case 0 -> {
                        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
                        meta.addStoredEnchant(Enchantment.UNBREAKING, 4, true);
                        book.setItemMeta(meta);

                        giveReward(p, new ItemStack(Material.NETHERITE_INGOT, 3));
                        giveReward(p, new ItemStack(Material.ELYTRA));
                        giveReward(p, book);
                    }
                    case 1 -> {
                        giveReward(p, new ItemStack(Material.NETHERITE_INGOT, 3));
                        giveReward(p, new ItemStack(Material.ELYTRA));
                    }
                    case 2 -> giveReward(p, new ItemStack(Material.NETHERITE_INGOT, 3));
                }
            }
            player.sendMessage(Component.text("-------------------------------------").color(NamedTextColor.YELLOW));
            player.sendMessage(Component.text("Recompensas Entregadas a los Mejores!"));
            player.sendMessage(Component.text("-------------------------------------").color(NamedTextColor.YELLOW));
        }

        dragonDamageMap.clear();
    }

    private void giveReward(Player p, ItemStack item) {
        if (p.getInventory().firstEmpty() == -1) {
            p.getWorld().dropItemNaturally(p.getLocation(), item);
        } else {
            p.getInventory().addItem(item);
        }
    }
}

