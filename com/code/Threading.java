package com.code;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

public class Threading implements Runnable {
    private final Path files;
    private final String outputDir;

    public Threading(Path file, String outputDir) {
        this.files = file;
        this.outputDir = outputDir;
    }

    @Override
    public void run() {
        System.out.println("Thread: " + Thread.currentThread().getId() + " working on file " + files);

        try {

            ImageProcessing.scale(
                    java.util.Collections.singletonList(files),
                    outputDir);

            System.out.println("Thumbnail for" + files + " is created");

        } catch (Exception e) {
            System.out.println("Exception is caught: " + e);
        }
    }

}
