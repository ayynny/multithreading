/**
 * Multi-threaded Image Processor - Starter Template
 * 
 * This is a minimal starting point. You need to design and implement:
 * - Image scanning logic
 * - Thread pool configuration
 * - Work queue management
 * - Image processing (thumbnail creation)
 * - Statistics collection
 * 
 * Think about:
 * - What classes do you need?
 * - How will threads communicate?
 * - How to handle thread-safe operations?
 */

public class ImageProcessor {
    
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java ImageProcessor <input-dir> <output-dir> [num-threads]");
            System.out.println("Example: java ImageProcessor sample-images output 4");
            return;
        }
        
        String inputDir = args[0];
        String outputDir = args[1];
        int numThreads = args.length > 2 ? Integer.parseInt(args[2]) : 4;
        
        System.out.println("Image Processor");
        System.out.println("Input directory: " + inputDir);
        System.out.println("Output directory: " + outputDir);
        System.out.println("Worker threads: " + numThreads);
        System.out.println();
        
        // TODO: Your implementation here
        // 1. How will you scan for image files?
        // 2. How will you create a thread pool?
        // 3. How will you distribute work to threads?
        // 4. How will you process images?
        // 5. How will you collect statistics?
        
        // Your code goes here...
    }
}
