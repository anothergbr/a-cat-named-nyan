package com.gbr.nyan.support;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;

public class HttpClientHelper {

    public static HttpGet get(String endpoint, String... params) throws URISyntaxException {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("pass params in name=value form");
        }

        URIBuilder builder = new URIBuilder(endpoint);
        for (int i = 0; i < params.length; i = i + 2) {
            builder.addParameter(params[i], params[i + 1]).build();
        }
        return new HttpGet(builder.build());
    }
}
