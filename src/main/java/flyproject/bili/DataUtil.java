package flyproject.bili;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DataUtil {
    protected static JsonObject initJson(String uid){
        JsonObject jo = new JsonObject();
        jo.addProperty("uid",uid);
        return jo;
    }
    protected static JsonObject addVideo(String aid,JsonObject jsonObject){
        if (!jsonObject.has("used")){
            JsonArray ja = new JsonArray();
            ja.add(aid);
            jsonObject.add("used",ja);
        } else {
            JsonArray ja = jsonObject.get("used").getAsJsonArray();
            ja.add(aid);
            jsonObject.remove("used");
            jsonObject.add("used",ja);
        }
        return jsonObject;
    }
    protected static boolean hasVideo(String aid,JsonObject jsonObject){
        if (!jsonObject.has("used")){
            return false;
        } else {
            JsonArray ja = jsonObject.get("used").getAsJsonArray();
            for (JsonElement je : ja){
                if (je.getAsString().equals(aid)) return true;
            }
            return false;
        }
    }
}
