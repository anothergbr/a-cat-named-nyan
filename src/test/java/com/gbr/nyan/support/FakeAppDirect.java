package com.gbr.nyan.support;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import static com.gbr.nyan.support.ContentOf.resourceAsBytes;
import static com.gbr.nyan.support.ContentOf.resourceAsString;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Strongly inspired by http://stackoverflow.com/a/3732328/26605
 */
public class FakeAppDirect {
    private final HttpServer server;
    private String lastRequestPath;

    public static FakeAppDirect create(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        return new FakeAppDirect(server);
    }

    private FakeAppDirect(HttpServer server) {
        this.server = server;
    }

    public FakeAppDirect start() {
        server.createContext("/v1/events/dummyOrder", new OauthSecuredJson("events/subscription-order-stateless.json"));
        server.createContext("/v1/events/dummyChange", new OauthSecuredJson("events/subscription-change-stateless.json"));
        server.createContext("/v1/events/dummyCancel", new OauthSecuredJson("events/subscription-cancel-stateless.json"));
        server.createContext("/v1/events/dummyNotice", new OauthSecuredJson("events/subscription-notice-stateless.json"));

        server.createContext("/v1/events/dev-order", new OauthSecuredJson("events/subscription-order-development.json"));
        server.createContext("/v1/events/dev-change", new OauthSecuredJson("events/subscription-change-development.json"));
        server.createContext("/v1/events/dev-cancel", new OauthSecuredJson("events/subscription-cancel-development.json"));

        server.start();
        return this;
    }

    public void stop() {
        server.stop(0);
    }

    public String lastRequestPath() {
        return lastRequestPath;
    }

    private class OauthSecuredJson implements HttpHandler {
        private final String jsonResource;

        private OauthSecuredJson(String jsonResource) {
            this.jsonResource = jsonResource;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            lastRequestPath = t.getRequestURI().toString();

            String authorization = t.getRequestHeaders().getFirst("Authorization");
            if (authorization == null || !authorization.startsWith("OAuth oauth_consumer_key")) {
                sendResponse(t, 401, "UNAUTHORIZED! Use OAUTH!".getBytes(UTF_8));
                return;
            }

            t.getResponseHeaders().add("Content-Type", "application/json");

            String accountId = getOptionalAccountIdParam(t.getRequestURI());
            byte[] response = accountId == null ? resourceAsBytes(jsonResource) : resourceAsString(jsonResource).replace("{{account-id}}", accountId).getBytes(UTF_8);

            sendResponse(t, 200, response);
        }

        private void sendResponse(HttpExchange t, int statusCode, byte[] response) throws IOException {
            t.sendResponseHeaders(statusCode, response.length);

            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }

        private String getOptionalAccountIdParam(URI requestURI) {
            String query = requestURI.getQuery();
            if (query == null || !query.startsWith("account-id")) {
                return null;
            }
            return query.split("=")[1];
        }
    }
}
