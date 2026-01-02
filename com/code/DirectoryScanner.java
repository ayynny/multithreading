package com.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectoryScanner {

    public static List<Path> imageFiles = Collections.synchronizedList(new ArrayList<>());

    public static Runnable scan(String path) {
        Collection<String> formats = Arrays.asList(".png", ".jpeg", ".jpg");
        try {
            imageFiles = filterFilesByExtensions(path, formats);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Path> getList() {
        return imageFiles;
    }

    public static List<Path> filterFilesByExtensions(String directoryPath, Collection<String> extensions)
            throws IOException {
        try (Stream<Path> walk = Files.walk(Paths.get(directoryPath))) {
            return walk
                    .filter(Files::isRegularFile) // Ensure it's a file, not a directory
                    .filter(p -> extensions.stream()
                            .anyMatch(ext -> p.getFileName().toString().toLowerCase().endsWith(ext.toLowerCase())))
                    .collect(Collectors.toList());
        }
    }
}
