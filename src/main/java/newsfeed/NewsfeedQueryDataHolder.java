package newsfeed;

import org.apache.http.client.utils.URIBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class NewsfeedQueryDataHolder {
    private final static String PATH = "/method/newsfeed.search";
    private String host;
    private String scheme;
    private Integer port;
    private String apiVersion;
    private String accessToken;
    private String hashtag;
    private long startTime;
    private long endTime;

    private NewsfeedQueryDataHolder() {
        scheme = "https";
        host = "api.vk.com";
        port = null;
    }

    public class Builder {
        private Builder() {}

        public Builder setScheme(final String scheme) {
            NewsfeedQueryDataHolder.this.scheme = scheme;
            return this;
        }

        public Builder setHost(final String host) {
            NewsfeedQueryDataHolder.this.host = host;
            return this;
        }

        public Builder setPort(final int port) {
            NewsfeedQueryDataHolder.this.port = port;
            return this;
        }

        public Builder setApiVersion(final String apiVersion) {
            NewsfeedQueryDataHolder.this.apiVersion = apiVersion;
            return this;
        }

        public Builder setAccessToken(final String accessToken) {
            NewsfeedQueryDataHolder.this.accessToken = accessToken;
            return this;
        }

        public Builder setHashtag(final String hashtag) {
            NewsfeedQueryDataHolder.this.hashtag = hashtag;
            return this;
        }

        public Builder setStartTime(long startTime) {
            NewsfeedQueryDataHolder.this.startTime = startTime;
            return this;
        }

        public Builder setEndTime(long endTime) {
            NewsfeedQueryDataHolder.this.endTime = endTime;
            return this;
        }

        public NewsfeedQueryDataHolder build() {
            return NewsfeedQueryDataHolder.this;
        }
    }

    public static Builder newBuilder() {
        return new NewsfeedQueryDataHolder().new Builder();
    }

    public String buildQuery() {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(scheme).setHost(host).setPath(PATH);
        if (port != null) {
            builder.setPort(port);
        }
        builder.setParameter("v", apiVersion)
                .setParameter("access_token", accessToken)
                .setParameter("q", hashtag)
                .setParameter("count", "0")
                .setParameter("start_time", String.valueOf(startTime))
                .setParameter("end_time", String.valueOf(endTime));
        try {
            return builder.build().toURL().toString();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException("Error while building URL", e);
        }
    }
}
