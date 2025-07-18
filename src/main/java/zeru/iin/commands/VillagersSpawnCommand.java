package zeru.iin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zeru.iin.timba.VillagersManager;

import java.util.List;

public class VillagersSpawnCommand implements CommandExecutor, TabCompleter {
    private final VillagersManager villagersManager;
    public VillagersSpawnCommand(VillagersManager villagersManager) {
        this.villagersManager = villagersManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 0) return false;

        switch (args[0]) {
            case "getter" -> villagersManager.villagerGetterTimba(player);
            case "seller" -> villagersManager.villagerSellerTimba(player);
            case "keySeller" -> villagersManager.villagerKeySeller(player);
        }

        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("getter", "seller", "keySeller");
        }
        return List.of();
    }
}
