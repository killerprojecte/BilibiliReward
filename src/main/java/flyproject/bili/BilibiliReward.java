package flyproject.bili;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class BilibiliReward extends JavaPlugin {

    public static BilibiliReward instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        saveResource("data.yml", false);
        if (getConfig().getString("database").equalsIgnoreCase("mysql")) {
            MySQL.setUP();
        }
        getCommand("breward").setExecutor(new BiliCommand());
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            try {
                RewardUtil.excute();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, 20L * 60L * 10L, 20L * 60L * 10L);
        if (MySQL.ENABLED) {
            if (new File(getDataFolder() + "/data.yml").exists()) {
                getLogger().info("正在尝试导入YAML数据...");
                int amount = 0;
                FileConfiguration data = YamlConfiguration.loadConfiguration(new File(getDataFolder() + "/data.yml"));
                for (String key : data.getConfigurationSection("data").getKeys(false)) {
                    String js = data.getString("data." + key);
                    if (MySQL.getData(key) != null) continue;
                    amount++;
                    MySQL.savePlayer(js, key);
                }
                getLogger().info("已导入 " + amount + "个 YAML数据");
            }
        }
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        if (MySQL.ENABLED) {
            MySQL.close();
        }
        // Plugin shutdown logic
    }
}
