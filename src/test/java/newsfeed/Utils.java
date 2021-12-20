package newsfeed;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<String[]> getSampleData() {
        List<String[]> res = new ArrayList<>();
        long curTime = System.currentTimeMillis() / 1000;
        long interval = 40 * 60;
        for (int i = 0; i < 36; i++) {
            res.add(new String[2]);
            if (i % 3 == 0) {
                res.get(i)[0] = "#dog";
            } else {
                res.get(i)[0] = "#cat";
            }
            res.get(i)[1] = Long.toString(curTime);
            curTime -= interval;
        }
        return res;
    }
}
