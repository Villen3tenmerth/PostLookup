package http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import rule.HostReachableRule;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(HostReachableRule.class)
@HostReachableRule.HostReachable("api.vk.com")
class UrlReaderTest {

    @Test
    public void read() {
        String[] result = new String[1];
        assertDoesNotThrow(() -> {
            result[0] = UrlReader.readAsText("https://api.vk.com/method/newsfeed.search");
        });
        assertFalse(result[0].isEmpty());
    }
}