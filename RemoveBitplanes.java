import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class RemoveBitplanes {

    int imageWidth, imageHeight;
    int maskSize;
    int[][] imagePixelValues = new int[imageHeight][imageWidth];
    
    public RemoveBitplanes(int[][] imagePixelValues, int imageWidth, int imageHeight, int maskSize) {
        this.maskSize = maskSize;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }
    
    
    public void outputImageFile(int[][] processedImagePixelValues) {
        BufferedImage outputImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);

        //set the pixel values in the BufferedImage
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int pixelValue = processedImagePixelValues[y][x];
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

    public int[][] applyBoxFilter(int[][] originalArr) {

        int[][] smoothedImageValues = new int[imageHeight][imageWidth];
        int topRow, bottomRow, leftMostColumn, rightMostColumn;

        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {

                topRow = y-(maskSize/2);
                bottomRow = y+(maskSize/2);
                leftMostColumn = x-(maskSize/2);
                rightMostColumn = x+(maskSize/2);
                int sum=0;
                int numberOfPixels=0;


                if(topRow < 0)
                    topRow = 0;                    
                
                if (bottomRow >= originalArr.length)
                    bottomRow = originalArr.length-1;
                
                if (leftMostColumn < 0)
                    leftMostColumn = 0;

                if (rightMostColumn >= originalArr[0].length)
                    rightMostColumn = originalArr[0].length-1;

                for (int j=topRow; j<=bottomRow; j++) {
                    for (int i=leftMostColumn; i<= rightMostColumn; i++) {
                        sum += originalArr[j][i];
                        numberOfPixels++;
                    }
                }

                smoothedImageValues[y][x] = sum / numberOfPixels;
            }
        }
        return smoothedImageValues;
    }

    public String[][] convertToBinary() {
        String[][] binaryValues = new String[imageHeight][imageWidth];
    
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int number = imagePixelValues[y][x];
                String binaryString = Integer.toBinaryString(number);
    
                // Pad with leading zeros to make it 8 characters long
                while (binaryString.length() < 8) {
                    binaryString = "0" + binaryString;
                }
    
                binaryValues[y][x] = binaryString;
            }
        }
    
        return binaryValues;
    }
    

    public void removeBitplanes() {

        String[][] binaryValues = new String[imageHeight][imageWidth];
        binaryValues = convertToBinary();

        Scanner myObj = new Scanner(System.in);
        System.out.print("Enter bit plane you'd like to remove (1-8 from MSB to LSB): ");
        int bitPlaneToRemove = myObj.nextInt();

        myObj.nextLine();

        String[][] binaryValuesAfterBitPlaneRemoved = new String[imageHeight][imageWidth];
        char[] charArray;
        
        //remove bit plane specified
        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {
                charArray = binaryValues[y][x].toCharArray();
                charArray[bitPlaneToRemove-1] = '0';
                binaryValuesAfterBitPlaneRemoved[y][x] = new String(charArray);
            }
        }

        int[][] finalImageValues = new int[imageHeight][imageWidth];

        //convert String array binaryValuesAfterBitPlaneRemoved to int array
        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {
                String binaryString = binaryValuesAfterBitPlaneRemoved[y][x];
                finalImageValues[y][x] = Integer.parseInt(binaryString, 2);
            }
        }

        outputImageFile(finalImageValues);
    }
}
