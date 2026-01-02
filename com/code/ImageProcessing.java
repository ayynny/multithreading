package com.code;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ListIterator;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageProcessing {

    public static void scale(List<Path> input, String outputPath) throws IOException {
        ListIterator<Path> directoryIterator = input.listIterator();

        Path destinationFolder = Paths.get(outputPath);
        try {
            Files.createDirectory(destinationFolder);
            System.out.println("Directory created successfully at: " + destinationFolder.toAbsolutePath());
        } catch (FileAlreadyExistsException e) {
            // System.out.println("Directory already exists: " +
            // destinationFolder.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to create directory: " + e.getMessage());
            e.printStackTrace();
        }

        while (directoryIterator.hasNext()) {
            Path currentImg = directoryIterator.next();
            File file = currentImg.toFile();
            String thumbnailName = "thumbnail_" + file.getName();
            try {
                BufferedImage originalImage = ImageIO.read((file));
                if (originalImage != null) {
                    // System.out.println("Sucessfuly read image: " + file.getName());
                }

                Image scaledImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                BufferedImage thumbnail = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
                thumbnail.getGraphics().drawImage(scaledImage, 0, 0, null);

                Path thumbnailPath = destinationFolder.resolve(thumbnailName);
                ImageIO.write(thumbnail, "png", thumbnailPath.toFile());

                // System.out.println("Thumbnail created");
            } catch (IOException e) {
                System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
            }

        }

    }
}
