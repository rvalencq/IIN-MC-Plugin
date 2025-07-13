package zeru.iin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

public class PlayedCommand implements CommandExecutor, TabCompleter {
    private final ArchiveManager archiveManager;

    public PlayedCommand(ArchiveManager playedTimeManager) {
        this.archiveManager = playedTimeManager;
    }

    private static String formatTime(int totalSeconds) {
        int days = totalSeconds / 86400;
        int hours = (totalSeconds % 86400) / 3600;
        int minutes = (totalSeconds % 3600) / 60;

        return String.format("%02dd%02dh%02dm", days, hours, minutes);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(sender instanceof Player player)) return true;
        UUID uuid = player.getUniqueId();
        archiveManager.updatePlayerData(player);
        archiveManager.saveFile();
        PlayerStats stats = archiveManager.getData().get(uuid);
        if (stats != null) {
            sender.sendMessage(Component.text("-------------------------------").color(NamedTextColor.YELLOW));
            sender.sendMessage(Component.text()
                    .append(Component.text("Tu Tiempo Jugado "))
                    .append(Component.text("-> ").color(NamedTextColor.LIGHT_PURPLE).decorate(TextDecoration.BOLD))
                    .append(Component.text(formatTime(stats.getPlayedTime()))));
        }
        sender.sendMessage(Component.text("-------------------------------").color(NamedTextColor.YELLOW));
        sender.sendMessage(Component.text()
                .append(Component.text("🏆")
                        .color(NamedTextColor.YELLOW))
                .append(Component.text(" Top Tiempo Jugado del Servidor:")
                        .color(NamedTextColor.AQUA)));
        sender.sendMessage(Component.text("-------------------------------").color(NamedTextColor.YELLOW));

        AtomicInteger pos = new AtomicInteger(1);
        archiveManager.getData().entrySet().stream()
                .sorted(Map.Entry.<UUID, PlayerStats>comparingByValue(
                        Comparator.comparingInt(PlayerStats::getPlayedTime)).reversed())
                .limit(5)
                .forEach(entry -> {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(entry.getKey());
                    PlayerStats stat = entry.getValue();
                    int index = pos.getAndIncrement();
                    sender.sendMessage(Component.text()
                            .append(Component.text(index + ". " + p.getName()).color(NamedTextColor.WHITE))
                            .append(Component.text(" -> ").color(NamedTextColor.LIGHT_PURPLE).decorate(TextDecoration.BOLD))
                            .append(Component.text(formatTime(stat.getPlayedTime()))));
                });
        sender.sendMessage(Component.text("-------------------------------").color(NamedTextColor.YELLOW));

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        return Collections.emptyList();
    }
}
