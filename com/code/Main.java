package com.code;

import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // if (args.length < 2){
        // System.out.println("Usage: java ImageProcessor <input-dir> <output-dir>
        // [num-threads]");
        // System.out.println( "Example: java imageProcessor sample-images output 3");
        // }

        String inputDir = args[0];
        String outputDir = args[1];
        int numThreads = args.length > 2 ? Integer.parseInt(args[2]) : 4;

        long start = System.currentTimeMillis();
        DirectoryScanner.scan(inputDir);

        ImageProcessing.createFolder(outputDir);

        List<Path> imgList = DirectoryScanner.getList();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (Path file : imgList) {
            executor.submit(new Threading(file, outputDir));
        }

        try {
            // Specify the directory which supposed to be watched
            Path directoryPath = Paths.get(inputDir);
            // Create a WatchService
            WatchService watchService = directoryPath.getFileSystem().newWatchService();

            // Register the directory for specific events
            directoryPath.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE);

            System.out.println("Watching directory: " + directoryPath.toAbsolutePath());

            // Infinite loop to continuously watch for events
            while (true) {
                WatchKey key = watchService.take();

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context().toAbsolutePath();
                    String f = filename.toString();
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        System.out.println("File created: " + event.context());
                        DirectoryScanner.scan((f));
                        if (imgList.size() > 0) {
                            for (Path file : imgList) {
                                executor.submit(new Threading(file, outputDir));
                            }
                        }

                    }
                }
                key.reset();

            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();

        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("Time to process:" + time + " with # threads:" + numThreads);

        // System.out.println(imgList);
        // ImageProcessing.scale(imgList, outputDir);
    }

}