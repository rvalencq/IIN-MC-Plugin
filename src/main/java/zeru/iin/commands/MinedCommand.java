package zeru.iin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zeru.iin.managers.ArchiveManager;
import zeru.iin.managers.PlayerStats;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MinedCommand implements CommandExecutor, TabCompleter {
    private final ArchiveManager archiveManager;

    public MinedCommand(ArchiveManager archiveManager) {
        this.archiveManager = archiveManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(sender instanceof Player player)) return true;
        UUID uuid = player.getUniqueId();
        archiveManager.updatePlayerData(player);
        archiveManager.saveFile();
        PlayerStats stats = archiveManager.getData().get(uuid);
        if (stats != null) {
            sender.sendMessage(Component.text("----------------------------").color(NamedTextColor.YELLOW));
            sender.sendMessage(Component.text()
                    .append(Component.text("Tus Bloques Minados "))
                    .append(Component.text("-> ").color(NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text(stats.getMinedBlocks())));
        }
        sender.sendMessage(Component.text("----------------------------").color(NamedTextColor.YELLOW));
        sender.sendMessage(Component.text()
                .append(Component.text("🏆")
                        .color(NamedTextColor.YELLOW))
                .append(Component.text(" Top enfermitos del servidor:")
                        .color(NamedTextColor.AQUA)));
        sender.sendMessage(Component.text("----------------------------").color(NamedTextColor.YELLOW));

        AtomicInteger pos = new AtomicInteger(1);
        archiveManager.getData().entrySet().stream()
                .sorted(Map.Entry.<UUID, PlayerStats>comparingByValue(
                        Comparator.comparingInt(PlayerStats::getMinedBlocks)).reversed())
                .limit(10)
                .forEach(entry -> {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(entry.getKey());
                    PlayerStats stat = entry.getValue();
                    int index = pos.getAndIncrement();
                    sender.sendMessage(Component.text()
                            .append(Component.text(index + ". " + p.getName()).color(NamedTextColor.WHITE))
                            .append(Component.text(" -> ").color(NamedTextColor.LIGHT_PURPLE))
                            .append(Component.text(stat.getMinedBlocks())));
                });
        sender.sendMessage(Component.text("----------------------------").color(NamedTextColor.YELLOW));

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        return Collections.emptyList();
    }
}
