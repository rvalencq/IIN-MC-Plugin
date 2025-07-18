package zeru.iin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import zeru.iin.managers.ArchiveManager;

import java.util.Map;

public class GeneralEvents implements Listener {
    private final int reservedSpaces = 5;
    private final ArchiveManager archiveManager;
    private final IIN plugin;
    public GeneralEvents(IIN plugin, ArchiveManager playedTimeManager) {
        this.archiveManager = playedTimeManager;
        this.plugin = plugin;
    }

    private String centerText(String text) {
        int maxWidth = 52; // Aproximado para MOTD en píxeles
        int textLength = ChatColor.stripColor(text).length();
        int spaces = (maxWidth - textLength) / 2;
        return " ".repeat(Math.max(0, spaces)) + text;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String message = "§e>> " + event.getPlayer().getDisplayName();
        event.setJoinMessage(message);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String message = "§c<< " + event.getPlayer().getDisplayName();
        event.setQuitMessage(message);

        Player player = event.getPlayer();

        archiveManager.updatePlayerData(player);
        archiveManager.saveFile();
    }

    // Reserved Spaces Logic
    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if (plugin.isMaintenanceMode() && !event.getPlayer().isOp()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("Server in maintenance").color(NamedTextColor.YELLOW));
            return;
        }

        int online = Bukkit.getOnlinePlayers().size();
        int max = Bukkit.getMaxPlayers() - reservedSpaces;
        if (online >= max) {
            Player player = event.getPlayer();
            if (player.isOp()) {
                return;
            }
            event.disallow(PlayerLoginEvent.Result.KICK_FULL,
                    Component.text("The server is full"));
        }

    }

    // MOTD Logic
    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        int maxVisible = Bukkit.getMaxPlayers() - reservedSpaces;

        String serverTitle = ChatColor.AQUA + "§lIIN " + "§r§fBICHOS " + ChatColor.LIGHT_PURPLE + "§l2025§r\n";
        String eventTitle = "§l§e>>> " + "§r§fTHE END" + "§l§e <<<";
        String maintenanceTitle = "§l§e>>> " + "§r§6 En Mantenimiento" + "§l§e <<<";

        if (plugin.isMaintenanceMode()) {
            event.setMaxPlayers(0);
            event.setMotd(centerText(serverTitle) + centerText(maintenanceTitle));
            return;
        }
        event.setMaxPlayers(maxVisible);
        event.setMotd(centerText(serverTitle) + centerText(eventTitle));
    }

    // Anvil Validation
    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory inv = event.getInventory();
        ItemStack first = inv.getItem(0); // ítem base
        ItemStack second = inv.getItem(1); // libro encantado

        if (first == null || second == null || second.getType() != Material.ENCHANTED_BOOK) return;

        ItemStack result = first.clone();
        Map<Enchantment, Integer> bookEnchants = ((EnchantmentStorageMeta) second.getItemMeta()).getStoredEnchants();

        for (Map.Entry<Enchantment, Integer> entry : bookEnchants.entrySet()) {
            result.addUnsafeEnchantment(entry.getKey(), entry.getValue());
        }

        event.setResult(result);
    }
}
