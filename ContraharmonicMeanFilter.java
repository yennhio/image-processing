import java.util.Scanner;

public class ContraharmonicMeanFilter {

    int imageWidth, imageHeight;
    int maskSize;
    int[][] imagePixelValues = new int[imageHeight][imageWidth];
    
    public ContraharmonicMeanFilter(int[][] imagePixelValues, int imageWidth, int imageHeight, int maskSize) {
        this.maskSize = maskSize;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }

    //no need to pad for mean filter    
    public int[][] applyContraharmonicMeanFilter() {

        int[][] processedImage = new int[imageHeight][imageWidth];
        int topRow, bottomRow, leftMostColumn, rightMostColumn;

        Scanner myObj = new Scanner(System.in);

        System.out.println("Enter a value for q: ");
        double q = myObj.nextDouble();

        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {

                topRow = y-(maskSize/2);
                bottomRow = y+(maskSize/2);
                leftMostColumn = x-(maskSize/2);
                rightMostColumn = x+(maskSize/2);
                double numberOfPixels=0;
                double numerator=0, denominator=0;

                for (int j = topRow; j <= bottomRow; j++) {
                    for (int i = leftMostColumn; i <= rightMostColumn; i++) {
                        if (j >= 0 && j < imageHeight && i >= 0 && i < imageWidth) {

                            numerator += Math.pow((double)imagePixelValues[j][i], q+1);
                            denominator += Math.pow((double)imagePixelValues[j][i], q);
                
                            numberOfPixels++;
                        }
                    }
                }
                
                processedImage[y][x] = (int)(numerator/denominator);

            }
        }
        return processedImage;

    }
}
