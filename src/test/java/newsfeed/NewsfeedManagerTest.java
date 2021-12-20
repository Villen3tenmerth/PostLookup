package newsfeed;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class NewsfeedManagerTest {
    private NewsfeedManager manager;

    @Mock
    private NewsfeedClient client;

    @BeforeEach
    private void beforeEach() throws IOException {
        MockitoAnnotations.initMocks(this);
        manager = new NewsfeedManager(client);

        when(client.getPostsCount(anyString(), anyLong(), anyLong())).thenAnswer(
                invocation -> {
                    String hashtag = invocation.getArgument(0, String.class);
                    Long startTime = invocation.getArgument(1, Long.class);
                    Long endTime = invocation.getArgument(2, Long.class);

                    List<String[]> sample = Utils.getSampleData();
                    int cnt = 0;
                    for (String[] entry : sample) {
                        if (entry[0].equals(hashtag)
                                && startTime <= Long.parseLong(entry[1])
                                && Long.parseLong(entry[1]) <= endTime) {
                            cnt++;
                        }
                    }

                    return cnt;
                }
        );
    }

    private void compareWithList(int[] actual, List<Integer> expected) {
        assertEquals(actual.length, expected.size());
        for (int i = 0; i < actual.length; i++) {
            assertEquals(actual[i], expected.get(i));
        }
    }

    @Test
    public void test1() throws IOException {
        compareWithList(manager.countPosts("#cat", 1), List.of(1));
    }

    @Test
    public void test2() throws IOException {
        compareWithList(manager.countPosts("#dog", 4), List.of(1, 0, 1, 0));
    }

    @Test
    public void test3() throws IOException {
        compareWithList(manager.countPosts("#cat", 24), Collections.nCopies(24, 1));
    }

    @Test
    public void test4() throws IOException {
        compareWithList(manager.countPosts("#dragon", 10), Collections.nCopies(10, 0));
    }
}