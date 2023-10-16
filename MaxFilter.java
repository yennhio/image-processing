
public class MaxFilter {

    int imageWidth, imageHeight;
    int maskSize;
    int[][] imagePixelValues = new int[imageHeight][imageWidth];
    
    public MaxFilter(int[][] imagePixelValues, int imageWidth, int imageHeight, int maskSize) {
        this.maskSize = maskSize;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }

    //no need to pad for mean filter    
    public int[][] applyMaxFilter() {

        int[][] processedImage = new int[imageHeight][imageWidth];
        int topRow, bottomRow, leftMostColumn, rightMostColumn;

        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {

                topRow = y-(maskSize/2);
                bottomRow = y+(maskSize/2);
                leftMostColumn = x-(maskSize/2);
                rightMostColumn = x+(maskSize/2);
                int max=0;

                for (int j = topRow; j <= bottomRow; j++) {
                    for (int i = leftMostColumn; i <= rightMostColumn; i++) {
                        if (j >= 0 && j < imageHeight && i >= 0 && i < imageWidth) {

                            if (imagePixelValues[j][i] > max)
                                max = imagePixelValues[j][i];
                        }
                    }
                }
                
                processedImage[y][x] = max;

            }
        }
        return processedImage;

    }
}
