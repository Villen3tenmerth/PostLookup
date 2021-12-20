package newsfeed;

import http.UrlReader;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class NewsfeedClient {
    private final NewsfeedQueryDataHolder.Builder queryBuilder;

    public NewsfeedClient() throws IOException {
        queryBuilder = NewsfeedQueryDataHolder.newBuilder();
        loadProperties();
    }

    public NewsfeedClient(String scheme, String host, Integer port) throws IOException {
        this();
        queryBuilder.setScheme(scheme)
                .setHost(host)
                .setPort(port);
    }

    public int getPostsCount(String hashtag, long startTime, long endTime) throws IOException {
        String query = queryBuilder.setHashtag(hashtag)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .build().buildQuery();
        return parseResponse(UrlReader.readAsText(query));
    }

    private static int parseResponse(final String response) {
        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getJSONObject("response").getInt("total_count");
    }

    private void loadProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(input);
        }
        queryBuilder.setAccessToken(properties.getProperty("access_token"));
        queryBuilder.setApiVersion(properties.getProperty("api_version"));
    }
}
