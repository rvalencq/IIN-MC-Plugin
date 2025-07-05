package zeru.iin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import zeru.iin.DragonFight.CustomDragonSpawn;
import zeru.iin.DragonFight.DragonFightEvents;
import zeru.iin.DragonFight.DragonDamageHandler;
import zeru.iin.DragonFight.mobsLogic.CustomMobsDrops;
import zeru.iin.commands.CustomItemCommand;
import zeru.iin.commands.MaintenanceMode;
import zeru.iin.commands.MinedCommand;
import zeru.iin.commands.PlayedCommand;
import zeru.iin.customItems.CustomItemCreator;
import zeru.iin.customItems.RegisterCraft;
import zeru.iin.customItems.events.Mace3x3Event;
import zeru.iin.managers.ArchiveManager;

public final class IIN extends JavaPlugin {
    private ArchiveManager archiveManager;
    private boolean maintenanceMode = false;
    private static IIN plugin;



    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        // Plugin Folder Creator
        Bukkit.getLogger().info("Tiempo guardado correctamente");

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Instances
        this.archiveManager = new ArchiveManager(this);
        CustomItemCreator creator = new CustomItemCreator(this);
        RegisterCraft registerCraft = new RegisterCraft(this, creator);
        CustomItemCommand customItemCommand = new CustomItemCommand(creator);
        MaintenanceMode maintenanceMode = new MaintenanceMode(this);
        PlayedCommand playedCommand = new PlayedCommand(archiveManager);
        MinedCommand minedCommand = new MinedCommand(archiveManager);

        // Events
        registerCraft.registerAll();
        Bukkit.getPluginManager().registerEvents(new GeneralEvents(this, archiveManager), this);
        Bukkit.getPluginManager().registerEvents(new Mace3x3Event(this, creator), this);

        // Dragon Events
        Bukkit.getPluginManager().registerEvents(new DragonFightEvents(), this);
        Bukkit.getPluginManager().registerEvents(new CustomDragonSpawn(this), this);
        Bukkit.getPluginManager().registerEvents(new DragonDamageHandler(), this);
        Bukkit.getPluginManager().registerEvents(new CustomMobsDrops(this), this);


        // Commands & TabCompleter
        getCommand("played").setExecutor(playedCommand);
        this.getCommand("played").setTabCompleter(playedCommand);

        getCommand("mined").setExecutor(minedCommand);
        this.getCommand("mined").setTabCompleter(minedCommand);

        getCommand("customitem").setExecutor(customItemCommand);
        this.getCommand("customitem").setTabCompleter(customItemCommand);

        getCommand("maintenance").setExecutor(maintenanceMode);
        this.getCommand("maintenance").setTabCompleter(maintenanceMode);


        // Runnable autosave
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    archiveManager.updatePlayerData(player);
                }
                archiveManager.saveFile();
                Bukkit.getLogger().info("Tiempo guardado correctamente");
            }
        }.runTaskTimer(this, 0L, 15 * 60 * 20L);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player player : Bukkit.getOnlinePlayers()) {
            archiveManager.updatePlayerData(player);
        }
        archiveManager.saveFile();
    }
    public static IIN getPlugin() {
        return plugin;
    }
    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }
    public void setMaintenanceMode(boolean maintenanceMode) {
        this.maintenanceMode = maintenanceMode;
    }
}
