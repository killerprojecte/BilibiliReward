package flyproject.bili;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RewardUtil {
    public static void excute() throws Exception {
        Map<UUID, String> data_map;
        if (MySQL.ENABLED) {
            data_map = MySQL.getAll();
        } else {
            data_map = YAML.getAll();
        }
        FileConfiguration configuration = BilibiliReward.instance.getConfig();
        for (UUID uuid : data_map.keySet()) {
            JsonObject jsonObject = new JsonParser().parse(data_map.get(uuid)).getAsJsonObject();
            String uid = DataUtil.getUID(jsonObject);
            List<LikeInfo> likeInfos = RequestUtil.getLikes(uid);
            List<CoinInfo> coinInfos = RequestUtil.getCoins(uid);
            List<List<FolderMedia>> foldermedias = new ArrayList<>();
            for (long[] fid : RequestUtil.getUserPublicFoldersId(uid)) {
                foldermedias.add(RequestUtil.getFolders(String.valueOf(fid[0]), String.valueOf(fid[1])));
            }
            List<Following> followings = RequestUtil.get20Following(uid);
            boolean bind_user = configuration.getBoolean("bind_user");
            long bind_id = configuration.getLong("bind");
            //Like
            for (LikeInfo likeInfo : likeInfos) {
                if (bind_user) {
                    if (likeInfo.getOwner().mid != bind_id) continue;
                    if (DataUtil.hasLikeVideo(String.valueOf(likeInfo.aid), jsonObject)) continue;
                    cmd(configuration.getStringList("like"), Bukkit.getOfflinePlayer(uuid).getName(), likeInfo.title);
                } else {
                    if (likeInfo.aid != bind_id) continue;
                    if (DataUtil.hasLikeVideo(String.valueOf(likeInfo.aid), jsonObject)) continue;
                    cmd(configuration.getStringList("like"), Bukkit.getOfflinePlayer(uuid).getName(), likeInfo.title);
                }
                jsonObject = DataUtil.addLikeVideo(String.valueOf(likeInfo.aid), jsonObject);
            }
            //Coin
            for (CoinInfo coinInfo : coinInfos) {
                if (bind_user) {
                    if (coinInfo.getOwner().mid != bind_id) continue;
                    if (DataUtil.hasCoinVideo(String.valueOf(coinInfo.aid), jsonObject)) continue;
                    cmd(configuration.getStringList("coin"), Bukkit.getOfflinePlayer(uuid).getName(), coinInfo.title, String.valueOf(coinInfo.coins));
                } else {
                    if (coinInfo.aid != bind_id) continue;
                    if (DataUtil.hasCoinVideo(String.valueOf(coinInfo.aid), jsonObject)) continue;
                    cmd(configuration.getStringList("coin"), Bukkit.getOfflinePlayer(uuid).getName(), coinInfo.title, String.valueOf(coinInfo.coins));
                }
                jsonObject = DataUtil.addCoinVideo(String.valueOf(coinInfo.aid), jsonObject);
            }
            //Folder
            for (List<FolderMedia> fmlist : foldermedias) {
                for (FolderMedia media : fmlist) {
                    if (bind_user) {
                        if (media.getUpper().mid != bind_id) continue;
                        if (DataUtil.hasFolderVideo(String.valueOf(media.id), jsonObject)) continue;
                        cmd(configuration.getStringList("folder"), Bukkit.getOfflinePlayer(uuid).getName(), media.title);
                    } else {
                        if (media.id != bind_id) continue;
                        if (DataUtil.hasFolderVideo(String.valueOf(media.id), jsonObject)) continue;
                        cmd(configuration.getStringList("folder"), Bukkit.getOfflinePlayer(uuid).getName(), media.title);
                    }
                    jsonObject = DataUtil.addFolderVideo(String.valueOf(media.id), jsonObject);
                }
            }
            //Following
            if (bind_user) {
                if (DataUtil.hasFollowing(jsonObject)) {
                    for (Following following : followings) {
                        if (following.mid != bind_id) continue;
                        cmd(configuration.getStringList("following"), Bukkit.getOfflinePlayer(uuid).getName());
                        jsonObject = DataUtil.setFollowing(jsonObject, true);
                    }
                }
            }
            if (MySQL.ENABLED) {
                MySQL.savePlayer(new Gson().toJson(jsonObject), uuid.toString());
            } else {
                YAML.setData(uuid, new Gson().toJson(jsonObject));
            }
        }
    }

    private static void cmd(List<String> cmd, String player) {
        Bukkit.getScheduler().runTask(BilibiliReward.instance, () -> {
            for (String c : cmd) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.replace("%player%", player));
            }
        });
    }

    private static void cmd(List<String> cmd, String player, String title, String coin) {
        Bukkit.getScheduler().runTask(BilibiliReward.instance, () -> {
            for (String c : cmd) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.replace("%player%", player).replace("%title%", title).replace("%coin%", coin));
            }
        });
    }

    private static void cmd(List<String> cmd, String player, String title) {
        Bukkit.getScheduler().runTask(BilibiliReward.instance, () -> {
            for (String c : cmd) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.replace("%player%", player).replace("%title%", title));
            }
        });
    }
}
