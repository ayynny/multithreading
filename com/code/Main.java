package com.code;

import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
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

        List<Path> imgList = DirectoryScanner.getList();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (Path file : imgList) {
            executor.submit(new Threading(file, outputDir));
        }

        executor.shutdown();

        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("Time to process:" + time + " with # threads:" + numThreads);

        // System.out.println(imgList);
        // ImageProcessing.scale(imgList, outputDir);
    }

}