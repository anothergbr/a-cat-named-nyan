package com.gbr.nyan.support;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import static com.gbr.nyan.support.ContentOf.resourceAsBytes;

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
        server.createContext("/v1/events/dummyOrder", new ReturnJson("events/subscription-order-stateless.json"));
        server.createContext("/v1/events/dev-order", new ReturnJson("events/subscription-order-development.json"));

        server.start();
        return this;
    }

    public void stop() {
        server.stop(0);
    }

    public String lastRequestPath() {
        return lastRequestPath;
    }

    private class ReturnJson implements HttpHandler {
        private final String jsonResource;

        private ReturnJson(String jsonResource) {
            this.jsonResource = jsonResource;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders().add("Content-Type", "application/json");

            byte[] response = resourceAsBytes(jsonResource);
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();

            lastRequestPath = t.getRequestURI().toString();
        }
    }
}
