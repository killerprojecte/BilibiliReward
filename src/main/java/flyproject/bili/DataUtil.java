package flyproject.bili;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DataUtil {
    protected static JsonObject initJson(String uid) {
        JsonObject jo = new JsonObject();
        jo.addProperty("uid", uid);
        return jo;
    }

    protected static JsonObject addLikeVideo(String aid, JsonObject jsonObject) {
        if (!jsonObject.has("likeused")) {
            JsonArray ja = new JsonArray();
            ja.add(aid);
            jsonObject.add("likeused", ja);
        } else {
            JsonArray ja = jsonObject.get("likeused").getAsJsonArray();
            ja.add(aid);
            jsonObject.remove("likeused");
            jsonObject.add("likeused", ja);
        }
        return jsonObject;
    }

    protected static boolean hasLikeVideo(String aid, JsonObject jsonObject) {
        if (!jsonObject.has("likeused")) {
            return false;
        } else {
            JsonArray ja = jsonObject.get("likeused").getAsJsonArray();
            for (JsonElement je : ja) {
                if (je.getAsString().equals(aid)) return true;
            }
            return false;
        }
    }

    protected static JsonObject addCoinVideo(String aid, JsonObject jsonObject) {
        if (!jsonObject.has("coinused")) {
            JsonArray ja = new JsonArray();
            ja.add(aid);
            jsonObject.add("coinused", ja);
        } else {
            JsonArray ja = jsonObject.get("coinused").getAsJsonArray();
            ja.add(aid);
            jsonObject.remove("coinused");
            jsonObject.add("likeused", ja);
        }
        return jsonObject;
    }

    protected static boolean hasCoinVideo(String aid, JsonObject jsonObject) {
        if (!jsonObject.has("coinused")) {
            return false;
        } else {
            JsonArray ja = jsonObject.get("coinused").getAsJsonArray();
            for (JsonElement je : ja) {
                if (je.getAsString().equals(aid)) return true;
            }
            return false;
        }
    }

    protected static JsonObject addFolderVideo(String aid, JsonObject jsonObject) {
        if (!jsonObject.has("folderused")) {
            JsonArray ja = new JsonArray();
            ja.add(aid);
            jsonObject.add("folderused", ja);
        } else {
            JsonArray ja = jsonObject.get("folderused").getAsJsonArray();
            ja.add(aid);
            jsonObject.remove("folderused");
            jsonObject.add("folderused", ja);
        }
        return jsonObject;
    }

    protected static boolean hasFolderVideo(String aid, JsonObject jsonObject) {
        if (!jsonObject.has("folderused")) {
            return false;
        } else {
            JsonArray ja = jsonObject.get("folderused").getAsJsonArray();
            for (JsonElement je : ja) {
                if (je.getAsString().equals(aid)) return true;
            }
            return false;
        }
    }

    protected static boolean hasFollowing(JsonObject jsonObject) {
        if (!jsonObject.has("following")) return false;
        return jsonObject.get("following").getAsBoolean();
    }

    protected static JsonObject setFollowing(JsonObject jsonObject, boolean status) {
        jsonObject.addProperty("following", status);
        return jsonObject;
    }

    protected static String getUID(JsonObject jsonObject) {
        return jsonObject.get("uid").getAsString();
    }
}
