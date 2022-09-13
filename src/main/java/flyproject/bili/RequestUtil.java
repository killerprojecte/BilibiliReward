package flyproject.bili;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RequestUtil {
    protected static List<LikeInfo> getLikes(String vmid) throws Exception {
        URL url = new URL("https://api.bilibili.com/x/space/like/video?vmid=" + vmid + "&jsonp=jsonp");
        URLConnection connection = url.openConnection();
        StringBuilder sb = new StringBuilder();
        String str;
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        while ((str = reader.readLine()) != null) {
            sb.append(str);
            sb.append("\n");
        }
        sb.delete(sb.length() - 1, sb.length());
        JsonObject jo = new JsonParser().parse(sb.toString()).getAsJsonObject();
        long code = jo.get("code").getAsLong();
        if (code != 0) {
            System.err.println("Failed to request [" + jo.get("message").getAsString() + "]");
            return new ArrayList<>();
        }
        JsonArray ja = jo.get("data").getAsJsonObject().get("list").getAsJsonArray();
        List<LikeInfo> likes = new ArrayList<>();
        for (JsonElement je : ja) {
            likes.add(new Gson().fromJson(je, LikeInfo.class));
        }
        return likes;
    }

    protected static List<CoinInfo> getCoins(String vmid) throws Exception {
        URL url = new URL("https://api.bilibili.com/x/space/coin/video?vmid=" + vmid + "&jsonp=jsonp");
        URLConnection connection = url.openConnection();
        StringBuilder sb = new StringBuilder();
        String str;
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        while ((str = reader.readLine()) != null) {
            sb.append(str);
            sb.append("\n");
        }
        sb.delete(sb.length() - 1, sb.length());
        JsonObject jo = new JsonParser().parse(sb.toString()).getAsJsonObject();
        long code = jo.get("code").getAsLong();
        if (code != 0) {
            System.err.println("Failed to request [" + jo.get("message").getAsString() + "]");
            return new ArrayList<>();
        }
        JsonArray ja = jo.get("data").getAsJsonArray();
        List<CoinInfo> likes = new ArrayList<>();
        for (JsonElement je : ja) {
            likes.add(new Gson().fromJson(je, CoinInfo.class));
        }
        return likes;
    }

    protected static List<FolderMedia> getFolders(String vmid, String limit) throws Exception {
        URL url = new URL("https://api.bilibili.com/x/v3/fav/resource/list?media_id=" + vmid + "&ps=" + limit);
        URLConnection connection = url.openConnection();
        StringBuilder sb = new StringBuilder();
        String str;
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        while ((str = reader.readLine()) != null) {
            sb.append(str);
            sb.append("\n");
        }
        sb.delete(sb.length() - 1, sb.length());
        JsonObject jo = new JsonParser().parse(sb.toString()).getAsJsonObject();
        long code = jo.get("code").getAsLong();
        if (code != 0) {
            System.err.println("Failed to request [" + jo.get("message").getAsString() + "]");
            return new ArrayList<>();
        }
        JsonArray ja = jo.get("data").getAsJsonObject().get("medias").getAsJsonArray();
        List<FolderMedia> likes = new ArrayList<>();
        for (JsonElement je : ja) {
            likes.add(new Gson().fromJson(je, FolderMedia.class));
        }
        return likes;
    }

    protected static List<long[]> getUserPublicFoldersId(String uid) throws Exception {
        URL url = new URL("https://api.bilibili.com/x/v3/fav/folder/created/list-all?up_mid=" + uid);
        URLConnection connection = url.openConnection();
        StringBuilder sb = new StringBuilder();
        String str;
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        while ((str = reader.readLine()) != null) {
            sb.append(str);
            sb.append("\n");
        }
        sb.delete(sb.length() - 1, sb.length());
        JsonObject jo = new JsonParser().parse(sb.toString()).getAsJsonObject();
        long code = jo.get("code").getAsLong();
        if (code != 0) {
            System.err.println("Failed to request [" + jo.get("message").getAsString() + "]");
            return new ArrayList<>();
        }
        List<long[]> ids = new ArrayList<>();
        for (JsonElement je : jo.get("data").getAsJsonObject().get("list").getAsJsonArray()) {
            JsonObject djo = je.getAsJsonObject();
            ids.add(new long[]{djo.get("id").getAsLong(), djo.get("media_count").getAsLong()});
        }
        return ids;
    }

    protected static List<Following> get20Following(String uid) throws Exception {
        URL url = new URL("http://api.bilibili.com/x/relation/followings?vmid=" + uid + "&ps=20");
        URLConnection connection = url.openConnection();
        StringBuilder sb = new StringBuilder();
        String str;
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        while ((str = reader.readLine()) != null) {
            sb.append(str);
            sb.append("\n");
        }
        sb.delete(sb.length() - 1, sb.length());
        JsonObject jo = new JsonParser().parse(sb.toString()).getAsJsonObject();
        long code = jo.get("code").getAsLong();
        if (code != 0) {
            System.err.println("Failed to request [" + jo.get("message").getAsString() + "]");
            return new ArrayList<>();
        }
        List<Following> ids = new ArrayList<>();
        for (JsonElement je : jo.get("data").getAsJsonObject().get("list").getAsJsonArray()) {
            ids.add(new Gson().fromJson(je, Following.class));
        }
        return ids;
    }
}
