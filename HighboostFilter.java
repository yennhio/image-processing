import java.util.Arrays;
import java.util.Scanner;

public class HighboostFilter {

    int imageWidth, imageHeight;
    int maskSize;
    int[][] imagePixelValues = new int[imageHeight][imageWidth];
    
    public HighboostFilter(int[][] imagePixelValues, int imageWidth, int imageHeight, int maskSize) {
        this.maskSize = maskSize;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }
    
    public int[][] applyMedianFilter(int[][] originalArr) {

        int[][] medianFilteredValues = new int[imageHeight][imageWidth];
        int topRow, bottomRow, leftMostColumn, rightMostColumn;
        int[] numbersArray;
        int median=0;

        for (int y=0; y<originalArr.length; y++) {
            for (int x=0; x<originalArr[0].length; x++) {

                topRow = y-(maskSize/2);
                bottomRow = y+(maskSize/2);
                leftMostColumn = x-(maskSize/2);
                rightMostColumn = x+(maskSize/2);
                int numberOfPixels=0, currentIndex=0;

                for (int j = topRow; j <= bottomRow; j++) {
                    for (int i = leftMostColumn; i <= rightMostColumn; i++) {
                        if (j >= 0 && j < imageHeight && i >= 0 && i < imageWidth) {
                            numberOfPixels++;
                        }
                    }
                }

                numberOfPixels = (bottomRow-topRow+1)*(rightMostColumn-leftMostColumn+1);
                
                numbersArray = new int[numberOfPixels];
                currentIndex=0;

                //store neighborhood pixel values in array to sort
                for (int j = topRow; j <= bottomRow; j++) {
                    for (int i = leftMostColumn; i <= rightMostColumn; i++) {
                        if (j >= 0 && j < imageHeight && i >= 0 && i < imageWidth) {
                            numbersArray[currentIndex] = imagePixelValues[j][i];
                            currentIndex++;
                        }
                    }
                }

                //sort values in array in increasing order
                Arrays.sort(numbersArray);
                if (numbersArray.length%2 == 0)
                    median = numbersArray[numbersArray.length/2] + numbersArray[numbersArray.length/2-1] /2;
                else if (numbersArray.length%2 != 0)
                    median = numbersArray[numbersArray.length / 2];

                medianFilteredValues[y][x] = median;
            }
        }
        return medianFilteredValues;
    }

    public int[][] applyHighboostFilter() {
        int[][] smoothedImageValues = new int[imageHeight][imageWidth];
        smoothedImageValues = applyMedianFilter(imagePixelValues);

        int[][] unsharpMasking = new int[imageHeight][imageWidth];
        
        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {
                unsharpMasking[y][x] = imagePixelValues[y][x] - smoothedImageValues[y][x];
            }
        }

        Scanner myObj = new Scanner(System.in);
        System.out.print("Enter a value for 'a': ");
        int a = myObj.nextInt();

        int[][] highBoostedValues = new int[imageHeight][imageWidth];
        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {
                highBoostedValues[y][x] = imagePixelValues[y][x] + a*unsharpMasking[y][x];
            }
        }

        return highBoostedValues;
    }
}
