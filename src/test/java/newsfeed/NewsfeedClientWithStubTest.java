package newsfeed;

import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;
import static com.xebialabs.restito.semantics.Condition.startsWithUri;
import static org.junit.jupiter.api.Assertions.*;

class NewsfeedClientWithStubTest {
    private final static int PORT = 32453;
    private final NewsfeedClient client;

    NewsfeedClientWithStubTest() throws IOException {
        client = new NewsfeedClient("http", "localhost", PORT);
    }

    private void withStubServer(Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(PORT).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }

    private String generateResponse(int count) {
        return "{\"response\":{\"items\":[],\"count\":" + count + ",\"total_count\":" + count + "}}";
    }

    private void testWithStub(String hashtag, long interval) {
        long endTime = System.currentTimeMillis() / 1000;
        long startTime = endTime - interval;

        withStubServer(s -> {
            List<String[]> sample = Utils.getSampleData();
            int expected = 0;
            for (String[] entry : sample) {
                if (entry[0].equals(hashtag)
                        && startTime <= Long.parseLong(entry[1])
                        && Long.parseLong(entry[1]) <= endTime) {
                    expected++;
                }
            }

            whenHttp(s).match(method(Method.GET), startsWithUri("/method/newsfeed.search"))
                    .then(stringContent(generateResponse(expected)));

            int actual;
            try {
                actual = client.getPostsCount(hashtag, startTime, endTime);
            } catch (IOException e) {
                actual = -1;
            }
            assertEquals(actual, expected);
        });
    }

    @Test
    public void test1() {
        testWithStub("#cat", 60 * 60 * 5);
    }

    @Test
    public void test2() {
        testWithStub("#dog", 60 * 60 * 10);
    }

    @Test
    public void test3() {
        testWithStub("#dragon", 60 * 60 * 20);
    }
}