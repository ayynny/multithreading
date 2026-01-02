# Day 3-5: Multi-threaded Image Processor

## 🎯 Project Goal
Build a multi-threaded application that processes images concurrently to create thumbnails and extract metadata.

## 📋 Requirements

### Core Functionality
1. **Directory Scanning**
   - Recursively scan a directory for image files (.jpg, .png, .gif, .bmp)
   - Ignore non-image files
   - Collect all valid image paths

2. **Image Processing** (for each image):
   - Create thumbnail (200x200 pixels, maintaining aspect ratio)
   - Extract metadata:
     - Original dimensions (width x height)
     - File size in KB
     - Image format
     - Modification date
   - Save thumbnail to `output/thumbnails/` directory
   - Calculate file checksum (MD5 or SHA-256)

3. **Concurrent Processing**
   - Use `ExecutorService` with configurable thread pool size
   - Implement producer-consumer pattern:
     - Scanner thread: Finds image files
     - Worker threads: Process images
   - Use thread-safe queues for work distribution

4. **Statistics & Reporting**
   - Total files found
   - Total files processed successfully
   - Total failures (with error details)
   - Total processing time
   - Average time per file
   - Throughput (files per second)

5. **Progress Tracking**
   - Show real-time progress as files are processed
   - Display which thread is processing which file

### Example Output
```
Scanning directory: sample-images/
Found 8 image files

Processing with 4 worker threads...
[Worker-1] Processing: photo1.jpg (2048 KB)
[Worker-2] Processing: photo2.png (3072 KB)
[Worker-1] ✓ Completed: photo1.jpg (250ms) -> thumbnail saved
[Worker-3] Processing: vacation/beach1.jpg (2560 KB)
[Worker-2] ✓ Completed: photo2.png (340ms) -> thumbnail saved
[Worker-4] Processing: photo3.jpg (1536 KB)
[Worker-3] ✓ Completed: vacation/beach1.jpg (280ms) -> thumbnail saved
[Worker-1] Processing: vacation/beach2.jpg (1843 KB)
[Worker-4] ✓ Completed: photo3.jpg (220ms) -> thumbnail saved
[Worker-2] Processing: photo4.jpg (4096 KB)
[Worker-1] ✓ Completed: vacation/beach2.jpg (260ms) -> thumbnail saved
[Worker-3] Processing: large-photo.jpg (5120 KB)
[Worker-2] ✓ Completed: photo4.jpg (410ms) -> thumbnail saved
[Worker-4] ✗ Failed: corrupted.jpg (Error: Invalid image format)
[Worker-3] ✓ Completed: large-photo.jpg (520ms) -> thumbnail saved

=== Processing Summary ===
Total files found: 8
Successfully processed: 7
Failed: 1
Total processing time: 2.85s
Average time per file: 407ms
Throughput: 2.46 files/sec

Output directory: output/thumbnails/
Metadata saved to: output/metadata.txt
```

## 🤔 Design Challenges

Before coding, think about these questions:

1. **Architecture**
   - What classes do you need?
   - How will components communicate?
   - Where should responsibilities be divided?

2. **Work Distribution**
   - How to represent a work item?
   - How to pass work from scanner to workers?
   - What type of queue should you use?
   - How does the scanner signal "no more work"?

3. **Thread Management**
   - How many threads should you use?
   - How to configure pool size?
   - When to start and stop threads?
   - How to ensure graceful shutdown?

4. **Thread Safety**
   - What data is shared between threads?
   - Which collections need to be thread-safe?
   - How to aggregate results safely?
   - Where do you need synchronization?

5. **Error Handling**
   - What if an image is corrupted?
   - What if a worker thread crashes?
   - Should one failure stop all processing?
   - How to collect and report errors?

6. **Progress Tracking**
   - How to show progress from multiple threads?
   - How to avoid garbled console output?
   - How to track completion percentage?

## 📂 Sample Data

`sample-images/` contains:
- 6 valid test images (various sizes)
- 1 subdirectory with 2 more images
- 1 text file (should be ignored)

Total: 8 images to process

## 🚀 Getting Started

### Suggested Implementation Order

1. **Phase 1: Basic Structure**
   - Implement directory scanning (single-threaded)
   - Test with sample data
   - Print found files

2. **Phase 2: Simple Processing**
   - Implement image thumbnail creation
   - Process files sequentially (no threading yet)
   - Test output quality

3. **Phase 3: Add Threading**
   - Introduce ExecutorService
   - Implement producer-consumer pattern
   - Test with multiple threads

4. **Phase 4: Statistics & Reporting**
   - Track processing metrics
   - Aggregate results safely
   - Generate final report

5. **Phase 5: Polish**
   - Add progress indicators
   - Improve error handling
   - Add metadata extraction

## 📚 Java Concepts to Learn

### Threading
- `ExecutorService` and `Executors`
- `ThreadPoolExecutor` configuration
- `Future` and `Callable`
- Thread lifecycle and shutdown

### Concurrency
- `BlockingQueue` (ArrayBlockingQueue, LinkedBlockingQueue)
- `CountDownLatch` or `Phaser` for coordination
- `AtomicInteger` for counters
- `ConcurrentHashMap` for thread-safe maps
- `Collections.synchronizedList()` vs concurrent collections

### I/O & Images
- `java.nio.file.Files` and `Paths`
- `javax.imageio.ImageIO`
- `java.awt.image.BufferedImage`
- `Graphics2D` for image manipulation

### Best Practices
- Try-with-resources for `ExecutorService`
- Proper exception handling in threads
- Avoiding race conditions
- Graceful shutdown patterns

## ✨ Bonus Features (Optional)

- **Configurable thumbnail size**: Accept size as command-line argument
- **Multiple output formats**: Save as JPG, PNG, WebP
- **Image filters**: Apply grayscale, sepia, blur effects
- **Comparison mode**: Compare single vs multi-threaded performance
- **EXIF data extraction**: Read camera info, GPS, timestamp
- **Progress bar**: Show visual progress indicator
- **Batch processing**: Process multiple directories
- **Resume capability**: Skip already-processed files

## 🧪 Testing Ideas

1. **Small dataset**: 3-5 images, verify correctness
2. **Large dataset**: 100+ images, measure performance
3. **Different thread counts**: Compare 1, 2, 4, 8 threads
4. **Error conditions**: Corrupt images, missing permissions
5. **Edge cases**: Empty directory, very large images, nested folders

## ✅ Success Criteria

Your implementation should:
- ✅ Successfully process all valid images
- ✅ Use thread pool for concurrent processing
- ✅ Implement producer-consumer pattern correctly
- ✅ Handle errors gracefully without crashing
- ✅ Aggregate results from multiple threads safely
- ✅ Shutdown cleanly (no zombie threads)
- ✅ Show performance benefits of multi-threading
- ✅ Generate correct thumbnails and metadata

## 💡 Common Pitfalls to Avoid

- **Race conditions**: Not synchronizing shared data
- **Deadlocks**: Improper locking order
- **Resource leaks**: Forgetting to shutdown ExecutorService
- **Exception swallowing**: Catching exceptions in worker threads but not reporting
- **Busy waiting**: Using `while(true)` loops instead of blocking queues
- **Memory issues**: Loading all images into memory at once

Good luck! This project will teach you the fundamentals of concurrent programming in Java.
