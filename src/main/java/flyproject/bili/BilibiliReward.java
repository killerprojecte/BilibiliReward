package flyproject.bili;

import org.bukkit.plugin.java.JavaPlugin;

public final class BilibiliReward extends JavaPlugin {

    public static BilibiliReward instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
