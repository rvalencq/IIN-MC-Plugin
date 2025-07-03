package zeru.iin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zeru.iin.IIN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MaintenanceMode implements CommandExecutor, TabCompleter {
    private final IIN plugin;
    public MaintenanceMode(IIN plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!sender.isOp()) {
            return true;
        }
        boolean activate;
        if (args[0].equalsIgnoreCase("on")) {
            activate = true;
        } else if (args[0].equalsIgnoreCase("off")) {
            activate = false;
        } else {
            return true;
        }
        plugin.setMaintenanceMode(activate);

        String status = activate ? "activado" : "desactivado";
        sender.sendMessage("🔧 Modo mantenimiento " + status);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length == 1)
            return Arrays.asList("on", "off");
        return Collections.emptyList();
    }
}
