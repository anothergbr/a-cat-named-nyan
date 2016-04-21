package com.gbr.nyan.support;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ContentOf {
    public static byte[] resource(String name) throws IOException {
        return Files.readAllBytes(Paths.get(resourceUri(name)));
    }

    private static URI resourceUri(String name) {
        try {
            URL resource = ContentOf.class.getClassLoader().getResource(name);
            if (resource == null) {
                throw new RuntimeException("Cannot find resource: " + name);
            }
            return resource.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
