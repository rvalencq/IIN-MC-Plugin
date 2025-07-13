package zeru.iin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zeru.iin.customItems.CustomItemType;
import zeru.iin.timba.VillagersTimbaManager;

import java.util.List;

public class TimbaVillagerSpawnCommand implements CommandExecutor, TabCompleter {
    private final VillagersTimbaManager timbaVillager;
    public TimbaVillagerSpawnCommand(VillagersTimbaManager timbaVillager) {
        this.timbaVillager = timbaVillager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 0) return false;

        switch (args[0]) {
            case "getter" -> timbaVillager.villagerGetterTimba(player);
            case "seller" -> timbaVillager.villagerSellerTimba(player);
        }

        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("getter", "seller");
        }
        return List.of();
    }
}
