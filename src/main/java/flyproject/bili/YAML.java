package flyproject.bili;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class YAML {
    public static String getData(UUID uuid) {
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File(BilibiliReward.instance.getDataFolder() + "/data.yml"));
        return configuration.getString("data." + uuid.toString());
    }

    public static boolean hasData(UUID uuid) {
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File(BilibiliReward.instance.getDataFolder() + "/data.yml"));
        return configuration.get("data." + uuid.toString()) != null;
    }

    public static void setData(UUID uuid, String data) {
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File(BilibiliReward.instance.getDataFolder() + "/data.yml"));
        configuration.set("data." + uuid.toString(), data);
        try {
            configuration.save(new File(BilibiliReward.instance.getDataFolder() + "/data.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<UUID, String> getAll() {
        Map<UUID, String> map = new HashMap<>();
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File(BilibiliReward.instance.getDataFolder() + "/data.yml"));
        for (String uuid : configuration.getConfigurationSection("data").getKeys(false)) {
            map.put(UUID.fromString(uuid), configuration.getString("data." + uuid));
        }
        return map;
    }
}
