import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HistEqualization {

    int imageWidth, imageHeight;
    int maskSize;
    int[][] imagePixelValues = new int[imageHeight][imageWidth];

    public HistEqualization(int[][] imagePixelValues, int imageWidth, int imageHeight, int maskSize) {
        this.maskSize = maskSize;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }

    public void outputImageFile(int[][] processedImagePixelValues){
        BufferedImage outputImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);

        // set the pixel values in the BufferedImage
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int newGrayValue = processedImagePixelValues[y][x];
                int newPixel = (newGrayValue << 16) | (newGrayValue << 8) | newGrayValue;
                outputImage.setRGB(x, y, newPixel);
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

    public void globalEquilization(int[][] originalArr) {
        int[][] equalizedArray = new int[imageHeight][imageWidth];
        int[] histogram = new int[256];
        int[] equalizedHistogram = new int[256];

        double MN = imageHeight*imageWidth;

        //count number of pixels with a certain value
        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {
                histogram[originalArr[y][x]]++;
            }
        }

        //perform equalization
        int sum = 0;
        for (int i = 0; i < 256; i++) {
            sum += histogram[i];
            equalizedHistogram[i] = (int) Math.round(255.0 * (double) sum / MN);
        }

        //apply equalized values to old image array
            for (int y=0; y<imageHeight; y++) {
                for (int x=0; x<imageWidth; x++) {
                    int grayscaleVal = originalArr[y][x];
                    equalizedArray[y][x] = equalizedHistogram[grayscaleVal];
                }
            }
        for (int i=200; i<220; i++) {
            System.out.print(equalizedHistogram[i] + " ");
        }        

        outputImageFile(equalizedArray);
    }

    public void localEqualization(int[][] originalArr) {
        int[][] equalizedArray = new int[imageHeight][imageWidth];
        int[] neighborhood = new int[maskSize*maskSize];
        int[] equalizedNeighborhood = new int[256];

        int firstX=0, firstY=0, lastX=firstX+maskSize-1, lastY= firstY+maskSize-1;

        while (lastY < originalArr.length){
            firstX=0;
            lastX=firstX+maskSize-1;

            while (lastX < originalArr[0].length) {
                int neighborhoodIndex=0;

                //store values of neighborhood in separate array for local processing
                for (int y=firstY; y<=lastY; y++) {
                    for (int x=firstX; x<=lastX; x++) {
                        neighborhood[neighborhoodIndex] = originalArr[y][x];
                        neighborhoodIndex++;
                    }
                }

                int centralY = maskSize/2+firstY;
                int centralX = maskSize/2+firstX;

                equalizedNeighborhood = equalize(neighborhood);
                int centralVal = originalArr[centralY][centralX];
                equalizedArray[centralY][centralX] = equalizedNeighborhood[centralVal]; 

                firstX++;
                lastX=firstX+maskSize-1;
            }
            firstY++;
            lastY= firstY+maskSize-1;
        }        

        outputImageFile(equalizedArray);
    }

    public int[] equalize(int[] neighborhoodValues) {
        int[] equalizedHistogram = new int[256];
        int[] histogram =  new int[256];
        float MN = maskSize*maskSize;

        //count number of pixels with a certain value
        for (int y=0; y<maskSize; y++) {
            histogram[neighborhoodValues[y]]++;
        }
        
        //perform equalization
        int sum=0;
        for (int i=0; i<256; i++) {
            sum += histogram[i];
            equalizedHistogram[i] = (int) Math.ceil(255.0*(float)sum/MN+0.5);
        }

        return equalizedHistogram;
    }
        
}