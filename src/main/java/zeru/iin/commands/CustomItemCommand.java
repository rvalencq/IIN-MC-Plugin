package zeru.iin.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zeru.iin.customItems.CustomItemCreator;
import zeru.iin.customItems.CustomItemType;

import java.util.List;

public class CustomItemCommand implements CommandExecutor, TabCompleter {
    private final CustomItemCreator creator;
    public CustomItemCommand(CustomItemCreator creator) {
        this.creator = creator;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 0) return false;

        switch (args[0]) {
            case "mace" -> player.getInventory().addItem(creator.createItem(CustomItemType.MACE_3X3));
            case "minerPotion" -> player.getInventory().addItem(creator.createItem(CustomItemType.MINER_POTION));
            case "timbaCoin" -> player.getInventory().addItem(creator.createItem(CustomItemType.TIMBA_COIN));
            case "premiumKey" -> player.getInventory().addItem(creator.createItem(CustomItemType.LOOTBOX_PREMIUM_KEY));
            case "standardKey" -> player.getInventory().addItem(creator.createItem(CustomItemType.LOOTBOX_STANDARD_KEY));
            case "xtra" -> player.getInventory().addItem(createCustomEnchant(Enchantment.EFFICIENCY, 8));
        }

        return true;
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("mace", "minerPotion", "timbaCoin", "premiumKey", "standardKey");
        }
        return List.of();
    }
}
