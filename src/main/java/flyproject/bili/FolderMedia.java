package flyproject.bili;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class FolderMedia {
    @SerializedName("id")
    public long id;
    @SerializedName("type")
    public int type;
    @SerializedName("title")
    public String title;
    @SerializedName("cover")
    public String cover;
    @SerializedName("upper")
    private JsonObject upper;

    public BiliUser getUpper(){
        return new Gson().fromJson(upper,BiliUser.class);
    }
}
