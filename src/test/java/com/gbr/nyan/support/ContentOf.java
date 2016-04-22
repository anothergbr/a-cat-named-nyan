package com.gbr.nyan.support;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ContentOf {
    public static byte[] resourceAsBytes(String name) throws IOException {
        return Files.readAllBytes(Paths.get(resourceUri(name)));
    }

    public static String resourceAsString(String name) throws IOException {
        return new String(Files.readAllBytes(Paths.get(resourceUri(name))), UTF_8);
    }

    private static URI resourceUri(String name) {
        try {
            URL resource = ContentOf.class.getClassLoader().getResource(name);
            if (resource == null) {
                throw new RuntimeException("Cannot find resourceBytes: " + name);
            }
            return resource.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
