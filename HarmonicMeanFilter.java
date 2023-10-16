
public class HarmonicMeanFilter {

    int imageWidth, imageHeight;
    int maskSize;
    int[][] imagePixelValues = new int[imageHeight][imageWidth];
    
    public HarmonicMeanFilter(int[][] imagePixelValues, int imageWidth, int imageHeight, int maskSize) {
        this.maskSize = maskSize;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imagePixelValues = imagePixelValues;
    }

    //no need to pad for mean filter    
    public int[][] applyHarmonicMeanFilter() {

        int[][] processedImage = new int[imageHeight][imageWidth];
        int topRow, bottomRow, leftMostColumn, rightMostColumn;
        double sum;

        for (int y=0; y<imageHeight; y++) {
            for (int x=0; x<imageWidth; x++) {

                topRow = y-(maskSize/2);
                bottomRow = y+(maskSize/2);
                leftMostColumn = x-(maskSize/2);
                rightMostColumn = x+(maskSize/2);
                double numberOfPixels=0;
                sum=0;

                for (int j = topRow; j <= bottomRow; j++) {
                    for (int i = leftMostColumn; i <= rightMostColumn; i++) {
                        if (j >= 0 && j < imageHeight && i >= 0 && i < imageWidth) {

                            if (imagePixelValues[j][i] == 0)
                                sum += 0;
                            else
                                sum += 1.0/(double)imagePixelValues[j][i];

                            numberOfPixels++;
                        }
                    }
                }
                
                processedImage[y][x] = (int) (numberOfPixels/sum);

            }
        }
        return processedImage;

    }
}
