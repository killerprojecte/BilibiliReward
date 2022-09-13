package flyproject.bili;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class CoinInfo {
    @SerializedName("aid")
    public long aid;
    @SerializedName("videos")
    public int videos;
    @SerializedName("tid")
    public int tid;
    @SerializedName("tname")
    public String tname;
    @SerializedName("copyright")
    public int copyright;
    @SerializedName("pic")
    public String pic;
    @SerializedName("title")
    public String title;
    @SerializedName("coins")
    public long coins;
    @SerializedName("owner")
    private JsonObject owner;

    public BiliUser getOwner() {
        return new Gson().fromJson(owner, BiliUser.class);
    }
}
