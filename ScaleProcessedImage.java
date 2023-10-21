import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ScaleProcessedImage {

    int imageWidth, imageHeight;
    int[][] imagePixelValues = new int[imageHeight][imageWidth];
    int[][] processedImagePixelValues = new int[imageHeight][imageWidth];
    
    public ScaleProcessedImage(int[][] imagePixelValues, int imageWidth, int imageHeight, int[][] processedImagePixelValues) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
        this.processedImagePixelValues = processedImagePixelValues;
    }

    public int findNewMin() {
        int min = processedImagePixelValues[0][0];

        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {
                if (processedImagePixelValues[y][x] < min)
                    min = processedImagePixelValues[y][x];
            }
        }
                System.out.println(min);

        return min;
    }

    public int findNewMax() {
        int max = processedImagePixelValues[0][0];

        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {
                if (processedImagePixelValues[y][x] > max)
                    max = processedImagePixelValues[y][x];
            }
        }
                System.out.println(max);

        return max;
    }

    public int[][] scaleValues() {
        int oldMin = findNewMin();
        int oldMax = findNewMax();
        int[][] scaledValues = new int[imageHeight][imageWidth];

        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {
                scaledValues[y][x] = (int)(((double)(processedImagePixelValues[y][x] - oldMin) / ((double)(oldMax - oldMin) / 255.0)));
            }
        }
        return scaledValues;
    }
    
    public void outputImageFile() {
        BufferedImage outputImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);

        int[][] scaledValues = new int[imageHeight][imageWidth];
        scaledValues = scaleValues();

        //set the pixel values in the BufferedImage
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int pixelValue = scaledValues[y][x];
                int grayColor = (pixelValue << 16) | (pixelValue << 8) | pixelValue;
                outputImage.setRGB(x, y, grayColor);
            }
        }

        //save the BufferedImage as a JPG file
        File outputFile = new File("output.jpg");
        try {
            ImageIO.write(outputImage, "jpg", outputFile);
            System.out.println("JPEG image created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}