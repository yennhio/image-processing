import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Test {
    public static void main(String[] args) {
        try {
            // Load the input image
            BufferedImage inputImage = ImageIO.read(new File("moon.png"));
            
            // Perform histogram equalization
            BufferedImage outputImage = equalizeImage(inputImage);
            
            // Save the output image
            ImageIO.write(outputImage, "jpg", new File("outputTest.jpg"));
            
            System.out.println("Histogram equalization completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static BufferedImage equalizeImage(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        
        // Create an array to store the histogram
        int[] histogram = new int[256];
        
        // Calculate the histogram
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = inputImage.getRGB(x, y);
                int grayValue = (pixel >> 16) & 0xFF; // Extract the red component (assuming grayscale image)
                histogram[grayValue]++;
            }
        }

        
        // Calculate the cumulative histogram
        int[] cumulativeHistogram = new int[256];
        cumulativeHistogram[0] = histogram[0];
        for (int i = 1; i < 256; i++) {
            cumulativeHistogram[i] = cumulativeHistogram[i - 1] + histogram[i];
        }
        
        // Calculate the mapping function
        int[] mappingFunction = new int[256];
        int totalPixels = width * height;
        for (int i = 0; i < 256; i++) {
            mappingFunction[i] = (int) (255.0 * cumulativeHistogram[i] / totalPixels + 0.5);
        }

        for (int i=200; i<220; i++) {
            System.out.print(mappingFunction[i] + " ");
        }
        
        // Create the output image
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        // Apply the mapping function to each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = inputImage.getRGB(x, y);
                int grayValue = (pixel >> 16) & 0xFF; // Extract the red component (assuming grayscale image)
                int newGrayValue = mappingFunction[grayValue];
                int newPixel = (newGrayValue << 16) | (newGrayValue << 8) | newGrayValue;
                outputImage.setRGB(x, y, newPixel);
            }
        }
        
        return outputImage;
    }}