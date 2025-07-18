package zeru.iin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zeru.iin.customItems.CustomItemCreator;
import zeru.iin.customItems.CustomItemType;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LootBoxCommand implements CommandExecutor, TabCompleter {
    private final CustomItemCreator creator;
    public LootBoxCommand(CustomItemCreator creator) {
        this.creator = creator;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof BlockCommandSender blockSender)) return false;
        if (args.length == 0) {
            blockSender.sendMessage(Component.text("Falta el tipo de lootbox: standard o premium").color(NamedTextColor.RED));
            return true;
        }

        Location cbLoc = blockSender.getBlock().getLocation();
        Player player = getClosestPlayer(cbLoc, 5);

        if (player == null) return true;

        switch (args[0]) {
            case "standard" -> {
                if (consumeKey(player, CustomItemType.LOOTBOX_STANDARD_KEY)) return true;
                dropRandomItem(player,"standard");
            }
            case "premium" -> {
                if (consumeKey(player, CustomItemType.LOOTBOX_PREMIUM_KEY)) return true;
                dropRandomItem(player,"premium");
            }
        }

        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("standard", "premium");
        }
        return List.of();
    }


    private boolean consumeKey(Player player, CustomItemType key) {
        Inventory inventory = player.getInventory();
        for (ItemStack item : inventory.getContents()) {
            if (creator.isCustomItem(item, key)) {
                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                } else inventory.removeItem(item);
                return false;
            }
        }

        player.sendMessage(Component.text("No Tienes Ninguna Llave!").color(NamedTextColor.RED));
        return true;
    }

    public Player getClosestPlayer(Location commandBlockLoc, double radius) {
        return commandBlockLoc.getWorld().getNearbyEntities(commandBlockLoc, radius, radius, radius).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .min(Comparator.comparingDouble(p -> p.getLocation().distanceSquared(commandBlockLoc)))
                .orElse(null);
    }


    private void dropRandomItem(Player player, String type) {
        World world = player.getWorld();
        Location loc = player.getLocation();
        List<ItemStack> lootPool;
        Random random = ThreadLocalRandom.current();

        double chance = random.nextDouble();
        if (type.equalsIgnoreCase("standard") && chance < 0.001) {
            ItemStack rareDropStandard = new ItemStack(Material.STICK);
            rareDropStandard.addUnsafeEnchantment(Enchantment.KNOCKBACK, 20);
            giveReward(player, rareDropStandard, type);
            world.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.2f);
            return;
        } else if (type.equalsIgnoreCase("premium") && chance < 0.01) {
            ItemStack rareDropPremium = new ItemStack(Material.BOW);
            rareDropPremium.addEnchantment(Enchantment.INFINITY, 1);
            rareDropPremium.addEnchantment(Enchantment.UNBREAKING, 3);
            rareDropPremium.addEnchantment(Enchantment.POWER, 5);
            rareDropPremium.addEnchantment(Enchantment.MENDING, 5);
            giveReward(player, rareDropPremium, type);
            world.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.2f);
            return;
        }

        int randomAmount = random.nextInt(1, 4);
        if (type.equalsIgnoreCase("standard")) {
            lootPool = List.of(
                    new ItemStack(Material.DIAMOND, randomAmount),
                    new ItemStack(Material.NETHERITE_SCRAP, 2),
                    new ItemStack(Material.GOLDEN_APPLE),
                    new ItemStack(Material.COAL)
                    );
        } else if (type.equalsIgnoreCase("premium")) {
            lootPool = List.of(
                    new ItemStack(Material.COAL),
                    createCustomEnchant(Enchantment.UNBREAKING, 4),
                    createCustomEnchant(Enchantment.UNBREAKING, 3),
                    createCustomEnchant(Enchantment.PROTECTION, 5),
                    createCustomEnchant(Enchantment.PROTECTION, 4),
                    createCustomEnchant(Enchantment.PROJECTILE_PROTECTION, 5),
                    createCustomEnchant(Enchantment.PROJECTILE_PROTECTION, 4),
                    createCustomEnchant(Enchantment.BLAST_PROTECTION, 5),
                    createCustomEnchant(Enchantment.BLAST_PROTECTION, 4),
                    createCustomEnchant(Enchantment.FIRE_PROTECTION, 5),
                    createCustomEnchant(Enchantment.FIRE_PROTECTION, 4),
                    createCustomEnchant(Enchantment.FORTUNE, 4),
                    createCustomEnchant(Enchantment.FORTUNE, 3),
                    createCustomEnchant(Enchantment.LOOTING, 4),
                    createCustomEnchant(Enchantment.LOOTING, 3),
                    createCustomEnchant(Enchantment.SHARPNESS, 5),
                    createCustomEnchant(Enchantment.SHARPNESS, 6),
                    createCustomEnchant(Enchantment.EFFICIENCY, 8),
                    createCustomEnchant(Enchantment.EFFICIENCY, 5),
                    new ItemStack(Material.ENCHANTED_GOLDEN_APPLE),
                    new ItemStack(Material.DIAMOND_BLOCK),
                    new ItemStack(Material.DIAMOND_HELMET),
                    new ItemStack(Material.DIAMOND_CHESTPLATE),
                    new ItemStack(Material.DIAMOND_LEGGINGS),
                    new ItemStack(Material.DIAMOND_BOOTS),
                    new ItemStack(Material.DIAMOND_SWORD),
                    new ItemStack(Material.DIAMOND_AXE),
                    new ItemStack(Material.DIAMOND_PICKAXE),
                    new ItemStack(Material.DIAMOND_HOE),
                    new ItemStack(Material.DIAMOND_SHOVEL),
                    new ItemStack(Material.NETHER_STAR),
                    new ItemStack(Material.DIAMOND_BLOCK),
                    new ItemStack(Material.GOLD_BLOCK),
                    new ItemStack(Material.IRON_BLOCK),
                    new ItemStack(Material.REDSTONE_BLOCK),
                    new ItemStack(Material.NETHERITE_INGOT),
                    creator.createItem(CustomItemType.LOOTBOX_PREMIUM_KEY),
                    creator.createItem(CustomItemType.LOOTBOX_STANDARD_KEY)
            );
        } else return;

        ItemStack item = lootPool.get(random.nextInt(lootPool.size()));
        giveReward(player, item, type);
        world.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.2f);
    }

    private ItemStack createCustomEnchant(Enchantment enchantment, int level) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = book.getItemMeta();

        if (meta instanceof EnchantmentStorageMeta storageMeta) {
            storageMeta.addStoredEnchant(enchantment, level, true);
            book.setItemMeta(storageMeta);
        }
        return book;
    }

    private void giveReward(Player p, ItemStack item, String type) {
        if (p.getInventory().firstEmpty() == -1) {
            Location dropLoc = p.getLocation().add(0, 1, 0);
            p.getWorld().dropItemNaturally(dropLoc, item);
        } else {
            p.getInventory().addItem(item);
        }

        switch (type) {
            case "standard" -> p.sendMessage(Component.text("¡LootBox Standard Abierta!").color(NamedTextColor.YELLOW));
            case "premium" -> p.sendMessage(Component.text("¡LootBox Premium Abierta!").color(NamedTextColor.AQUA));
        }

        p.sendMessage(Component.text("Has recibido -> ").color(NamedTextColor.WHITE)
                .append(item.displayName().color(NamedTextColor.AQUA))
        );
    }
}
