package com.gbr.nyan.web.support;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OauthHttpClient {
    private final String consumerKey;
    private final String consumerSecret;

    public OauthHttpClient(String consumerKey, String consumerSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    public String getJson(String rawUrl) throws Exception {
        OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);

        URL url = new URL(rawUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        consumer.sign(connection);
        connection.addRequestProperty("Accept", "application/json");

        connection.connect();

        if (connection.getResponseCode() >= 200 && connection.getResponseCode() <= 299) {
            return streamToString(connection.getInputStream());
        } else {
            throw new Exception("Request to " + url + " failed with status " + connection.getResponseCode());
        }
    }

    private String streamToString(InputStream inputStream) throws IOException {
        // from http://stackoverflow.com/a/35446009/26605
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }
}
