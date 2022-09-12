package flyproject.bili;

import com.google.gson.annotations.SerializedName;

public class BiliUser {
    @SerializedName("mid")
    public long mid;
    @SerializedName("name")
    public String name;
    @SerializedName("face")
    public String face;
}
