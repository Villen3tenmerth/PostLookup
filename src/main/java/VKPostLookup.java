import newsfeed.NewsfeedClient;
import newsfeed.NewsfeedManager;

public class VKPostLookup {
    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.err.println("Expected 2 arguments");
            return;
        }

        try {
            String hashtag = args[0];
            if (hashtag.isEmpty()) {
                System.err.println("Hashtag shouldn't be empty");
                return;
            }
            int hours = Integer.parseInt(args[1]);
            if (hours < 1 || hours > 24) {
                System.err.println("Hours should be in range from 1 to 24");
                return;
            }

            int[] cnt = new NewsfeedManager(new NewsfeedClient()).countPosts("#" + hashtag, hours);
            for (int h = 0; h < hours; h++) {
                System.out.println("Posts made " + (h + 1) + " hours ago: " + cnt[h]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
