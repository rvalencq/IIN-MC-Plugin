package zeru.iin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (strings[0].equals("mace")) {
            ItemStack mace = creator.createItem(CustomItemType.MACE_3X3);
            player.getInventory().addItem(mace);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("mace");
        }
        return List.of();
    }
}
