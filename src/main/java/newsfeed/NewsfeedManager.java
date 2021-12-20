package newsfeed;

import java.io.IOException;

public class NewsfeedManager {
    private final NewsfeedClient client;

    public NewsfeedManager(NewsfeedClient client) {
        this.client = client;
    }

    public int[] countPosts(String hashtag, int hours) throws IOException {
        int[] res = new int[hours];
        long curTime = System.currentTimeMillis() / 1000;

        for (int h = 0; h < hours; h++) {
            long SECONDS_IN_HOUR = 60 * 60;
            res[h] = client.getPostsCount(hashtag,
                    curTime - (h + 1) * SECONDS_IN_HOUR + 1,
                    curTime - h * SECONDS_IN_HOUR);
        }

        return res;
    }
}
