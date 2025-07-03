package zeru.iin.managers;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import zeru.iin.IIN;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ArchiveManager {
    private final File file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Map<UUID, PlayerStats> data = new HashMap<>();

    public ArchiveManager(IIN plugin) {
        this.file = new File(plugin.getDataFolder(), "playersData.json");
        loadFile();
    }

    public void saveFile() {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile() {
        if (!file.exists()) return;
        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<UUID, PlayerStats>>(){}.getType();
            data = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerData(Player player) {
        int timePlayed = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
        int blocks = 0;

        for (Material mat : Material.values()) {
            if (mat.isBlock()) {
                try {
                    blocks += player.getStatistic(Statistic.MINE_BLOCK, mat);
                } catch (IllegalArgumentException ignored) {}
            }
        }

        data.put(player.getUniqueId(), new PlayerStats(timePlayed, blocks));
    }

    public Map<UUID, PlayerStats> getData() {
        return data;
    }
}
