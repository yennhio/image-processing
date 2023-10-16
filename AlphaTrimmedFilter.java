import java.util.Arrays;
import java.util.Scanner;

public class AlphaTrimmedFilter {

    int imageWidth, imageHeight;
    int maskSize;
    int[][] imagePixelValues = new int[imageHeight][imageWidth];
    
    public AlphaTrimmedFilter(int[][] imagePixelValues, int imageWidth, int imageHeight, int maskSize) {
        this.maskSize = maskSize;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }

    //no need to pad for mean filter    
    public int[][] applyAlphaTrimmedMeanFilter() {

        int[][] processedImage = new int[imageHeight][imageWidth];
        int topRow, bottomRow, leftMostColumn, rightMostColumn;
        double sum;

        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter value for d: ");
        double d = myObj.nextDouble();

        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {

                topRow = y-(maskSize/2);
                bottomRow = y+(maskSize/2);
                leftMostColumn = x-(maskSize/2);
                rightMostColumn = x+(maskSize/2);
                sum=0;

                int[] neighborhoodValuesToSort = new int[maskSize*maskSize];
                int arrIndex=0;

                for (int j = topRow; j <= bottomRow; j++) {
                    for (int i = leftMostColumn; i <= rightMostColumn; i++) {
                        if (j >= 0 && j < imageHeight && i >= 0 && i < imageWidth) {

                            neighborhoodValuesToSort[arrIndex] = imagePixelValues[j][i];
                            arrIndex++;

                        }
                    }
                }

                Arrays.sort(neighborhoodValuesToSort);

                for (int j=0+(int)d/2; j<=neighborhoodValuesToSort.length-(int)d/2; j++) {
                    sum += neighborhoodValuesToSort[j];
                }

                processedImage[y][x] = (int)(sum/((maskSize*maskSize)-d));
            }
        }
        return processedImage;

    }
}
