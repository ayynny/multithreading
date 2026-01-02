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
        try {

            ImageProcessing.scale(
                    java.util.Collections.singletonList(files),
                    outputDir);
        } catch (Exception e) {
            System.out.println("Exception is caught");
        }
    }
    /*
     * need a thread pool for scanning and image processing
     * 
     * 
     * scanner
     * compare versions of directory to see if there are new files added in, then
     * the new files will be added into the queue
     * - compare current directory vs directory after sleeping for 10s
     * - scan the versions. if the future directory has relevant files that don't
     * match the current, then add those to the queue
     * - use a thread to compare the versions and have this thread always working on
     * looking for new files
     * - first scan the current directory and get the relevant files. then do the
     * comparisons to look for files
     * 
     * processing
     * - use mutex to allow 1 thread at a time to enter the queue and get a file to
     * work on. once the thread gets a file, unlock
     * the queue
     * - can have multiple threads
     * - if there are more tasks than threads, then all threads will be in use and
     * the thread that finishes first will get the new task
     * - idle threads will stay idle until there's a task in the queue
     * 
     */

}
