package flyproject.bili;

import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        List<LikeInfo> likes = RequestUtil.getLikes("616411306");
        System.out.println(likes.size());
        for (LikeInfo likeInfo : likes) {
            System.out.println(likeInfo.title);
        }
        List<CoinInfo> coins = RequestUtil.getCoins("616411306");
        System.out.println(coins.size());
        for (CoinInfo coinInfo : coins) {
            System.out.println(coinInfo.title + "###" + coinInfo.coins);
        }
        List<long[]> ids = RequestUtil.getUserPublicFoldersId("616411306");
        for (long[] id : ids) {
            System.out.println(id[0] + "###" + id[1]);
            List<FolderMedia> folderMedias = RequestUtil.getFolders(String.valueOf(id[0]), String.valueOf(id[1]));
            for (FolderMedia folderMedia : folderMedias) {
                System.out.println(folderMedia.title);
            }
        }
        List<Following> followings = RequestUtil.get20Following("616411306");
        System.out.println(followings.size());
        for (Following following : followings) {
            System.out.println(following.uname);
        }
    }
}
