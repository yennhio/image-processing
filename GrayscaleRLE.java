import java.util.ArrayList;
import java.util.List;

public class GrayscaleRLE {
    
    int imageWidth, imageHeight;
    int[][] imagePixelValues;
    
    public GrayscaleRLE(int[][] imagePixelValues, int imageWidth, int imageHeight) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }

    public int[][] applyRLE() {
        List<Integer> encoded = new ArrayList<>();
        int pixelValue=imagePixelValues[0][0], count=1;

        long start = System.currentTimeMillis();

        for (int i=0; i<imageHeight; i++) {
            for (int j=0; j<imageWidth; j++) {
                
                if (imagePixelValues[i][j] == pixelValue) {
                    count++;
                } else {
                    encoded.add(pixelValue);
                    encoded.add(count);
                    pixelValue = imagePixelValues[i][j];
                    count=1;
                }
            }
        }
        long end = System.currentTimeMillis();
        long elapsed = end - start;

        System.out.println("Compression ratio: " + (double)(imageHeight*imageWidth) / (double)encoded.size() + ":1");
        System.out.println("Encoding time: " + elapsed + " milliseconds.");

        start = System.currentTimeMillis();

        int[][] decompressedImg = new int[imageHeight][imageWidth];

        int index = 0;

        for (int i = 0; i < encoded.size(); i += 2) {
            int pixValue = encoded.get(i);
            int length = encoded.get(i + 1);

            for (int j = 0; j < length; j++) {
                decompressedImg[index / imageWidth][index % imageWidth] = pixValue;
                index++;
            }
        }

        end = System.currentTimeMillis();
        elapsed = end - start;

        System.out.println("Decoding time: " + elapsed + " milliseconds.");
        System.out.println("Root mean squared error: " +  calculateRMSE(imagePixelValues, decompressedImg));


        return decompressedImg;

    }


    public double calculateRMSE(int[][] originalImage, int[][] decompressedImage) {
        int height = originalImage.length;
        int width = originalImage[0].length;
    
        double sumSquaredDiff = 0.0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int diff = originalImage[i][j] - decompressedImage[i][j];
                sumSquaredDiff += diff * diff;
            }
        }
        double meanSquaredDiff = sumSquaredDiff / (height * width);
    
        double rmse = Math.sqrt(meanSquaredDiff);
    
        return rmse;
    }

}
